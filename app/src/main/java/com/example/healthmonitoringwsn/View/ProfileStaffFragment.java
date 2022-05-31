package com.example.healthmonitoringwsn.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.healthmonitoringwsn.Model.ProfileStaff;
import com.example.healthmonitoringwsn.Presenter.ProfileStaffPresenter;
import com.example.healthmonitoringwsn.R;
import com.example.healthmonitoringwsn.Sqlite;
import com.example.healthmonitoringwsn.SqliteStaff;

public class ProfileStaffFragment extends Fragment implements View.OnClickListener{
    private FragmentManager fragmentManager;
    private FragmentListener listener;
    private SqliteStaff sqliteStaff;
    private ProfileStaff profileStaff;
    ProfileStaffPresenter profileStaffPresenter;

    public TextView tvNamaStaff, tvNamaKlinikStaff, tvIdStaff;

    String idStaff;
    Button btnLogout;

    public ProfileStaffFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_staff, container, false);

        this.tvNamaStaff = view.findViewById(R.id.nama_Staff);
        this.tvNamaKlinikStaff = view.findViewById(R.id.namaKlinik_staff);
        this.tvIdStaff = view.findViewById(R.id.id_staff);
        this.btnLogout = view.findViewById(R.id.btn_logout_staff);
        this.btnLogout.setOnClickListener(this);
        this.sqliteStaff = new SqliteStaff(this.getActivity());

        Bundle bundle = getArguments();
        if(bundle != null){
            this.idStaff = bundle.getString("idStff");
        }
        this.profileStaff = this.sqliteStaff.getContact(Integer.parseInt(idStaff));
        this.tvNamaStaff.setText(profileStaff.getNamaStaff());
        this.tvNamaKlinikStaff.setText(profileStaff.getNamaKlinikStaff());
        this.tvIdStaff.setText(idStaff);
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

    public static ProfileStaffFragment newInstance() {
        ProfileStaffFragment fragment = new ProfileStaffFragment();
        return fragment;
    }

    @Override
    public void onClick(View view) {
        if (view == btnLogout){
            AlertDialog.Builder dialog1 = new AlertDialog.Builder(getContext());
            dialog1.setMessage("Apakah anda yakin ingin keluar?");
            dialog1.setCancelable(true);

            dialog1.setPositiveButton(
                    "keluar",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getContext(), "anda berhasil keluar", Toast.LENGTH_SHORT).show();
                            listener.changePage(1);
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
}
