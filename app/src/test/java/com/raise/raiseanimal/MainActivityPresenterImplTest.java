package com.raise.raiseanimal;

import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MainActivityPresenterImplTest {

    @Mock
    private MainActivityPresenterImpl presenter;
    @Mock
    private MainActivityVu mView;

    private ArrayList<String> imageArray;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MainActivityPresenterImpl(mView);
        imageArray = new ArrayList<>();
        presenter.setImageArray(imageArray);
    }

    @Test
    public void onCatchData() {
        AnimalObject data = new AnimalObject();
        data.setAlbumFile("XXXX");
        ArrayList<AnimalObject> dataArray = new ArrayList<>();
        dataArray.add(data);
        presenter.onCatchData(dataArray);
        imageArray.add(dataArray.get(0).getAlbumFile());
        Mockito.verify(mView).showPointer(imageArray.size());
        Mockito.verify(mView).showPhoto(imageArray);
    }

    @Test
    public void onShowAlertDialog() {
        boolean isShowDialog = true;
        presenter.onShowAlertDialog(isShowDialog);
        Mockito.verify(mView).showDialog();
    }

    @Test
    public void onInfoBtnClickListener() {
        presenter.onInfoBtnClickListener();
        Mockito.verify(mView).showDialog();
    }

    @Test
    public void onDialogButtonClickListener() {
        presenter.onDialogButtonClickListener();
        Mockito.verify(mView).closeDialog();
    }

    @Test
    public void onCheckedChangeListener() {
        boolean isCheck = true;
        presenter.onCheckedChangeListener(isCheck);
        Mockito.verify(mView).saveDialogShowCheck(isCheck);
    }
}