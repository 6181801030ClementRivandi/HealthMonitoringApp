package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.Presenter.MedrecPresenter;
import com.example.healthmonitoringwsn.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MedrecFragment extends Fragment implements PostCalculateTask.IMainActivity, PostCalculateTask.ILoginActivity, MedrecPresenter.IMainActivity{

    private ListView medrecList;
    private MedrecPresenter presenter;
    private MedrecListAdapter adapter;
    private FragmentListener listener;
    private Activity activity;
    private ArrayList<MedrecDetails> hasilMedrec;
    PostCalculateTask postCalculateTask;

    public MedrecFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medrec, container, false);

        hasilMedrec = new ArrayList<>();

        this.medrecList = view.findViewById(R.id.list_medrec);
        this.presenter = new MedrecPresenter((MedrecPresenter.IMainActivity) this);

        this.adapter = new MedrecListAdapter((requireActivity()));
        //this.presenter.loadData();
        this.medrecList.setAdapter(this.adapter);

        this.medrecList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity main = (MainActivity) getActivity();
                MedrecDetails incidentDetailsATM = (MedrecDetails) adapter.getItem(position);
                //main.psIncident(incidentDetailsATM);
            }
        });

        this.postCalculateTask = new PostCalculateTask(getContext(), this, this);//, (PostCalculateTask.ILoginActivity) this, this);
        String[] apicall = new String[2];
        apicall[0] = "medrec";
        apicall[1] = "1";
        try {
            postCalculateTask.callVolley(apicall);
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public static MedrecFragment newInstance() {
        MedrecFragment fragment = new MedrecFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void hasil(MedrecDetails medrecDetails) {
        MedrecDetails medrecs = medrecDetails;
        presenter.addList(medrecs.getTanggal(), medrecs.getIdPeriksa(), medrecs.getSuhuTubuh(), medrecs.getDetakJantung(), medrecs.getTekananDarah(), medrecs.getSaturasiOksigen(), medrecs.getIdPasien(), medrecs.getIdPetugas(), medrecs.getIdNode());
    }

    @Override
    public void updateList(List<MedrecDetails> medrecDetailsUP) {
        this.adapter.updateArray(medrecDetailsUP);
    }

    @Override
    public void logResult(Profile profile) {

    }

//    @Override
//    public void updateList(List<MedrecDetails> medrecDetailsUP) {
//        this.adapter.updateArray(medrecDetailsUP);
//    }
//
//    @Override
//    public void hasil(IncidentDetails hasilAkses) {
//        IncidentDetails tester = hasilAkses;
//        presenter.addList(tester.getTitle(), tester.getDescription(), tester.getAddress());
//    }
}

