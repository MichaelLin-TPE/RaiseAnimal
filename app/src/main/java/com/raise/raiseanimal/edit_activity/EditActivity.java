package com.raise.raiseanimal.edit_activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.raise.raiseanimal.MainActivity;
import com.raise.raiseanimal.R;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;
import com.raise.raiseanimal.tool.GlideEngine;
import com.raise.raiseanimal.tool.ImageLoaderManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditActivity extends AppCompatActivity implements EditActivityVu{

    private EditActivityPresenter presenter;

    private Button btnName,btnPhoto,btnPersonality,btnStory,btnSave;

    private TextView tvNumber,tvName,tvPersonality,tvStory;

    private ImageView ivPhoto,ivAnimalPhoto;

    private ArrayList<AnimalObject> dataArray;

    private AnimalObject data;

    private int itemPosition;

    private ArrayList<Bitmap> bitmapArrayList;

    private FirebaseFirestore firestore;

    private StorageReference river;

    private static final String ANIMAL_DATA = "animal_data";
    private static final String DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        river = FirebaseStorage.getInstance().getReference();
        firestore = FirebaseFirestore.getInstance();
        initPresenter();
        initView();
        initBundle();
        presenter.onShowTitle(data);
        presenter.onCatchAllData(dataArray,data,itemPosition);
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.edit_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBackButtonClickListener();
            }
        });
        ivAnimalPhoto = findViewById(R.id.edit_animal_photo);
        ivPhoto = findViewById(R.id.edit_photo);
        tvName = findViewById(R.id.edit_name);
        tvPersonality = findViewById(R.id.edit_personality);
        tvStory = findViewById(R.id.edit_story);
        tvNumber = findViewById(R.id.edit_info);
        btnName = findViewById(R.id.edit_btn_name);
        btnPhoto = findViewById(R.id.edit_btn_photo);
        btnPersonality = findViewById(R.id.edit_btn_personality);
        btnStory = findViewById(R.id.edit_btn_story);
        btnSave = findViewById(R.id.edit_btn_save);

        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBtnNameClickListener();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(EditActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(1)
                        .compress(true)
                        .enableCrop(true)
                        .hideBottomControls(false)
                        .showCropFrame(false)
                        .freeStyleCropEnabled(true)
                        .forResult(new OnResultCallbackListener() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                bitmapArrayList = new ArrayList<>();

                                for (int i = 0; i < result.size(); i++) {
                                    File file = new File(result.get(i).getCutPath());
                                    Uri uri = Uri.fromFile(file);
                                    try {
                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                                        bitmapArrayList.add(bitmap);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream(bitmapArrayList.get(0).getByteCount());
                                bitmapArrayList.get(0).compress(Bitmap.CompressFormat.JPEG, 60, outputStream);
                                byte[] photoBytes = outputStream.toByteArray();
                                presenter.onCatchPhoto(bitmapArrayList,photoBytes);
                            }
                        });
            }
        });
        btnPersonality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBtnPersonalityClickListener();
            }
        });
        btnStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBtnStoryClickListener();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onBtnSaveClickListener();
            }
        });
    }

    private void initBundle() {
        Intent it = getIntent();
        Bundle bundle = it.getExtras();
        if (bundle != null){
            data = (AnimalObject) bundle.getSerializable("data");
            dataArray = (ArrayList<AnimalObject>) bundle.getSerializable("dataArray");
            itemPosition = bundle.getInt("itemPosition");
        }
    }

    private void initPresenter() {
        presenter = new EditActivityPresenterImpl(this);
    }

    @Override
    public void showEditNameDialog() {
        final EditText editText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.change_name))
                .setView(editText)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onEditDialogConfirmClickListener(editText.getText().toString());
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showName(String name) {
        tvName.setVisibility(View.VISIBLE);
        tvName.setText(String.format(Locale.getDefault(),"更改後名字為 : %s",name));
    }

    @Override
    public void showPhoto(Bitmap bitmap) {
        ivPhoto.setVisibility(View.VISIBLE);
        ivPhoto.setImageBitmap(bitmap);
    }

    @Override
    public void showTitle(String animalId) {
        tvNumber.setText(String.format(Locale.getDefault(),"目前更改狗狗的編號為 : %s",animalId));
    }

    @Override
    public void showPersonalityDialog() {
        EditText editText = new EditText(this);
        editText.setHint(R.string.enter_personality);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.change_personality))
                .setView(editText)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onPersonalityDialogConfirmClickListener(editText.getText().toString());
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }

    @Override
    public void showPersonality(String personality) {
        tvPersonality.setVisibility(View.VISIBLE);
        tvPersonality.setText(String.format(Locale.getDefault(),"更改後的個性TAG : %s",personality));
    }

    @Override
    public void showStoryDialog() {
        EditText editText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.changet_story))
                .setView(editText)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.onStoryDialogConfirmListener(editText.getText().toString());
                    }
                }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.show();
    }

    @Override
    public void showStory(String story) {
        tvStory.setVisibility(View.VISIBLE);
        tvStory.setText(String.format(Locale.getDefault(),"更改後的故事為 : \n%s",story));
    }

    @Override
    public void uploadPhotoToStorage(byte[] photoBytes) {
        StorageReference storage = river.child(data.getAnimalId()+"/"+"photo"+data.getAnimalId()+".jpg");
        UploadTask task = storage.putBytes(photoBytes);
        task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storage.getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                presenter.onCatchDownloadUrl(uri.toString());
                            }
                        });
            }
        });
    }

    @Override
    public void saveUpdateData(String jsonStr) {

        Map<String,Object> map = new HashMap<>();
        map.put("json",jsonStr);
        firestore.collection(ANIMAL_DATA)
                .document(DATA)
                .set(map, SetOptions.merge());
        presenter.onUpdateSuccessful();

    }

    @Override
    public void closePage() {
        finish();
    }

    @Override
    public void enableButton(boolean isEnable) {
        btnSave.setEnabled(!isEnable);
    }

    @Override
    public void showAnimalsDog(AnimalObject data) {
        ImageLoaderManager.getInstance(this).setPhotoUrl(data.getAlbumFile(),ivAnimalPhoto);
    }

}
