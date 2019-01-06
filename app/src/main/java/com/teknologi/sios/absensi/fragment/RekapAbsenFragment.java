package com.teknologi.sios.absensi.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teknologi.sios.absensi.R;

public class RekapAbsenFragment extends Fragment {

    public RekapAbsenFragment() {
        // Required empty public constructor
    }

    public static RekapAbsenFragment newInstance() {
        RekapAbsenFragment fragment = new RekapAbsenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rekap_absen, container, false);
    }
}
