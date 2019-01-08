package com.teknologi.labakaya.siosabsensiandroid.entity;

import java.io.Serializable;

public class User implements Serializable{
    //EMPLOYEE
    private  String aiu;
    private  String nama_lengkap;
    private  String tanda_pengenal;
    private  String tempat_lahir;
    private  String tgl_lahir;
    private  String alamat;
    private  String jenis_kelamin;
    private  String agama;
    private  String no_hp;
    private  String email;
    private  String username;
    private  String password;
    private  String token;
    private  String ip_absen;
    private  String keteranagan_absen;
    private  String jabatan;
    private  String id_penugasan;
    private  String tgl_penugasan;
    private  String tgl_berakhir_penugasan;
    private  String penugasan;


    public String getAiu() {
        return aiu;
    }

    public void setAiu(String aiu) {
        this.aiu = aiu;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }

    public String getId_penugasan() {
        return id_penugasan;
    }

    public void setId_penugasan(String id_penugasan) {
        this.id_penugasan = id_penugasan;
    }

    public String getTgl_penugasan() {
        return tgl_penugasan;
    }

    public void setTgl_penugasan(String tgl_penugasan) {
        this.tgl_penugasan = tgl_penugasan;
    }

    public String getTgl_berakhir_penugasan() {
        return tgl_berakhir_penugasan;
    }

    public void setTgl_berakhir_penugasan(String tgl_berakhir_penugasan) {
        this.tgl_berakhir_penugasan = tgl_berakhir_penugasan;
    }

    public String getPenugasan() {
        return penugasan;
    }

    public void setPenugasan(String penugasan) {
        this.penugasan = penugasan;
    }

    //COMPANY
    private String name;
    private  String foto;


    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getTanda_pengenal() {
        return tanda_pengenal;
    }

    public void setTanda_pengenal(String tanda_pengenal) {
        this.tanda_pengenal = tanda_pengenal;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp_absen() {
        return ip_absen;
    }

    public void setIp_absen(String ip_absen) {
        this.ip_absen = ip_absen;
    }

    public String getKeteranagan_absen() {
        return keteranagan_absen;
    }

    public void setKeteranagan_absen(String keteranagan_absen) {
        this.keteranagan_absen = keteranagan_absen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
