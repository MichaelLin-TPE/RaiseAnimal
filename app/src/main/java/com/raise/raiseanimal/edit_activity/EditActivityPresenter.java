package com.raise.raiseanimal.edit_activity;

import android.graphics.Bitmap;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;

public interface EditActivityPresenter {

    void onCatchPhoto(ArrayList<Bitmap> bitmapArrayList, byte[] photoBytes);

    void onShowTitle(AnimalObject data);

    void onBtnSaveClickListener(String Name, String personality, String story);

    void onCatchAllData(ArrayList<AnimalObject> dataArray, AnimalObject data, int itemPosition);

    void onCatchDownloadUrl(String toString);

    void onUpdateSuccessful();

    void onBackButtonClickListener();
}
