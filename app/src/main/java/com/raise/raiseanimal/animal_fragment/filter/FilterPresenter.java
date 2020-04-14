package com.raise.raiseanimal.animal_fragment.filter;

import java.util.ArrayList;

public interface FilterPresenter {
    void setColorData(ArrayList<String> colorArray);

    void setNoSexData(ArrayList<String> noSexArray);

    void setSexData(ArrayList<String> sexArray);

    void setSizeData(ArrayList<String> sizeArray);

    int getItemViewType(int position);

    int getItemCount();

    void onBindNoSexViewHolder(NoSexViewHolder holder, int position);

    void onBindSexViewHolder(SexViewHolder holder, int position);

    void onBindSizeViewHolder(SizeViewHolder holder, int position);

    void onBindColorViewHolder(ColorViewHolder holder, int position);

    void onColorClickListener(ColorViewHolder holder, FilterItemAdapter.OnFilterItemClickListener listener);

    void onSizeClickListener(SizeViewHolder holder, FilterItemAdapter.OnFilterItemClickListener listener);

    void onSexClickListener(SexViewHolder holder, FilterItemAdapter.OnFilterItemClickListener listener);

    void onNoSexClickListener(NoSexViewHolder holder, FilterItemAdapter.OnFilterItemClickListener listener);
}
