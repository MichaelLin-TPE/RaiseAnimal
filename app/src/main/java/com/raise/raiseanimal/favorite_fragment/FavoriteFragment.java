package com.raise.raiseanimal.favorite_fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raise.raiseanimal.R;
import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.detail_activity.DetailActivity;
import com.raise.raiseanimal.tool.UserDataManager;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment implements FavoriteVu {

    private FavoritePresenter presenter;

    private RecyclerView recyclerView;

    private Gson gson;

    private Context context;

    private TextView tvInfo;

    private ImageView ivHeart;

    private FavoriteAdapter adapter;

    private ArrayList<AnimalFavorite> dataArray;

    private ArrayList<Boolean> isOpenArray;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }

    private void initPresenter() {
        presenter = new FavoritePresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        initView(view);

        // Inflate the layout for this fragment
        return view;

    }

    private void initView(View view) {
        tvInfo = view.findViewById(R.id.favorite_text_info);
        ivHeart = view.findViewById(R.id.favorite_icon);
        recyclerView = view.findViewById(R.id.favorite_recycler_view);
    }

    @Override
    public void onResume() {
        Log.i("Michael", "favorite onResume : "+UserDataManager.getInstance(context).getFavorite());
        super.onResume();
        gson = new Gson();
        dataArray = gson.fromJson(UserDataManager.getInstance(context).getFavorite(), new TypeToken<List<AnimalFavorite>>() {
        }.getType());
        if (dataArray != null && dataArray.size() != 0) {
            presenter.onCatchData(dataArray);
        }else {
            presenter.onCatchNoData();
        }
    }

    @Override
    public void showRecyclerView(final ArrayList<AnimalFavorite> dataArray) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new FavoriteAdapter(context);
        isOpenArray = new ArrayList<>();
        for (AnimalFavorite data : dataArray){
            isOpenArray.add(false);
        }
        adapter.setData(dataArray,isOpenArray);
        recyclerView.setAdapter(adapter);

        adapter.setOnFavoriteItemClickListener(new FavoriteAdapter.OnFavoriteItemClickListener() {
            @Override
            public void onPhotoClick(AnimalFavorite data) {
                presenter.onPhotoClickListener(data);
            }

            @Override
            public void onHeartClick(AnimalFavorite data) {
                presenter.onHeartClick(data,dataArray,isOpenArray);
            }

            @Override
            public void onShareClick(AnimalFavorite data) {
                presenter.onShareClick(data);
            }

            @Override
            public void onPhoneClick() {
                presenter.onPhoneClickListener();
            }

            @Override
            public void onOpenInfoClick() {
            }
        });
    }

    @Override
    public void intentToDetailActivity(AnimalFavorite data) {
        Intent it = new Intent(context, DetailActivity.class);
        it.putExtra("newData", data);
        context.startActivity(it);
    }

    @Override
    public void callToHomeOfAnimal() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:02 8966 2158");
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void intentToAnotherApp(final AnimalFavorite data) {
//        ImageLoaderManager.getInstance(context).getBitmap(data.getPhoto(), new ImageLoaderManager.OnBitmapListener() {
//            @Override
//            public void onSuccessful(Bitmap bitmap) {
//                if (bitmap != null && getActivity() != null) {
//
//
//
//
//
////                    Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "快來領養我", "快來領養我"));
////                    Intent it = new Intent(Intent.ACTION_SEND);
////                    it.setType("image/jpeg");
////                    it.putExtra(Intent.EXTRA_TEXT, "快領養我");
////                    it.putExtra(Intent.EXTRA_STREAM, uri);
////                    //下方這個也不行
//////                    it.setType("*/*");
////                    context.startActivity(Intent.createChooser(it, "Select App to Share Text and Image"));
//                }
//            }
//        });
        String noSex = data.isNoSex() ? "是" : "否";
        String sex = data.getSex().equals("F") ? "女" : "男";

        String message = "編號 : "+data.getNumber()+"\n"+
                         "名字 : "+data.getName()+"\n"+
                         "性別 : "+sex+"\n"+
                         "是否已結育 : "+noSex+"\n"+
                         "目前位置 : "+data.getLocation()+"\n"+
                         data.getPhoto();

        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/plain");
        it.putExtra(Intent.EXTRA_TEXT,message);
        context.startActivity(it);



    }

    @Override
    public void removeUserData(ArrayList<AnimalFavorite> dataArray, ArrayList<Boolean> isOpenArray) {
        if (adapter != null){
            adapter.setData(dataArray,isOpenArray);
            adapter.notifyDataSetChanged();
        }
        String json = gson.toJson(dataArray);
        UserDataManager.getInstance(context).saveFavorite(json);
    }

    @Override
    public void showNoDataView(boolean isShow) {
        tvInfo.setVisibility(isShow ? View.VISIBLE : View.GONE);
        ivHeart.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }
}
