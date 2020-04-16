package com.raise.raiseanimal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.detail_activity.DetailAdapter;
import com.raise.raiseanimal.home_activity.HomeActivity;
import com.raise.raiseanimal.tool.UserDataManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityVu {

    private MainActivityPresenter presenter;

    public FirebaseFirestore firestore;

    public LinearLayout pointer;

    public ArrayList<String> imageUrl;

    public BannerHandler handler = new BannerHandler(new WeakReference<>(this));

    private static final String ANIMAL_DATA = "animal_data";
    private static final String DATA = "data";

    public ViewPager viewPager;

    public ImageView[] pointers;

    private AlertDialog dialog;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPresenter();
        initView();
        boolean isShowDialog = UserDataManager.getInstance(this).isShow();
        presenter.onShowAlertDialog(isShowDialog);
        searchData();
    }

    private void searchData() {
        firestore.collection(ANIMAL_DATA)
                .document(DATA)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DocumentSnapshot snapshot = task.getResult();

                            ArrayList<AnimalObject> dataArray = gson.fromJson((String)snapshot.get("json"),new TypeToken<List<AnimalObject>>(){}.getType());
                            if (dataArray != null && dataArray.size() != 0){
                                presenter.onCatchData(dataArray);
                            }
                        }
                    }
                });
    }

    private void initView() {
        ImageView button = findViewById(R.id.main_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(it);
                finish();
            }
        });
        pointer = findViewById(R.id.main_pointer);

        viewPager = findViewById(R.id.main_view_pager);


    }

    private void initPresenter() {
        presenter = new MainActivityPresenterImpl(this);
        firestore = FirebaseFirestore.getInstance();
        gson = new Gson();
    }

    @Override
    public void showPhoto(ArrayList<String> imageUrlArray) {
        this.imageUrl = imageUrlArray;
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(imageUrlArray, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(bannerListner);

        handler.sendEmptyMessageDelayed(BannerHandler.MSG_UPDATE_IMAGE,BannerHandler.MSG_DELAY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    private ViewPager.OnPageChangeListener bannerListner = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < pointers.length; i++) {
                if (position == i) {
                    pointers[i].setBackgroundResource(R.drawable.banner_point_on);
                } else {
                    pointers[i].setBackgroundResource(R.drawable.banner_point_off);
                }
            }
            handler.sendMessage(Message.obtain(handler,BannerHandler.MSG_PAGE_CHANGE,position,0));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    handler.sendEmptyMessage(BannerHandler.MSG_KEEP_SILENT);
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    handler.sendEmptyMessageDelayed(BannerHandler.MSG_UPDATE_IMAGE, BannerHandler.MSG_DELAY);
                default:
                    break;
            }
        }
    };

    @Override
    public void showPointer(int index) {
        pointer.removeAllViews();
        pointers = new ImageView[index];
        for (int i = 0; i < index; i++) {
            ImageView circle = new ImageView(this);
            int pix = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pix, pix);
            int rightDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            params.setMargins(0, 0, rightDp, 0);
            circle.setLayoutParams(params);
            circle.setPadding(20, 0, 20, 0);
            pointers[i] = circle;
            if (i == 0) {
                circle.setBackgroundResource(R.drawable.banner_point_on);
            } else {
                circle.setBackgroundResource(R.drawable.banner_point_off);
            }
            pointer.addView(pointers[i]);
        }
    }

    @Override
    public void showDialog() {
        View view = View.inflate(this,R.layout.main_alert_dialog,null);
        TextView tvMessage = view.findViewById(R.id.main_dialog_info);
        CheckBox cbNoShow = view.findViewById(R.id.main_check);
        TextView tvBtn = view.findViewById(R.id.main_dialog_btn);

        tvMessage.setText(getString(R.string.all_information));

        dialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();
        dialog.show();

        cbNoShow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.onCheckedChangeListener(isChecked);
            }
        });

        tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onDialogButtonClickListener();
            }
        });
    }

    @Override
    public void closeDialog() {
        dialog.dismiss();
    }

    @Override
    public void saveDialogShowCheck(boolean isChecked) {
        UserDataManager.getInstance(this).saveShowDialog(!isChecked);
    }
}
