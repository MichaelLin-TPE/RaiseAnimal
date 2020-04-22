package com.raise.raiseanimal.detail_activity.view;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.raise.raiseanimal.R;
import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import java.util.Locale;

public class InformationViewHolder extends RecyclerView.ViewHolder {

    private TextView tvNumber,tvName,tvNoSex,tvLocation,tvWhy,tvSex,tvStory;

    public InformationViewHolder(@NonNull View itemView) {
        super(itemView);
        tvNumber = itemView.findViewById(R.id.detail_number);
        tvName = itemView.findViewById(R.id.detail_name);
        tvNoSex = itemView.findViewById(R.id.detail_no_sex);
        tvLocation = itemView.findViewById(R.id.detail_location);
        tvWhy = itemView.findViewById(R.id.detail_why);
        tvSex = itemView.findViewById(R.id.detail_sex);
        tvStory = itemView.findViewById(R.id.detail_story);
    }

    public void setData(AnimalObject data) {
        tvNumber.setText(String.format(Locale.getDefault(),"編號 : %s",data.getAnimalId()));
        if (data.getAnimalTitle().isEmpty()){
            tvName.setText(String.format(Locale.getDefault(),"名字 : %s","尚未提供"));
        }else {
            tvName.setText(String.format(Locale.getDefault(),"名字 : %s",data.getAnimalTitle()));
        }
        if (data.getAnimalSterilization().equals("T")){
            tvNoSex.setText(String.format(Locale.getDefault(),"是否已結育 : %s","是"));
        }else {
            tvNoSex.setText(String.format(Locale.getDefault(),"是否已結育 : %s","否"));
        }
        tvLocation.setText(String.format(Locale.getDefault(),"目前位置 : %s",data.getShleterName()));

        tvWhy.setText(String.format(Locale.getDefault(),"尋獲地 : %s",data.getAnimalFoundPlace()));

        if (data.getAnimalSex().equals("F")){
            tvSex.setText(String.format(Locale.getDefault(),"性別 : %s","母"));
        }else {
            tvSex.setText(String.format(Locale.getDefault(),"性別 : %s","公"));
        }

        if (data.getStory().isEmpty() || data.getStory() == null){
            tvStory.setText(String.format(Locale.getDefault(),"%s","志工尚未提供"));
        }else {
            tvStory.setText(String.format(Locale.getDefault(),"%s",data.getStory()));
        }



    }

    public void setNewData(AnimalFavorite newData) {
        tvNumber.setText(String.format(Locale.getDefault(),"編號 : %s",newData.getNumber()));
        if (newData.getName().isEmpty()){
            tvName.setText(String.format(Locale.getDefault(),"名字 : %s","尚未提供"));
        }else {
            tvName.setText(String.format(Locale.getDefault(),"名字 : %s",newData.getName()));
        }
        if (newData.isNoSex()){
            tvNoSex.setText(String.format(Locale.getDefault(),"是否已結育 : %s","是"));
        }else {
            tvNoSex.setText(String.format(Locale.getDefault(),"是否已結育 : %s","否"));
        }
        tvLocation.setText(String.format(Locale.getDefault(),"目前位置 : %s",newData.getLocation()));

        tvWhy.setText(String.format(Locale.getDefault(),"尋獲地 : %s",newData.getFoundPlace()));

        if (newData.getSex().equals("F")){
            tvSex.setText(String.format(Locale.getDefault(),"性別 : %s","母"));
        }else {
            tvSex.setText(String.format(Locale.getDefault(),"性別 : %s","公"));
        }

        if (newData.getStory().isEmpty() || newData.getStory() == null){
            tvStory.setText(String.format(Locale.getDefault(),"%s","志工尚未提供"));
        }else {
            tvStory.setText(String.format(Locale.getDefault(),"%s",newData.getStory()));
        }

    }
}
