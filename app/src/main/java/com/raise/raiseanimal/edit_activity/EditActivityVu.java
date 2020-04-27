package com.raise.raiseanimal.edit_activity;

import android.graphics.Bitmap;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

public interface EditActivityVu {

    void showToast(String message);

    void showPhoto(Bitmap bitmap);

    void showTitle(String animalId);

    void uploadPhotoToStorage(byte[] photoBytes);

    void saveUpdateData(String jsonStr);

    void closePage();

    void enableButton(boolean isEnable);

    void showAnimalsDog(AnimalObject data);

    void showAlldata(AnimalObject data);

    void intentToDetailPage(AnimalObject data);
}
