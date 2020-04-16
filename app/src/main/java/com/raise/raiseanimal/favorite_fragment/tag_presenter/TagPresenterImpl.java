package com.raise.raiseanimal.favorite_fragment.tag_presenter;

import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.detail_activity.view.InformationViewHolder;
import com.raise.raiseanimal.detail_activity.view.TagViewHolder;

public class TagPresenterImpl implements TagPresenter {
    public static final int TAG_FAV = 0;
    
    public static final int INFO_FAV = 1;

    private AnimalFavorite data;

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TAG_FAV;
        }
        if (position == 1){
            return INFO_FAV;
        }
        return 0;
    }

    @Override
    public void setData(AnimalFavorite data) {
        this.data = data;
    }

    @Override
    public void onBindTagViewHolder(TagViewHolder holder, int position) {
        holder.setNewData(data);
    }

    @Override
    public void onBindInfoViewHolder(InformationViewHolder holder, int position) {
        holder.setNewData(data);
    }
}
