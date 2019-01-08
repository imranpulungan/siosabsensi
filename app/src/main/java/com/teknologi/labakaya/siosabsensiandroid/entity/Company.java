package com.teknologi.labakaya.siosabsensiandroid.entity;

import java.io.Serializable;

public class Company implements Serializable{
    private String nama_perusaaan;
    private String alamat;
    private String alamat_2;
    private String kota;
    private String kode_pos;
    private String keterangan;
    private String no_hp;
    private String email;
    private String foto;

    public String getNama_perusaaan() {
        return nama_perusaaan;
    }

    public void setNama_perusaaan(String nama_perusaaan) {
        this.nama_perusaaan = nama_perusaaan;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamat_2() {
        return alamat_2;
    }

    public void setAlamat_2(String alamat_2) {
        this.alamat_2 = alamat_2;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(String kode_pos) {
        this.kode_pos = kode_pos;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
