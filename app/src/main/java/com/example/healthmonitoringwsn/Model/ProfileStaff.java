package com.example.healthmonitoringwsn.Model;

public class ProfileStaff {

    String namaStaff;
    String namaKlinikStaff;
    int idStaff = 0;

    public ProfileStaff(String namaStaff, String namaKlinikStaff, int idStaff) {
        this.namaStaff = namaStaff;
        this.namaKlinikStaff = namaKlinikStaff;
        this.idStaff = idStaff;
    }

    public ProfileStaff(){}

    public String getNamaStaff() {
        return namaStaff;
    }

    public void setNamaStaff(String namaStaff) {
        this.namaStaff = namaStaff;
    }

    public String getNamaKlinikStaff() {
        return namaKlinikStaff;
    }

    public void setNamaKlinikStaff(String namaKlinikStaff) {
        this.namaKlinikStaff = namaKlinikStaff;
    }

    public int getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(int idStaff) {
        this.idStaff = idStaff;
    }
}
