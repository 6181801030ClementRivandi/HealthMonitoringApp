package com.example.healthmonitoringwsn.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.ProfileStaff;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.Presenter.ProfilePresenter;
import com.example.healthmonitoringwsn.Sqlite;
import com.example.healthmonitoringwsn.R;

import org.json.JSONException;

import java.util.List;

public class MainFragment extends Fragment implements PostCalculateTask.IMainActivityFindPsn, PostCalculateTask.IMainActivityAssignNode, PostCalculateTask.IMainActivity, PostCalculateTask.ILoginActivity, PostCalculateTask.ILoginActivityStaff, PostCalculateTask.IMainActivityPsn, PostCalculateTask.IMainActivityAddPsn, PostCalculateTask.IMainActivityEditPsn, PostCalculateTask.IMainActivityDelPsn {
    private FragmentManager fragmentManager;
    private FragmentListener listener;
    SwipeRefreshLayout refreshLayout;
    PostCalculateTask postCalculateTask;
    private MedrecDetails medrecDetailsLatest;
    TextView tvIdPeriksa, tvTanggal, tvSuhu, tvDetak, tvTekanan, tvSaturasi, tvIdPetugas, tvIdNode;
    TextView tvSuhuCond, tvDetakCond, tvTekananCond, tvSaturasiCond;
    String idUsr = "", idStff = "", nama = "";
    String[] apicall;

    public MainFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main,container, false);
        this.tvIdPeriksa = view.findViewById(R.id.judul_periksaMain);
        this.tvTanggal = view.findViewById(R.id.tanggal_periksaMain);
        this.tvSuhu = view.findViewById(R.id.suhuTubuh_periksaMain);
        this.tvDetak = view.findViewById(R.id.detakjantung_periksaMain);
        this.tvTekanan = view.findViewById(R.id.tekananDarah_periksaMain);
        this.tvSaturasi = view.findViewById(R.id.saturasiOksigen_periksaMain);
        this.tvIdPetugas = view.findViewById(R.id.idPetugas_periksaMain);
        this.tvIdNode = view.findViewById(R.id.idNode_periksaMain);
        this.tvSuhuCond = view.findViewById(R.id.suhuTubuh_periksaConditionMain);
        this.tvDetakCond = view.findViewById(R.id.detakJantung_periksaConditionMain);
        this.tvTekananCond = view.findViewById(R.id.tekananDarah_periksaConditionMain);
        this.tvSaturasiCond = view.findViewById(R.id.saturasiOksigen_periksaConditionMain);
        this.postCalculateTask = new PostCalculateTask(getContext(), this,this,this, this, this, this, this, this, this);

        Bundle bundle = getArguments();
        if(bundle != null){
            this.idStff = bundle.getString("idStaff");
            this.idUsr = bundle.getString("idUsr");
            if (idStff == null){
                apicall = new String[2];
                apicall[0] = "medrecLatest";
                apicall[1] = idUsr;
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
            }else{
                this.idUsr = bundle.getString("idPsn");
                apicall = new String[2];
                apicall[0] = "medrecLatest";
                apicall[1] = idUsr;
            }
            try {
                postCalculateTask.callVolley(apicall);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        refreshLayout = view.findViewById(R.id.swipe_to_refresh_layout);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_green_dark, android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark, android.R.color.holo_red_dark);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                        try {
                            postCalculateTask.callVolley(apicall);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, 1000);
            }
        });

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

    public static MainFragment newInstance(){
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void callParentMethod(){
        getActivity().onBackPressed();
    }

    @Override
    public void hasil(MedrecDetails medrecDetails) {
        medrecDetailsLatest = medrecDetails;
        this.nama = medrecDetailsLatest.getNamaPasien();
        this.tvIdPeriksa.setText("nama : " + nama + "\n" + "pemeriksaan ke : " + String.valueOf(medrecDetails.getIdPeriksa()));
        this.tvTanggal.setText(medrecDetailsLatest.getTanggal());
        this.tvSuhu.setText(String.valueOf(medrecDetailsLatest.getSuhuTubuh()) + " \u2103");
        this.tvDetak.setText(String.valueOf(medrecDetailsLatest.getDetakJantung()) + " bpm");
        this.tvTekanan.setText(String.valueOf(medrecDetailsLatest.getTekananDarah()) + " mmHg");
        this.tvSaturasi.setText(String.valueOf(medrecDetailsLatest.getSaturasiOksigen()) + " %");
        this.tvIdPetugas.setText(medrecDetailsLatest.getNamaPetugas());
        this.tvIdNode.setText(String.valueOf(medrecDetailsLatest.getIdNode()));
        if (medrecDetailsLatest.getSuhuTubuh() == 0){
        }else{
            if (medrecDetailsLatest.getSuhuTubuh() < 36.1 || medrecDetailsLatest.getSuhuTubuh() > 37.2){
                this.tvSuhuCond.setText("tidak normal");
            }else{
                this.tvSuhuCond.setText("normal");
            }
        }
        if(medrecDetailsLatest.getDetakJantung() == 0){
        }else{
            if (medrecDetailsLatest.getDetakJantung() < 60 || medrecDetailsLatest.getDetakJantung() > 100){
                this.tvDetakCond.setText("tidak normal");
            }else{
                this.tvDetakCond.setText("normal");
            }
        }
        if(medrecDetailsLatest.getTekananDarah() == 0){
        }else{
//                if (medrecDetails.getTekananDarah() < 60 || medrecDetails.getTekananDarah() > 100){
//                    this.tvTekananCond.setText("tidak normal");
//                }else{
//                    this.tvTekananCond.setText("normal");
//                }
        }
        if(medrecDetailsLatest.getSaturasiOksigen() == 0){
        }else{
            if (medrecDetailsLatest.getSaturasiOksigen() < 95.00){
                this.tvSaturasiCond.setText("tidak normal");
            }else{
                this.tvSaturasiCond.setText("normal");
            }
        }
    }

    @Override
    public void logResult(Profile profile) {

    }

    @Override
    public void hasil(PasienDetails pasienDetails) {

    }

    @Override
    public void result(String message) {

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

    }
}

