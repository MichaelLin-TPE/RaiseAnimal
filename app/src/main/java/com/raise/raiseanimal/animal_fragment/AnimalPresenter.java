package com.raise.raiseanimal.animal_fragment;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;

public interface AnimalPresenter {
    void startToCatchData();

    void catchData(ArrayList<AnimalObject> dataArray);

    void onShowProgress(boolean isShow);

    void catchNewData(ArrayList<AnimalObject> dataArray);

    void onAnimalItemClickListener(AnimalObject data);

    void onFilterItemClickListener(String name, String value);

    void onOpenFilterClickListener(boolean isOpenFilter);

    void onFavoriteIconClickListener(AnimalObject data);
}
