package com.teknologi.labakaya.siosabsensiandroid.core;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.teknologi.labakaya.siosabsensiandroid.R;
import com.teknologi.labakaya.siosabsensiandroid.api.response.ApiResponse;

import java.util.List;

public abstract class AppFragment extends Fragment {
    protected int res_layout;
    protected View view;

    protected ProgressBar progressBar;
    protected LinearLayout layoutDataNotAvailable;

    public AppFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.view = inflater.inflate(this.res_layout, container, false);
        return view;
    }

    protected View findViewById(int id){
        return view.findViewById(id);
    }

    public void doFail(String message) {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        if (layoutDataNotAvailable != null) layoutDataNotAvailable.setVisibility(View.VISIBLE);
//        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        final Snackbar snackbar = Snackbar.make(this.view, message, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public void doValidationError(List<ApiResponse.Error> errors) {
    }

    public void doConnectionError() {
        if (progressBar != null) progressBar.setVisibility(View.GONE);
        if (layoutDataNotAvailable != null) layoutDataNotAvailable.setVisibility(View.VISIBLE);
//        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
        final Snackbar snackbar = Snackbar.make(this.view, R.string.network_error, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public abstract void doSuccess(ApiResponse response, String tag);
}
