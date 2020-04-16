package com.raise.raiseanimal.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
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

    public void getBitmap(String url, final OnBitmapListener listener) {
        imageLoader.loadImage(url, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                listener.onSuccessful(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
    public interface OnBitmapListener{
        void onSuccessful(Bitmap bitmap);
    }
}
