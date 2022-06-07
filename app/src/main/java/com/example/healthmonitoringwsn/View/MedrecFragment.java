package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.ProfileStaff;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.Presenter.MedrecPresenter;
import com.example.healthmonitoringwsn.R;
import com.example.healthmonitoringwsn.Sqlite;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MedrecFragment extends Fragment implements PostCalculateTask.IMainActivityFindPsn, PostCalculateTask.IMainActivityAssignNode, PostCalculateTask.IMainActivity, PostCalculateTask.ILoginActivity, PostCalculateTask.ILoginActivityStaff, PostCalculateTask.IMainActivityPsn, MedrecPresenter.IMainActivity, PostCalculateTask.IMainActivityAddPsn, PostCalculateTask.IMainActivityEditPsn, PostCalculateTask.IMainActivityDelPsn, View.OnClickListener{

    private ListView medrecList;
    private MedrecPresenter presenter;
    private MedrecListAdapter adapter;
    private FragmentListener listener;
    private Activity activity;
    private ArrayList<MedrecDetails> hasilMedrec;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private Context context;
    Button filter, delFilter;
    String idUser, idStaff, idChecker, nama;
    String[] temp;
    Boolean state;
    PostCalculateTask postCalculateTask;
    private Sqlite sqlite;
    private Profile profile;

    public MedrecFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medrec, container, false);
        this.sqlite = new Sqlite(this.getActivity());
        Bundle bundle = getArguments();
        if(bundle != null){
            this.idUser = bundle.getString("idUsr");
            this.idStaff = bundle.getString("idStff");
            if(idUser == null){
                this.idChecker = idStaff;
            }else{
                this.idChecker = idUser;
                this.profile = this.sqlite.getContact(Integer.parseInt(idUser));
                this.nama = profile.getNamaUser();
            }
        }

        hasilMedrec = new ArrayList<>();

        this.medrecList = view.findViewById(R.id.list_medrec);
        this.presenter = new MedrecPresenter(this);

        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        this.adapter = new MedrecListAdapter((requireActivity()));
        //this.presenter.loadData();
        this.medrecList.setAdapter(this.adapter);

        this.medrecList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity main = (MainActivity) getActivity();
                MedrecDetails currentMedrec = (MedrecDetails) adapter.getItem(position);
                main.psMedrec(currentMedrec, idChecker, nama);
            }
        });

        this.postCalculateTask = new PostCalculateTask(getContext(), this,this,this, this, this, this, this, this, this);

        if ( temp != null && state == true){
            try {
                postCalculateTask.callVolley(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            String[] apicall = new String[2];
            if(idStaff != null){
                apicall[0] = "medrecStaff";
                apicall[1] = idStaff;
            }else{
                apicall[0] = "medrec";
                apicall[1] = idUser;
            }
            try {
                postCalculateTask.callVolley(apicall);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.filter = view.findViewById(R.id.btn_filter);

        this.filter.setOnClickListener(this);

        this.delFilter = view.findViewById(R.id.btn_delFilter);

        this.delFilter.setOnClickListener(this);

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
        if(medrecs.getIdPeriksa() == 0){
            Toast.makeText(getContext(), "hasil pemeriksaan tidak ada", Toast.LENGTH_SHORT).show();
            presenter.loadData();
        }else{
            presenter.addList(medrecs.getTanggal(), medrecs.getIdPeriksa(), medrecs.getSuhuTubuh(), medrecs.getDetakJantung(), medrecs.getTekananDarah(), medrecs.getSaturasiOksigen(), medrecs.getIdPasien(), medrecs.getNamaPetugas(), medrecs.getIdNode());
        }
    }

    @Override
    public void updateList(List<MedrecDetails> medrecDetailsUP) {
        this.adapter.updateArray(medrecDetailsUP);
    }

    @Override
    public void logResult(Profile profile) {

    }

    @Override
    public void onClick(View view) {
        if(view == this.filter){
            Calendar newCalendar = Calendar.getInstance();

            datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String[] apicallSort = new String[3];
                    if(idStaff == null){
                        apicallSort[0] = "medrecSort";
                        apicallSort[1] = idUser;
                        apicallSort[2] = dateFormatter.format(newDate.getTime());
                    }else{
                        apicallSort[0] = "medrecSortStaff";
                        apicallSort[1] = idStaff;
                        apicallSort[2] = dateFormatter.format(newDate.getTime());
                    }
                    temp = apicallSort;
                    state = true;
                    try {
                        postCalculateTask.callVolley(apicallSort);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
            presenter.refresh();
        }else{
            temp = null;
            state = false;
            String[] apicall = new String[2];
            if(idStaff == null){
                apicall[0] = "medrec";
                apicall[1] = idUser;
            }else{
                apicall[0] = "medrecStaff";
                apicall[1] = idStaff;
            }
            try {
                postCalculateTask.callVolley(apicall);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            presenter.refresh();
        }
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
}
