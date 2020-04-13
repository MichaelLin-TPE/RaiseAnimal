package com.raise.raiseanimal;

import java.util.ArrayList;

public interface MainActivityVu {
    void showPhoto(ArrayList<String> imageUrlArray);

    void showPointer(int index);

    void showDialog();

    void closeDialog();

    void saveDialogShowCheck(boolean isChecked);
}
