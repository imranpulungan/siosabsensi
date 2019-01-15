package com.teknologi.labakaya.siosabsensiandroid.fragment;

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

import com.teknologi.labakaya.siosabsensiandroid.R;
import com.teknologi.labakaya.siosabsensiandroid.adapter.RekapAbsensiAdapter;
import com.teknologi.labakaya.siosabsensiandroid.api.response.AbsensiResponse;
import com.teknologi.labakaya.siosabsensiandroid.api.response.ApiResponse;
import com.teknologi.labakaya.siosabsensiandroid.core.AppFragment;
import com.teknologi.labakaya.siosabsensiandroid.entity.User;
import com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter;
import com.teknologi.labakaya.siosabsensiandroid.presenter.iPresenterResponse;
import com.teknologi.labakaya.siosabsensiandroid.utils.ProgressDialogUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.teknologi.labakaya.siosabsensiandroid.presenter.Presenter.RES_GET_DATA;

public class RekapAbsenFragment extends AppFragment implements iPresenterResponse {

    Presenter mPresenter;

    ProgressDialogUtils progressDialogUtils;

    RekapAbsensiAdapter mAdapter;

    @BindView(R.id.rv_rekap)
    protected RecyclerView rvRekap;

    @BindView(R.id.swipe_refresh)
    protected SwipeRefreshLayout swipeRefresh;

    Map<String, String> data = new HashMap<>();

    User dataUser;

    public RekapAbsenFragment() {
        super();
        this.res_layout = R.layout.fragment_rekap_pegawai;
    }

    public static RekapAbsenFragment newInstance() {
        RekapAbsenFragment fragment = new RekapAbsenFragment();
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
                mPresenter.getDataAbsensi(data);
            }
        });
    }

    private void initObject() {
        progressDialogUtils = new ProgressDialogUtils(getContext(), null, "Loading");
        mPresenter = new Presenter(getContext(), this);
        data.put("niagait_post", "SELECT * FROM rekap_absensi WHERE id_pegawai="+dataUser.getAiu()+" AND rekap='False' ORDER by aiu DESC");
        progressDialogUtils.showProgress();
        mPresenter.getDataAbsensi(data);
    }

    @Override
    public void doSuccess(ApiResponse response, String tag) {
        progressDialogUtils.dissmisProgress();
        if (tag.equals(RES_GET_DATA)){
            AbsensiResponse resp = (AbsensiResponse) response;
            mAdapter = new RekapAbsensiAdapter(getContext(), resp.data);
            rvRekap.setLayoutManager(new LinearLayoutManager(getContext()));
            rvRekap.setAdapter(mAdapter);
            rvRekap.setHasFixedSize(true);
            swipeRefresh.setRefreshing(false);
        }
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
