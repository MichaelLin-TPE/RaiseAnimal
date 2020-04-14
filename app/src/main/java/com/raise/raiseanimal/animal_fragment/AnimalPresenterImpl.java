package com.raise.raiseanimal.animal_fragment;

import android.util.Log;

import androidx.annotation.VisibleForTesting;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raise.raiseanimal.connect.HttpConnection;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;
import java.util.List;

public class AnimalPresenterImpl implements AnimalPresenter{

    private AnimalVu mView;

    private Gson gson;

    private ArrayList<AnimalObject> catchFirebaseArray;

    private boolean isDataChange = true;

    private ArrayList<AnimalObject> catchArray;

    private ArrayList<AnimalObject> dataArray;


    public AnimalPresenterImpl(AnimalVu mView) {
        this.mView = mView;
        gson = new Gson();
    }

    @VisibleForTesting
    public void setOnCatchArray (ArrayList<AnimalObject> catchArray){
        this.catchArray = catchArray;
    }

    @VisibleForTesting
    public void setDataArray (ArrayList<AnimalObject> dataArray){
        this.dataArray = dataArray;
    }

    @Override
    public void startToCatchData() {
        //背景做
        HttpConnection connection = new HttpConnection();
        connection.execute("https://data.coa.gov.tw/Service/OpenData/TransService.aspx?UnitId=QcbUEzN6E6DL");
        connection.setOnHttpConnectionListener(new HttpConnection.OnHttpConnectionListener() {
            @Override
            public void onSuccess(String result) {
                Log.i("Michael","success : "+result);
                catchArray = gson.fromJson(result,new TypeToken<List<AnimalObject>>(){}.getType());
                if (catchArray != null && catchArray.size() != 0){
                    Log.i("Michael","地址 : "+catchArray.get(0).getShelterAddress());
                    dataArray = new ArrayList<>();
                    for (AnimalObject object : catchArray){
                        if (object.getShleterName().equals(mView.getPlaceString()) && object.getAnimalKind().equals(mView.getDogStr())){
                            dataArray.add(object);
                        }
                    }
                    //資料互相比對 若 ID 搜尋不到就新增到FIREBASE的資料
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("Michael","catchFirebaseArraySize : "+catchFirebaseArray.size() +" dataArraySize : "+dataArray.size());

                            if (dataArray.size() != catchFirebaseArray.size()){
                                for (int i = 0 ; i < catchFirebaseArray.size() ; i ++){
                                    for (int j = 0 ; j < catchFirebaseArray.size() ; j++){
                                        if (dataArray.get(i).getAnimalId() == catchFirebaseArray.get(j).getAnimalId()){
                                            isDataChange = false;
                                            break;
                                        }
                                    }
                                    if (isDataChange){
                                        Log.i("Michael","狗狗資料新增");
                                        catchFirebaseArray.add(dataArray.get(i));
                                    }
                                }
                                String jsonStr = gson.toJson(catchFirebaseArray);
                                Log.i("Michael","更新過後的Json : "+jsonStr);
                                mView.saveJsonToFirebase(jsonStr);
                            }else {
                                Log.i("Michael","長度一樣不須新增");
                            }
                        }
                    }).start();


                }
            }

            @Override
            public void onFailure(String errorCode) {
                Log.i("Michael",errorCode);
            }
        });
    }


    @Override
    public void catchData(ArrayList<AnimalObject> dataArray) {
        if (dataArray != null){
            this.catchFirebaseArray = dataArray;
            Log.i("Michael","json != null");
            mView.showProgress(false);
            mView.setRecyclerView(dataArray);
            startToCatchData();
        }else {
            Log.i("Michael","json == null");
            startToCatchData();
        }
    }

    @Override
    public void onShowProgress(boolean isShow) {
        mView.showProgress(isShow);
    }

    @Override
    public void catchNewData(ArrayList<AnimalObject> dataArray) {
        if (dataArray != null){
            this.catchFirebaseArray = dataArray;
            mView.showProgress(false);
            mView.setRecyclerView(dataArray);
        }
    }

    @Override
    public void onAnimalItemClickListener(AnimalObject data) {
        if (data != null){
            mView.intentToDetailPage(data);
        }
    }
}
