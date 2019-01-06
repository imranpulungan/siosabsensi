package com.teknologi.sios.absensi.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.teknologi.sios.absensi.R;
import com.teknologi.sios.absensi.api.response.ApiResponse;
import com.teknologi.sios.absensi.api.response.InfoIPResponse;
import com.teknologi.sios.absensi.core.AppFragment;
import com.teknologi.sios.absensi.entity.User;
import com.teknologi.sios.absensi.presenter.Presenter;
import com.teknologi.sios.absensi.presenter.iPresenterResponse;
import com.teknologi.sios.absensi.utils.DateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.teknologi.sios.absensi.presenter.Presenter.RES_ABSEN_PROSES;
import static com.teknologi.sios.absensi.presenter.Presenter.RES_CHECK_IP;

public class HomeFragment extends AppFragment implements iPresenterResponse {

    private static final int OPEN_CAMERA = 101;

    @BindView(R.id.img_user)
    protected ImageView imgUser;

    @BindView(R.id.edit_nama)
    protected EditText editNama;

    @BindView(R.id.edit_no_identitas)
    protected EditText editNoIdentitas;

    @BindView(R.id.edit_kota_lahir)
    protected EditText editKotaLahir;

    @BindView(R.id.edit_tgl_lahir)
    protected EditText editTglLahir;

    @BindView(R.id.edit_agama)
    protected EditText editAgama;

    @BindView(R.id.edit_jenis_kelamin)
    protected EditText editJenisKelamin;

    @BindView(R.id.edit_no_hp)
    protected EditText editNoHp;

    @BindView(R.id.edit_email)
    protected EditText editEmail;

    @BindView(R.id.edit_ip_absen)
    protected EditText editIpAbsen;

    @BindView(R.id.edit_mulai_penugasan)
    protected EditText editMulaiPenugasan;

    @BindView(R.id.edit_akhir_penugasan)
    protected EditText editAkhirPenugasan;

    @BindView(R.id.edit_jabatan)
    protected EditText editJabatan;

    @BindView(R.id.edit_company_name)
    protected EditText editCompanyName;

    @BindView(R.id.tv_absen)
    protected TextView tvAbsen;

    @BindView(R.id.btn_absen)
    protected Button btnAbsen;

    @BindView(R.id.btn_masuk)
    protected Button btnMasuk;

    @BindView(R.id.btn_pulang)
    protected Button btnPulang;

    @BindView(R.id.btn_lembur)
    protected Button btnLembur;

    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    BottomSheetBehavior sheetBehavior;

    String jenis_absen;

    Uri imageItem;

    User dataUser;

    Presenter mPresenter;

    public HomeFragment() {
        super();
        this.res_layout = R.layout.fragment_home;
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, view);
        initObject();
        attachData();
        initEvent();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.goal, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh) {
            mPresenter.checkIP();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initEvent() {
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setPeekHeight(0);
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
//                        btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
//                        btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenis_absen = "MASUK";
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(cameraIntent, OPEN_CAMERA);
            }
        });

        btnPulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenis_absen = "PULANG";
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(cameraIntent, OPEN_CAMERA);
            }
        });

        btnLembur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jenis_absen = "LEMBUR";
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                startActivityForResult(cameraIntent, OPEN_CAMERA);
            }
        });

        btnAbsen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == OPEN_CAMERA){
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                imgUser.setImageBitmap(bitmap);
                saveBitmap(bitmap);
            }
        }
    }

    public RequestBody toRequestBody(String s){
        return RequestBody.create(MediaType.parse("text/plain"), s);
    }

    private void saveBitmap(Bitmap bitmap){
        ContextWrapper wrapper = new ContextWrapper(getContext());
        String root = Environment.getExternalStorageDirectory().toString();
        File file = new File(root);
        file.mkdir();
        file = new File(file, UUID.randomUUID().toString()+".jpg");
        try{
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
            imageItem = Uri.parse(file.getAbsolutePath());

            if (null != imageItem) {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                String generatedString = "SIOS_ABSENSI_" + dataUser.getAiu() +"_"+timestamp;
                File _file = new File(imageItem.getPath());
                Map<String, RequestBody> dataAdd = new HashMap<>();
                dataAdd.put("id_pegawai", toRequestBody(dataUser.getAiu()));
                dataAdd.put("id_penugasan", toRequestBody(dataUser.getId_penugasan()));
                dataAdd.put("jenis_absen", toRequestBody(jenis_absen));
                RequestBody _requestbody = RequestBody.create(MediaType.parse("image/png"), _file);
                dataAdd.put("fileToUpload\"; filename=\""+generatedString+".jpg\"", _requestbody);

                Log.d("Proccess", dataAdd.toString());

                mPresenter.absenProses(dataAdd);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private void initObject() {
        mPresenter = new Presenter(getContext(), this);
        mPresenter.checkIP();
    }

    private void attachData() {
        Picasso.with(getContext())
                .load("https://silbusiness.com/sios_management/SIOS_FILE/" + dataUser.getFoto())
                .placeholder(R.drawable.icon)
                .into(imgUser);

        editAgama.setText(dataUser.getAgama());
        editEmail.setText(dataUser.getEmail());
        editNama.setText(dataUser.getNama_lengkap());
        editNoIdentitas.setText(dataUser.getTanda_pengenal());
        editIpAbsen.setText(dataUser.getIp_absen());
        editKotaLahir.setText(dataUser.getTempat_lahir());
        editNoHp.setText(dataUser.getNo_hp());
        editTglLahir.setText(DateUtils.format("dd MMM yyyy", dataUser.getTgl_lahir()));
        editAkhirPenugasan.setText(DateUtils.format("dd MMM yyyy", dataUser.getTgl_berakhir_penugasan()));
        editMulaiPenugasan.setText(DateUtils.format("dd MMM yyyy", dataUser.getTgl_penugasan()));
        editJenisKelamin.setText(dataUser.getJenis_kelamin());
        editJabatan.setText(dataUser.getJabatan());
        editCompanyName.setText(dataUser.getPenugasan());
        tvAbsen.setText(dataUser.getKeteranagan_absen());


    }

    @Override
    public void doSuccess(ApiResponse response, String tag) {
        if (tag.equals(RES_CHECK_IP)){
            InfoIPResponse resp = (InfoIPResponse) response;
            if (!dataUser.getIp_absen().equals(resp.ip)){
                btnAbsen.setEnabled(false);
                btnAbsen.setBackgroundResource(R.color.cardview_shadow_start_color);
            }else{
                btnAbsen.setEnabled(true);
                btnAbsen.setBackgroundResource(R.color.colorSuccess);
            }
        }else if(tag.equals(RES_ABSEN_PROSES)){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            if (response.status){
                builder.setMessage(R.string.dialog_message_success)
                        .setTitle(R.string.dialog_title);
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                builder.setMessage(R.string.dialog_message_failed)
                        .setTitle(R.string.dialog_title);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    @Override
    public void doValidationError(boolean error) {

    }
}
