package com.raise.raiseanimal.staff_place;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;

public interface StaffActivityPresenter {
    void onLoginButtonClickListener(String account, String password);

    void onCatchData(ArrayList<AnimalObject> dataArray);

    void onBackButtonClickListener();

    void onFilterItemClickListener(String name, String value);

    void onAnimalItemClickListener(AnimalObject data, int itemPosition);

    void onTextChangedListener(String searchData);
}
