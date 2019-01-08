package com.teknologi.labakaya.siosabsensiandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.teknologi.labakaya.siosabsensiandroid.R;
import com.teknologi.labakaya.siosabsensiandroid.api.response.ApiResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.AuthResponse;
import com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter;
import com.teknologi.labakaya.siosabsensiandroid.presenter.iPresenterResponse;
import com.teknologi.labakaya.siosabsensiandroid.utils.ProgressDialogUtils;
import com.teknologi.labakaya.siosabsensiandroid.utils.Session;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter.RES_LOGIN;

public class LoginActivity extends AppCompatActivity implements iPresenterResponse {

    Presenter mPresenter;

    @BindView(R.id.edit_username)
    protected EditText editUsername;

    @BindView(R.id.edit_password)
    protected EditText editPassword;

    @BindView(R.id.btn_login)
    protected Button btnLogin;

    @BindView(R.id.radio_option)
    protected RadioGroup radioOption;

    @BindView(R.id.radio_employee)
    protected RadioButton radioOptionButton;

    ProgressDialogUtils progressDialogUtils;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initObject();
        initEvent();
    }

    private void initObject() {
        progressDialogUtils = new ProgressDialogUtils(this, null, "Loading...");
        mPresenter = new Presenter(this, this);
    }

    private void initEvent() {
        radioOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup group, int checkedId) {
                  radioOptionButton = (RadioButton) findViewById(checkedId);
              }
          }
        );

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editUsername.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, R.string.username_empty,Toast.LENGTH_SHORT).show();
                }else if(editPassword.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, R.string.password_empty,Toast.LENGTH_SHORT).show();
                }else{
                    if (radioOptionButton.getId() == R.id.radio_employee){
                        query = "SELECT * FROM data_penugasan_pegawai WHERE username='"+editUsername.getText().toString()+"' AND password='"+editPassword.getText().toString()+"'";
                    }else if(radioOptionButton.getId() == R.id.radio_company){
                        query = "SELECT  aiu as id_penugasan, nama_perusaaan as name, foto, username FROM si_pelanggan WHERE username='"+editUsername.getText().toString()+"' AND password='"+editPassword.getText().toString()+"'";
                    }
                    Map<String, String> data = new HashMap<>();
                    data.put("niagait_post", query);
                    data.put("role", radioOptionButton.getText().toString().toLowerCase());
                    progressDialogUtils.showProgress();
                    mPresenter.postLogin(data);
                }
            }
        });
    }

    @Override
    public void doSuccess(ApiResponse response, String tag) {
        if (tag.equals(RES_LOGIN)){
            if (response.status){
                AuthResponse resp = (AuthResponse) response;
                Session.with(getApplicationContext()).createLoginSession(resp.token);
                Intent  intent = new Intent(LoginActivity.this, MainActivity.class );
                intent.putExtra("dataUser", resp.data.get(0));
                intent.putExtra("roleUser", resp.role);
                startActivity(intent);
                finish();
            }else
                Toast.makeText(this, R.string.username_password_validation, Toast.LENGTH_SHORT).show();
            progressDialogUtils.dissmisProgress();
        }
    }

    @Override
    public void doFail(String message) {
        Toast.makeText(LoginActivity.this, message,Toast.LENGTH_SHORT).show();
        progressDialogUtils.dissmisProgress();
    }

    @Override
    public void doValidationError(boolean error) {
    }

    @Override
    public void doConnectionError() {
        Toast.makeText(LoginActivity.this, R.string.network_error,Toast.LENGTH_SHORT).show();
        progressDialogUtils.dissmisProgress();
    }
}
