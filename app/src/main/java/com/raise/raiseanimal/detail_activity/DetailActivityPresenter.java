package com.raise.raiseanimal.detail_activity;

import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;

public interface DetailActivityPresenter
{
    void onCatchData(AnimalObject data, AnimalFavorite newData);

    void onBackButtonClickListener();
}
