package com.raise.raiseanimal.favorite_fragment;

import com.raise.raiseanimal.animal_fragment.AnimalFavorite;

import java.util.ArrayList;

public interface FavoriteVu {
    void showRecyclerView(ArrayList<AnimalFavorite> dataArray);

    void intentToDetailActivity(AnimalFavorite data);

    void callToHomeOfAnimal();

    void intentToAnotherApp(AnimalFavorite data);

    void removeUserData(ArrayList<AnimalFavorite> dataArray, ArrayList<Boolean> isOpenArray);
}
