package com.teknologi.sios.absensi.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.teknologi.sios.absensi.R;
import com.teknologi.sios.absensi.api.response.ApiResponse;
import com.teknologi.sios.absensi.api.response.AuthResponse;
import com.teknologi.sios.absensi.api.response.UserResponse;
import com.teknologi.sios.absensi.core.AppActivity;
import com.teknologi.sios.absensi.presenter.Presenter;
import com.teknologi.sios.absensi.presenter.iPresenterResponse;
import com.teknologi.sios.absensi.utils.Session;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashSecreenActivity extends AppActivity implements iPresenterResponse {
    private static int SPLASH_TIME_OUT = 2000;

    @BindView(R.id.tv_vision)
    protected TextView tvVision;
    @BindView(R.id.tv_copyright)
    protected TextView tvCopyright;

    Presenter mPresenter;
    Intent intent;

    public SplashSecreenActivity() {
        super(R.layout.activity_splash_secreen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tvVision.setText(R.string.app_name);
        tvCopyright.setText(R.string.copyright);
        mPresenter = new Presenter(this, this);
        if (isGooglePlayServicesAvailable()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Session.with(getApplicationContext()).isSignIn()) {
                        callMain();
                    }else{
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }, SPLASH_TIME_OUT);
        }
    }

    private void callMain(){
        mPresenter.isLogged();
    }

    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void doSuccess(ApiResponse response, String tag) {
        if (tag.equals(Presenter.RES_IS_LOGGED)){
            AuthResponse dataresp = ((AuthResponse)response);
            if (dataresp.data.size() > 0){
                intent = new Intent(getApplicationContext(), GoalActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("dataUser", dataresp.data.get(0));
                intent.putExtra("roleUser", dataresp.role);
                startActivity(intent);
                finish();
            }else{
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void doValidationError(boolean error) {

    }

    @Override
    public void doConnectionError() {
        super.doConnectionError();
    }
}
