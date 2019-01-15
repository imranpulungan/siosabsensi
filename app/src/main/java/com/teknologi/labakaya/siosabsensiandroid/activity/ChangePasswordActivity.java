package com.teknologi.labakaya.siosabsensiandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.teknologi.labakaya.siosabsensiandroid.R;
import com.teknologi.labakaya.siosabsensiandroid.core.AppActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangePasswordActivity extends AppActivity {

    @BindView(R.id.edit_password_old)
    protected EditText editOldPassword;

    @BindView(R.id.edit_password_new)
    protected EditText editNewPassword;

    @BindView(R.id.edit_password_new_confirm)
    protected EditText editNewPasswordConfirm;

    @BindView(R.id.btn_change)
    protected Button btnChange;

    @BindView(R.id.btn_verify)
    protected Button btnVerify;


    public ChangePasswordActivity() {
        super(R.layout.activity_change_password, true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> data = new HashMap<>();
                data.put("niagait_post", "SELECT * From ");
            }
        });
    }
}
