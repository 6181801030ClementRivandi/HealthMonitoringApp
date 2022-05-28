package com.example.healthmonitoringwsn.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class PasienDetails implements Parcelable {

    String nama;
    int NIK;
    String usia;
    String tanggalLahir;
    int idPasien;
    String nomorHP;
    String email;
    String password;
    String tanggalDaftar;
    int idKlinik;
    String namaKlinik;

    public PasienDetails(String nama, int NIK, String usia, String tanggalLahir, int idPasien, String nomorHP, String email, String password, String tanggalDaftar, int idKlinik, String namaKlinik) {
        this.nama = nama;
        this.NIK = NIK;
        this.usia = usia;
        this.tanggalLahir = tanggalLahir;
        this.idPasien = idPasien;
        this.nomorHP = nomorHP;
        this.email = email;
        this.password = password;
        this.tanggalDaftar = tanggalDaftar;
        this.idKlinik = idKlinik;
        this.namaKlinik = namaKlinik;
    }

    public PasienDetails(){}

    public PasienDetails(Parcel in) {
        nama = in.readString();
        NIK = in.readInt();
        usia = in.readString();
        tanggalLahir = in.readString();
        idPasien = in.readInt();
        nomorHP = in.readString();
        email = in.readString();
        password = in.readString();
        tanggalDaftar = in.readString();
        idKlinik = in.readInt();
        namaKlinik = in.readString();
    }

    public static final Creator<PasienDetails> CREATOR = new Creator<PasienDetails>() {
        @Override
        public PasienDetails createFromParcel(Parcel in) {
            return new PasienDetails(in);
        }

        @Override
        public PasienDetails[] newArray(int size) {
            return new PasienDetails[size];
        }
    };

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getNIK() {
        return NIK;
    }

    public void setNIK(int NIK) {
        this.NIK = NIK;
    }

    public String getUsia() {
        return usia;
    }

    public void setUsia(String usia) {
        this.usia = usia;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(String tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public int getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(int idPasien) {
        this.idPasien = idPasien;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(String tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public int getIdKlinik() {
        return idKlinik;
    }

    public void setIdKlinik(int idKlinik) {
        this.idKlinik = idKlinik;
    }

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama);
        dest.writeInt(this.NIK);
        dest.writeString(this.usia);
        dest.writeString(this.tanggalLahir);
        dest.writeInt(this.idPasien);
        dest.writeString(this.nomorHP);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.tanggalDaftar);
        dest.writeInt(this.idKlinik);
        dest.writeString(this.namaKlinik);
    }
}
