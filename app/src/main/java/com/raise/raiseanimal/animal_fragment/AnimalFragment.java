package com.raise.raiseanimal.animal_fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.detail_activity.DetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnimalFragment extends Fragment implements AnimalVu {

    private AnimalPresenter presenter;

    private RecyclerView recyclerView;

    private Context context;

    private ProgressBar progressBar;

    private AnimalAdapter animalAdapter;

    private Gson gson;

    private static final String ANIMAL_DATA = "animal_data";
    private static final String DATA = "data";

    private FirebaseFirestore firestore;

    @Override
    public void onAttach(Context context) {
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,3);
        recyclerView.setLayoutManager(gridLayoutManager);
        progressBar = view.findViewById(R.id.animal_progress);
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
            animalAdapter.notifyDataSetChanged();
        }

        animalAdapter = new AnimalAdapter(context);

        animalAdapter.setData(dataArray);

        recyclerView.setAdapter(animalAdapter);

        animalAdapter.setOnAnimalItemClickListener(new AnimalAdapter.OnAnimalItemClickListener() {
            @Override
            public void onClick(AnimalObject data) {
                presenter.onAnimalItemClickListener(data);
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
    }

    @Override
    public String getDogStr() {
        return getActivity() != null ? getActivity().getString(R.string.dog) : "";
    }
}
