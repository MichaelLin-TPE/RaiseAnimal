package com.raise.raiseanimal.basic_information;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raise.raiseanimal.R;


public class BasicInfoFragment extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_staff, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


}
