package com.example.healthmonitoringwsn;

import android.content.Context;
import android.icu.lang.UProperty;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.Profile;
import com.google.gson.JsonIOException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostCalculateTask {

    private String BASE_URL = "http://172.20.10.2/Api.php?apicall=";
    Context context;
    ILoginActivity uiLog;
    IMainActivity uiMedrec;

    String nama, tanggalLahir, usia;
    int idPasien;

    Profile profile;

    public PostCalculateTask(Context context, ILoginActivity uiLog, IMainActivity uiMedrec) {
        this.context = context;
        this.uiLog = uiLog;
        this.uiMedrec = uiMedrec;
    }

    public void callVolley(String[] apicall) throws JsonIOException, JSONException {

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest jsonObjRequest;

        switch (apicall[0]){
            case "login":
                BASE_URL += "login";
                String idPsn = apicall[1];
                String passPsn = apicall[2];
                jsonObjRequest = new StringRequest(

                        Request.Method.POST,BASE_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject result = new JSONObject(response);
                                    String checker = result.get("message").toString();
                                    if(checker.equals("invalid idPasien or password")){
                                        nama = "";
                                        usia = "";
                                        tanggalLahir = "";
                                        idPasien = 0;
                                    }else {
                                        for (int x = 0; x < result.getJSONObject("user").length(); x++) {
                                            nama = (String) result.getJSONObject("user").get("nama");
                                            usia = (String) result.getJSONObject("user").get("usia");
                                            tanggalLahir = (String) result.getJSONObject("user").get("tanggalLahir");
                                            idPasien = (Integer) result.getJSONObject("user").get("idPasien");
                                        }
                                    }
                                    profile = new Profile(nama, usia, tanggalLahir, idPasien);
                                    uiLog.logResult(profile);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("volley", "Error: " + error.getMessage());
                                error.printStackTrace();
                            }
                        }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("idPasien", idPsn);
                        params.put("password", passPsn);
                        return params;
                    }
                };
                break;
            case "medrec":
                BASE_URL += "medrec";
                idPsn = apicall[1];
                jsonObjRequest = new StringRequest(Request.Method.POST,BASE_URL, new Response.Listener<String>() {

                            String tanggal;
                            int idPeriksa, detakJantung, tekananDarah, idPasien, idPetugas, idNode;
                            double suhuTubuh, saturasiOksigen;
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject result = new JSONObject(response);
                                    for (int x = 0; x < result.getJSONArray("periksa").length(); x++) {
                                        tanggal = (String)result.getJSONArray("periksa").getJSONObject(x).get("tanggal");
                                        idPeriksa = (Integer)result.getJSONArray("periksa").getJSONObject(x).get("idPeriksa");
                                        suhuTubuh = (Double) result.getJSONArray("periksa").getJSONObject(x).get("suhuTubuh");
                                        detakJantung = (Integer)result.getJSONArray("periksa").getJSONObject(x).get("detakJantung");
                                        tekananDarah = (Integer)result.getJSONArray("periksa").getJSONObject(x).get("tekananDarah");
                                        //saturasiOksigen = (Double)result.getJSONArray("periksa").getJSONObject(x).get("saturasiOksigen");
                                        saturasiOksigen = 0.0;
                                        idPasien = (Integer) result.getJSONArray("periksa").getJSONObject(x).get("idPasien");
                                        idPetugas = (Integer) result.getJSONArray("periksa").getJSONObject(x).get("idPetugas");
                                        idNode = (Integer) result.getJSONArray("periksa").getJSONObject(x).get("idNode");
                                        MedrecDetails medrecDetails = new MedrecDetails(this.tanggal, this.idPeriksa, this.suhuTubuh, this.detakJantung, this.tekananDarah, this.saturasiOksigen, this.idPasien, this.idPetugas, this.idNode);
                                        uiMedrec.hasil(medrecDetails);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d("volley", "Error: " + error.getMessage());
                                error.printStackTrace();
                            }
                        }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded; charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("idPasien", idPsn);
                        return params;
                    }
                };
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + apicall[0]);
        }

        requestQueue.add(jsonObjRequest);
    }

//    public void processResult(MedrecDetails medrecDetails){
//        this.ui.hasil(resHasil);
//    }

    public interface ILoginActivity{
        void logResult(Profile profile);
    }

    public void processResult(MedrecDetails medrecDetails){
        this.uiMedrec.hasil(medrecDetails);
    }

    public interface IMainActivity{
        void hasil(MedrecDetails medrecDetails);
    }
}


