package com.raise.raiseanimal.detail_activity;

import android.content.Context;
import android.icu.text.IDNA;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raise.raiseanimal.R;
import com.raise.raiseanimal.detail_activity.view.InformationViewHolder;
import com.raise.raiseanimal.detail_activity.view.PhotoViewHolder;
import com.raise.raiseanimal.detail_activity.view.TagViewHolder;
import com.raise.raiseanimal.detail_activity.view_presenter.ViewPresenter;

import static com.raise.raiseanimal.detail_activity.view_presenter.ViewPresenterImpl.INFO;
import static com.raise.raiseanimal.detail_activity.view_presenter.ViewPresenterImpl.PHOTO;
import static com.raise.raiseanimal.detail_activity.view_presenter.ViewPresenterImpl.TAG;

public class DetailAdapter extends RecyclerView.Adapter {

    private Context context;

    private ViewPresenter viewPresenter;

    public DetailAdapter(Context context, ViewPresenter viewPresenter) {
        this.context = context;
        this.viewPresenter = viewPresenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case PHOTO:
                return new PhotoViewHolder(LayoutInflater.from(context).inflate(R.layout.photo_view, parent, false),context);
            case TAG:
                return new TagViewHolder(LayoutInflater.from(context).inflate(R.layout.tag_view, parent, false), context);
            case INFO:
                return new InformationViewHolder(LayoutInflater.from(context).inflate(R.layout.information_view, parent, false));
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PhotoViewHolder){
            viewPresenter.onBindPhotoViewHolder((PhotoViewHolder)holder,position);
        }
        if (holder instanceof TagViewHolder){
            viewPresenter.onBindTagViewHolder((TagViewHolder)holder,position);
        }
        if (holder instanceof InformationViewHolder){
            viewPresenter.onBindInformationViewHolder((InformationViewHolder)holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return viewPresenter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return viewPresenter.getItemViewType(position);
    }
}
