package com.raise.raiseanimal.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.raise.raiseanimal.R;

public class ImageLoaderManager {
    private static ImageLoaderManager imageLoaderManager;

    private ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options;

    private ImageLoaderManager(Context context){
        initImageLoader(context);
    }

    private void initImageLoader(Context context) {
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.no_pic)
                .showImageOnFail(R.drawable.no_pic)
                .showImageOnLoading(R.drawable.no_pic)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options).build();
        imageLoader.init(config);
    }

    public static synchronized ImageLoaderManager getInstance(Context context){
        if (imageLoaderManager == null){
            imageLoaderManager = new ImageLoaderManager(context);
        }
        return imageLoaderManager;
    }

    public void setPhotoUrl(String url, RoundedImageView ivImage){
        imageLoader.displayImage(url,ivImage,options);
    }

    public void setPhotoUrl(String url, ImageView ivImage){
        imageLoader.displayImage(url,ivImage,options);
    }
}
