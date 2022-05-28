package com.example.healthmonitoringwsn.Presenter;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;

import java.util.ArrayList;
import java.util.List;

public class PasienPresenter {

    public interface IMainActivity{
        void updateList(List<PasienDetails> pasienDetailsUP);
    }

    protected List<PasienDetails> pasien;
    protected IMainActivity ui;

    public PasienPresenter(IMainActivity ui){
        this.pasien = new ArrayList<>();
        this.ui = ui;
    }

    public void loadData(){
        this.ui.updateList(this.pasien);
    }

    public void refresh(){
        this.pasien.clear();
    }

    public int countItem(){
        return pasien.size();
    }

    public void addList(String nama, int NIK, String usia, String tanggalLahir, int idPasien, String nomorHP, String email, String password, String tanggalDaftar, int idKlinik, String namaKlinik){
        this.pasien.add(new PasienDetails(nama, NIK, usia, tanggalLahir, idPasien, nomorHP, email, password, tanggalDaftar, idKlinik, namaKlinik));
        this.ui.updateList(this.pasien);
    }

}
