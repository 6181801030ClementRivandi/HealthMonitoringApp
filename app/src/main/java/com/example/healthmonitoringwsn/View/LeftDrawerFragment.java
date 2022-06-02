package com.example.healthmonitoringwsn.View;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.healthmonitoringwsn.R;

public class LeftDrawerFragment extends Fragment implements View.OnClickListener {

    public LeftDrawerFragment(){}

    Button btnPasien, btnPemeriksaan;

    FragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_left_drawer,container,false);

        this.btnPasien = view.findViewById(R.id.drawer_pasien);
        btnPasien.setOnClickListener(this);

        this.btnPemeriksaan = view.findViewById(R.id.drawer_pemeriksaan);
        btnPemeriksaan.setOnClickListener(this);

        Bundle bundle  = getArguments();
        if ( bundle == null){
            btnPemeriksaan.setVisibility(View.INVISIBLE);
        }else{
            String check = bundle.getString("btnAssign");
            if(check.equals("on")){
                btnPemeriksaan.setVisibility(View.VISIBLE);
            }
        }
//
//        this.btnSetting = view.findViewById(R.id.drawer_setting);
//        btnSetting.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        if ( context instanceof FragmentListener){
            this.listener = (FragmentListener) context;
        }else{
            throw new ClassCastException(context.toString()
                    + "must implement FragmentListener");
        }
    }
    public static LeftDrawerFragment newInstance(String title){
        LeftDrawerFragment fragment = new LeftDrawerFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if (v == btnPasien) {
            this.listener.changePage(6);
        }else if ( v == btnPemeriksaan){
            this.listener.changePage(2);
        }
//        }else if (v == btnSetting){
//            this.listener.changePage(3);
//        }else{
//            this.listener.closeApplication();
//        }
    }
}
