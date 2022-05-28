package com.example.healthmonitoringwsn;

import android.content.Context;
import android.icu.lang.UProperty;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthmonitoringwsn.Model.MedrecDetails;
import com.example.healthmonitoringwsn.Model.PasienDetails;
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
    IMainActivityPsn uiPasien;
    IMainActivityAddPsn uiAddPasien;

    String nama, tanggalLahir, usia, nomorHP, email, tanggalDaftar, namaKlinik;
    int NIK, idPasien;

    public PostCalculateTask(Context context, ILoginActivity uiLog, IMainActivity uiMedrec, IMainActivityPsn uiPasien, IMainActivityAddPsn uiAddPasien) {
        this.context = context;
        this.uiLog = uiLog;
        this.uiMedrec = uiMedrec;
        this.uiPasien = uiPasien;
        this.uiAddPasien = uiAddPasien;
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
                                        NIK = 0;
                                        nama = "";
                                        usia = "";
                                        tanggalLahir = "";
                                        nomorHP = "";
                                        email = "";
                                        tanggalDaftar = "";
                                        namaKlinik = "";
                                        idPasien = 0;
                                    }else {
                                        for (int x = 0; x < result.getJSONObject("user").length(); x++) {
                                            nama = (String) result.getJSONObject("user").get("nama");
                                            usia = (String) result.getJSONObject("user").get("usia");
                                            tanggalLahir = (String) result.getJSONObject("user").get("tanggalLahir");
                                            nomorHP = (String) result.getJSONObject("user").get("nomorHP");
                                            email = (String) result.getJSONObject("user").get("email");
                                            tanggalDaftar = (String) result.getJSONObject("user").get("tanggalDaftar");
                                            namaKlinik = (String) result.getJSONObject("user").get("namaKlinik");
                                            NIK = (Integer) result.getJSONObject("user").get("NIK");
                                            idPasien = (Integer) result.getJSONObject("user").get("idPasien");
                                        }
                                    }
                                    Profile profile = new Profile(NIK, nama, usia, tanggalLahir, nomorHP, email, tanggalDaftar, namaKlinik, idPasien);
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
                BASE_URL = "http://172.20.10.2/Api.php?apicall=";
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
                BASE_URL = "http://172.20.10.2/Api.php?apicall=";
                break;
            case "medrecSort":
                BASE_URL += "medrecSort";
                idPsn = apicall[1];
                String tgl = apicall[2];
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
                        params.put("tanggal", tgl);
                        return params;
                    }
                };
                BASE_URL = "http://172.20.10.2/Api.php?apicall=";
                break;
            case "pasien":
                BASE_URL += "pasien";
                jsonObjRequest = new StringRequest(

                        Request.Method.POST,BASE_URL,
                        new Response.Listener<String>() {
                            String nama, usia, tanggalLahir, nomorHP, email, password, tanggalDaftar, namaKlinik;
                            int NIK, idPasien, idKlinik;
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject result = new JSONObject(response);
                                    for (int x = 0; x < result.getJSONArray("user").length(); x++) {
                                        nama = (String) result.getJSONArray("user").getJSONObject(x).get("nama");
                                        NIK = (Integer) result.getJSONArray("user").getJSONObject(x).get("NIK");
                                        usia = (String) result.getJSONArray("user").getJSONObject(x).get("usia");
                                        tanggalLahir = (String) result.getJSONArray("user").getJSONObject(x).get("tanggalLahir");
                                        idPasien = (Integer) result.getJSONArray("user").getJSONObject(x).get("idPasien");
                                        nomorHP = (String) result.getJSONArray("user").getJSONObject(x).get("nomorHP");
                                        email = (String) result.getJSONArray("user").getJSONObject(x).get("email");
                                        password = (String) result.getJSONArray("user").getJSONObject(x).get("password");
                                        tanggalDaftar = (String) result.getJSONArray("user").getJSONObject(x).get("tanggalDaftar");
                                        idKlinik = (Integer) result.getJSONArray("user").getJSONObject(x).get("idKlinik");
                                        namaKlinik = (String) result.getJSONArray("user").getJSONObject(x).get("namaKlinik");
                                        PasienDetails pasienDetails = new PasienDetails(this.nama, this.NIK, this.usia, this.tanggalLahir, this.idPasien, this.nomorHP, this.email, this.password, this.tanggalDaftar, this.idKlinik, this.namaKlinik);
                                        uiPasien.hasil(pasienDetails);
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
                        return params;
                    }
                };
                BASE_URL = "http://172.20.10.2/Api.php?apicall=";
                break;
            case "addPasien":
                BASE_URL += "addPasien";
                jsonObjRequest = new StringRequest(

                        Request.Method.POST,BASE_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject result = new JSONObject(response);
                                    String res = "";
                                    String checker = result.get("message").toString();
                                    if(checker.equals("add successful")) {
                                        res = "add successful";
                                    }else{
                                        res = "add failed";
                                    }
                                    uiAddPasien.result(res);
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
                        return params;
                    }
                };
                BASE_URL = "http://172.20.10.2/Api.php?apicall=";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + apicall[0]);
        }

        requestQueue.add(jsonObjRequest);
    }

    public interface ILoginActivity{
        void logResult(Profile profile);
    }

    public void processResult(MedrecDetails medrecDetails){
        this.uiMedrec.hasil(medrecDetails);
    }

    public interface IMainActivity{
        void hasil(MedrecDetails medrecDetails);
    }

    public interface IMainActivityPsn{
        void hasil(PasienDetails pasienDetails);
    }

    public interface IMainActivityAddPsn{
        void result(String message);
    }
}


