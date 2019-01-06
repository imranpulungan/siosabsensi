package com.teknologi.sios.absensi.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.teknologi.sios.absensi.R;
import com.teknologi.sios.absensi.entity.User;
import com.teknologi.sios.absensi.fragment.BarcodeScannerFargment;
import com.teknologi.sios.absensi.fragment.HomeFragment;
import com.teknologi.sios.absensi.fragment.RekapAbsenFragment;
import com.teknologi.sios.absensi.utils.Session;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GoalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Fragment fragmentContent;
    private int selectedMenu;
    boolean doubleBack = false;
    DrawerLayout drawer;

    User dataUser;
    String role;

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
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataUser = (User) getIntent().getSerializableExtra("dataUser");
        role = getIntent().getStringExtra("roleUser");

        tvCompanyName.setText(dataUser.getName());

        Picasso.with(this)
                .load("https://silbusiness.com/sios_management/SIOS_FILE/" + dataUser.getFoto())
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
                .load("https://silbusiness.com/sios_management/SIOS_FILE/" + dataUser.getFoto())
                .resize(150,200)
                .placeholder(R.drawable.icon)
                .into(imgSideMenu);

        if(role.equals("employee")) {
            selectMenu(R.id.nav_home);
            navigationView.getMenu().findItem(R.id.nav_home).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_rekap_absen).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_camera).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_rekap_pegawai).setVisible(false);
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
            tvCompanyMenu.setText(dataUser.getAlamat());
            tvUsernameMenu.setText(dataUser.getName());
            cardLogo.setVisibility(View.VISIBLE);
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
                            Session.with(GoalActivity.this).clearLoginSession();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(GoalActivity.this, "Logout Canceled", Toast.LENGTH_SHORT).show();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
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
                fragmentContent = BarcodeScannerFargment.newInstance();
            }else if(id == R.id.nav_rekap_absen){
                setTitle(R.string.rekap_absen);
                fragmentContent = RekapAbsenFragment.newInstance();
            }

            FragmentManager frgManager = getSupportFragmentManager();
            Bundle bundle = new Bundle();
            bundle.putSerializable("dataUser", dataUser);
            fragmentContent.setArguments(bundle);
            frgManager.beginTransaction().replace(R.id.content_fragment, fragmentContent).commit();
        }
    }

}
