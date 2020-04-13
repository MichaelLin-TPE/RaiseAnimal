package com.raise.raiseanimal;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;


public class BannerHandler extends Handler {
    /**
     * 請求更新顯示 VIEW
     */
    public static final int MSG_UPDATE_IMAGE = 1;

    /**
     * 請求暫停輪播
     */
    public static final int MSG_KEEP_SILENT = 2;

    /**
     * 請求恢復輪播
     */
    public static final int MSG_BREAK_SILENT = 3;

    /**
     * 紀錄最新的一頁,當用戶手動滑動時需要紀錄新頁號,否則會使輪播頁面出錯
     * 例如當前如果在第一頁,本來準備播放的是第二頁,而這時候用戶滑動到了末頁
     * 則應該播放第一頁,如果繼續按照原本的第二頁播放,邏輯上會有問題
     */
    public static final int MSG_PAGE_CHANGE = 4;

    public static final long MSG_DELAY = 3000;


    private int currentItem = 0;

    private WeakReference<MainActivity> weakReference;

    public BannerHandler(WeakReference<MainActivity> weakReference){
        this.weakReference = weakReference;
    }

    @Override
    public void handleMessage(Message msg) {
        MainActivity bannerView = weakReference.get();

        if (bannerView == null){
            return;
        }
        if (bannerView.handler.hasMessages(MSG_UPDATE_IMAGE)){
            bannerView.handler.removeMessages(MSG_UPDATE_IMAGE);
        }


        switch (msg.what){
            case MSG_UPDATE_IMAGE:
                currentItem++;
                for (int i = 0 ; i < bannerView.pointers.length ; i ++){
                    bannerView.pointers[i].setBackgroundResource(R.drawable.banner_point_on);
                    if (currentItem != i){
                        bannerView.pointers[i].setBackgroundResource(R.drawable.banner_point_off);
                    }
                }
                if (currentItem == bannerView.imageUrl.size()){
                    currentItem = 0;
                    bannerView.viewPager.setCurrentItem(currentItem);
                }else {
                    bannerView.viewPager.setCurrentItem(currentItem);
                }
                bannerView.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);
                break;
            case MSG_KEEP_SILENT:
                break;
            case MSG_BREAK_SILENT:
                bannerView.handler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE,MSG_DELAY);
                break;
            case MSG_PAGE_CHANGE:
                currentItem = msg.arg1;
                break;
            default:
                break;
        }
    }
}
