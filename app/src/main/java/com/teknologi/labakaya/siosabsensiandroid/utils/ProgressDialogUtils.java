package com.teknologi.labakaya.siosabsensiandroid.utils;


import android.app.ProgressDialog;
import android.content.Context;

import com.teknologi.labakaya.siosabsensiandroid.R;

public class ProgressDialogUtils {

    private static ProgressDialog progressDialog;

    public ProgressDialogUtils(Context context, String title, String message){
        progressDialog = new ProgressDialog(context, R.style.AppCompatAlertDialogStyle);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCancelable( false );
    }

    public void dissmisProgress() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public void showProgress() {
        if (progressDialog != null)
            progressDialog.show();
    }


}