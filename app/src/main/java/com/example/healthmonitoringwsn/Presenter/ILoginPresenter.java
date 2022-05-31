package com.example.healthmonitoringwsn.Presenter;

import org.json.JSONException;

public interface ILoginPresenter {
    void onLogin(String idUser, String password) throws JSONException;
}
