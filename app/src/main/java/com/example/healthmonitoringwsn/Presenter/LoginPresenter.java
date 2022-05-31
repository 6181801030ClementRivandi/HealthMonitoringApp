package com.example.healthmonitoringwsn.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.ProfileStaff;
import com.example.healthmonitoringwsn.Model.User;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.Sqlite;
import com.example.healthmonitoringwsn.SqliteStaff;
import com.example.healthmonitoringwsn.View.FragmentListener;
import com.example.healthmonitoringwsn.View.ILoginView;
import com.example.healthmonitoringwsn.View.ProfileStaffFragment;

import org.json.JSONException;

public class LoginPresenter implements ILoginPresenter, PostCalculateTask.ILoginActivity, PostCalculateTask.ILoginActivityStaff, PostCalculateTask.IMainActivity, PostCalculateTask.IMainActivityPsn, PostCalculateTask.IMainActivityAddPsn, PostCalculateTask.IMainActivityEditPsn, PostCalculateTask.IMainActivityDelPsn{

    ILoginView loginView;
    PostCalculateTask postCalculateTask;
    Context context;
    ProfilePresenter presenter;
    Sqlite sqlite;
    SqliteStaff sqliteStaff;
    String iduser;

    public LoginPresenter(ILoginView loginView, Context context){
        this.loginView = loginView;
        this.context = context;
    }

    @Override
    public void onLogin(String idUser, String password) throws JSONException {
        iduser = idUser;
        this.postCalculateTask = new PostCalculateTask(context, this, this, this, this, this, this, this);
        this.sqlite = new Sqlite(context);
        this.sqliteStaff = new SqliteStaff(context);
        User user = new User(idUser, password);
        int loginCode = user.isValidData();

        if(loginCode == 0) {
            loginView.onLoginError("Id user tidak boleh kosong");
        } else if(loginCode == 2) {
            loginView.onLoginError("Password harus terdiri dari 6 digit gabungan huruf dan angka");
        } else {
            if(idUser.indexOf("924") == -1){
                String[] apicall = new String[3];
                apicall[0] = "login";
                apicall[1] = idUser;
                apicall[2] = password;
                postCalculateTask.callVolley(apicall);
            }else{
                String[] apicall = new String[3];
                apicall[0] = "loginStaff";
                apicall[1] = idUser;
                apicall[2] = password;
                postCalculateTask.callVolley(apicall);
            }

        }
    }

    @Override
    public void logResult(Profile profile) {
        Profile prof = profile;
        if (prof.getIdUser() == 0){
            loginView.onLoginError("Id user atau password salah");
        }else{
            loginView.onLoginSuccess("Login berhasil", String.valueOf(prof.getIdUser()));
            Profile profile1 = new Profile();
            profile1 = sqlite.getContact(Integer.parseInt(iduser));
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

    @Override
    public void result(String message) {

    }

    @Override
    public void logResult(ProfileStaff profileStaff) {
        ProfileStaff profStaff = profileStaff;
        if (profStaff.getIdStaff() == 0){
            loginView.onLoginError("Id user atau password salah");
        }else{
            loginView.onLoginSuccess("Login berhasil", String.valueOf(profStaff.getIdStaff()));
            ProfileStaff profileStaff1 = new ProfileStaff();
            profileStaff1 = sqliteStaff.getContact(Integer.parseInt(iduser));
            if (profileStaff1.getIdStaff() == 0) {
                sqliteStaff.addRecord(profileStaff1);
            }
        }
    }

//    public interface passUserId{
//        void pass(String userId);
//    }
}
