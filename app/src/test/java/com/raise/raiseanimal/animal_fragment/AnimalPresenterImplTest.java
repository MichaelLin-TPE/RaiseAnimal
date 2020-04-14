package com.raise.raiseanimal.animal_fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.raise.raiseanimal.connect.HttpConnection;
import com.raise.raiseanimal.connect.gson_object.AnimalObject;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.*;

public class AnimalPresenterImplTest {

    @Mock
    private AnimalPresenterImpl presenter;

    @Mock
    private AnimalVu mView;

    private ArrayList<AnimalObject> catchArray;

    private ArrayList<AnimalObject> dataArray;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new AnimalPresenterImpl(mView);
        catchArray = new ArrayList<>();
        dataArray = new ArrayList<>();
        presenter.setOnCatchArray(catchArray);
        presenter.setDataArray(dataArray);
    }

    @Test
    public void startToCatchData() {


        presenter.startToCatchData();
        HttpConnection httpConnection = new HttpConnection();
        httpConnection.execute("https://data.coa.gov.tw/Service/OpenData/TransService.aspx?UnitId=QcbUEzN6E6DL");
        httpConnection.setOnHttpConnectionListener(new HttpConnection.OnHttpConnectionListener() {
            @Override
            public void onSuccess(String result) {
                AnimalObject data = new AnimalObject();
                data.setAlbumFile("XXX");
                catchArray.add(data);
                dataArray.add(catchArray.get(0));
                Mockito.verify(mView).saveJsonToFirebase("XXXX");
            }

            @Override
            public void onFailure(String errorCode) {

            }
        });

    }

    @Test
    public void catchData() {
        ArrayList<AnimalObject> dataArray = new ArrayList<>();
        AnimalObject data = new AnimalObject();
        data.setAlbumFile("XXX");
        dataArray.add(data);
        presenter.catchData(dataArray);
        Mockito.verify(mView).showProgress(false);
        Mockito.verify(mView).setRecyclerView(dataArray);
    }

    @Test
    public void onShowProgress() {
        presenter.onShowProgress(true);
        Mockito.verify(mView).showProgress(Mockito.anyBoolean());
    }

    @Test
    public void catchNewData() {
        ArrayList<AnimalObject> dataArray = new ArrayList<>();
        AnimalObject data = new AnimalObject();
        data.setAlbumFile("XXX");
        dataArray.add(data);
        presenter.catchNewData(dataArray);
        Mockito.verify(mView).showProgress(false);
        Mockito.verify(mView).setRecyclerView(dataArray);
    }

    @Test
    public void onAnimalItemClickListener() {
        AnimalObject data = new AnimalObject();
        data.setAlbumFile("xxxx");
        presenter.onAnimalItemClickListener(data);
        Mockito.verify(mView).intentToDetailPage(data);
    }
}