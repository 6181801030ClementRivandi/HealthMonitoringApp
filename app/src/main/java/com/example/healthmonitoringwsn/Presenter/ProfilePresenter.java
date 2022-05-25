package com.example.healthmonitoringwsn.Presenter;

import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Sqlite;

import java.util.ArrayList;
import java.util.List;

public class ProfilePresenter {

    public interface IMainActivity{
        void updateList(List<Profile>profileUser);
    }
    protected Sqlite sqlite;
    protected List<Profile> profile;
    protected IMainActivity ui;

    public ProfilePresenter(IMainActivity ui,Sqlite sqlite){
        this.profile = new ArrayList<>();
        this.ui = ui;
        this.sqlite=sqlite;
    }

    public void loadData(){
        this.profile=sqlite.getAllRecord();
        //this.ui.updateList(this.profile);
    }

    public void addList(String NamaUser, String UsiaUser, String TanggalLahirUser, Integer IdUser){
        this.profile.add(new Profile(NamaUser, UsiaUser, TanggalLahirUser, IdUser));
        this.ui.updateList(this.profile);
    }

}

