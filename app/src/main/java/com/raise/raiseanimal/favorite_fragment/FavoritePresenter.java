package com.raise.raiseanimal.favorite_fragment;

import com.raise.raiseanimal.animal_fragment.AnimalFavorite;

import java.util.ArrayList;

public interface FavoritePresenter {
    void onCatchData(ArrayList<AnimalFavorite> dataArray);

    void onPhotoClickListener(AnimalFavorite data);

    void onPhoneClickListener();

    void onShareClick(AnimalFavorite data);

    void onHeartClick(AnimalFavorite data, ArrayList<AnimalFavorite> dataArray, ArrayList<Boolean> isOpenArray);

    void onCatchNoData();
}
