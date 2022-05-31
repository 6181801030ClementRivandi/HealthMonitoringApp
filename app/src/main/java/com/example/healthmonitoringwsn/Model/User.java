package com.example.healthmonitoringwsn.Model;

import android.text.TextUtils;

public class User implements IUser {

    String idUser, password;

    public User(String idUser, String password) {
        this.idUser = idUser;
        this.password = password;
    }

    @Override
    public String getIdUser() {
        return idUser;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public int isValidData() {
        if(TextUtils.isEmpty(getIdUser())) {
            return 0;
        } else if(getPassword().length() < 6) {
            return 2;
        } else {
            return -1;
        }
    }
}
