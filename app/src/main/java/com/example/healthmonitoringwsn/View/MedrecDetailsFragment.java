package com.example.healthmonitoringwsn.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.ProfileStaff;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.R;

import org.json.JSONException;

public class MedrecDetailsFragment extends Fragment implements PostCalculateTask.ILoginActivity, PostCalculateTask.ILoginActivityStaff, PostCalculateTask.IMainActivity, PostCalculateTask.IMainActivityPsn, PostCalculateTask.IMainActivityAddPsn, PostCalculateTask.IMainActivityEditPsn, PostCalculateTask.IMainActivityDelPsn, PostCalculateTask.IMainActivityAssignNode, View.OnClickListener//, PostCalculateTask.IMainActivity1 {
    {
    private FragmentListener listener;
    Button btnDelete;
    TextView tvIdPeriksa, tvTanggal, tvSuhu, tvDetak, tvTekanan, tvSaturasi, tvIdPetugas, tvIdNode;
    TextView tvSuhuCond;
    String idCheck;
    private MedrecDetails medrecDetails;
    PostCalculateTask postCalculateTask;

    public MedrecDetailsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medrec_details_fragment, container, false);

        this.tvIdPeriksa = view.findViewById(R.id.judul_periksa);
        this.tvTanggal = view.findViewById(R.id.tanggal_periksa);
        this.tvSuhu = view.findViewById(R.id.suhuTubuh_periksa);
        this.tvDetak = view.findViewById(R.id.detakjantung_periksa);
        this.tvTekanan = view.findViewById(R.id.tekananDarah_periksa);
        this.tvSaturasi = view.findViewById(R.id.saturasiOksigen_periksa);
        this.tvIdPetugas = view.findViewById(R.id.idPetugas_periksa);
        this.tvIdNode = view.findViewById(R.id.idNode_periksa);
        this.tvSuhuCond = view.findViewById(R.id.suhuTubuh_periksaCondition);
        this.btnDelete = view.findViewById(R.id.btn_delMedrec);
        this.btnDelete.setOnClickListener(this);

        Bundle bundle = getArguments();
        if ( bundle != null){
            this.medrecDetails = bundle.getParcelable("medrecDetails");
            this.tvIdPeriksa.setText("id pemeriksaan : " + String.valueOf(medrecDetails.getIdPeriksa()));
            this.tvTanggal.setText(medrecDetails.getTanggal());
            this.tvSuhu.setText(String.valueOf(medrecDetails.getSuhuTubuh()));
            if (medrecDetails.getSuhuTubuh() <= 36.5 || medrecDetails.getSuhuTubuh() >= 37.5){
                this.tvSuhuCond.setText("tidak normal");
            }else{
                this.tvSuhuCond.setText("normal");
            }
            this.tvDetak.setText(String.valueOf(medrecDetails.getDetakJantung()));
            this.tvTekanan.setText(String.valueOf(medrecDetails.getTekananDarah()));
            this.tvSaturasi.setText(String.valueOf(medrecDetails.getSaturasiOksigen()));
            this.tvIdPetugas.setText(String.valueOf(medrecDetails.getIdPetugas()));
            this.tvIdNode.setText(String.valueOf(medrecDetails.getIdNode()));
            this.idCheck = bundle.getString("checkId");
        }

        btnDelete.setVisibility(View.INVISIBLE);
        if(idCheck.indexOf("924") == -1){
            btnDelete.setVisibility(View.INVISIBLE);
        }else{
            btnDelete.setVisibility(View.VISIBLE);
        }
        this.postCalculateTask = new PostCalculateTask(getContext(), this,this, this, this, this, this, this, this);

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
    public static MedrecDetailsFragment newInstance() {
        MedrecDetailsFragment fragment = new MedrecDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if (btnDelete == v) {
            String[] apicall = new String[2];
            apicall[0] = "deleteMedrec";
            apicall[1] = String.valueOf(medrecDetails.getIdPeriksa());
            AlertDialog.Builder dialog1 = new AlertDialog.Builder(getContext());
            dialog1.setMessage("Apakah anda yakin ingin menghapus hasil ini?");
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
                            listener.changePage(4);
                            Toast.makeText(getContext(), "hasil berhasil dihapus", Toast.LENGTH_SHORT).show();

                        }
                    });

            dialog1.setNegativeButton(
                    "batal",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = dialog1.create();
            alert11.show();
        }
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

        }
    }
