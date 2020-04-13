package com.raise.raiseanimal.home_activity;

import java.util.ArrayList;

public interface HomeActivityVu {
    void showTabLout(ArrayList<String> tabArray);

    void changeTabSelectedIcon(int position, ArrayList<Integer> pressedIconArray);

    void changeTabUnselectedIcon(int position, ArrayList<Integer> notPressedIconArray);

    void setViewPager();
}
