package com.raise.raiseanimal.favorite_fragment;

import com.raise.raiseanimal.animal_fragment.AnimalFavorite;

import java.util.ArrayList;

public class FavoritePresenterImpl implements FavoritePresenter{

    private FavoriteVu mView;

    public FavoritePresenterImpl(FavoriteVu mView) {
        this.mView = mView;
    }

    @Override
    public void onCatchData(ArrayList<AnimalFavorite> dataArray) {
        mView.showNoDataView(false);
        mView.showRecyclerView(dataArray);
    }

    @Override
    public void onPhotoClickListener(AnimalFavorite data) {
        mView.intentToDetailActivity(data);
    }

    @Override
    public void onPhoneClickListener() {
        mView.callToHomeOfAnimal();
    }

    @Override
    public void onShareClick(AnimalFavorite data) {
        mView.intentToAnotherApp(data);
    }

    @Override
    public void onHeartClick(AnimalFavorite data, ArrayList<AnimalFavorite> dataArray, ArrayList<Boolean> isOpenArray) {

        if (dataArray != null){
            int index = 0;
            for (AnimalFavorite fav : dataArray){
                if (data.getNumber() == fav.getNumber()){
                    dataArray.remove(index);
                    isOpenArray.remove(index);
                    break;
                }
                index++;
            }

            mView.removeUserData(dataArray,isOpenArray);
        }


    }

    @Override
    public void onCatchNoData() {
        mView.showNoDataView(true);
    }
}
