package com.example.healthmonitoringwsn.View;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
import com.example.healthmonitoringwsn.R;

import java.util.ArrayList;
import java.util.List;

public class PasienListAdapter extends BaseAdapter {

    private List<PasienDetails> pasienDetailsList;
    private Activity activity;

    public PasienListAdapter(Activity activity) {
        this.pasienDetailsList = new ArrayList<PasienDetails>();
        this.activity = activity;
    }

    private class ViewHolder{
        protected TextView nama;
        public ViewHolder(View view) {
            this.nama = view.findViewById(R.id.name_pasien);
        }
    }

    public void addLine(PasienDetails newPasien){
        this.pasienDetailsList.add(newPasien);
        this.notifyDataSetChanged();
    }

    public void updateArray(List<PasienDetails> pasien){
        this.pasienDetailsList = pasien;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.pasienDetailsList.size();
    }

    @Override
    public Object getItem(int position) {
        return pasienDetailsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        View convertView = LayoutInflater.from(this.activity).inflate(R.layout.item_list_pasien, parent, false);
        PasienDetails currentPasien = (PasienDetails) this.getItem(i);
        ViewHolder viewHolder = new ViewHolder(convertView);
        viewHolder.nama.setText(String.valueOf(currentPasien.getNama()));
        return convertView;
    }
}

