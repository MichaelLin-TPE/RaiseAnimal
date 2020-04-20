package com.raise.raiseanimal.staff_place;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raise.raiseanimal.R;
import com.raise.raiseanimal.animal_fragment.filter.FilterAdapter;
import com.raise.raiseanimal.animal_fragment.filter.FilterItemAdapter;
import com.raise.raiseanimal.animal_fragment.filter.FilterPresenter;
import com.raise.raiseanimal.animal_fragment.filter.FilterPresenterImpl;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.edit_activity.EditActivity;
import com.raise.raiseanimal.tool.UserDataManager;

import java.util.ArrayList;
import java.util.List;

public class StaffActivity extends AppCompatActivity implements StaffActivityVu{
    
    private StaffActivityPresenter presenter;
    
    private EditText etAccount,etPassword;

    private RecyclerView recyclerView,rvFilter;

    private Button btnLogin;

    private TextView tvTitle,tvAccount,tvPassword;

    private FirebaseFirestore firestore;

    private ProgressBar progressBar;

    private static final String ANIMAL_DATA = "animal_data";
    private static final String DATA = "data";

    private ArrayList<AnimalObject> dataArray;

    private Gson gson;

    private StaffAdapter adapter;

    private FilterPresenter filterPresenter;

    private ImageView ivIcon;

    private TextView tvSearchInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        firestore = FirebaseFirestore.getInstance();
        gson = new Gson();
        initPresenter();
        initView();
        String account = UserDataManager.getInstance(this).getAcccount();
        String password = UserDataManager.getInstance(this).getPassword();
        presenter.onLoginButtonClickListener(account,password);

    }

    private void initPresenter() {
        presenter = new StaffActivityPresenterImpl(this);
        filterPresenter = new FilterPresenterImpl();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.staff_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackButtonClickListener();
            }
        });

        ivIcon = findViewById(R.id.staff_icon);
        tvSearchInfo = findViewById(R.id.staff_search_info);
        progressBar = findViewById(R.id.staff_progress);
        tvTitle = findViewById(R.id.staff_title);
        tvAccount = findViewById(R.id.staff_account);
        tvPassword = findViewById(R.id.staff_password);
        etAccount = findViewById(R.id.staff_edit_account);
        etPassword = findViewById(R.id.staff_edit_password);
        recyclerView = findViewById(R.id.staff_recycler_view);
        rvFilter = findViewById(R.id.staff_filter_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        rvFilter.setLayoutManager(new LinearLayoutManager(this));
        btnLogin = findViewById(R.id.staff_btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                presenter.onLoginButtonClickListener(account,password);
            }
        });
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideLoginView(boolean isShow) {
        btnLogin.setVisibility(isShow ? View.GONE : View.VISIBLE);
        tvTitle.setVisibility(isShow ? View.GONE : View.VISIBLE);
        tvAccount.setVisibility(isShow ? View.GONE : View.VISIBLE);
        tvPassword.setVisibility(isShow ? View.GONE : View.VISIBLE);
        etAccount.setVisibility(isShow ? View.GONE : View.VISIBLE);
        etPassword.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showRecycler(boolean isShow) {
        recyclerView.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void saveAccountAndPassword(String account, String password) {
        UserDataManager.getInstance(this).saveAccountAndPassword(account,password);
    }

    @Override
    public void searchData() {
        firestore.collection(ANIMAL_DATA)
                .document(DATA)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful() && task.getResult() != null){
                            DocumentSnapshot snapshot = task.getResult();
                            if ((String)snapshot.get("json") != null){
                                dataArray = gson.fromJson((String)snapshot.get("json"),new TypeToken<List<AnimalObject>>(){}.getType());
                                if (dataArray != null){
                                    presenter.onCatchData(dataArray);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String account = UserDataManager.getInstance(this).getAcccount();
        String password = UserDataManager.getInstance(this).getPassword();
        presenter.onLoginButtonClickListener(account,password);
    }

    @Override
    public void showProgress(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setRecyclerView(ArrayList<AnimalObject> dataArray) {

        if (adapter != null){
            adapter.setData(dataArray);
            adapter.notifyDataSetChanged();
        }

        adapter = new StaffAdapter(this);
        adapter.setData(dataArray);
        recyclerView.setAdapter(adapter);
        adapter.setOnAnimalItemClickListener(new StaffAdapter.OnAnimalItemClickListener() {
            @Override
            public void onClick(AnimalObject data,int itemPosition) {
                presenter.onAnimalItemClickListener(data,itemPosition);
            }

            @Override
            public void onFavorite(AnimalObject data) {
                //這邊不需要用
            }
        });
    }

    @Override
    public void showFilterView(ArrayList<String> colorArray, ArrayList<String> noSexArray, ArrayList<String> sexArray, ArrayList<String> sizeArray) {
        filterPresenter.setColorData(colorArray);
        filterPresenter.setNoSexData(noSexArray);
        filterPresenter.setSexData(sexArray);
        filterPresenter.setSizeData(sizeArray);
        FilterAdapter filterAdapter = new FilterAdapter(filterPresenter,this);
        rvFilter.setAdapter(filterAdapter);
        filterAdapter.setOnFilterItemClickListener(new FilterItemAdapter.OnFilterItemClickListener() {
            @Override
            public void onClick(String name, String value) {
                presenter.onFilterItemClickListener(name,value);
            }
        });
    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void showSearchNoData(boolean isShow) {
        ivIcon.setVisibility(isShow ? View.VISIBLE : View.GONE);
        tvSearchInfo.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public void intentToEditPage(AnimalObject data, int itemPosition, ArrayList<AnimalObject> catchFirebaseArray) {
        Intent it = new Intent(this, EditActivity.class);
        it.putExtra("data",data);
        it.putExtra("dataArray",catchFirebaseArray);
        it.putExtra("itemPosition",itemPosition);
        startActivity(it);
    }
}
