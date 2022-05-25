package com.example.healthmonitoringwsn.Presenter;

import org.json.JSONException;

public interface ILoginPresenter {
    void onLogin(String idPasien, String password) throws JSONException;
}
