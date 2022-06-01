package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Presenter.ILoginPresenter;
import com.example.healthmonitoringwsn.Presenter.LoginPresenter;
import com.example.healthmonitoringwsn.R;
import com.example.healthmonitoringwsn.Sqlite;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

public class LoginFragment extends Fragment implements View.OnClickListener, ILoginView {

    EditText eTidUser, eTpassword;
    Button btnLogin;
    ILoginPresenter loginPresenter;
    Sqlite sqlite;
    String idUser;
    String password;

    public LoginFragment(){}

    FragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        BottomNavigationView navBar = getActivity().findViewById(R.id.bottomNavigationView);
        navBar.setVisibility(View.GONE);

        View view = inflater.inflate(R.layout.fragment_login,container,false);

        loginPresenter = new LoginPresenter(this, getContext());

        this.sqlite = new Sqlite(this.getActivity());

        this.eTidUser = view.findViewById(R.id.et_idUser);
        this.eTpassword = view.findViewById(R.id.et_password);
        this.btnLogin = view.findViewById(R.id.btn_login);
        this.btnLogin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentListener) {
            this.listener = (FragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " Must Implement Fragment Listener");
        }
    }

    public static LoginFragment newInstance(){
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        idUser = eTidUser.getText().toString();
        password = eTpassword.getText().toString();
        try {
            loginPresenter.onLogin(idUser, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoginSuccess(String message, String id) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        MainActivity mainActivity = (MainActivity)getActivity();
        this.hideKeyboard(getActivity());
        if(id.indexOf("924") == -1){
            mainActivity.passId(id);
            this.listener.changePage(2);
        }else{
            mainActivity.passIdStaff(id);
            this.listener.changePage(10);
        }
        eTidUser.setText(null);
        eTpassword.setText(null);
    }

    @Override
    public void onLoginError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
