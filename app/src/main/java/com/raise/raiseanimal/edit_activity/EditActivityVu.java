package com.raise.raiseanimal.edit_activity;

import android.graphics.Bitmap;

public interface EditActivityVu {
    void showEditNameDialog();

    void showToast(String message);

    void showName(String name);

    void showPhoto(Bitmap bitmap);

    void showTitle(int animalId);

    void showPersonalityDialog();

    void showPersonality(String personality);

    void showStoryDialog();

    void showStory(String story);

    void uploadPhotoToStorage(byte[] photoBytes);

    void saveUpdateData(String jsonStr);

    void closePage();

    void enableButton(boolean isEnable);
}
