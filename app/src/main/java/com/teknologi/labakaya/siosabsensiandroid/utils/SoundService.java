package com.teknologi.labakaya.siosabsensiandroid.utils;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.teknologi.labakaya.siosabsensiandroid.R;

public class SoundService extends Service {
    MediaPlayer player;

    String jenis_absen = "";
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
//        player.setLooping(true); //set looping
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        jenis_absen = intent.getStringExtra("jenis_absen");
        if (jenis_absen.equals("MASUK")){
            player = MediaPlayer.create(this, R.raw.absen_masuk);
        }else if (jenis_absen.equals("PULANG")){
            player = MediaPlayer.create(this, R.raw.absen_pulang);
        }else if (jenis_absen.equals("LEMBUR")){
            player = MediaPlayer.create(this, R.raw.absen_lembur);
        }else if (jenis_absen.equals("TIDAK_BERTUGAS")){
            player = MediaPlayer.create(this, R.raw.tidak_ada_jadwal);
        }else if(jenis_absen.equals("BARCODE")){
            player = MediaPlayer.create(this, R.raw.pegawai_tidak_ditemukan);
        }else if(jenis_absen.equals("EXIST")){
            player = MediaPlayer.create(this, R.raw.sudah_absen);
        }
        player.start();
        return Service.START_NOT_STICKY;
    }

    public void onDestroy() {
        player.stop();
        player.release();
        stopSelf();
        super.onDestroy();
    }

}