package com.teknologi.labakaya.siosabsensiandroid.core;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.teknologi.labakaya.siosabsensiandroid.R;


public class AppActivity extends AppCompatActivity {
    protected Boolean enableBackButton;
    protected int resLayoutId;

    public AppActivity(int resLayoutId){
        this.enableBackButton = false;
        this.resLayoutId = resLayoutId;
    }

    public AppActivity(int resLayoutId, Boolean enableBackButton){
        this.enableBackButton = enableBackButton;
        this.resLayoutId = resLayoutId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.resLayoutId);

        if (this.enableBackButton) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }

    public void doConnectionError() {
//        Toast.makeText(this, R.string.network_error, Toast.LENGTH_SHORT).show();
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.network_error, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public void doFail(String message) {
//        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar.dismiss();
            }
        });
        snackbar.show();
    }

    public void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
