package com.example.healthmonitoringwsn.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.User;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.Sqlite;
import com.example.healthmonitoringwsn.View.FragmentListener;
import com.example.healthmonitoringwsn.View.ILoginView;

import org.json.JSONException;

public class LoginPresenter implements ILoginPresenter, PostCalculateTask.ILoginActivity, PostCalculateTask.IMainActivity, PostCalculateTask.IMainActivityPsn{

    ILoginView loginView;
    PostCalculateTask postCalculateTask;
    Context context;
    ProfilePresenter presenter;
    Sqlite sqlite;
    String idpasien;

    public LoginPresenter(ILoginView loginView, Context context){
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    public void onLogin(String idPasien, String password) throws JSONException {
        idpasien = idPasien;
        this.postCalculateTask = new PostCalculateTask(context, this, this, this);
        this.sqlite = new Sqlite(context);
        User user = new User(idPasien, password);
        int loginCode = user.isValidData();

        if(loginCode == 0) {
            loginView.onLoginError("Id pasien tidak boleh kosong");
        } else if(loginCode == 2) {
            loginView.onLoginError("Password harus terdiri dari 6 digit gabungan huruf dan angka");
        } else {
            String[] apicall = new String[3];
            apicall[0] = "login";
            apicall[1] = idPasien;
            apicall[2] = password;
            postCalculateTask.callVolley(apicall);
        }
    }

    @Override
    public void logResult(Profile profile) {
        Profile prof = profile;
        if (prof.getIdUser() == 0){
            loginView.onLoginError("Id Pasien atau password salah");
        }else{
            loginView.onLoginSuccess("Login berhasil", String.valueOf(prof.getIdUser()));
            Profile profile1 = new Profile();
            profile1 = sqlite.getContact(Integer.parseInt(idpasien));
            if (profile1.getIdUser() == 0) {
                sqlite.addRecord(prof);
            }
        }
    }

    @Override
    public void hasil(MedrecDetails medrecDetails) {
    }

    @Override
    public void hasil(PasienDetails pasienDetails) {

    }

//    public interface passUserId{
//        void pass(String userId);
//    }
}
