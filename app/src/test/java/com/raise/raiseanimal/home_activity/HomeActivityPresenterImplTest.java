package com.raise.raiseanimal.home_activity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class HomeActivityPresenterImplTest {

    @Mock
    private HomeActivityPresenterImpl presenter;

    @Mock
    private HomeActivityVu mView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new HomeActivityPresenterImpl(mView);
    }

    @Test
    public void onCatchTabData() {
        ArrayList<String> tabArray = new ArrayList<>();
        tabArray.add("XXXX");
        presenter.onCatchTabData(tabArray);
        Mockito.verify(mView).showTabLout(tabArray);
        Mockito.verify(mView).setViewPager();
    }

    @Test
    public void onTabSelectedListener() {
        int position = 3;
        ArrayList<Integer> pressedIconArray = new ArrayList<>();
        pressedIconArray.add(2);
        presenter.onTabSelectedListener(position,pressedIconArray);
        Mockito.verify(mView).changeTabSelectedIcon(position,pressedIconArray);
    }

    @Test
    public void onTabUnselectedListener() {
        int position = 3;
        ArrayList<Integer> pressedIconArray = new ArrayList<>();
        pressedIconArray.add(2);
        presenter.onTabUnselectedListener(position,pressedIconArray);
        Mockito.verify(mView).changeTabUnselectedIcon(position,pressedIconArray);
    }
}