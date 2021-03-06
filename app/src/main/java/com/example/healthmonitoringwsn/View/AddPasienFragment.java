package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.ProfileStaff;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.R;
import com.example.healthmonitoringwsn.Sqlite;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddPasienFragment extends Fragment implements PostCalculateTask.IMainActivity, PostCalculateTask.ILoginActivity, PostCalculateTask.ILoginActivityStaff, PostCalculateTask.IMainActivityPsn, PostCalculateTask.IMainActivityAddPsn, PostCalculateTask.IMainActivityEditPsn, PostCalculateTask.IMainActivityDelPsn, PostCalculateTask.IMainActivityAssignNode, PostCalculateTask.IMainActivityFindPsn, View.OnClickListener{
    private FragmentManager fragmentManager;
    private FragmentListener listener;
    private SimpleDateFormat dateFormatter;

    private TextView etNama, etNIK, etUsia, etTanggalLahir, etId, etNomorHP, etEmail, etPassword, etIdKlinik;
    private Button btnAdd, generate;
    private PostCalculateTask postCalculateTask;
    private String namaPasien, usiaPasien, tanggalLahirPasien, nomorHPPasien, emailPasien, passwordPasien, tanggalDaftarPasien, NIKPasien, idPasien, idKlinik;
    private String result, idGenerated;

    public AddPasienFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_add_pasien,container, false);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd H:m:s", Locale.US);

        this.postCalculateTask = new PostCalculateTask(getContext(), this,this,this, this, this, this, this, this, this);

        this.etNama = view.findViewById(R.id.ETNamaPasien);
        this.etNIK = view.findViewById(R.id.ETNIKPasien);
        this.etUsia = view.findViewById(R.id.ETUsiaPasien);
        this.etTanggalLahir = view.findViewById(R.id.ETTanggalLahirPasien);
        this.etNomorHP = view.findViewById(R.id.ETNomorHP);
        this.etEmail = view.findViewById(R.id.ETEmail);
        this.etPassword = view.findViewById(R.id.ETPassword);
        this.etIdKlinik = view.findViewById(R.id.ETIdKlinik);
        this.btnAdd = view.findViewById(R.id.btnAddPasien);
        this.generate = view.findViewById(R.id.btnGenerateId);
        this.etId = view.findViewById(R.id.idPasien);

        this.btnAdd.setOnClickListener(this);
        this.generate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.listener = (FragmentListener) context;
        }else{
            throw new ClassCastException(context.toString()+ " Must Implement Fragment Listener");
        }
    }

    public static AddPasienFragment newInstance(){
        AddPasienFragment fragment = new AddPasienFragment();
        return fragment;
    }

    @Override
    public void onClick(View view) {
        if(view == this.btnAdd){
            if (TextUtils.isEmpty(this.etNama.getText().toString()) ||
                    TextUtils.isEmpty(this.etNIK.getText().toString()) ||
                    TextUtils.isEmpty(this.etUsia.getText().toString()) ||
                    TextUtils.isEmpty(this.etTanggalLahir.getText().toString()) ||
                    TextUtils.isEmpty(this.etId.getText().toString()) ||
                    TextUtils.isEmpty(this.etNomorHP.getText().toString()) ||
                    TextUtils.isEmpty(this.etEmail.getText().toString()) ||
                    TextUtils.isEmpty(this.etPassword.getText().toString()) ||
                    TextUtils.isEmpty(this.etIdKlinik.getText().toString())){
                Toast.makeText(getContext(), "tidak boleh ada baris yang kosong!", Toast.LENGTH_SHORT).show();
            }else{
                Date currentTime = Calendar.getInstance().getTime();

                this.namaPasien = this.etNama.getText().toString();
                this.NIKPasien = this.etNIK.getText().toString();
                this.usiaPasien = this.etUsia.getText().toString();
                this.tanggalLahirPasien = this.etTanggalLahir.getText().toString();
                this.nomorHPPasien = this.etNomorHP.getText().toString();
                this.emailPasien = this.etEmail.getText().toString();
                this.passwordPasien = this.etPassword.getText().toString();
                this.tanggalDaftarPasien = dateFormatter.format(currentTime);
                this.idKlinik = this.etIdKlinik.getText().toString();

                String[] apicall = new String[11];
                apicall[0] = "addPasien";
                apicall[1] = namaPasien;
                apicall[2] = NIKPasien;
                apicall[3] = usiaPasien;
                apicall[4] = tanggalLahirPasien;
                apicall[5] = idGenerated;
                apicall[6] = nomorHPPasien;
                apicall[7] = emailPasien;
                apicall[8] = passwordPasien;
                apicall[9] = tanggalDaftarPasien;
                apicall[10] = idKlinik;
                this.hideKeyboard(getActivity());
                try {
                    postCalculateTask.callVolley(apicall);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else if(view == this.generate){
            String[] apicall = new String[1];
            apicall[0] = "generate";
            try {
                postCalculateTask.callVolley(apicall);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
    public void hasil(MedrecDetails medrecDetails) {

    }

    @Override
    public void hasil(PasienDetails pasienDetails) {

    }

    @Override
    public void result(String message) {
        result = message;
        if(result.equals("add successful")){
            Toast.makeText(getContext(), "pasien berhasil ditambahkan", Toast.LENGTH_SHORT).show();
            this.listener.changePage(6);
            etNama.setText(null);
            etNIK.setText(null);
            etUsia.setText(null);
            etTanggalLahir.setText(null);
            etId.setText(null);
            etNomorHP.setText(null);
            etEmail.setText(null);
            etPassword.setText(null);
            etIdKlinik.setText(null);
        }else{
            Toast.makeText(getContext(), "tambah pasien gagal", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void logResult(ProfileStaff profileStaff) {

    }

    @Override
    public void resultAssign(String message) {

    }

    @Override
    public void resultFind(String message) {

    }

    @Override
    public void resultEdit(String message) {
        this.idGenerated = message;
        int temp = Integer.parseInt(idGenerated);
        temp += 1;
        idGenerated = String.valueOf(temp);
        this.etId.setText("Id : " + idGenerated);
    }
}
