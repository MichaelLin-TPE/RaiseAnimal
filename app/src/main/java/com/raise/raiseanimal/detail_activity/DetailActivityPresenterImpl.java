package com.raise.raiseanimal.detail_activity;

import android.util.Log;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

public class DetailActivityPresenterImpl implements  DetailActivityPresenter {

    private DetailActivityVu mView;

    public DetailActivityPresenterImpl(DetailActivityVu mView) {
        this.mView = mView;
    }

    @Override
    public void onCatchData(AnimalObject data) {
        if (data != null){
            mView.setTitle(data.getAnimalTitle());
            mView.setRecyclerView(data);
        }else {
            Log.i("Michael","data == null");
        }
    }
}
