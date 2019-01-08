package com.teknologi.labakaya.siosabsensiandroid.entity;

import java.io.Serializable;

public class Absensi implements Serializable {
    private String tgl_jam_absen;
    private String tgl_jam_seharusnya;
    private String jenis_absen;
    private String status;
    private String keterangan_admin;
    private String foto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTgl_jam_absen() {
        return tgl_jam_absen;
    }

    public void setTgl_jam_absen(String tgl_jam_absen) {
        this.tgl_jam_absen = tgl_jam_absen;
    }

    public String getTgl_jam_seharusnya() {
        return tgl_jam_seharusnya;
    }

    public void setTgl_jam_seharusnya(String tgl_jam_seharusnya) {
        this.tgl_jam_seharusnya = tgl_jam_seharusnya;
    }

    public String getJenis_absen() {
        return jenis_absen;
    }

    public void setJenis_absen(String jenis_absen) {
        this.jenis_absen = jenis_absen;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan_admin() {
        return keterangan_admin;
    }

    public void setKeterangan_admin(String keterangan_admin) {
        this.keterangan_admin = keterangan_admin;
    }
}
