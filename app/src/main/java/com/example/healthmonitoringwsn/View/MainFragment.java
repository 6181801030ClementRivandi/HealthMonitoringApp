package com.example.healthmonitoringwsn.View;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Presenter.ProfilePresenter;
import com.example.healthmonitoringwsn.Sqlite;
import com.example.healthmonitoringwsn.R;

import java.util.List;

public class MainFragment extends Fragment implements View.OnClickListener{
    private FragmentManager fragmentManager;
    private FragmentListener listener;
    private Button btnProfile;
    private Sqlite sqlite;
    public MainFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_main,container, false);
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

        //this.sqlite = new Sqlite(this.getActivity());
        this.btnProfile = view.findViewById(R.id.btn_profile);

        this.btnProfile.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        if(v==this.btnProfile){
            try {
                listener.changePage(3);
            } catch (Exception e) {
            }

        }
    }

    public void callParentMethod(){
        getActivity().onBackPressed();
    }
}

