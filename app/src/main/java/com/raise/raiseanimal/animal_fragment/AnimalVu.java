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

    void showFilterView(ArrayList<String> colorArray, ArrayList<String> noSexArray, ArrayList<String> sexArray, ArrayList<String> sizeArray);

    void showSearchNoData(boolean isShow);

    void openFilterView(boolean isShow);

    void saveUserFavoriteData(AnimalObject data);

    void checkGooglePlayVersion();

    void showTotalSize(int size);
}
