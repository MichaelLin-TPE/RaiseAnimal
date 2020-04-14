package com.raise.raiseanimal.animal_fragment.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Size;
import androidx.recyclerview.widget.RecyclerView;

import com.raise.raiseanimal.R;

import static com.raise.raiseanimal.animal_fragment.filter.FilterPresenterImpl.COLOR;
import static com.raise.raiseanimal.animal_fragment.filter.FilterPresenterImpl.NO_SEX;
import static com.raise.raiseanimal.animal_fragment.filter.FilterPresenterImpl.SEX;
import static com.raise.raiseanimal.animal_fragment.filter.FilterPresenterImpl.SIZE;

public class FilterAdapter extends RecyclerView.Adapter {

    private FilterPresenter presenter;

    private Context context;

    private FilterItemAdapter.OnFilterItemClickListener listener;

    public void setOnFilterItemClickListener(FilterItemAdapter.OnFilterItemClickListener listener){
        this.listener = listener;
    }

    public FilterAdapter(FilterPresenter presenter, Context context) {
        this.presenter = presenter;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.filter_view, parent, false);
        switch (viewType) {
            case COLOR:
                return new ColorViewHolder(view, context);
            case SEX:
                return new SexViewHolder(view, context);
            case SIZE:
                return new SizeViewHolder(view, context);
            case NO_SEX:
                return new NoSexViewHolder(view, context);
            default:
                return null;
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ColorViewHolder){
            presenter.onBindColorViewHolder((ColorViewHolder)holder,position);
            presenter.onColorClickListener((ColorViewHolder)holder,listener);
        }
        if (holder instanceof SizeViewHolder){
            presenter.onBindSizeViewHolder((SizeViewHolder)holder,position);
            presenter.onSizeClickListener((SizeViewHolder)holder,listener);
        }
        if (holder instanceof SexViewHolder){
            presenter.onBindSexViewHolder((SexViewHolder)holder,position);
            presenter.onSexClickListener((SexViewHolder)holder,listener);
        }
        if (holder instanceof NoSexViewHolder){
            presenter.onBindNoSexViewHolder((NoSexViewHolder)holder,position);
            presenter.onNoSexClickListener((NoSexViewHolder)holder,listener);
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
