package com.teknologi.sios.absensi.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.teknologi.sios.absensi.R;
import com.teknologi.sios.absensi.core.AppActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RedirectActivity extends AppActivity {

    private static final int SCAN_BARCODE = 1945;

    @BindView(R.id.web_view)
    protected WebView webView;

    @BindView(R.id.btn_scann)
    protected Button btnScann;

    String idPegawai;

    boolean doubleBack = false;

    public RedirectActivity() {
        super(R.layout.activity_redirect, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        idPegawai = getIntent().getStringExtra("id_pegawai");
        webView.loadUrl("https://web.pt-medan.go.id/siduk/index.php/peg/index/" + idPegawai);
//        webView.loadUrl("https://web.pt-medan.go.id/siduk/index.php/peg/index/9303934");

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
                Intent intent = new Intent(RedirectActivity.this, ScanBarcodeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCAN_BARCODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCAN_BARCODE) { // Please, use a final int instead of hardcoded int value
            if (resultCode == RESULT_OK) {
                String value = (String) data.getExtras().getString("data");
//                Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RedirectActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id_pegawai", value);
                startActivity(intent);
            }
        }
    }
}
