package com.raise.raiseanimal.animal_fragment;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;

public interface AnimalVu {
    String getPlaceString();

    void showProgress(boolean isShow);

    void setRecyclerView(ArrayList<AnimalObject> dataArray);

    void saveJsonToFirebase(String jsonStr);

    void intentToDetailPage(AnimalObject data);

    String getDogStr();
}
