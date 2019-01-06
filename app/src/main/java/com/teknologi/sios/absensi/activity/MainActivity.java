package com.teknologi.sios.absensi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.teknologi.sios.absensi.R;
import com.teknologi.sios.absensi.api.response.ApiResponse;
import com.teknologi.sios.absensi.core.AppActivity;
import com.teknologi.sios.absensi.presenter.iPresenterResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppActivity implements iPresenterResponse {

    private static final int SCAN_BARCODE = 1945;

//    private Presenter mPresenter;

    @BindView(R.id.btn_scann)
    protected LinearLayout btnScann;

    boolean doubleBack = false;

    public MainActivity() {
        super(R.layout.activity_main, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initObject();
        initEvent();
    }

    @Override
    public void onBackPressed(){
        if (doubleBack) {
            super.onBackPressed();
            return;
        }

        this.doubleBack = true;
        Toast.makeText(this, "Tekan tombol back sekali lagi untuk keluar.",
                Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBack = false;
            }
        }, 2000);
    }

    private void initEvent() {
        btnScann.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanBarcodeActivity.class);
                startActivityForResult(intent, SCAN_BARCODE);
            }
        });
    }

    private void initObject() {
//        mPresenter = new Presenter(this, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_BARCODE) { // Please, use a final int instead of hardcoded int value
            if (resultCode == RESULT_OK) {
                String value = (String) data.getExtras().getString("data");
//                Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RedirectActivity.class);
                intent.putExtra("id_pegawai", value);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void doSuccess(ApiResponse response, String tag) {

    }

    @Override
    public void doFail(String message) {

    }

    @Override
    public void doValidationError(boolean error) {

    }

    @Override
    public void doConnectionError() {

    }
}
