package com.example.healthmonitoringwsn.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class PasienDetails implements Parcelable {

    String nama;
    int NIK;
    String usia;
    String tanggalLahir;
    int idPasien;
    String namaKlinik;

    public PasienDetails(String nama, int NIK, String usia, String tanggalLahir, int idPasien, String namaKlinik) {
        this.nama = nama;
        this.NIK = NIK;
        this.usia = usia;
        this.tanggalLahir = tanggalLahir;
        this.idPasien = idPasien;
        this.namaKlinik = namaKlinik;
    }

    public PasienDetails(){}

    public PasienDetails(Parcel in) {
        nama = in.readString();
        NIK = in.readInt();
        usia = in.readString();
        tanggalLahir = in.readString();
        idPasien = in.readInt();
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
        dest.writeString(this.namaKlinik);
    }
}
