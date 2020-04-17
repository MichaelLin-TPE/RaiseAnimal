package com.raise.raiseanimal.detail_activity.view;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.raise.raiseanimal.R;
import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.detail_activity.TagViewAdapter;

import java.util.ArrayList;

public class TagViewHolder extends RecyclerView.ViewHolder {

    private RecyclerView recyclerView;

    private Context context;

    public TagViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        recyclerView = itemView.findViewById(R.id.detail_tag_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,5);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    public void setData(AnimalObject data) {
        ArrayList<String> tagArray = new ArrayList<>();
        if (data.getAnimalSex().equals("F")){
            tagArray.add("母");
        }else {
            tagArray.add("公");
        }
        tagArray.add(data.getAnimalColour());
        tagArray.add(data.getAnimalKind());
        if (data.getAnimalBodyType().equals("MEDIUM")){
            tagArray.add("中型");
        }else if (data.getAnimalBodyType().equals("SMALL")){
            tagArray.add("小型");
        }else {
            tagArray.add("大型");
        }
        if (data.getPersonality() != null && data.getPersonality().size() != 0){
            tagArray.addAll(data.getPersonality());
        }


        TagViewAdapter adapter = new TagViewAdapter(tagArray,context);
        recyclerView.setAdapter(adapter);
    }

    public void setNewData(AnimalFavorite newData) {
        ArrayList<String> tagArray = new ArrayList<>();
        if (newData.getSex().equals("F")){
            tagArray.add("母");
        }else {
            tagArray.add("公");
        }
        tagArray.add(newData.getColor());
        tagArray.add("狗");
        if (newData.getSize().equals("MEDIUM")){
            tagArray.add("中型");
        }else if (newData.getSize().equals("SMALL")){
            tagArray.add("小型");
        }else {
            tagArray.add("大型");
        }
        if (newData.getPersonality() != null && newData.getPersonality().size() != 0){
            tagArray.addAll(newData.getPersonality());
        }

        TagViewAdapter adapter = new TagViewAdapter(tagArray,context);
        recyclerView.setAdapter(adapter);
    }
}
