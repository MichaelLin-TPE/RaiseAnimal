package com.raise.raiseanimal.animal_fragment.filter;

import java.util.ArrayList;

public class FilterPresenterImpl implements FilterPresenter {

    private ArrayList<String> colorArray,noSexArray,sexArray,sizeArray;

    public static final int COLOR = 0;
    public static final int NO_SEX = 1;
    public static final int SEX = 2;
    public static final int SIZE = 3;

    @Override
    public void setColorData(ArrayList<String> colorArray) {
        this.colorArray = colorArray;
    }

    @Override
    public void setNoSexData(ArrayList<String> noSexArray) {
        this.noSexArray = noSexArray;
    }

    @Override
    public void setSexData(ArrayList<String> sexArray) {
        this.sexArray = sexArray;
    }

    @Override
    public void setSizeData(ArrayList<String> sizeArray) {
        this.sizeArray = sizeArray;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return SEX;
        }
        if (position == 1){
            return SIZE;
        }
        if (position == 2){
            return NO_SEX;
        }
        if (position == 3){
            return COLOR;
        }

        return 0;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onBindNoSexViewHolder(NoSexViewHolder holder, int position) {
        holder.setData(noSexArray);
    }

    @Override
    public void onBindSexViewHolder(SexViewHolder holder, int position) {
        holder.setData(sexArray);
    }

    @Override
    public void onBindSizeViewHolder(SizeViewHolder holder, int position) {
        holder.setData(sizeArray);
    }

    @Override
    public void onBindColorViewHolder(ColorViewHolder holder, int position) {
        holder.setData(colorArray);
    }

    @Override
    public void onColorClickListener(ColorViewHolder holder, FilterItemAdapter.OnFilterItemClickListener listener) {
        holder.setOnFilterClickListener(listener);
    }

    @Override
    public void onSizeClickListener(SizeViewHolder holder, FilterItemAdapter.OnFilterItemClickListener listener) {
        holder.setOnFilterClickListener(listener);
    }

    @Override
    public void onSexClickListener(SexViewHolder holder, FilterItemAdapter.OnFilterItemClickListener listener) {
        holder.setOnFilterClickListener(listener);
    }

    @Override
    public void onNoSexClickListener(NoSexViewHolder holder, FilterItemAdapter.OnFilterItemClickListener listener) {
        holder.setOnFilterClickListener(listener);
    }
}
