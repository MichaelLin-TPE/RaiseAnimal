package com.raise.raiseanimal.animal_fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raise.raiseanimal.R;
import com.raise.raiseanimal.animal_fragment.filter.FilterAdapter;
import com.raise.raiseanimal.animal_fragment.filter.FilterItemAdapter;
import com.raise.raiseanimal.animal_fragment.filter.FilterPresenter;
import com.raise.raiseanimal.animal_fragment.filter.FilterPresenterImpl;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.detail_activity.DetailActivity;
import com.raise.raiseanimal.tool.UserDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnimalFragment extends Fragment implements AnimalVu {

    private AnimalPresenter presenter;

    private RecyclerView recyclerView,rvFilter;

    private Context context;

    private ProgressBar progressBar;

    private AnimalAdapter animalAdapter;

    private Gson gson;

    private TextView tvSearchInfo,tvOpenFilter;

    private ImageView ivLogo,ivOpenFilter;

    private FilterPresenter filterPresenter;

    private static final String ANIMAL_DATA = "animal_data";
    private static final String DATA = "data";

    private FirebaseFirestore firestore;

    private boolean isOpenFilter;

    //測試
    private int mHeight;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public static AnimalFragment newInstance() {
        AnimalFragment fragment = new AnimalFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_animal, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.animal_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressBar = view.findViewById(R.id.animal_progress);
        rvFilter = view.findViewById(R.id.animal_filter_recycler_view);
        rvFilter.setLayoutManager(new LinearLayoutManager(context));
        tvSearchInfo = view.findViewById(R.id.animal_search_info);
        ivLogo = view.findViewById(R.id.animal_icon);
        tvOpenFilter = view.findViewById(R.id.animal_filter_info);
        ivOpenFilter = view.findViewById(R.id.animal_filter_icon);

        tvOpenFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onOpenFilterClickListener(isOpenFilter);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
        initFirebase();
        gson = new Gson();
    }

    private void initFirebase() {
        firestore = FirebaseFirestore.getInstance();
    }

    private void initPresenter() {
        presenter = new AnimalPresenterImpl(this);
        filterPresenter = new FilterPresenterImpl();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        searchData();
        DocumentReference reference = firestore.collection(ANIMAL_DATA).document(DATA);
        reference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.i("Michael","資料新增失敗 : "+e.toString());
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.i("Michael","firebase更新資料了");
                    searchNewData();
                } else {
                    System.out.print("Current data: null");
                }
            }
        });
    }

    private void searchNewData() {

        firestore.collection(ANIMAL_DATA)
                .document(DATA)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null){

                            DocumentSnapshot snapshot = task.getResult();
                            String json = (String) snapshot.get("json");

                            ArrayList<AnimalObject> dataArray = gson.fromJson(json,new TypeToken<List<AnimalObject>>(){}.getType());
                            if (dataArray != null && dataArray.size() != 0){
                                presenter.catchNewData(dataArray);
                            }
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void searchData() {
        presenter.onShowProgress(true);
        firestore.collection(ANIMAL_DATA)
                .document(DATA)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null){
                            DocumentSnapshot snapshot = task.getResult();
                            String json = (String) snapshot.get("json");

                            ArrayList<AnimalObject> dataArray = gson.fromJson(json,new TypeToken<List<AnimalObject>>(){}.getType());
                            if (dataArray != null && dataArray.size() != 0){
                                presenter.catchData(dataArray);
                            }else {
                                presenter.catchNoData();
                            }


                        }
                    }
                });
    }

    @Override
    public String getPlaceString() {
        return context.getString(R.string.shelter);
    }

    @Override
    public void showProgress(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setRecyclerView(ArrayList<AnimalObject> dataArray) {

        if (animalAdapter != null){
            animalAdapter.setData(dataArray);
            ArrayList<AnimalFavorite> favArray = gson.fromJson(UserDataManager.getInstance(context).getFavorite(),new TypeToken<List<AnimalFavorite>>(){}.getType());
            animalAdapter.setFavoriteData(favArray);
            animalAdapter.notifyDataSetChanged();
        }

        animalAdapter = new AnimalAdapter(context);
        ArrayList<AnimalFavorite> favArray = gson.fromJson(UserDataManager.getInstance(context).getFavorite(),new TypeToken<List<AnimalFavorite>>(){}.getType());
        animalAdapter.setFavoriteData(favArray);
        animalAdapter.setData(dataArray);

        recyclerView.setAdapter(animalAdapter);

        animalAdapter.setOnAnimalItemClickListener(new AnimalAdapter.OnAnimalItemClickListener() {
            @Override
            public void onClick(AnimalObject data) {
                presenter.onAnimalItemClickListener(data);
            }

            @Override
            public void onFavorite(AnimalObject data) {
                presenter.onFavoriteIconClickListener(data);
            }
        });
    }

    @Override
    public void saveJsonToFirebase(final String jsonStr) {
        if (getActivity() != null){
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Map<String,Object> map = new HashMap<>();
                    map.put("json",jsonStr);
                    firestore.collection(ANIMAL_DATA)
                            .document(DATA)
                            .set(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Log.i("Michael","儲存成功");
                                    }else {
                                        Log.i("Michael","儲存失敗");
                                    }
                                }
                            });
                }
            });
        }


    }

    @Override
    public void intentToDetailPage(AnimalObject data) {
        Intent it = new Intent(context, DetailActivity.class);
        it.putExtra("data",data);
        context.startActivity(it);
        //dk dk
    }

    @Override
    public String getDogStr() {
        return getActivity() != null ? getActivity().getString(R.string.dog) : "";
    }

    @Override
    public void showFilterView(ArrayList<String> colorArray, ArrayList<String> noSexArray, ArrayList<String> sexArray, ArrayList<String> sizeArray) {
        filterPresenter.setColorData(colorArray);
        filterPresenter.setNoSexData(noSexArray);
        filterPresenter.setSexData(sexArray);
        filterPresenter.setSizeData(sizeArray);
        FilterAdapter adapter = new FilterAdapter(filterPresenter,context);
        rvFilter.setAdapter(adapter);

        //測試動畫
        rvFilter.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rvFilter.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mHeight = rvFilter.getHeight();
                rvFilter.setPadding(0,-mHeight,0,0);
            }
        });
        adapter.setOnFilterItemClickListener(new FilterItemAdapter.OnFilterItemClickListener() {
            @Override
            public void onClick(String name,String value) {
                presenter.onFilterItemClickListener(name,value);
            }
        });
    }

    @Override
    public void showSearchNoData(boolean isShow) {

        tvSearchInfo.setVisibility(isShow ? View.VISIBLE : View.GONE);
        ivLogo.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void openFilterView(boolean isShow) {
        if (isShow){
            isOpenFilter = true;
        }else {
            isOpenFilter = false;
        }

        ValueAnimator valueAnimator = new ValueAnimator();
        if (isShow){
            valueAnimator.setIntValues(-mHeight,0);
        }else {
            valueAnimator.setIntValues(0,-mHeight);
        }
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int)animation.getAnimatedValue();
                rvFilter.setPadding(0,value,0,0);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                tvOpenFilter.setClickable(true);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                tvOpenFilter.setClickable(false);
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
        ViewCompat.animate(ivOpenFilter).rotationBy(180f).setDuration(500).start();
//        rvFilter.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tvOpenFilter.setText(isShow ? context.getString(R.string.close_filter) : context.getString(R.string.open_filter));
//        ivOpenFilter.setImageResource(isShow ? R.drawable.up_arrow : R.drawable.down_arrow);
    }

    @Override
    public void saveUserFavoriteData(AnimalObject data) {
        Log.i("Michael","animal Id : "+data.getAnimalId());
        ArrayList<AnimalFavorite> favArray = gson.fromJson(UserDataManager.getInstance(context).getFavorite(),new TypeToken<List<AnimalFavorite>>(){}.getType());

        if (favArray == null){
            favArray = new ArrayList<>();
            AnimalFavorite fav = new AnimalFavorite();
            fav.setColor(data.getAnimalColour());
            fav.setName(data.getAnimalTitle());
            fav.setNoSex(data.getAnimalSterilization().equals("T"));
            fav.setNumber(data.getAnimalId());
            fav.setPhoto(data.getAlbumFile());
            fav.setSize(data.getAnimalBodyType());
            fav.setLocation(data.getShleterName());
            fav.setFoundPlace(data.getAnimalFoundPlace());
            fav.setSex(data.getAnimalSex());
            fav.setFavorite(true);
            fav.setStory(data.getStory());
            fav.setPersonality(data.getPersonality());
            favArray.add(fav);
            String favJson = gson.toJson(favArray);
            Log.i("Michael","即將儲存的json : "+favJson);
            UserDataManager.getInstance(context).saveFavorite(favJson);
            animalAdapter.setFavoriteData(favArray);
            animalAdapter.notifyDataSetChanged();
        }else {
            boolean isRepeat = false;
            int index = 0;
            for (AnimalFavorite favorite : favArray){
                if (data.getAnimalId() == favorite.getNumber()){
                    isRepeat = true;
                    break;
                }
                index ++;
            }
            if (isRepeat){
                favArray.remove(index);
                String favJson = gson.toJson(favArray);
                UserDataManager.getInstance(context).saveFavorite(favJson);
                animalAdapter.setFavoriteData(favArray);
                animalAdapter.notifyDataSetChanged();
                return;
            }
            AnimalFavorite fav = new AnimalFavorite();
            fav.setColor(data.getAnimalColour());
            fav.setName(data.getAnimalTitle());
            fav.setNoSex(data.getAnimalSterilization().equals("T"));
            fav.setNumber(data.getAnimalId());
            fav.setPhoto(data.getAlbumFile());
            fav.setSize(data.getAnimalBodyType());
            fav.setLocation(data.getShleterName());
            fav.setFoundPlace(data.getAnimalFoundPlace());
            fav.setSex(data.getAnimalSex());
            fav.setFavorite(true);
            fav.setStory(data.getStory());
            fav.setPersonality(data.getPersonality());
            fav.setStory(data.getStory());
            fav.setPersonality(data.getPersonality());
            favArray.add(fav);
            String favJson = gson.toJson(favArray);
            Log.i("Michael","即將儲存的json : "+favJson);
            UserDataManager.getInstance(context).saveFavorite(favJson);
            animalAdapter.setFavoriteData(favArray);
            animalAdapter.notifyDataSetChanged();
        }

    }
}
