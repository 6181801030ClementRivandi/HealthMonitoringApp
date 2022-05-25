package com.example.healthmonitoringwsn.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile{

    String namaUser;
    String usiaUser;
    String tanggalLahirUser;
    int idUser = 0;

    public Profile(String namaUser, String usiaUser, String tanggalLahirUser, int idUser) {
        this.namaUser = namaUser;
        this.usiaUser = usiaUser;
        this.tanggalLahirUser = tanggalLahirUser;
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

}
