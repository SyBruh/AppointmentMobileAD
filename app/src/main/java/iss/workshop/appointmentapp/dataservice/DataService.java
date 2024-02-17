package iss.workshop.appointmentapp.dataservice;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.AppointmentStatusEnum;
import iss.workshop.appointmentapp.model.Department;
import iss.workshop.appointmentapp.model.Feedback;
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
        String url = "https://adprojectapi.azurewebsites.net/api/checkuser?name="+ name+"&password=" + password;
        int timeoutMs = 20000;
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);
    }

    public interface GetPatientsListener{
        void onError(String message);
        void onResponse(List<Patient> patients);
    }

    public void GetPatients(int UserID, GetPatientsListener getPatientsListener){
        List<Patient> patients = new ArrayList<>();
        String url = "https://adprojectapi.azurewebsites.net/api/viewpatient?id="+ UserID;
        int timeoutMs = 20000;
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface AddPatientListener{
        void onError(String message);
        void onResponse(Patient patient);
    }

    public void AddPatient(int UserID,Patient patient,AddPatientListener addPatientListener){
        Patient responsepatient = new Patient();
        String url = "https://adprojectapi.azurewebsites.net/api/addpatient/"+UserID;
        int timeoutMs = 20000;
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public interface GetDepartmentsListener{
        void onError(String message);
        void onResponse(List<Department> departments);
    }

    public void GetDepartments(GetDepartmentsListener getDepartmentsListener){
        List<Department> departments = new ArrayList<>();
        String url = "https://adprojectapi.azurewebsites.net/api/getdepartments";
        int timeoutMs = 20000;
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public interface GetDoctorsListener{
        void onError(String message);
        void onResponse(List<Staff> staffs);
    }

    public void GetDoctors(int id,GetDoctorsListener getDoctorsListener){
        List<Staff> staffs = new ArrayList<>();
        String url = "https://adprojectapi.azurewebsites.net/api/getdoctors?id="+id;

        int timeoutMs = 40000;
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface GetAppointmentsListener{
        void onError(String message);
        void onResponse(List<Appointment> appointments);
    }

    public void GetAppointments(int id,GetAppointmentsListener getAppointmentsListener){
        List<Appointment> appointments = new ArrayList<>();
        String url = "https://adprojectapi.azurewebsites.net/api/getappointments?id="+id;
        int timeoutMs = 20000;
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public interface GetSchedulesListener{
        void onError(String message);
        void onResponse(List<Schedule> schedules);
    }

    public void GetSchedules(int id,GetSchedulesListener getSchedulesListener){
        List<Schedule> schedules = new ArrayList<>();
        String url = "https://adprojectapi.azurewebsites.net/api/getschedules?id="+id;
        int timeoutMs = 20000;
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface AddAppointmentListener{
        void onError(String message);
        void onResponse(Appointment appointment);
    }

    public void AddAppointment(int UserID,int StaffID,int PatientID,Appointment appointment,AddAppointmentListener addAppointmentListener){
        String url = "https://adprojectapi.azurewebsites.net/api/addappointment?userid="+UserID+"&staffid="+StaffID+"&patientid="+PatientID;
        String formData = appointment.toString();
        int timeoutMs = 20000;
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public interface UpdatePatientListener{
        void onError(String message);
        void onResponse(Patient patient);
    }

    public void UpdatePatient(int userid,Patient patient,UpdatePatientListener updatePatientListener){

        String url = "https://adprojectapi.azurewebsites.net/api/updatepatient/"+userid;
        String formData = "id="+ patient.getId() + "&" +patient.toString();
        int timeoutMs = 20000;
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Patient responsepatient = new Patient(jsonObject);
                    updatePatientListener.onResponse(responsepatient);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                updatePatientListener.onError("Error occur");
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public interface GetSymptomsGroupListener{
        void onError(String message);
        void onResponse(List<String> symptomGroups);
    }

    public void GetSymptomsGroup(GetSymptomsGroupListener getSymptomsGroupListener){
        List<String> symptomgroups = new ArrayList<>();
        String url = "https://adprojectapi.azurewebsites.net/api/getSymptomsGroup";
        int timeoutMs = 20000;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0;i<response.length();i++){
                        symptomgroups.add(response.get(i).toString());
                    }
                    getSymptomsGroupListener.onResponse(symptomgroups);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getSymptomsGroupListener.onError("Error occur");
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public interface GetSymptomsListener{
        void onError(String message);
        void onResponse(List<String> symptoms);
    }

    public void GetSymptoms(String symptomgroup,GetSymptomsListener getSymptomsListener){
        List<String> symptoms = new ArrayList<>();
        String url = "https://adprojectapi.azurewebsites.net/api/getSymptoms/"+symptomgroup;
        int timeoutMs = 20000;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0;i<response.length();i++){
                        symptoms.add(response.get(i).toString());
                    }
                    getSymptomsListener.onResponse(symptoms);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getSymptomsListener.onError("Error occur");
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface PredictListener{
        void onError(String message);
        void onResponse(Department department);
    }

    public void Predict (int patientid,int userid,List<String> selectedsymptoms,PredictListener predictListener){
        String StringList = selectedsymptoms.stream()
                .map(symptom -> "symptoms=" + symptom)
                .collect(Collectors.joining("&"));

        String url = "https://adprojectapi.azurewebsites.net/api/predict?patientid="+patientid+"&userid="+userid+"&"+StringList;
        //int timeoutMs = 20000;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Department department = new Department(response);
                predictListener.onResponse(department);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                predictListener.onError("Error occur");
            }
        });
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                timeoutMs,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//        ));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface AddFeedbackListener{
        void onError(String message);
        void onResponse(Feedback feedback);
    }

    public void AddFeedback(int UserID,int Appointmentid,Feedback feedback,AddFeedbackListener addFeedbackListener){
        String url = "https://adprojectapi.azurewebsites.net/api/savefeedback?userid="+UserID+"&appointmentid="+Appointmentid;
        int timeoutMs = 20000;
        String formData = feedback.toString();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Feedback responsefeedback = new Feedback(jsonObject);
                    addFeedbackListener.onResponse(responsefeedback);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addFeedbackListener.onError("Error occur");
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public interface GetFeedbacksListener{
        void onError(String message);
        void onResponse(List<Feedback> feedbacks    );
    }

    public void GetFeedbacks(int id,GetFeedbacksListener getFeedbacksListener){
        List<Feedback> feedbacks = new ArrayList<>();
        String url = "https://adprojectapi.azurewebsites.net/api/getfeedbacks?id="+id;
        int timeoutMs = 20000;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0;i<response.length();i++){
                        Feedback feedback = new Feedback((JSONObject) response.get(i));
                        feedbacks.add(feedback);
                    }
                    getFeedbacksListener.onResponse(feedbacks);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getFeedbacksListener.onError("Error occur");
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }

    public interface KeywordsListener{
        void onError(String message);
        void onResponse(List<String> keywords);
    }

    public void GetKeywords (List<String> feedbackdeslist,KeywordsListener keywordsListener){
        List<String> keywords = new ArrayList<>();
        String StringList = feedbackdeslist.stream()
                .map(des -> "feedbacks=" + des)
                .collect(Collectors.joining("&"));
        int timeoutMs = 20000;
        String url = "https://adprojectapi.azurewebsites.net/api/getkeywords?"+StringList;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for(int i = 0;i<response.length();i++){
                        keywords.add(response.get(i).toString());
                    }
                    keywordsListener.onResponse(keywords);
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                keywordsListener.onError("Error occur");
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface CancelAppointmentListener{
        void onError(String message);
        void onResponse(Appointment appointment);
    }

    public void CancelAppointment(int id,CancelAppointmentListener cancelAppointmentListener){

        String url = "https://adprojectapi.azurewebsites.net/api/cancelappointment/"+id;
        int timeoutMs = 20000;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Appointment appointment = new Appointment(response);
                cancelAppointmentListener.onResponse(appointment);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cancelAppointmentListener.onError("Error occur");
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);

    }
    public interface RemovePatientListener{
        void onError(String message);
        void onResponse(String success);
    }

    public void RemovePatient(int UserID,int PatientID,RemovePatientListener removePatientListener){
        String url = "https://adprojectapi.azurewebsites.net/api/removepatient/"+UserID+"/"+PatientID;
        int timeoutMs = 20000;
        StringRequest request = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                removePatientListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                removePatientListener.onError("Error occur");
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
    public interface AddUserListener{
        void onError(String message);
        void onResponse(User user);
    }

    public void AddUser(User user,AddUserListener addUserListener){
        String url = "https://adprojectapi.azurewebsites.net/api/registeruser";
        int timeoutMs = 20000;
        String formData = user.toString();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    User responseuser = new User(jsonObject);
                    addUserListener.onResponse(responseuser);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                addUserListener.onError("Error occur");
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
        request.setRetryPolicy(new DefaultRetryPolicy(
                timeoutMs,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        MySingleton.getInstance(context).addToRequestQueue(request);
    }
}
