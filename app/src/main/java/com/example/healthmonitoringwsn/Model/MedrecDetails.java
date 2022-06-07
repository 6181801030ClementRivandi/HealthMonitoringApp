package com.example.healthmonitoringwsn.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class MedrecDetails implements Parcelable {

    String tanggal;
    int idPeriksa;
    double suhuTubuh;
    int detakJantung;
    int tekananDarah;
    double saturasiOksigen;
    int idPasien;
    String namaPetugas;
    String namaPasien;
    int idNode;

    public MedrecDetails(String tanggal, int idPeriksa, double suhuTubuh, int detakJantung, int tekananDarah, double saturasiOksigen, int idPasien, String namaPetugas, String namaPasien, int idNode) {
        this.tanggal = tanggal;
        this.idPeriksa = idPeriksa;
        this.suhuTubuh = suhuTubuh;
        this.detakJantung = detakJantung;
        this.tekananDarah = tekananDarah;
        this.saturasiOksigen = saturasiOksigen;
        this.idPasien = idPasien;
        this.namaPetugas = namaPetugas;
        this.namaPasien = namaPasien;
        this.idNode = idNode;
    }

    public MedrecDetails(){}

    public MedrecDetails(Parcel in) {
        tanggal = in.readString();
        idPeriksa = in.readInt();
        suhuTubuh = in.readDouble();
        detakJantung = in.readInt();
        tekananDarah = in.readInt();
        saturasiOksigen = in.readDouble();
        idPasien = in.readInt();
        namaPetugas = in.readString();
        namaPasien = in.readString();
        idNode = in.readInt();
    }

    public static final Creator<MedrecDetails> CREATOR = new Creator<MedrecDetails>() {
        @Override
        public MedrecDetails createFromParcel(Parcel in) {
            return new MedrecDetails(in);
        }

        @Override
        public MedrecDetails[] newArray(int size) {
            return new MedrecDetails[size];
        }
    };

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getIdPeriksa() {
        return idPeriksa;
    }

    public void setIdPeriksa(int idPeriksa) {
        this.idPeriksa = idPeriksa;
    }

    public double getSuhuTubuh() {
        return suhuTubuh;
    }

    public void setSuhuTubuh(double suhuTubuh) {
        this.suhuTubuh = suhuTubuh;
    }

    public int getDetakJantung() {
        return detakJantung;
    }

    public void setDetakJantung(int detakJantung) {
        this.detakJantung = detakJantung;
    }

    public int getTekananDarah() {
        return tekananDarah;
    }

    public void setTekananDarah(int tekananDarah) {
        this.tekananDarah = tekananDarah;
    }

    public double getSaturasiOksigen() {
        return saturasiOksigen;
    }

    public void setSaturasiOksigen(float saturasiOksigen) {
        this.saturasiOksigen = saturasiOksigen;
    }

    public int getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(int idPasien) {
        this.idPasien = idPasien;
    }

    public String getNamaPetugas() {
        return namaPetugas;
    }

    public void setNamaPetugas(String namaPetugas) {
        this.namaPetugas = namaPetugas;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    public int getIdNode() {
        return idNode;
    }

    public void setIdNode(int idNode) {
        this.idNode = idNode;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tanggal);
        dest.writeInt(this.idPeriksa);
        dest.writeDouble(this.suhuTubuh);
        dest.writeInt(this.detakJantung);
        dest.writeInt(this.tekananDarah);
        dest.writeDouble(this.saturasiOksigen);
        dest.writeInt(this.idPasien);
        dest.writeString(this.namaPetugas);
        dest.writeString(this.namaPasien);
        dest.writeInt(this.idNode);
    }
}
