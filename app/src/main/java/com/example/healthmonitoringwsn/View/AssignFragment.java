package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.ProfileStaff;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.Presenter.ILoginPresenter;
import com.example.healthmonitoringwsn.Presenter.LoginPresenter;
import com.example.healthmonitoringwsn.R;
import com.example.healthmonitoringwsn.Sqlite;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

public class AssignFragment extends Fragment implements PostCalculateTask.IMainActivityFindPsn, PostCalculateTask.IMainActivity, PostCalculateTask.ILoginActivity, PostCalculateTask.ILoginActivityStaff, PostCalculateTask.IMainActivityPsn, PostCalculateTask.IMainActivityAddPsn, PostCalculateTask.IMainActivityEditPsn, PostCalculateTask.IMainActivityDelPsn, PostCalculateTask.IMainActivityAssignNode, View.OnClickListener {

    EditText eTidPasien, eTidNode;
    Button btnSubmit, btnPemeriksaan, btnReset;
    String idPasien;
    String idNode;
    String idStaff;
    boolean reset = false, btnPeriksa = false;
    PostCalculateTask postCalculateTask;

    public AssignFragment(){}

    FragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assign,container,false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((view1, i, keyEvent) -> {
            if (keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                if (i == KeyEvent.KEYCODE_BACK){
                    return true;
                }
            }
            return false;
        });

        Bundle bundle = getArguments();
        if(bundle != null){
            this.idStaff = bundle.getString("idStff");
        }

        this.eTidPasien = view.findViewById(R.id.assign_idPasien);
        this.eTidNode = view.findViewById(R.id.assign_idNode);
        this.btnSubmit = view.findViewById(R.id.btn_submit);
        this.btnSubmit.setOnClickListener(this);
        this.btnReset = view.findViewById(R.id.btn_reset);
        this.btnReset.setOnClickListener(this);
        this.btnPemeriksaan = view.findViewById(R.id.btn_pemeriksaan);
        this.btnPemeriksaan.setOnClickListener(this);
        btnPemeriksaan.setVisibility(View.INVISIBLE);
        if(btnPeriksa == false){
            btnPemeriksaan.setVisibility(View.INVISIBLE);
        }else{
            btnPemeriksaan.setVisibility(View.VISIBLE);
        }

        this.postCalculateTask = new PostCalculateTask(getContext(), this,this,this, this, this, this, this, this, this);

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

    public static AssignFragment newInstance(){
        AssignFragment fragment = new AssignFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if( v == btnSubmit){
            idPasien = eTidPasien.getText().toString();
            idNode = eTidNode.getText().toString();
            if (idPasien.equals("") || idNode.equals("")){
                Toast.makeText(getContext(), "data tidak boleh kosong", Toast.LENGTH_SHORT).show();

            }else{
                String status = "1";
                String[] apicall = new String[5];
                apicall[0] = "assignNode";
                apicall[1] = idStaff;
                apicall[2] = idNode;
                apicall[3] = idPasien;
                apicall[4] = status;
                try {
                    postCalculateTask.callVolley(apicall);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.hideKeyboard(getActivity());
                MainActivity main = (MainActivity) getActivity();
                main.passIdAssign(idPasien, idStaff);
                eTidPasien.setText(null);
                eTidNode.setText(null);
                reset = true;
            }
        }else if (v == btnReset){
            if(idNode == null){
                Toast.makeText(getContext(), "reset gagal, node belum dialokasi", Toast.LENGTH_SHORT).show();

            }else{
                String[] apicall = new String[5];
                apicall[0] = "resetAssign";
                apicall[1] = idNode;
                try {
                    postCalculateTask.callVolley(apicall);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getContext(), "reset berhasil", Toast.LENGTH_SHORT).show();
                btnPemeriksaan.setVisibility(View.INVISIBLE);
                btnPeriksa = false;
            }
        }else if (v == btnPemeriksaan){
            listener.changePage(2);
            MainActivity main = (MainActivity) getActivity();
            main.bottomNavigationView.getMenu().findItem(R.id.home_icon).setCheckable(false);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void logResult(Profile profile) {

    }

    @Override
    public void logResult(ProfileStaff profileStaff) {

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
    public void resultAssign(String message) {
        String result = message;
        if(result.equals("assign successful") && reset == true){
            listener.changePage(2);
            Toast.makeText(getContext(), "alokasi berhasil", Toast.LENGTH_SHORT).show();
            reset = false;
            btnPeriksa = true;
            btnPemeriksaan.setVisibility(View.VISIBLE);
            MainActivity main = (MainActivity) getActivity();
            main.bottomNavigationView.getMenu().findItem(R.id.home_icon).setCheckable(false);
        }
    }

    @Override
    public void resultFind(String message) {

    }
}
