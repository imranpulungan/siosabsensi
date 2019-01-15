package com.teknologi.labakaya.siosabsensiandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.teknologi.labakaya.siosabsensiandroid.R;
import com.teknologi.labakaya.siosabsensiandroid.activity.ZoomActivity;
import com.teknologi.labakaya.siosabsensiandroid.entity.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RekapAdapter extends
        RecyclerView.Adapter<RekapAdapter.MyViewHolder>{

    private String url = "https://sios-sil.silbusiness.com/SIOS_FILE/";
    private View mView;

    Context context;
    List<User> usersList;

    public RekapAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rekap,parent, false);

        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/segoeui.ttf");
//        holder.titleCategory.setTypeface(typeface);

        holder.nameEmployee.setText(usersList.get(position).getNama_lengkap());
        holder.posEmployee.setText(usersList.get(position).getJabatan());
        holder.genderEmployee.setText(usersList.get(position).getJenis_kelamin());

        Picasso.with(context)
                .load( url + usersList.get(position).getFoto())
                .placeholder(R.drawable.ic_menu_camera)
                .into(holder.imgEmployee);

        holder.imgEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ZoomActivity.class);
                intent.putExtra("image_zoom", url + usersList.get(position).getFoto());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    static final class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_employee) ImageView imgEmployee;
        @BindView(R.id.employee_name) TextView nameEmployee;
        @BindView(R.id.employee_position) TextView posEmployee;
        @BindView(R.id.employee_gender) TextView genderEmployee;
        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}