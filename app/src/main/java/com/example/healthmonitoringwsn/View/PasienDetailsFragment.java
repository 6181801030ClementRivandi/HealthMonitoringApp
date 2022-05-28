package com.example.healthmonitoringwsn.View;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PasienDetailsFragment extends Fragment implements View.OnClickListener//, PostCalculateTask.IMainActivity1 {
    {
    private FragmentListener listener;
    FloatingActionButton btnEdit;
    TextView tvNama, tvNIK, tvUsia, tvTanggalLahir, tvIdPasien, tvNomorHP, tvEmail, tvPassword, tvTanggalDaftar, tvIdKlinik, tvNamaKlinik;

    private PasienDetails pasienDetails;

    public PasienDetailsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pasien_details_fragment, container, false);

        this.tvNama = view.findViewById(R.id.pasien_name);
        this.tvNIK = view.findViewById(R.id.details_NIK);
        this.tvUsia = view.findViewById(R.id.details_usia);
        this.tvTanggalLahir = view.findViewById(R.id.details_tanggalLahir);
        this.tvIdPasien = view.findViewById(R.id.details_idPasien);
        this.tvNomorHP = view.findViewById(R.id.details_nomorHP);
        this.tvEmail = view.findViewById(R.id.details_email);
        this.tvPassword = view.findViewById(R.id.details_password);
        this.tvTanggalDaftar = view.findViewById(R.id.details_tanggalDaftar);
        this.tvIdKlinik = view.findViewById(R.id.details_idKlinik);
        this.tvNamaKlinik = view.findViewById(R.id.details_klinik);

        this.btnEdit = view.findViewById(R.id.fab_edit);
        this.btnEdit.setOnClickListener(this);

        Bundle bundle = getArguments();
        if ( bundle != null){
            this.pasienDetails = bundle.getParcelable("pasienDetails");
            this.tvNama.setText("nama pasien : " + pasienDetails.getNama());
            this.tvNIK.setText(String.valueOf(pasienDetails.getNIK()));
            this.tvUsia.setText(String.valueOf(pasienDetails.getUsia()));
            this.tvTanggalLahir.setText(String.valueOf(pasienDetails.getTanggalLahir()));
            this.tvIdPasien.setText(String.valueOf(pasienDetails.getIdPasien()));
            this.tvNomorHP.setText(String.valueOf(pasienDetails.getNomorHP()));
            this.tvEmail.setText(String.valueOf(pasienDetails.getEmail()));
            this.tvPassword.setText(String.valueOf(pasienDetails.getPassword()));
            this.tvTanggalDaftar.setText(String.valueOf(pasienDetails.getTanggalDaftar()));
            this.tvIdKlinik.setText(String.valueOf(pasienDetails.getIdKlinik()));
            this.tvNamaKlinik.setText(String.valueOf(pasienDetails.getNamaKlinik()));
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
    public static PasienDetailsFragment newInstance() {
        PasienDetailsFragment fragment = new PasienDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if (btnEdit == v) {
            //this.listener.changePage(4);
        }
    }
}
