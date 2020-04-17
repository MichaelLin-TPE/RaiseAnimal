package com.raise.raiseanimal.home_activity;

import java.util.ArrayList;

public class HomeActivityPresenterImpl implements HomeActivityPresenter {

    private HomeActivityVu mView;

    public HomeActivityPresenterImpl(HomeActivityVu mView) {
        this.mView = mView;
    }

    @Override
    public void onCatchTabData(ArrayList<String> tabArray) {
        mView.showTabLout(tabArray);

        mView.setViewPager();
    }

    @Override
    public void onTabSelectedListener(int position, ArrayList<Integer> pressedIconArray) {
        mView.changeTabSelectedIcon(position,pressedIconArray);
    }

    @Override
    public void onTabUnselectedListener(int position, ArrayList<Integer> notPressedIconArray) {
        mView.changeTabUnselectedIcon(position,notPressedIconArray);
    }

    @Override
    public void onStaffPlaceClickListener() {
        mView.intentToStaffPlace();
    }
}
