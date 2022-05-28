package com.example.healthmonitoringwsn.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Presenter.ProfilePresenter;
import com.example.healthmonitoringwsn.R;
import com.example.healthmonitoringwsn.Sqlite;

public class ProfileFragment extends Fragment{
    private FragmentManager fragmentManager;
    private FragmentListener listener;
    private Sqlite sqlite;
    private Profile profile;
    ProfilePresenter profilePresenter;

    public TextView tvNIKUser, tvNamaUser, tvUsiaUser, tvTanggalLahirUser, tvNomorHPUser, tvEmailUser, tvTanggalDaftarUser, tvNamaKlinikUser, tvIdUser;

    String idUser;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        this.tvNIKUser = view.findViewById(R.id.NIK_user);
        this.tvNamaUser = view.findViewById(R.id.nama_user);
        this.tvUsiaUser = view.findViewById(R.id.usia_user);
        this.tvTanggalLahirUser = view.findViewById(R.id.tanggalLahir_user);
        this.tvNomorHPUser = view.findViewById(R.id.nomorHP_user);
        this.tvEmailUser = view.findViewById(R.id.email_user);
        this.tvTanggalDaftarUser = view.findViewById(R.id.tanggalDaftar_user);
        this.tvNamaKlinikUser = view.findViewById(R.id.namaKlinik_user);
        this.tvIdUser = view.findViewById(R.id.id_user);

        this.sqlite = new Sqlite(this.getActivity());

        Bundle bundle = getArguments();
        if(bundle != null){
            this.idUser = bundle.getString("idUsr");
        }
        this.profile = this.sqlite.getContact(Integer.parseInt(idUser));
        this.tvNIKUser.setText(String.valueOf(profile.getNIK()));
        this.tvNamaUser.setText(profile.getNamaUser());
        this.tvUsiaUser.setText(profile.getUsiaUser());
        this.tvTanggalLahirUser.setText(profile.getTanggalLahirUser());
        this.tvNomorHPUser.setText(profile.getNomorHP());
        this.tvEmailUser.setText(profile.getEmail());
        this.tvTanggalDaftarUser.setText(profile.getTanggalDaftar());
        this.tvNamaKlinikUser.setText(profile.getNamaKlinik());
        this.tvIdUser.setText(idUser);
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

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }
}
