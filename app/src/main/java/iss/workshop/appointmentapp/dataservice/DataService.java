package iss.workshop.appointmentapp.dataservice;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.AppointmentStatusEnum;
import iss.workshop.appointmentapp.model.Department;
import iss.workshop.appointmentapp.model.Patient;
import iss.workshop.appointmentapp.model.Schedule;
import iss.workshop.appointmentapp.model.Staff;
import iss.workshop.appointmentapp.model.User;

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
                        JSONObject data = (JSONObject) response.get(i);
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
        Patient responsepatient = new Patient();
        String url = "http://10.0.2.2:8080/api/addpatient/"+UserID;
        String formData = patient.toString();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    responsepatient.setId(jsonObject.getInt("id"));
                    responsepatient.setAddress(jsonObject.getString("address"));
                    responsepatient.setName(jsonObject.getString("name"));
                    responsepatient.setSex(jsonObject.getString("sex"));
                    responsepatient.setAllergy(jsonObject.getString("allergy"));
                    responsepatient.setMedical_condition(jsonObject.getString("medical_condition"));
                    responsepatient.setAdditional_information(jsonObject.getString("additional_information"));
                    addPatientListener.onResponse(responsepatient);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addPatientListener.onError("Error occur");
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return formData.getBytes();
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public interface GetDepartmentsListener{
        void onError(String message);
        void onResponse(List<Department> departments);
    }

    public void GetDepartments(GetDepartmentsListener getDepartmentsListener){
        List<Department> departments = new ArrayList<>();
        String url = "http://10.0.2.2:8080/api/getdepartments";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0;i<response.length();i++){
                        Department department = new Department();
                        JSONObject data = (JSONObject) response.get(i);
                        department.setId(data.getInt("id"));
                        department.setName(data.getString("name"));
                        departments.add(department);
                    }
                    getDepartmentsListener.onResponse(departments);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getDepartmentsListener.onError("Error occur");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public interface GetDoctorsListener{
        void onError(String message);
        void onResponse(List<Staff> staffs);
    }

    public void GetDoctors(int id,GetDoctorsListener getDoctorsListener){
        List<Staff> staffs = new ArrayList<>();
        String url = "http://10.0.2.2:8080/api/getdoctors?id="+id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0;i<response.length();i++){
                        Staff staff = new Staff();
                        Department department = new Department();
                        JSONObject data = (JSONObject) response.get(i);
                        staff.setId(data.getInt("id"));
                        staff.setName(data.getString("name"));
                        staff.setDesignation(data.getString("designation"));
                        department.setId(data.getJSONObject("department").getInt("id"));
                        department.setName(data.getJSONObject("department").getString("name"));
                        staff.setDepartment(department);
                        staffs.add(staff);
                    }
                    getDoctorsListener.onResponse(staffs);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getDoctorsListener.onError("Error occur");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface GetAppointmentsListener{
        void onError(String message);
        void onResponse(List<Appointment> appointments);
    }

    public void GetAppointments(int id,GetAppointmentsListener getAppointmentsListener){
        List<Appointment> appointments = new ArrayList<>();
        String url = "http://10.0.2.2:8080/api/getappointments?id="+id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0;i<response.length();i++){
                        JSONObject data = (JSONObject) response.get(i);
                        Appointment appointment = new Appointment(data);
                        appointments.add(appointment);
                    }
                    getAppointmentsListener.onResponse(appointments);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getAppointmentsListener.onError("Error occur");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public interface GetSchedulesListener{
        void onError(String message);
        void onResponse(List<Schedule> schedules);
    }

    public void GetSchedules(int id,GetSchedulesListener getSchedulesListener){
        List<Schedule> schedules = new ArrayList<>();
        String url = "http://10.0.2.2:8080/api/getschedules?id="+id;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0;i<response.length();i++){
                        Schedule schedule = new Schedule((JSONObject) response.get(i));
                        schedules.add(schedule);
                    }
                    getSchedulesListener.onResponse(schedules);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getSchedulesListener.onError("Error occur");
            }
        });
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface AddAppointmentListener{
        void onError(String message);
        void onResponse(Appointment appointment);
    }

    public void AddAppointment(int UserID,int StaffID,int PatientID,Appointment appointment,AddAppointmentListener addAppointmentListener){
        String url = "http://10.0.2.2:8080/api/addappointment?userid="+UserID+"&staffid="+StaffID+"&patientid="+PatientID;
        String formData = appointment.toString();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Appointment responseappointment = new Appointment(jsonObject);
                    addAppointmentListener.onResponse(responseappointment);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addAppointmentListener.onError("Error occur");
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return formData.getBytes();
            }
        };
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
