package com.raise.raiseanimal.home_activity;

import java.util.ArrayList;

public interface HomeActivityPresenter {
    void onCatchTabData(ArrayList<String> tabArray);

    void onTabSelectedListener(int position, ArrayList<Integer> pressedIconArray);

    void onTabUnselectedListener(int position, ArrayList<Integer> notPressedIconArray);
}
