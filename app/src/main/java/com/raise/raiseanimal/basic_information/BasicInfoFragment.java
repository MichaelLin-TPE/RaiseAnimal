package com.raise.raiseanimal.basic_information;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.raise.raiseanimal.R;


public class BasicInfoFragment extends Fragment {

    private Context context;

    private GoogleMap mMap;

    private MapView mapView;

    public static BasicInfoFragment newInstance() {
        BasicInfoFragment fragment = new BasicInfoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff, container, false);
        // Inflate the layout for this fragment
        initView(view,savedInstanceState);
        return view;
    }

    private void initView(View view, Bundle savedInstanceState) {
        mapView = view.findViewById(R.id.basic_map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception e){
            e.printStackTrace();
        }
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                LatLng sydney = new LatLng(24.995787, 121.448291);
                mMap.addMarker(new MarkerOptions().position(sydney).title("板橋動物之家"));

                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                                PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
                } else if (getActivity() != null){
                    ActivityCompat.requestPermissions(getActivity(),new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                    },0);
                }
                mMap.getUiSettings().setZoomControlsEnabled(true);  // 右下角的放大縮小功能
                mMap.getUiSettings().setCompassEnabled(true);       // 左上角的指南針，要兩指旋轉才會出現
                mMap.getUiSettings().setMapToolbarEnabled(true);    // 右下角的導覽及開啟 Google Map功能

                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));     // 放大地圖到 16 倍大
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
