package com.example.healthmonitoringwsn.Presenter;

import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.ProfileStaff;
import com.example.healthmonitoringwsn.Sqlite;
import com.example.healthmonitoringwsn.SqliteStaff;

import java.util.ArrayList;
import java.util.List;

public class ProfileStaffPresenter {

    public interface IMainActivity{
        void updateList(List<ProfileStaff>profileStaffs);
    }
    protected SqliteStaff sqliteStaff;
    protected List<ProfileStaff> profileStaff;
    protected IMainActivity ui;

    public ProfileStaffPresenter(IMainActivity ui, SqliteStaff sqliteStaff){
        this.profileStaff = new ArrayList<>();
        this.ui = ui;
        this.sqliteStaff = sqliteStaff;
    }

    public void loadData(){
        this.profileStaff = sqliteStaff.getAllRecord();
    }

    public void addList(String NamaStaff, String namaKlinikStaff, int idStaff){
        this.profileStaff.add(new ProfileStaff(NamaStaff, namaKlinikStaff, idStaff));
        this.ui.updateList(this.profileStaff);
    }

}

