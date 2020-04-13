package com.raise.raiseanimal.detail_activity.view_presenter;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.detail_activity.view.InformationViewHolder;
import com.raise.raiseanimal.detail_activity.view.PhotoViewHolder;
import com.raise.raiseanimal.detail_activity.view.TagViewHolder;

public interface ViewPresenter {
    void setData(AnimalObject data);

    int getItemViewType(int position);

    int getItemCount();

    void onBindPhotoViewHolder(PhotoViewHolder holder, int position);

    void onBindInformationViewHolder(InformationViewHolder holder, int position);

    void onBindTagViewHolder(TagViewHolder holder, int position);
}
