package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.R;

import java.util.ArrayList;
import java.util.List;

public class MedrecListAdapter extends BaseAdapter {

    private List<MedrecDetails> medrecDetailsList;
    private Activity activity;

    public MedrecListAdapter(Activity activity) {
        this.medrecDetailsList = new ArrayList<MedrecDetails>();
        this.activity = activity;
    }

    private class ViewHolder{
        protected TextView title;
        public ViewHolder(View view) {
            this.title = view.findViewById(R.id.medrec_title);
        }
    }

    public void addLine(MedrecDetails newMedrec){
        this.medrecDetailsList.add(newMedrec);
        this.notifyDataSetChanged();
    }

    public void updateArray(List<MedrecDetails> medrecs){
        this.medrecDetailsList = medrecs;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.medrecDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return medrecDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View convertView = LayoutInflater.from(this.activity).inflate(R.layout.item_list_medrec, parent, false);
        MedrecDetails currentMedrec = (MedrecDetails) this.getItem(i);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.title.setText("tanggal pemeriksaan : " + String.valueOf(currentMedrec.getTanggal()));
        return convertView;
    }
}

