package com.raise.raiseanimal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.gms.common.images.ImageManager;
import com.raise.raiseanimal.home_activity.ViewPagerAdapter;
import com.raise.raiseanimal.tool.ImageLoaderManager;

import java.util.ArrayList;

public class MainViewPagerAdapter extends PagerAdapter {

    private ArrayList<String> imageUrlArray;

    private Context context;

    public MainViewPagerAdapter(ArrayList<String> imageUrlArray, Context context) {
        this.imageUrlArray = imageUrlArray;
        this.context = context;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.widget_banner_item,null);
        ImageView imageView = view.findViewById(R.id.iv_banner_item);
        ImageLoaderManager.getInstance(context).setPhotoUrl(imageUrlArray.get(position),imageView);
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return imageUrlArray == null ? 0 : imageUrlArray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
