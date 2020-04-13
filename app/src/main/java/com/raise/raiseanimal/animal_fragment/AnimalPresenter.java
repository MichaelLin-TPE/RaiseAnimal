package com.raise.raiseanimal.animal_fragment;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

public interface AnimalPresenter {
    void startToCatchData();

    void catchData(String json);

    void onShowProgress(boolean isShow);

    void catchNewData(String json);

    void onAnimalItemClickListener(AnimalObject data);
}
