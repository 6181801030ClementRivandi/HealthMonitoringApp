package com.example.healthmonitoringwsn.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.R;

public class MedrecDetailsFragment extends Fragment implements View.OnClickListener//, PostCalculateTask.IMainActivity1 {
    {
    private FragmentListener listener;
    Button btnBack;
    TextView tvIdPeriksa, tvTanggal, tvSuhu, tvDetak, tvTekanan, tvSaturasi, tvIdPetugas, tvIdNode;

    private MedrecDetails medrecDetails;

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

        this.btnBack = view.findViewById(R.id.btn_viewmedrec);
        this.btnBack.setOnClickListener(this);

        Bundle bundle = getArguments();
        if ( bundle != null){
            this.medrecDetails = bundle.getParcelable("medrecDetails");
            this.tvIdPeriksa.setText(String.valueOf(medrecDetails.getIdPeriksa()));
            this.tvTanggal.setText(medrecDetails.getTanggal());
            this.tvSuhu.setText(String.valueOf(medrecDetails.getSuhuTubuh()));
            this.tvDetak.setText(String.valueOf(medrecDetails.getDetakJantung()));
            this.tvTekanan.setText(String.valueOf(medrecDetails.getTekananDarah()));
            this.tvSaturasi.setText(String.valueOf(medrecDetails.getSaturasiOksigen()));
            this.tvIdPetugas.setText(String.valueOf(medrecDetails.getIdPetugas()));
            this.tvIdNode.setText(String.valueOf(medrecDetails.getIdNode()));
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
    public static MedrecDetailsFragment newInstance() {
        MedrecDetailsFragment fragment = new MedrecDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if (btnBack == v) {
            this.listener.changePage(4);
        }
    }
//
//    @Override
//    public void hasil(MedrecDetails hasilAkses) {
//        this.tvIdPeriksa.setText(hasilAkses.getIdPeriksa());
//    }
}
