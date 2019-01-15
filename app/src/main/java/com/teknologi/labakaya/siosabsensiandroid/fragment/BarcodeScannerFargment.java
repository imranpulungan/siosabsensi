package com.teknologi.labakaya.siosabsensiandroid.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.teknologi.labakaya.siosabsensiandroid.R;
import com.teknologi.labakaya.siosabsensiandroid.activity.AbsenActivity;
import com.teknologi.labakaya.siosabsensiandroid.api.response.ApiResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.UserResponse;
import com.teknologi.labakaya.siosabsensiandroid.core.AppFragment;
import com.teknologi.labakaya.siosabsensiandroid.entity.User;
import com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter;
import com.teknologi.labakaya.siosabsensiandroid.presenter.iPresenterResponse;
import com.teknologi.labakaya.siosabsensiandroid.utils.ProgressDialogUtils;
import com.teknologi.labakaya.siosabsensiandroid.utils.SoundService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter.RES_CHECK_CODE;

public class BarcodeScannerFargment extends AppFragment implements
        ZXingScannerView.ResultHandler, iPresenterResponse {



    private ZXingScannerView  mScannerView;

    Presenter mPresenter;

    ProgressDialogUtils progressDialogUtils;
    User dataUser;

    public BarcodeScannerFargment() {
        super();
        this.res_layout = R.layout.fragment_barcode_scanner;
    }

    public static BarcodeScannerFargment newInstance() {
        BarcodeScannerFargment fragment = new BarcodeScannerFargment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataUser = (User) getArguments().getSerializable("dataUser");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
//        ButterKnife.bind(this, view);
        progressDialogUtils = new ProgressDialogUtils(getContext(), null, "Loading...");
        mPresenter = new Presenter(getContext(), this);
        mScannerView = new ZXingScannerView(getActivity());
        mScannerView.startCamera(1);
        return mScannerView;
//        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
            Log.i("IsRefresh", "Yes");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        progressDialogUtils = new ProgressDialogUtils(getContext(), null, "Loading...");
        mScannerView.startCamera(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
    }

    @Override
    public void handleResult(Result result) {
        Map<String, String> data = new HashMap<>();
        data.put("code", result.getText().toString());
        data.put("id_penugasan", dataUser.getId_penugasan());
        progressDialogUtils.showProgress();
        mPresenter.checkQr(data);
    }


    @Override
    public void doSuccess(ApiResponse response, String tag) {
        if (tag.equals(RES_CHECK_CODE)){
            UserResponse resp = (UserResponse) response;
            if (resp.data.size() > 0){
                Intent intent = new Intent(getContext(), AbsenActivity.class);
                intent.putExtra("dataUser", resp.data.get(0));
                startActivity(intent);
//                setUserVisibleHint(true);
            }
            else{
//                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//                    builder.setMessage(R.string.dialog_qr_not_valid)
//                            .setTitle(R.string.dialog_qr);
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    AlertDialog dialog = builder.create();
//                    dialog.show();

                Intent intent = new Intent(getContext(), SoundService.class);
                intent.putExtra("jenis_absen", "BARCODE");
                getContext().startService(intent);
//                MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.pegawai_tidak_ditemukan);
//                mp.start();
                new Thread(runnable).start();//to work in Background
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        setUserVisibleHint(true);
//                    }
//                }, 500);
            }
            progressDialogUtils.dissmisProgress();
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            setUserVisibleHint(true);
        }
    };

    @Override
    public void doConnectionError() {
        super.doConnectionError();
        progressDialogUtils.dissmisProgress();
    }

    @Override
    public void doFail(String message) {
        super.doFail(message);
        progressDialogUtils.dissmisProgress();
    }

    @Override
    public void doValidationError(boolean error) {

    }
}
