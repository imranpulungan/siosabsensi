package com.teknologi.labakaya.siosabsensiandroid.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.teknologi.labakaya.siosabsensiandroid.R;
import com.teknologi.labakaya.siosabsensiandroid.entity.User;
import com.teknologi.labakaya.siosabsensiandroid.fragment.BarcodeScannerFargment;
import com.teknologi.labakaya.siosabsensiandroid.fragment.ChangePasswordFragment;
import com.teknologi.labakaya.siosabsensiandroid.fragment.HomeFragment;
import com.teknologi.labakaya.siosabsensiandroid.fragment.RekapAbsenFragment;
import com.teknologi.labakaya.siosabsensiandroid.fragment.RekapPegawaiFragment;
import com.teknologi.labakaya.siosabsensiandroid.utils.Session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE };
    private String url = "https://sios-sil.silbusiness.com/SIOS_FILE/";

    private Fragment fragmentContent;
    private int selectedMenu;
    boolean doubleBack = false;
    DrawerLayout drawer;

    User dataUser;
    String role, type ="LEMBUR";

    @BindView(R.id.nav_view)
    protected NavigationView navigationView;

    @BindView(R.id.img_company)
    protected ImageView imgCompany;

    @BindView(R.id.tv_company_name)
    protected TextView tvCompanyName;

    @BindView(R.id.tv_scan_text)
    protected TextView tvScanText;

    @BindView(R.id.card_logo)
    protected CardView cardLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        checkPermissions();
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataUser = (User) getIntent().getSerializableExtra("dataUser");
        role = getIntent().getStringExtra("roleUser");

        tvCompanyName.setText(dataUser.getName());

        Picasso.with(this)
                .load(url + dataUser.getFoto())
                .placeholder(R.drawable.icon)
                .into(imgCompany);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        ImageView imgSideMenu = (ImageView) header.findViewById(R.id.imageView);
        TextView tvCompanyMenu = (TextView) header.findViewById(R.id.tv_company_name_menu);
        TextView tvUsernameMenu = (TextView) header.findViewById(R.id.tv_username_menu);


        Picasso.with(this)
                .load(url + dataUser.getFoto())
                .resize(150,200)
                .placeholder(R.drawable.icon)
                .into(imgSideMenu);

        if(role.equals("employee")) {
            selectMenu(R.id.nav_home);
            navigationView.getMenu().findItem(R.id.nav_home).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_rekap_absen).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_camera).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_rekap_pegawai).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_chooice_type).setVisible(false);
            tvCompanyName.setVisibility(View.GONE);
            imgCompany.setVisibility(View.GONE);
            tvScanText.setVisibility(View.GONE);
            tvCompanyMenu.setText(dataUser.getJabatan());
            tvUsernameMenu.setText(dataUser.getNama_lengkap());
        }else if(role.equals("company")){
            selectMenu(R.id.nav_camera);
            navigationView.getMenu().findItem(R.id.nav_home).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_rekap_absen).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_camera).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_rekap_pegawai).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_chooice_type).setVisible(true);
            tvCompanyMenu.setText(dataUser.getAlamat());
            tvUsernameMenu.setText(dataUser.getName());
            cardLogo.setVisibility(View.VISIBLE);
        }


    }

    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<String>();
        // check all required dynamic permissions
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            // request all missing permissions
            final String[] permissions = missingPermissions
                    .toArray(new String[missingPermissions.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        // exit the app if one permission is not granted
                        Toast.makeText(this, "Required permission '" + permissions[index]
                                + "' not granted, exiting", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            if (selectedMenu != R.id.nav_camera){
//                navigationView.setCheckedItem(R.id.nav_camera);
//                selectMenu(R.id.nav_camera);
//            }else{
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
//            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.goal, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_refresh) {
//
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Menu m = navigationView.getMenu();
        if (id == R.id.nav_logout){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle(R.string.value_logout);
            builder.setMessage("Yakin ingin keluar");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            Toast.makeText(MainActivity.this, "Logout Success", Toast.LENGTH_SHORT).show();
                            Session.with(MainActivity.this).clearLoginSession();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "Logout Canceled", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return false;
        }else if(id == R.id.nav_chooice_type ){
            View radioButtonView = View.inflate(this, R.layout.checkbox, null);
            final RadioButton rbMasuk  = (RadioButton) radioButtonView.findViewById(R.id.rb_masuk);
            final RadioButton rbPulang = (RadioButton) radioButtonView.findViewById(R.id.rb_pulang);
            final RadioButton rbLembur = (RadioButton) radioButtonView.findViewById(R.id.rb_lembur);

            rbMasuk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (rbMasuk.isChecked()){
                        rbMasuk.setChecked(true);
                        type = "MASUK";
                        rbPulang.setChecked(false);
                        rbLembur.setChecked(false);
                    }
                }
            });

            rbPulang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (rbPulang.isChecked()){
                        rbPulang.setChecked(true);
                        type = "PULANG";
                        rbMasuk.setChecked(false);
                        rbLembur.setChecked(false);
                    }
                }
            });

            rbLembur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (rbLembur.isChecked()){
                        rbLembur.setChecked(true);
                        type = "LEMBUR";
                        rbPulang.setChecked(false);
                        rbMasuk.setChecked(false);
                    }
                }
            });

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setTitle(R.string.chooice_type);
            builder.setView(radioButtonView);
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, type, Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();

            if ( dialog!=null && dialog.isShowing()){
                dialog.dismiss();
            }else{
                dialog.show();
            }
            return false;
        }else{
            selectMenu(id);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    //jabatan, masa penugasan, nama perusahaan

    private void selectMenu(int id){
        if (selectedMenu != id){
            selectedMenu = id;
            if(id == R.id.nav_home){
                setTitle(R.string.home);
                fragmentContent = new HomeFragment();
            }else if (id == R.id.nav_camera) {
                setTitle(R.string.scanbarcode);
                tvCompanyName.setVisibility(View.VISIBLE);
                imgCompany.setVisibility(View.VISIBLE);
                tvScanText.setVisibility(View.VISIBLE);
                cardLogo.setVisibility(View.VISIBLE);
                fragmentContent = BarcodeScannerFargment.newInstance();
            }else if(id == R.id.nav_rekap_absen){
                setTitle(R.string.rekap_absen);
                fragmentContent = RekapAbsenFragment.newInstance();
            }else if(id == R.id.nav_rekap_pegawai){
                setTitle(R.string.rekap_pegawai);
                tvCompanyName.setVisibility(View.GONE);
                imgCompany.setVisibility(View.GONE);
                tvScanText.setVisibility(View.GONE);
                cardLogo.setVisibility(View.GONE);
                fragmentContent = RekapPegawaiFragment.newInstance();
            }else if(id == R.id.nav_change_password){
                setTitle(R.string.change_password);
                tvCompanyName.setVisibility(View.GONE);
                imgCompany.setVisibility(View.GONE);
                tvScanText.setVisibility(View.GONE);
                cardLogo.setVisibility(View.GONE);
                fragmentContent = ChangePasswordFragment.newInstance();
            }

            FragmentManager frgManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataUser", dataUser);
            bundle.putString("role", role);
            bundle.putString("type", type);
            fragmentContent.setArguments(bundle);
            frgManager.beginTransaction().replace(R.id.content_fragment, fragmentContent).commit();
        }
    }

}
