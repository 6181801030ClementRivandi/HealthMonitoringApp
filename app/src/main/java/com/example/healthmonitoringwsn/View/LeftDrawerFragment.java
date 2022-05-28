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

    Button btnProfile;
//    Button btnSetting;
    Button btnExit;

    FragmentListener listener;
    DrawerLayout drawer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_left_drawer,container,false);

        this.btnProfile = view.findViewById(R.id.drawer_profile);
        btnProfile.setOnClickListener(this);
//
//        this.btnSetting = view.findViewById(R.id.drawer_setting);
//        btnSetting.setOnClickListener(this);

        this.btnExit = view.findViewById(R.id.drawer_exit);
        btnExit.setOnClickListener(this);

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
        if (v == btnProfile) {
            this.listener.changePage(6);
        }else if (v == btnExit){
            this.listener.closeApplication();
        }
//        }else if (v == btnSetting){
//            this.listener.changePage(3);
//        }else{
//            this.listener.closeApplication();
//        }
    }
}
