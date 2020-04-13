package com.raise.raiseanimal.detail_activity;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

public interface DetailActivityVu {

    void setRecyclerView(AnimalObject data);

    void setTitle(String animalTitle);
}
