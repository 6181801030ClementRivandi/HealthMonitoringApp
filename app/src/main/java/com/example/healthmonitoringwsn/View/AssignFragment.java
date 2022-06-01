package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.healthmonitoringwsn.PostCalculateTask;
import com.example.healthmonitoringwsn.Presenter.ILoginPresenter;
import com.example.healthmonitoringwsn.Presenter.LoginPresenter;
import com.example.healthmonitoringwsn.R;
import com.example.healthmonitoringwsn.Sqlite;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;

public class AssignFragment extends Fragment implements View.OnClickListener {

    EditText eTidPasien, eTidNode;
    Button btnSubmit;
    String idPasien;
    String idNode;
    PostCalculateTask postCalculateTask;

    public AssignFragment(){}

    FragmentListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_assign,container,false);

        this.eTidPasien = view.findViewById(R.id.assign_idPasien);
        this.eTidNode = view.findViewById(R.id.assign_idNode);
        this.btnSubmit = view.findViewById(R.id.btn_submit);
        this.btnSubmit.setOnClickListener(this);

        //this.postCalculateTask = new PostCalculateTask(getContext(), this, this, this, this, this, this, this);

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

    public static AssignFragment newInstance(){
        AssignFragment fragment = new AssignFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        if( v == btnSubmit){
            idPasien = eTidPasien.getText().toString();
            idNode = eTidNode.getText().toString();
            String[] apicall = new String[3];
            apicall[0] = "assign";
            apicall[1] = idPasien;
            apicall[2] = idNode;
            try {
                postCalculateTask.callVolley(apicall);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.hideKeyboard(getActivity());
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
