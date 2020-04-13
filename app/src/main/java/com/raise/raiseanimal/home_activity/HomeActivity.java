package com.raise.raiseanimal.home_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.raise.raiseanimal.R;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements HomeActivityVu {

    private HomeActivityPresenter presenter;

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private ImageView ivIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initPresenter();
        initView();

        ArrayList<String> tabArray = new ArrayList<>();
        tabArray.add(getString(R.string.animal));
        tabArray.add(getString(R.string.personal));
        tabArray.add(getString(R.string.staff));

        presenter.onCatchTabData(tabArray);

    }

    private void initView() {
        viewPager = findViewById(R.id.home_view_pager);
        tabLayout = findViewById(R.id.home_tab_layout);
        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void initPresenter() {
        presenter = new HomeActivityPresenterImpl(this);
    }

    @Override
    public void showTabLout(ArrayList<String> tabArray) {
        //未點擊的icon
        final ArrayList<Integer> notPressedIconArray = new ArrayList<>();
        notPressedIconArray.add(R.drawable.pets_not_pressed);
        notPressedIconArray.add(R.drawable.user_not_pressed);
        notPressedIconArray.add(R.drawable.staff_not_pressed);
        //點擊的icon
        final ArrayList<Integer> pressedIconArray = new ArrayList<>();
        pressedIconArray.add(R.drawable.pets_pressed);
        pressedIconArray.add(R.drawable.user_pressed);
        pressedIconArray.add(R.drawable.staff_pressed);


        tabLayout.removeAllTabs();
        for (int i = 0 ; i < tabArray.size() ; i ++){
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(prepareView(tabArray.get(i),notPressedIconArray.get(i)));
            tab.setTag(tabArray.get(i));
            tabLayout.addTab(tab);
        }
        //設置第一個點擊的TAB
        final TabLayout.Tab firstTab = tabLayout.getTabAt(0);
        if (firstTab != null && firstTab.getCustomView() != null){
            ivIcon = firstTab.getCustomView().findViewById(R.id.bottom_tab_icon);
            ivIcon.setImageResource(pressedIconArray.get(0));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                presenter.onTabSelectedListener(tab.getPosition(),pressedIconArray);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                presenter.onTabUnselectedListener(tab.getPosition(),notPressedIconArray);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void changeTabSelectedIcon(int position, ArrayList<Integer> pressedIconArray) {
        TabLayout.Tab singleTab = tabLayout.getTabAt(position);
        if (singleTab != null && singleTab.getCustomView() != null){
            ivIcon = singleTab.getCustomView().findViewById(R.id.bottom_tab_icon);
            ivIcon.setImageResource(pressedIconArray.get(position));
        }
    }

    @Override
    public void changeTabUnselectedIcon(int position, ArrayList<Integer> notPressedIconArray) {
        TabLayout.Tab singleTab = tabLayout.getTabAt(position);
        if (singleTab != null) {
            if (singleTab.getCustomView() != null) {
                ivIcon = singleTab.getCustomView().findViewById(R.id.bottom_tab_icon);
                ivIcon.setImageResource(notPressedIconArray.get(position));
            }
        }
    }

    @Override
    public void setViewPager() {
        FragmentManager manager = getSupportFragmentManager();
        ViewPagerAdapter adapter = new ViewPagerAdapter(manager);
        viewPager.setAdapter(adapter);
    }

    private View prepareView(String title, Integer icon) {

        View view = View.inflate(this,R.layout.home_bottom_tablayout_custom_view,null);
        TextView tvTitle = view.findViewById(R.id.bottom_tab_title);
        ivIcon = view.findViewById(R.id.bottom_tab_icon);
        tvTitle.setText(title);
        ivIcon.setImageResource(icon);
        return view;
    }
}
