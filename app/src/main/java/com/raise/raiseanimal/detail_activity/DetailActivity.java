package com.raise.raiseanimal.detail_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.raise.raiseanimal.R;
import com.raise.raiseanimal.animal_fragment.AnimalFavorite;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.detail_activity.view_presenter.ViewPresenter;
import com.raise.raiseanimal.detail_activity.view_presenter.ViewPresenterImpl;

public class DetailActivity extends AppCompatActivity implements DetailActivityVu{

    private RecyclerView recyclerView;

    private DetailActivityPresenter presenter;

    private ViewPresenter viewPresenter;

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initPresenter();
        initView();
        initBundle();
    }

    private void initBundle() {
        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        if (bundle != null){
            AnimalObject data = (AnimalObject) bundle.getSerializable("data");
            AnimalFavorite newData = (AnimalFavorite) bundle.getSerializable("newData");
            presenter.onCatchData(data,newData);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("測試");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackButtonClickListener();
            }
        });
        recyclerView = findViewById(R.id.detail_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initPresenter() {
        presenter = new DetailActivityPresenterImpl(this);
        viewPresenter = new ViewPresenterImpl();
    }

    @Override
    public void setRecyclerView(AnimalObject data, AnimalFavorite newData) {
        viewPresenter.setData(data,newData);
        DetailAdapter adapter = new DetailAdapter(this,viewPresenter);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void setTitle(String animalTitle) {
        if (animalTitle.isEmpty()){
            toolbar.setTitle(getString(R.string.not_provide));
        }else {
            toolbar.setTitle(animalTitle);
        }

    }

    @Override
    public void closePage() {
        finish();
    }
}
