package com.example.healthmonitoringwsn.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile{

    int NIK;
    String namaUser;
    String usiaUser;
    String tanggalLahirUser;
    int idUser = 0;
    String nomorHP;
    String email;
    String tanggalDaftar;
    String namaKlinik;

    public Profile(int NIKUser, String namaUser, String usiaUser, String tanggalLahirUser, String nomorHPUser, String emailUser, String tanggalDaftarUser, String namaKlinikUser, int idUser) {
        this.NIK = NIKUser;
        this.namaUser = namaUser;
        this.usiaUser = usiaUser;
        this.tanggalLahirUser = tanggalLahirUser;
        this.nomorHP = nomorHPUser;
        this.email = emailUser;
        this.tanggalDaftar = tanggalDaftarUser;
        this.namaKlinik = namaKlinikUser;
        this.idUser = idUser;
    }

    public Profile(){}

    public void setNamaUser(String namaUser) {
        this.namaUser = namaUser;
    }

    public void setUsiaUser(String usiaUser) {
        this.usiaUser = usiaUser;
    }

    public void setTanggalLahirUser(String tanggalLahirUser) {
        this.tanggalLahirUser = tanggalLahirUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public String getUsiaUser() {
        return usiaUser;
    }

    public String getTanggalLahirUser() {
        return tanggalLahirUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getNIK() {
        return NIK;
    }

    public void setNIK(int NIK) {
        this.NIK = NIK;
    }

    public String getNomorHP() {
        return nomorHP;
    }

    public void setNomorHP(String nomorHP) {
        this.nomorHP = nomorHP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(String tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }
}
