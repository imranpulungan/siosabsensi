package com.teknologi.labakaya.siosabsensiandroid.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.teknologi.labakaya.siosabsensiandroid.R;
import com.teknologi.labakaya.siosabsensiandroid.activity.LoginActivity;
import com.teknologi.labakaya.siosabsensiandroid.api.response.ApiResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.InfoIPResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.UserResponse;
import com.teknologi.labakaya.siosabsensiandroid.core.AppFragment;
import com.teknologi.labakaya.siosabsensiandroid.entity.User;
import com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter;
import com.teknologi.labakaya.siosabsensiandroid.presenter.iPresenterResponse;
import com.teknologi.labakaya.siosabsensiandroid.utils.DateUtils;
import com.teknologi.labakaya.siosabsensiandroid.utils.ProgressDialogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter.RES_ABSEN_PROSES;
import static com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter.RES_CHANGE_PASSWORD;
import static com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter.RES_CHECK_IP;
import static com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter.RES_GET_DATA;

public class ChangePasswordFragment extends AppFragment implements iPresenterResponse {

    private Fragment fragmentContent;

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

    @BindView(R.id.layout_change)
    protected LinearLayout layoutChange;

    @BindView(R.id.layout_verify)
    protected LinearLayout layoutVerify;

    User dataUser;
    String role;
    Presenter mPresenter;

    ProgressDialogUtils progressDialogUtils;

    public ChangePasswordFragment() {
        super();
        this.res_layout = R.layout.activity_change_password;
    }

    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            dataUser = (User) getArguments().getSerializable("dataUser");
            role = getArguments().getString("role");


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        initObject();
        initEvent();
        return view;
    }


    private void initEvent() {
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryVerify;
                if(role.equals("employee")) {
                    queryVerify = "SELECT * FROM data_penugasan_pegawai WHERE username='"+ dataUser.getUsername() +"' AND password='"+ editOldPassword.getText().toString()+"'";
                }else{
                    queryVerify = "SELECT * FROM si_pelanggan WHERE username='"+ dataUser.getUsername() +"' AND password='"+ editOldPassword.getText().toString()+"'";
                }
                Map<String, String> data = new HashMap<>();
                data.put("niagait_post", queryVerify);
                progressDialogUtils.showProgress();
                mPresenter.getData(data);
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editNewPassword.getText().toString().equals(editNewPasswordConfirm.getText().toString())){
                    Map<String, String> data = new HashMap<>();
                    data.put("username", dataUser.getUsername());
                    data.put("role", role);
                    data.put("new_password", editNewPassword.getText().toString());
                    progressDialogUtils.showProgress();
                    mPresenter.postChangePassword(data);
                }else{
                    Toast.makeText(getContext(), "New Password Not Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initObject() {
        progressDialogUtils = new ProgressDialogUtils(getContext(), null, "Loading");
        mPresenter = new Presenter(getContext(), this);
    }

    @Override
    public void doSuccess(ApiResponse response, String tag) {
        if (tag.equals(RES_GET_DATA)){
            if (response.status){
                UserResponse resp = (UserResponse) response;
                if (resp.data.size() > 0){
                    layoutChange.setVisibility(View.VISIBLE);
                    layoutVerify.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getContext(), "Old Password not valid", Toast.LENGTH_SHORT).show();
                }
            }
        }else if(tag.equals(RES_CHANGE_PASSWORD)){
            Toast.makeText(getContext(), response.message, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }, 1500);
        }
        progressDialogUtils.dissmisProgress();
    }

    @Override
    public void doFail(String message) {
        super.doFail(message);
        progressDialogUtils.dissmisProgress();
    }

    @Override
    public void doConnectionError() {
        super.doConnectionError();
        progressDialogUtils.dissmisProgress();
    }

    @Override
    public void doValidationError(boolean error) {
    }
}
