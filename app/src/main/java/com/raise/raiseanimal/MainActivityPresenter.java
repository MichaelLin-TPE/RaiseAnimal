package com.raise.raiseanimal;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;

public interface MainActivityPresenter {
    void onCatchData(ArrayList<AnimalObject> dataArray);

    void onShowAlertDialog(boolean isShowDialog);

    void onInfoBtnClickListener();

    void onDialogButtonClickListener();

    void onCheckedChangeListener(boolean isChecked);
}
