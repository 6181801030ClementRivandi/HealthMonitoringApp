package com.example.healthmonitoringwsn.Model;

import android.text.TextUtils;

public class User implements IUser {

    String idPasien, password;

    public User(String idPasien, String password) {
        this.idPasien = idPasien;
        this.password = password;
    }

    @Override
    public String getIdPasien() {
        return idPasien;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int isValidData() {
        if(TextUtils.isEmpty(getIdPasien())) {
            return 0;
        } else if(getPassword().length() < 6) {
            return 2;
        } else {
            return -1;
        }
    }
}
