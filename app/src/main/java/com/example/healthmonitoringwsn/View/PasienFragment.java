package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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

public class PasienFragment extends Fragment implements PostCalculateTask.IMainActivityAssignNode, PostCalculateTask.IMainActivity, PostCalculateTask.ILoginActivity, PostCalculateTask.ILoginActivityStaff, PostCalculateTask.IMainActivityPsn, PasienPresenter.IMainActivity, PostCalculateTask.IMainActivityAddPsn, PostCalculateTask.IMainActivityEditPsn, PostCalculateTask.IMainActivityDelPsn, PostCalculateTask.IMainActivityFindPsn, View.OnClickListener{

    private ListView pasienList;
    private PasienPresenter presenter;
    private PasienListAdapter adapter;
    private FragmentListener listener;
    private Activity activity;
    private ArrayList<PasienDetails> hasilPasien;
    FloatingActionButton add;
    String idUser;
    PostCalculateTask postCalculateTask;
    Button btnCariPasien, btnDelFilter;
    EditText edCari;

    String[] temp;
    Boolean state;
    String checker;

    public PasienFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pasien, container, false);

        Bundle bundle = getArguments();
        if(bundle != null){
            this.idUser = bundle.getString("pasienDetails");
        }

        this.btnCariPasien = view.findViewById(R.id.btn_cariPasien);
        this.btnDelFilter = view.findViewById(R.id.btn_delFilterPasien);
        this.btnCariPasien.setOnClickListener(this);
        this.btnDelFilter.setOnClickListener(this);

        this.edCari = view.findViewById(R.id.ed_cariPasien);

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

        this.postCalculateTask = new PostCalculateTask(getContext(), this,this,this, this, this, this, this, this, this);

        if ( temp != null && state == true){
            try {
                postCalculateTask.callVolley(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            String[] apicall = new String[1];
            apicall[0] = "pasien";
            try {
                postCalculateTask.callVolley(apicall);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        this.add = view.findViewById(R.id.fab_add);

        this.add.setOnClickListener(this);

//        presenter.refresh();

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
    public void onClick(View view) {
        if(view == this.add){
            this.listener.changePage(8);
        }else if(view == btnCariPasien){
            String nama = edCari.getText().toString();
            if(nama.equals("")){
                Toast.makeText(getContext(), "anda belum memasukkan nama", Toast.LENGTH_SHORT).show();
            }else{
                String[] apicall = new String[2];
                apicall[0] = "findPasien";
                apicall[1] = nama;
                temp = apicall;
                state = true;
                try {
                    postCalculateTask.callVolley(apicall);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                presenter.refresh();
            }
            this.hideKeyboard(getActivity());
        }else if(view == btnDelFilter){
            temp = null;
            state = false;
            String[] apicall = new String[1];
            apicall[0] = "pasien";
            try {
                postCalculateTask.callVolley(apicall);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            edCari.setText(null);
            presenter.refresh();
        }
    }

    @Override
    public void hasil(PasienDetails pasienDetails) {
        PasienDetails pasien = pasienDetails;
        if(pasien.getIdPasien() == 0){
            Toast.makeText(getContext(), "nama pasien tidak ada", Toast.LENGTH_SHORT).show();
            presenter.loadData();
        }else{
            presenter.addList(pasien.getNama(), pasien.getNIK(), pasien.getUsia(), pasien.getTanggalLahir(), pasien.getIdPasien(), pasien.getNomorHP(), pasien.getEmail(), pasien.getPassword(), pasien.getTanggalDaftar(), pasien.getIdKlinik(), pasien.getNamaKlinik());
        }
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
        checker = message;
        Log.d("pasien gaada", "pasien gaada");
        check();
    }

    public void check(){
        int temp;
//        Log.d("checker", checker);
        if(checker != null){
            temp = 1;
        }else{
            temp = 0;
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
