package com.raise.raiseanimal;

public interface MainActivityPresenter {
    void onCatchData(String json);

    void onShowAlertDialog(boolean isShowDialog);

    void onInfoBtnClickListener();

    void onDialogButtonClickListener();

    void onCheckedChangeListener(boolean isChecked);
}
