package com.raise.raiseanimal;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivityPresenterImpl implements MainActivityPresenter {

    private MainActivityVu mView;

    private Gson gson;

    public MainActivityPresenterImpl(MainActivityVu mView) {
        this.mView = mView;
        gson = new Gson();
    }

    @Override
    public void onCatchData(String json) {

        if (json != null){

            ArrayList<AnimalObject> dataArray = gson.fromJson(json,new TypeToken<List<AnimalObject>>(){}.getType());

            if (dataArray.size() != 0){
                ArrayList<String> imageUrlArray = new ArrayList<>();
                Collections.shuffle(dataArray);
                int index = 0;
                for (AnimalObject data : dataArray){
                    if (index < 8){
                        imageUrlArray.add(data.getAlbumFile());
                        index ++;
                    }else {
                        break;
                    }
                }
                mView.showPointer(imageUrlArray.size());
                mView.showPhoto(imageUrlArray);
            }else {
                Log.i("Michael","解析後沒資料");
            }

        }else {
            Log.i("Michael","json = null");
        }
    }

    @Override
    public void onShowAlertDialog(boolean isShowDialog) {
        if (isShowDialog){
            mView.showDialog();
        }

    }

    @Override
    public void onInfoBtnClickListener() {
        mView.showDialog();
    }

    @Override
    public void onDialogButtonClickListener() {
        mView.closeDialog();
    }

    @Override
    public void onCheckedChangeListener(boolean isChecked) {
        mView.saveDialogShowCheck(isChecked);
    }

}
