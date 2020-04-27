package com.raise.raiseanimal.edit_activity;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;
import java.util.Collections;

public class EditActivityPresenterImpl implements EditActivityPresenter {

    private EditActivityVu mView;

    private String message;


    private String[] personalityArray;

    private String downloadUrl;


    private ArrayList<AnimalObject> dataArray;
    private AnimalObject data;
    private int itemPosition;

    private Gson gson;

    public EditActivityPresenterImpl(EditActivityVu mView) {
        this.mView = mView;
        gson = new Gson();
    }

    @Override
    public void onCatchAllData(ArrayList<AnimalObject> dataArray, AnimalObject data, int itemPosition) {
        this.dataArray = dataArray;
        this.itemPosition = itemPosition;
        this.data = data;
        mView.showAnimalsDog(data);
    }

    @Override
    public void onCatchDownloadUrl(String downloadUrl) {
        message = "上傳成功";
        mView.showToast(message);
        mView.enableButton(false);
        this.downloadUrl = downloadUrl;
    }

    @Override
    public void onUpdateSuccessful() {
        message = "更新成功";
        mView.showToast(message);
    }

    @Override
    public void onBackButtonClickListener() {
        mView.closePage();
    }

    @Override
    public void onPreViewButtonClickListner(String AnimalName, String personality, String story) {
        if (personality != null && !personality.isEmpty()) {
            personalityArray = new String[personality.split(",").length];
            personalityArray = personality.split(",");
            if (personalityArray.length == 0) {
                message = "請輸入個性TAG";
                mView.showToast(message);
            }
        }
        if (AnimalName != null && !AnimalName.isEmpty()) {
            data.setAnimalTitle(AnimalName);
        }
        if (personalityArray != null && personalityArray.length != 0) {
            ArrayList<String> personArray = new ArrayList<>();
            Collections.addAll(personArray, personalityArray);
            data.setPersonality(personArray);
        }
        if (downloadUrl != null && !downloadUrl.isEmpty()) {
            data.setAlbumFile(downloadUrl);
        }
        if (story != null && !story.isEmpty()) {
            data.setStory(story);
        }
        mView.intentToDetailPage(data);
    }


    @Override
    public void onCatchPhoto(ArrayList<Bitmap> bitmapArrayList, byte[] photoBytes) {
        if (bitmapArrayList.size() != 0) {
            mView.enableButton(true);
            mView.uploadPhotoToStorage(photoBytes);
            mView.showPhoto(bitmapArrayList.get(0));
        } else {
            message = "請重新選擇一次照片";
            mView.showToast(message);
        }
    }

    @Override
    public void onShowTitle(AnimalObject data) {
        mView.showTitle(data.getAnimalId() + "");
        mView.showAllData(data);
    }


    @Override
    public void onBtnSaveClickListener(String AnimalName, String personality, String story) {
        if (personality != null && !personality.isEmpty()) {
            personalityArray = new String[personality.split(",").length];
            personalityArray = personality.split(",");
            if (personalityArray.length == 0) {
                message = "請輸入個性TAG";
                mView.showToast(message);
            }
        }
        if (AnimalName != null && !AnimalName.isEmpty()) {
            data.setAnimalTitle(AnimalName);
        }
        if (personalityArray != null && personalityArray.length != 0) {
            ArrayList<String> personArray = new ArrayList<>();
            Collections.addAll(personArray, personalityArray);
            data.setPersonality(personArray);
        }
        if (downloadUrl != null && !downloadUrl.isEmpty()) {
            data.setAlbumFile(downloadUrl);
        }
        if (story != null && !story.isEmpty()) {
            data.setStory(story);
        }
        dataArray.set(itemPosition, data);
        String jsonStr = gson.toJson(dataArray);
        mView.saveUpdateData(jsonStr);
    }


}
