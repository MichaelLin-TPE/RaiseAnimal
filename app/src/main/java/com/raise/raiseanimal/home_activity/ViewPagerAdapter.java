package com.raise.raiseanimal.home_activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.raise.raiseanimal.animal_fragment.AnimalFragment;
import com.raise.raiseanimal.basic_information.BasicInfoFragment;
import com.raise.raiseanimal.favorite_fragment.FavoriteFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return AnimalFragment.newInstance();
        }else if (position == 1){
            return BasicInfoFragment.newInstance();
        }else {
            return FavoriteFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
