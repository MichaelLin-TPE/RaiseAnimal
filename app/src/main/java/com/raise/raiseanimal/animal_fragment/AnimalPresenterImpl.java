package com.raise.raiseanimal.animal_fragment;

import android.util.Log;

import androidx.annotation.VisibleForTesting;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raise.raiseanimal.connect.HttpConnection;
import com.raise.raiseanimal.connect.gson_object.AnimalNewObject;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;
import java.util.List;

public class AnimalPresenterImpl implements AnimalPresenter {

    private AnimalVu mView;

    private Gson gson;

    private ArrayList<AnimalObject> catchFirebaseArray;

    private boolean isDataChange = true;

    private ArrayList<AnimalNewObject> catchArray;

    private ArrayList<AnimalNewObject> dataArray;

    private String sex, noSex, size, color;

    private ArrayList<AnimalObject> filterArray;

    boolean isSexAll,isNoSexAll,isSizeAll,isColorAll;


    public AnimalPresenterImpl(AnimalVu mView) {
        this.mView = mView;
        gson = new Gson();
        sex = "全部";
        noSex = "全部";
        size = "全部";
        color = "全部";
        isSexAll = true;
        isNoSexAll = true;
        isSizeAll = true;
        isColorAll = true;
    }

    @VisibleForTesting
    public void setOnCatchArray(ArrayList<AnimalNewObject> catchArray) {
        this.catchArray = catchArray;
    }

