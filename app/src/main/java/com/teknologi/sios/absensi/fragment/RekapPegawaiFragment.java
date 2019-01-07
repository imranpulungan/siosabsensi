package com.teknologi.sios.absensi.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teknologi.sios.absensi.R;
import com.teknologi.sios.absensi.adapter.RekapAdapter;
import com.teknologi.sios.absensi.api.response.ApiResponse;
import com.teknologi.sios.absensi.api.response.UserResponse;
import com.teknologi.sios.absensi.core.AppFragment;
import com.teknologi.sios.absensi.entity.User;
import com.teknologi.sios.absensi.presenter.Presenter;
import com.teknologi.sios.absensi.presenter.iPresenterResponse;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.teknologi.sios.absensi.presenter.Presenter.RES_GET_DATA;

public class RekapPegawaiFragment extends AppFragment implements iPresenterResponse {

    Presenter mPresenter;

    RekapAdapter mAdapter;

    @BindView(R.id.rv_rekap)
    protected RecyclerView rvRekap;

    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefresh;

    User dataUser;


    Map<String, String> data = new HashMap<>();

    public RekapPegawaiFragment() {
        super();
        this.res_layout = R.layout.fragment_rekap_pegawai;
    }

    public static RekapPegawaiFragment newInstance() {
        RekapPegawaiFragment fragment = new RekapPegawaiFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        initEvent();
        return view;
    }

    private void initEvent() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getData(data);
            }
        });
    }

    private void initObject() {
        mPresenter = new Presenter(getContext(), this);
        data.put("niagait_post", "SELECT * FROM data_penugasan_pegawai WHERE id_perusahaan="+dataUser.getId_penugasan());
        mPresenter.getData(data);
    }

    @Override
    public void doSuccess(ApiResponse response, String tag) {
        if (tag.equals(RES_GET_DATA)){
            UserResponse resp = (UserResponse) response;
            mAdapter = new RekapAdapter(getContext(), resp.data);
            rvRekap.setLayoutManager(new LinearLayoutManager(getContext()));
            rvRekap.setAdapter(mAdapter);
            rvRekap.setHasFixedSize(true);
            swipeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void doValidationError(boolean error) {

    }
}
