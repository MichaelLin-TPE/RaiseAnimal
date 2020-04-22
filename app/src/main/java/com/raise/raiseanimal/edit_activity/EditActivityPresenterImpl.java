package com.raise.raiseanimal.edit_activity;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.ArrayList;
import java.util.Collections;

public class EditActivityPresenterImpl implements EditActivityPresenter {

    private EditActivityVu mView;

    private String message;

    private String preName;

    private String[] personalityArray;

    private String downloadUrl;

    private String preStory;

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
    public void onBtnNameClickListener() {
        mView.showEditNameDialog();
    }

    @Override
    public void onEditDialogConfirmClickListener(String name) {
        if (name != null){
            preName = name;
            mView.showName(name);
        }else {
            message = "請打字好嗎?";
            mView.showToast(message);
        }
    }

    @Override
    public void onCatchPhoto(ArrayList<Bitmap> bitmapArrayList, byte[] photoBytes) {
        if (bitmapArrayList.size() != 0){
            mView.enableButton(true);
            mView.uploadPhotoToStorage(photoBytes);
            mView.showPhoto(bitmapArrayList.get(0));
        }else {
            message = "請重新選擇一次照片";
            mView.showToast(message);
        }
    }

    @Override
    public void onShowTitle(AnimalObject data) {
        mView.showTitle(data.getAnimalId()+"");
    }

    @Override
    public void onBtnPersonalityClickListener() {
        mView.showPersonalityDialog();
    }

    @Override
    public void onPersonalityDialogConfirmClickListener(String personality) {
        personalityArray =  new String[personality.split(",").length];
        personalityArray = personality.split(",");
        if (!personality.isEmpty() && personalityArray.length != 0){
            mView.showPersonality(personality);
        }else {
            message = "請輸入個性TAG";
            mView.showToast(message);
        }

    }

    @Override
    public void onBtnStoryClickListener() {
        mView.showStoryDialog();
    }

    @Override
    public void onStoryDialogConfirmListener(String story) {
        if (!story.isEmpty()){
            preStory = story;
            mView.showStory(story);
        }else {
            message = "請輸入一點故事..";
            mView.showToast(message);
        }
    }

    @Override
    public void onBtnSaveClickListener() {
        if (preName != null && !preName.isEmpty()){
            data.setAnimalTitle(preName);
        }
        if (personalityArray != null && personalityArray.length != 0){
            ArrayList<String> personArray = new ArrayList<>();
            Collections.addAll(personArray, personalityArray);
            data.setPersonality(personArray);
        }
        if (downloadUrl != null && !downloadUrl.isEmpty()){
            data.setAlbumFile(downloadUrl);
        }
        if (preStory != null && !preStory.isEmpty()){
            data.setStory(preStory);
        }
        dataArray.set(itemPosition,data);
        String jsonStr = gson.toJson(dataArray);
        mView.saveUpdateData(jsonStr);
    }


}
