package com.raise.raiseanimal.staff_place;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;

public interface StaffActivityVu {
    void showToast(String message);

    void hideLoginView(boolean isShow);

    void showRecycler(boolean b);

    void saveAccountAndPassword(String account, String password);

    void searchData();

    void showProgress(boolean isShow);

    void setRecyclerView(ArrayList<AnimalObject> dataArray);

    void showFilterView(ArrayList<String> colorArray, ArrayList<String> noSexArray, ArrayList<String> sexArray, ArrayList<String> sizeArray);

    void closePage();

    void showSearchNoData(boolean isShow);

    void intentToEditPage(AnimalObject data, int itemPosition, ArrayList<AnimalObject> catchFirebaseArray);

    void showTotalSize(int size);
}
