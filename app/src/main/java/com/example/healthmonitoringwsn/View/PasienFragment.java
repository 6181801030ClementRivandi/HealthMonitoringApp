package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.ProfileStaff;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.Presenter.MedrecPresenter;
import com.example.healthmonitoringwsn.Presenter.PasienPresenter;
import com.example.healthmonitoringwsn.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PasienFragment extends Fragment implements PostCalculateTask.IMainActivity, PostCalculateTask.ILoginActivity, PostCalculateTask.ILoginActivityStaff, PostCalculateTask.IMainActivityPsn, PasienPresenter.IMainActivity, PostCalculateTask.IMainActivityAddPsn, PostCalculateTask.IMainActivityEditPsn, PostCalculateTask.IMainActivityDelPsn, View.OnClickListener{

    private ListView pasienList;
    private PasienPresenter presenter;
    private PasienListAdapter adapter;
    private FragmentListener listener;
    private Activity activity;
    private ArrayList<PasienDetails> hasilPasien;
    FloatingActionButton add;
    String idUser;
    PostCalculateTask postCalculateTask;

    public PasienFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pasien, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            this.idUser = bundle.getString("pasienDetails");
        }

        hasilPasien = new ArrayList<>();

        this.pasienList = view.findViewById(R.id.list_pasien);
        this.presenter = new PasienPresenter(this);

        this.adapter = new PasienListAdapter((requireActivity()));

        this.pasienList.setAdapter(this.adapter);

        this.pasienList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity main = (MainActivity) getActivity();
                PasienDetails currentPasien = (PasienDetails) adapter.getItem(position);
                main.psPasien(currentPasien);
            }
        });

        this.postCalculateTask = new PostCalculateTask(getContext(), this, this, this, this, this, this, this);

        String[] apicall = new String[1];
        apicall[0] = "pasien";
        try {
            postCalculateTask.callVolley(apicall);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.add = view.findViewById(R.id.fab_add);

        this.add.setOnClickListener(this);

        presenter.refresh();

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

    public static PasienFragment newInstance() {
        PasienFragment fragment = new PasienFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void hasil(MedrecDetails medrecDetails) {
    }

    @Override
    public void updateList(List<PasienDetails> pasienDetailsUP) {
        this.adapter.updateArray(pasienDetailsUP);
    }

    @Override
    public void logResult(Profile profile) {

    }

    @Override
    public void onClick(View view) {
        if(view == this.add){
            this.listener.changePage(8);
        }
    }

    @Override
    public void hasil(PasienDetails pasienDetails) {
        PasienDetails pasien = pasienDetails;
        presenter.addList(pasien.getNama(), pasien.getNIK(), pasien.getUsia(), pasien.getTanggalLahir(), pasien.getIdPasien(), pasien.getNomorHP(), pasien.getEmail(), pasien.getPassword(), pasien.getTanggalDaftar(), pasien.getIdKlinik(), pasien.getNamaKlinik());
    }

    @Override
    public void result(String message) {

    }

    @Override
    public void logResult(ProfileStaff profileStaff) {

    }
}
