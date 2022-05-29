package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.R;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditPasienFragment extends Fragment implements PostCalculateTask.ILoginActivity, PostCalculateTask.IMainActivity, PostCalculateTask.IMainActivityPsn, PostCalculateTask.IMainActivityAddPsn, PostCalculateTask.IMainActivityEditPsn, PostCalculateTask.IMainActivityDelPsn, View.OnClickListener {

    private FragmentManager fragmentManager;

    private EditText etNama, etNIK, etUsia, etTanggalLahir, etNomorHP, etEmail, etPassword, etIdKlinik;

    private FragmentListener listener;
    private Button btnEdit,btnHapus;
    private PostCalculateTask postCalculateTask;
    private String namaPasien, usiaPasien, tanggalLahirPasien, nomorHPPasien, emailPasien, passwordPasien, tanggalDaftarPasien, NIKPasien, idPasien, idKlinik;
    private String result;

    private SimpleDateFormat dateFormatter;

    private PasienDetails pasienDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_pasien, container, false);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd H:m:s", Locale.US);

        this.postCalculateTask = new PostCalculateTask(getContext(), this, this, this, this, this, this);

        this.etNama = view.findViewById(R.id.ETEditNamaPasien);
        this.etNIK = view.findViewById(R.id.ETEditNIKPasien);
        this.etUsia = view.findViewById(R.id.ETEditUsiaPasien);
        this.etTanggalLahir = view.findViewById(R.id.ETEditTanggalLahirPasien);
        this.etNomorHP = view.findViewById(R.id.ETEditNomorHP);
        this.etEmail = view.findViewById(R.id.ETEditEmail);
        this.etPassword = view.findViewById(R.id.ETEditPassword);
        this.etIdKlinik = view.findViewById(R.id.ETEditIdKlinik);
        this.btnEdit = view.findViewById(R.id.btn_edit);
        this.btnHapus = view.findViewById(R.id.btn_hapus);
        this.btnEdit.setOnClickListener(this);
        this.btnHapus.setOnClickListener(this);

        etNama.setText(null);
        etNIK.setText(null);
        etUsia.setText(null);
        etTanggalLahir.setText(null);
        etNomorHP.setText(null);
        etEmail.setText(null);
        etPassword.setText(null);
        etIdKlinik.setText(null);

        Bundle b = getArguments();

        if (b != null) {
            PasienDetails pasienDetails = b.getParcelable("editPasienDetails");

            this.etNama.setText(pasienDetails.getNama());
            this.etNIK.setText(String.valueOf(pasienDetails.getNIK()));
            this.etUsia.setText(pasienDetails.getUsia());
            this.etTanggalLahir.setText(pasienDetails.getTanggalLahir());
            this.idPasien = String.valueOf(pasienDetails.getIdPasien());
            this.etNomorHP.setText(pasienDetails.getNomorHP());
            this.etEmail.setText(pasienDetails.getEmail());
            this.etPassword.setText(pasienDetails.getPassword());
            this.etIdKlinik.setText(String.valueOf(pasienDetails.getIdKlinik()));
        }

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

    public static EditPasienFragment newInstance() {
        EditPasienFragment fragment = new EditPasienFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View v) {
        if (v == this.btnEdit) {

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
            apicall[0] = "editPasien";
            apicall[1] = namaPasien;
            apicall[2] = NIKPasien;
            apicall[3] = usiaPasien;
            apicall[4] = tanggalLahirPasien;
            apicall[5] = idPasien;
            apicall[6] = nomorHPPasien;
            apicall[7] = emailPasien;
            apicall[8] = passwordPasien;
            apicall[9] = tanggalDaftarPasien;
            apicall[10] = idKlinik;
            this.hideKeyboard(getActivity());
            etNama.setText(null);
            etNIK.setText(null);
            etUsia.setText(null);
            etTanggalLahir.setText(null);
            etNomorHP.setText(null);
            etEmail.setText(null);
            etPassword.setText(null);
            etIdKlinik.setText(null);

            try {
                postCalculateTask.callVolley(apicall);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(v == this.btnHapus){
            String[] apicall = new String[2];
            apicall[0] = "deletePasien";
            apicall[1] = idPasien;
            AlertDialog.Builder dialog1 = new AlertDialog.Builder(getContext());
            dialog1.setMessage("Apakah anda yakin ingin menghapus pasien ini?");
            dialog1.setCancelable(true);

            dialog1.setPositiveButton(
                    "hapus",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                postCalculateTask.callVolley(apicall);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            dialog1.setNegativeButton(
                    "batalkan",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = dialog1.create();
            alert11.show();
            etNama.setText(null);
            etNIK.setText(null);
            etUsia.setText(null);
            etTanggalLahir.setText(null);
            etNomorHP.setText(null);
            etEmail.setText(null);
            etPassword.setText(null);
            etIdKlinik.setText(null);
        }
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
        if(result.equals("edit successful")){
            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            this.listener.changePage(6);
        }else if(result.equals("delete successful")){
            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            this.listener.changePage(6);
        }else{
            Toast.makeText(getContext(), "error", Toast.LENGTH_SHORT).show();
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
}
