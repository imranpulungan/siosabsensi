package com.teknologi.labakaya.siosabsensiandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teknologi.labakaya.siosabsensiandroid.R;
import com.teknologi.labakaya.siosabsensiandroid.entity.Absensi;
import com.teknologi.labakaya.siosabsensiandroid.utils.DateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RekapAbsensiAdapter extends
        RecyclerView.Adapter<RekapAbsensiAdapter.MyViewHolder>{

    private String url = "https://sios-sil.silbusiness.com/SIOS_FILE_API/";
    private View mView;

    Context context;
    List<Absensi> absensiList;

    public RekapAbsensiAdapter(Context context, List<Absensi> absensiList) {
        this.context = context;
        this.absensiList = absensiList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rekap_absensi,parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/segoeui.ttf");
//        holder.titleCategory.setTypeface(typeface);

        holder.tvJenisAbsen.setText(absensiList.get(position).getJenis_absen());

        String[] jamSeharusnya = absensiList.get(position).getTgl_jam_seharusnya().split(" ");
        String[] jamAbsen = absensiList.get(position).getTgl_jam_absen().split(" ");

        holder.tvJamSeharusnya.setText(DateUtils.format("dd MMM yyyy", jamSeharusnya[0])+" "+jamSeharusnya[1]);
        holder.tvJamAbsen.setText(DateUtils.format("dd MMM yyyy", jamSeharusnya[0])+" "+jamSeharusnya[1]);
        holder.tvStatus.setText(absensiList.get(position).getStatus());
        holder.tvDesc.setText(absensiList.get(position).getKeterangan_admin());

        Picasso.with(context)
                .load( url + absensiList.get(position).getFoto())
                .placeholder(R.drawable.ic_menu_camera)
                .into(holder.imgEmployee);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return absensiList.size();
    }

    static final class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_employee) ImageView imgEmployee;
        @BindView(R.id.tv_jenis_absen) TextView tvJenisAbsen;
        @BindView(R.id.tv_jam_seharusnya) TextView tvJamSeharusnya;
        @BindView(R.id.tv_jam_absen) TextView tvJamAbsen;
        @BindView(R.id.tv_status) TextView tvStatus;
        @BindView(R.id.tv_desc) TextView tvDesc;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}