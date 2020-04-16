package com.raise.raiseanimal.favorite_fragment.tag_presenter;

import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.detail_activity.view.InformationViewHolder;
import com.raise.raiseanimal.detail_activity.view.TagViewHolder;

public interface TagPresenter {
    int getItemCount();

    int getItemViewType(int position);

    void setData(AnimalFavorite data);

    void onBindTagViewHolder(TagViewHolder holder, int position);

    void onBindInfoViewHolder(InformationViewHolder holder, int position);
}
