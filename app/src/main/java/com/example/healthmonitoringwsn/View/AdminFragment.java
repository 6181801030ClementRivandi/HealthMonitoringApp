package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.example.healthmonitoringwsn.Model.ProfileStaff;
import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.R;

import org.json.JSONException;

public class AdminFragment extends Fragment {

    TextView eT1, eT2;

    public AdminFragment(){}

    FragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_admin,container,false);
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

        this.eT1 = view.findViewById(R.id.tv_homeAdmin1);
        this.eT2 = view.findViewById(R.id.tv_homeAdmin2);

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

    public static AdminFragment newInstance(){
        AdminFragment fragment = new AdminFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
