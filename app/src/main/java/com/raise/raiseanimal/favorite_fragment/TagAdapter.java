package com.raise.raiseanimal.favorite_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raise.raiseanimal.R;
import com.raise.raiseanimal.detail_activity.view.InformationViewHolder;
import com.raise.raiseanimal.detail_activity.view.TagViewHolder;
import com.raise.raiseanimal.favorite_fragment.tag_presenter.TagPresenter;
import com.raise.raiseanimal.favorite_fragment.tag_presenter.TagPresenterImpl;

import static com.raise.raiseanimal.detail_activity.view_presenter.ViewPresenterImpl.TAG;
import static com.raise.raiseanimal.favorite_fragment.tag_presenter.TagPresenterImpl.INFO_FAV;
import static com.raise.raiseanimal.favorite_fragment.tag_presenter.TagPresenterImpl.TAG_FAV;

public class TagAdapter extends RecyclerView.Adapter {

    private Context context;

    private TagPresenter presenter;

    public TagAdapter(Context context) {
        this.context = context;


    }

    public void setPresenter(TagPresenter presenter) {
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case TAG_FAV:
                return new TagViewHolder(LayoutInflater.from(context).inflate(R.layout.tag_view, parent, false), context);
            case INFO_FAV:
                return new InformationViewHolder(LayoutInflater.from(context).inflate(R.layout.information_view, parent, false));
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TagViewHolder){
            presenter.onBindTagViewHolder((TagViewHolder)holder, position);
        }
        if (holder instanceof InformationViewHolder){
            presenter.onBindInfoViewHolder((InformationViewHolder)holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return presenter.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return presenter.getItemCount();
    }
}
