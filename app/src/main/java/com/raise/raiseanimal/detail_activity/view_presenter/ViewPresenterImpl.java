package com.raise.raiseanimal.detail_activity.view_presenter;

import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.detail_activity.view.InformationViewHolder;
import com.raise.raiseanimal.detail_activity.view.PhotoViewHolder;
import com.raise.raiseanimal.detail_activity.view.TagViewHolder;

public class ViewPresenterImpl implements ViewPresenter {

    public static final int PHOTO = 0;

    public static final int TAG = 1;

    public static final int INFO = 2;

    private AnimalObject data;

    private AnimalFavorite newData;


    @Override
    public void setData(AnimalObject data, AnimalFavorite newData) {
        this.data = data;
        this.newData = newData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return PHOTO;
        }
        if (position == 1){
            return TAG;
        }
        if (position == 2){
            return INFO;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onBindPhotoViewHolder(PhotoViewHolder holder, int position) {
        if (data != null){
            holder.setData(data.getAlbumFile());
        }else {
            holder.setData(newData.getPhoto());
        }

    }

    @Override
    public void onBindInformationViewHolder(InformationViewHolder holder, int position) {
        if (data != null){
            holder.setData(data);
        }else {
            holder.setNewData(newData);
        }

    }

    @Override
    public void onBindTagViewHolder(TagViewHolder holder, int position) {
        if (data != null){
            holder.setData(data);
        }else {
            holder.setNewData(newData);
        }
    }
}
