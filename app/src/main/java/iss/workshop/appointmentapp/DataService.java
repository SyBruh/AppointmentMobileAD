package iss.workshop.appointmentapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataService {
    Context context;
    public DataService(Context context){
        this.context = context;
    }

    public interface UserAuthenListener{
        void onError(String message);
        void onResponse(JSONObject user);
    }

    public void UserAuthen(String name,String password, UserAuthenListener userAuthenListener){
        User user;
        String url = "http://10.0.2.2:8080/api/checkuser?name="+ name+"&password=" + password;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                userAuthenListener.onResponse(response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                userAuthenListener.onError("Not match");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface GetPatientsListener{
        void onError(String message);
        void onResponse(List<Patient> patients);
    }

    public void GetPatients(int UserID, GetPatientsListener getPatientsListener){
        List<Patient> patients = new ArrayList<>();
        String url = "http://10.0.2.2:8080/api/viewpatient?id="+ UserID;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0;i<response.length();i++){
                        Patient patient = new Patient();
                        JSONObject data = (JSONObject) response.get(0);
                        patient.setId(data.getInt("id"));
                        patient.setAddress(data.getString("address"));
                        patient.setName(data.getString("name"));
                        patient.setSex(data.getString("sex"));
                        patient.setAllergy(data.getString("allergy"));
                        patient.setMedical_condition(data.getString("medical_condition"));
                        patient.setAdditional_information(data.getString("additional_information"));
                        patients.add(patient);
                    }
                    getPatientsListener.onResponse(patients);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getPatientsListener.onError("Error occur");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface AddPatientListener{
        void onError(String message);
        void onResponse(Patient patient);
    }

    public void AddPatient(int UserID,Patient patient,AddPatientListener addPatientListener){
        String url = "http://10.0.2.2:8080/api/viewpatient?id=";
    }
}
