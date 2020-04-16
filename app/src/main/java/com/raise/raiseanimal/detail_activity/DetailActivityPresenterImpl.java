package com.raise.raiseanimal.detail_activity;

import android.util.Log;

import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;

public class DetailActivityPresenterImpl implements  DetailActivityPresenter {

    private DetailActivityVu mView;

    public DetailActivityPresenterImpl(DetailActivityVu mView) {
        this.mView = mView;
    }

    @Override
    public void onCatchData(AnimalObject data, AnimalFavorite newData) {
        if (data != null){
            mView.setTitle(data.getAnimalTitle());

        }else {
            mView.setTitle(newData.getName());
            Log.i("Michael","data == null");
        }
        mView.setRecyclerView(data, newData);
    }

    @Override
    public void onBackButtonClickListener() {
        mView.closePage();
    }
}