    @VisibleForTesting
    public void setDataArray(ArrayList<AnimalNewObject> dataArray) {
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
                Log.i("Michael", "success : " + result);
                catchArray = gson.fromJson(result, new TypeToken<List<AnimalNewObject>>() {
                }.getType());
                if (catchArray != null && catchArray.size() != 0) {
                    Log.i("Michael", "地址 : " + catchArray.get(0).getShelterAddress());
                    dataArray = new ArrayList<>();
                    for (AnimalNewObject object : catchArray) {
                        if (object.getShleterName().equals(mView.getPlaceString()) && object.getAnimalKind().equals(mView.getDogStr())) {
                            dataArray.add(object);
                        }
                    }
                    //資料互相比對 若 ID 搜尋不到就新增到FIREBASE的資料
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("Michael", "catchFirebaseArraySize : " + catchFirebaseArray.size() + " dataArraySize : " + dataArray.size());

                            if (dataArray.size() != catchFirebaseArray.size() && catchFirebaseArray.size() < dataArray.size()) {
                                for (int i = 0; i < dataArray.size(); i++) {
                                    for (int j = 0; j < catchFirebaseArray.size(); j++) {
                                        if (dataArray.get(i).getAnimalSubid().equals(catchFirebaseArray.get(j).getAnimalId())) {
                                            isDataChange = false;
                                            break;
                                        }
                                    }
                                    if (isDataChange) {
                                        Log.i("Michael", "狗狗資料新增");
                                        AnimalObject object = new AnimalObject();
                                        object.setAlbumFile(dataArray.get(i).getAlbumFile());
                                        object.setShleterName(dataArray.get(i).getShleterName());
                                        object.setAnimalBodyType(dataArray.get(i).getAnimalBodyType());
                                        object.setAnimalSex(dataArray.get(i).getAnimalSex());
                                        object.setAnimalSterilization(dataArray.get(i).getAnimalSterilization());
                                        object.setAnimalFoundPlace(dataArray.get(i).getAnimalFoundPlace());
                                        object.setAnimalTitle(dataArray.get(i).getAnimalTitle());
                                        object.setAnimalColour(dataArray.get(i).getAnimalColour());
                                        object.setAnimalId(dataArray.get(i).getAnimalSubid());
                                        object.setAnimalKind(dataArray.get(i).getAnimalKind());
                                        object.setStory("");
                                        object.setPersonality(new ArrayList<String>());
                                        catchFirebaseArray.add(object);
                                    }
                                }
                                String jsonStr = gson.toJson(catchFirebaseArray);
                                Log.i("Michael", "更新過後的Json : " + jsonStr);
                                mView.saveJsonToFirebase(jsonStr);
                            } else {
                                Log.i("Michael", "長度一樣不須新增");
                            }
                        }
                    }).start();


                }
            }

            @Override
            public void onFailure(String errorCode) {
                Log.i("Michael", errorCode);
            }
        });
    }


    @Override
    public void catchData(ArrayList<AnimalObject> dataArray) {
        if (dataArray != null) {
            //這邊顯示有多少動物
            this.catchFirebaseArray = dataArray;
            Log.i("Michael","擷取後的數字 : "+dataArray.get(0).getAnimalId().substring(5));
            Log.i("Michael", "json != null");
            mView.showProgress(false);
            mView.setRecyclerView(dataArray);
            startToCatchData();

            //這邊顯示篩選
            ArrayList<String> colorArray = new ArrayList<>();
            ArrayList<String> noSexArray = new ArrayList<>();
            ArrayList<String> sexArray = new ArrayList<>();
            ArrayList<String> sizeArray = new ArrayList<>();
            noSexArray.add("結育類");
            noSexArray.add("全部");
            noSexArray.add("已結育");
            noSexArray.add("未結育");
            sexArray.add("性別類");
            sexArray.add("全部");
            sexArray.add("公");
            sexArray.add("母");
            sizeArray.add("體型類");
            sizeArray.add("全部");
            sizeArray.add("大型");
            sizeArray.add("中型");
            sizeArray.add("小型");
            colorArray.add("顏色類");
            colorArray.add("全部");
            for (AnimalObject data : dataArray) {
                if (colorArray.size() == 0) {
                    colorArray.add(data.getAnimalColour());
                } else {
                    boolean isEqual = false;
                    for (String name : colorArray) {
                        if (name.equals(data.getAnimalColour())) {
                            isEqual = true;
                        }
                    }
                    if (!isEqual) {
                        colorArray.add(data.getAnimalColour());
                    }
                }
            }
            mView.showFilterView(colorArray, noSexArray, sexArray, sizeArray);

        } else {
            Log.i("Michael", "json == null");
            startToCatchData();
        }
    }

    @Override
    public void onShowProgress(boolean isShow) {
        mView.showProgress(isShow);
    }

    @Override
    public void catchNewData(ArrayList<AnimalObject> dataArray) {
        if (dataArray != null) {
            this.catchFirebaseArray = dataArray;
            mView.showProgress(false);
            mView.setRecyclerView(dataArray);
        }
    }

    @Override
    public void onAnimalItemClickListener(AnimalObject data) {
        if (data != null) {
            mView.intentToDetailPage(data);
        }
    }

    @Override
    public void onFilterItemClickListener(String name, String value) {

        filterArray = new ArrayList<>();
        if (name.contains("公") || name.contains("母")) {
            if (name.equals("公")) {
                sex = "M";
            } else {
                sex = "F";
            }
            isSexAll = false;
        } else if (name.equals("已結育") || name.equals("未結育")) {
            if (name.equals("已結育")) {
                noSex = "T";
            } else {
                noSex = "F";
            }
            isNoSexAll = false;
        } else if (name.contains("型")) {
            if (name.equals("大型")) {
                size = "BIG";
            } else if (name.equals("中型")) {
                size = "MEDIUM";
            } else {
                size = "SMALL";
            }
            isSizeAll = false;
        } else if (name.contains("色")) {
            color = name;
            isColorAll = false;
        }
        if (name.equals("全部") && value.equals("性別類")){
            isSexAll = true;
        }
        if (name.equals("全部") && value.equals("結育類")){
            isNoSexAll = true;
        }

        if (name.equals("全部") && value.equals("體型類")){
            isSizeAll = true;
        }

        if (name.equals("全部") && value.equals("顏色類")){
            isColorAll = true;
        }


        Log.i("Michael", "性別 : " + sex + " , 節育 : " + noSex + " , 形體 : " + size + " , 顏色 : " + color);

        for (AnimalObject data : catchFirebaseArray) {
            if (sex.equals(data.getAnimalSex()) &&
                    noSex.equals(data.getAnimalSterilization()) &&
                    size.equals(data.getAnimalBodyType()) &&
                    color.equals(data.getAnimalColour())) {
                filterArray.add(data);
            }
            //1
            if (isSexAll && !isNoSexAll && !isSizeAll && !isColorAll){
                if (noSex.equals(data.getAnimalSterilization()) &&
                        size.equals(data.getAnimalBodyType()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //12
            if (isSexAll && isNoSexAll && !isSizeAll && !isColorAll){
                if (size.equals(data.getAnimalBodyType()) &&
                    color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }

            }
            //123
            if (isSexAll && isNoSexAll && isSizeAll && !isColorAll){
                if (color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //13
            if (isSexAll && !isNoSexAll && isSizeAll && !isColorAll){
                if (noSex.equals(data.getAnimalSterilization()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //14
            if (isSexAll && !isNoSexAll && !isSizeAll && isColorAll){
                if (noSex.equals(data.getAnimalSterilization()) &&
                        size.equals(data.getAnimalBodyType())) {
                    filterArray.add(data);
                }
            }
            //124
            if (isSexAll && isNoSexAll && !isSizeAll && isColorAll){
                if (size.equals(data.getAnimalBodyType())) {
                    filterArray.add(data);
                }
            }
            //134
            if (isSexAll && !isNoSexAll && isSizeAll && isColorAll){
                if (noSex.equals(data.getAnimalSterilization())) {
                    filterArray.add(data);
                }
            }

            //2
            if (!isSexAll && isNoSexAll && !isSizeAll && !isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        size.equals(data.getAnimalBodyType()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //23
            if (!isSexAll && isNoSexAll && isSizeAll && !isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //24
            if (!isSexAll && isNoSexAll && !isSizeAll && isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        size.equals(data.getAnimalBodyType())) {
                    filterArray.add(data);
                }
            }
            //234
            if (!isSexAll && isNoSexAll && isSizeAll && isColorAll){
                if (sex.equals(data.getAnimalSex())) {
                    filterArray.add(data);
                }
            }
            //3
            if (!isSexAll && !isNoSexAll && isSizeAll && !isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        noSex.equals(data.getAnimalSterilization()) &&
                        color.equals(data.getAnimalColour())) {
                    filterArray.add(data);
                }
            }
            //34
            if (!isSexAll && !isNoSexAll && isSizeAll && isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        noSex.equals(data.getAnimalSterilization())) {
                    filterArray.add(data);
                }
            }
            //4
            if (!isSexAll && !isNoSexAll && !isSizeAll && isColorAll){
                if (sex.equals(data.getAnimalSex()) &&
                        noSex.equals(data.getAnimalSterilization()) &&
                        size.equals(data.getAnimalBodyType())) {
                    filterArray.add(data);
                }
            }
        }
        if (isColorAll && isSizeAll && isNoSexAll && isSexAll){
            mView.setRecyclerView(catchFirebaseArray);
        }else {
            mView.setRecyclerView(filterArray);
            if (filterArray.size() != 0){
                mView.showSearchNoData(false);
            }else {
                mView.showSearchNoData(true);
            }
        }
    }

    @Override
    public void onOpenFilterClickListener(boolean isOpenFilter) {
        Log.i("Michael","isOpenFilter : "+isOpenFilter);
        if (!isOpenFilter){
            mView.openFilterView(true);
        }else {
            mView.openFilterView(false);
        }

    }

    @Override
    public void onFavoriteIconClickListener(AnimalObject data) {
        mView.saveUserFavoriteData(data);
    }

    @Override
    public void catchNoData() {
        HttpConnection connection = new HttpConnection();
        connection.execute("https://data.coa.gov.tw/Service/OpenData/TransService.aspx?UnitId=QcbUEzN6E6DL");
        connection.setOnHttpConnectionListener(new HttpConnection.OnHttpConnectionListener() {
            @Override
            public void onSuccess(String result) {
                catchFirebaseArray = new ArrayList<>();
                ArrayList<AnimalNewObject> dataArray = gson.fromJson(result,new TypeToken<List<AnimalNewObject>>(){}.getType());
                if (dataArray != null){
                    for (AnimalNewObject data : dataArray){
                        if (data.getShleterName().equals("新北市板橋區公立動物之家") && data.getAnimalKind().equals("狗")){
                            AnimalObject object = new AnimalObject();
                            object.setAlbumFile(data.getAlbumFile());
                            object.setShleterName(data.getShleterName());
                            object.setAnimalBodyType(data.getAnimalBodyType());
                            object.setAnimalSex(data.getAnimalSex());
                            object.setAnimalSterilization(data.getAnimalSterilization());
                            object.setAnimalFoundPlace(data.getAnimalFoundPlace());
                            object.setAnimalTitle(data.getAnimalTitle());
                            object.setAnimalColour(data.getAnimalColour());
                            object.setAnimalId(data.getAnimalSubid().substring(5));
                            object.setAnimalKind(data.getAnimalKind());
                            object.setStory("");
                            object.setPersonality(new ArrayList<String>());
                            catchFirebaseArray.add(object);
                        }
                    }
                    mView.saveJsonToFirebase(gson.toJson(catchFirebaseArray));
                    mView.setRecyclerView(catchFirebaseArray);
                }
            }

            @Override
            public void onFailure(String errorCode) {
                Log.i("Michael","錯誤 : "+errorCode);
            }
        });
    }

    @Override
    public void onCheckGooglePlayVersion() {
        mView.checkGooglePlayVersion();
    }
}
