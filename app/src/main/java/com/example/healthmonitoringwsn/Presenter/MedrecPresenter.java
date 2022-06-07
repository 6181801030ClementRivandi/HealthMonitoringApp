package com.example.healthmonitoringwsn.Presenter;

import com.example.healthmonitoringwsn.Model.MedrecDetails;

import java.util.ArrayList;
import java.util.List;

public class MedrecPresenter {

    public interface IMainActivity{
        void updateList(List<MedrecDetails> medrecDetailsUP);
    }

    protected List<MedrecDetails> medrecs;
    protected IMainActivity ui;

    public MedrecPresenter(IMainActivity ui){
        this.medrecs = new ArrayList<>();
        this.ui = ui;
    }

    public void loadData(){
        this.ui.updateList(this.medrecs);
    }

    public void refresh(){
        this.medrecs.clear();
    }

    public int countItem(){
        return medrecs.size();
    }

    public void addList(String tanggal, int idPeriksa, double suhuTubuh, int detakJantung, int tekananDarah, double saturasiOksigen, int idPasien, String namaPetugas, int idNode){
        this.medrecs.add(new MedrecDetails(tanggal, idPeriksa, suhuTubuh, detakJantung, tekananDarah, saturasiOksigen, idPasien, namaPetugas, idNode));
        this.ui.updateList(this.medrecs);
    }

}
