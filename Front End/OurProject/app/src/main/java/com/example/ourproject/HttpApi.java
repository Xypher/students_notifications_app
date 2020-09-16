package com.example.ourproject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class HttpApi {


    private final static String LOGIN_URL = "";
    private static final String SIGNUP_URL = "";
    private final static String GET_CLASSROOMS_BY_USER_URL = "";
    private static final String ADD_STUDENT = "";
    private static final String CREATE_CLASS = "";
    private static final String CREATE_NOTIFICATION = "";
    private static final String GET_NOTIFICATIONS_BY_USER = "";
    private static final String GET_NOTIFICATIONS_BY_CLASS = "";

    private static JSONArray jarray;


    public static boolean login(User user, final AppCompatActivity activity) {


        RequestQueue queue = Volley.newRequestQueue(activity);
        final String username = user.getUsername();
        final String password = user.getPassword();
        String url = LOGIN_URL + username + "/" + password + "/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {


                        try {

                            if (response.getBoolean("success")) {
                                JSONObject result = response.getJSONObject("result");
                                String username = result.getString("username");
                                String password = result.getString("password");
                                String type = result.getString("type");
                                User user = new User(username, password);
                                user.setType(type);

                                LocalUserDetails det = new LocalUserDetails(activity);
                                det.loginUser(user);
                            }
                        } catch (JSONException exp) {
                            exp.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

        };


        queue.add(request);

        return new LocalUserDetails(activity).isLoggedIn();
    }


    public static JResponse Register(final User user, AppCompatActivity activity) {


        RequestQueue queue = Volley.newRequestQueue(activity);
        final JResponse<JSONArray, JSONObject> jres = new JResponse(false, null, null);

        String url = SIGNUP_URL + user.getUsername() + "/" + user.getPassword() + "/" + user.getFullname()
                + "/" + user.getFullname() + "/" + user.getID() + "/" + user.getEmail() + "/" + user.getType() + "/";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jres.success = response.getBoolean("success");
                            jres.errors = response.getJSONArray("errors");
                            jres.response = response.getJSONObject("result");


                        } catch (JSONException exp) {
                            exp.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }) {
        };


        queue.add(request);

        return jres;
    }


    public static JSONArray getClassrooms(final User user, AppCompatActivity activity) {


        RequestQueue queue = Volley.newRequestQueue(activity);
        String url = GET_CLASSROOMS_BY_USER_URL + "/" + user.getUsername() + "/" + user.getType() + "/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jarray = response.getJSONObject("result").getJSONArray("classrooms");
                        } catch (JSONException exp) {

                            exp.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }) {
        };


        queue.add(request);

        return jarray;
    }


    public static JSONArray getNotificationsByUser(User user, AppCompatActivity activity) {

        String url = GET_NOTIFICATIONS_BY_USER + user.getUsername() + "/" + user.getType() + "/";

        RequestQueue queue = Volley.newRequestQueue(activity);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jarray = response.getJSONObject("result")
                                    .getJSONObject("notifications")
                                    .getJSONArray("classrooms");
                        } catch (JSONException exp) {

                            exp.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }) {
        };

        queue.add(request);

        return jarray;
    }


    public static JSONArray getNotificationsByClass(User user, AppCompatActivity activity, String classroom) {

        String url = GET_NOTIFICATIONS_BY_CLASS + classroom + "/";

        RequestQueue queue = Volley.newRequestQueue(activity);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jarray = response.getJSONObject("result")
                                    .getJSONArray("notifications");

                        } catch (JSONException exp) {

                            exp.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }) {
        };


        queue.add(request);

        return jarray;
    }


    public static JResponse createNotification(User user, AppCompatActivity activity, final String classroom, final String title, final String subject, final String content,
                                               final String dead_year, final String dead_month, final String dead_day, final String dead_hour, final String dead_min) {

        final String username = user.getUsername();
        final JResponse<JSONArray, JSONObject> jres = new JResponse(false, null, null);


        RequestQueue queue = Volley.newRequestQueue(activity);

        String url = CREATE_NOTIFICATION + classroom + "/" + title + "/" + title + "/" + subject + "/" + content +
                "/" + dead_year + "/" + dead_month + "/" + dead_day + "/" + dead_hour + "/" + dead_min + "/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jres.success = response.getBoolean("success");
                            jres.errors = response.getJSONArray("errors");
                            jres.response = response.getJSONObject("result");
                        } catch (JSONException exp) {
                            exp.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }) {
        };

        queue.add(request);

        return jres;
    }


    public static JResponse createClassRoom(User user, AppCompatActivity activity, final String classroom) {

        final String username = user.getUsername();
        final JResponse<JSONArray, JSONObject> jres = new JResponse(false, null, null);

        final String url = CREATE_CLASS + username + "/" + classroom + "/";


        RequestQueue queue = Volley.newRequestQueue(activity);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jres.success = response.getBoolean("success");
                            jres.errors = response.getJSONArray("errors");
                            jres.response = response.getJSONObject("response");
                        } catch (JSONException exp) {
                            exp.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }) {

        };

        queue.add(request);

        return jres;
    }


    public static JResponse addStudenttoClassroom(User user, AppCompatActivity activity, final String classroom, final String student_ID) {

        final String username = user.getUsername();
        final JResponse<JSONArray, JSONObject> jres = new JResponse(false, null, null);

        String url = ADD_STUDENT + student_ID + "/" + classroom + "/";


        RequestQueue queue = Volley.newRequestQueue(activity);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            jres.success = response.getBoolean("success");
                            jres.errors = response.getJSONArray("errors");
                            jres.response = response.getJSONObject("response");
                        } catch (JSONException exp) {
                            exp.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();
                    }
                }) {
        };


        queue.add(request);

        return jres;
    }
}


class JResponse<E, R> {

    public boolean success;
    public E errors;
    public R response;

    public JResponse(boolean success, E errors, R response) {
        this.success = success;
        this.errors = errors;
        this.response = response;
    }
}
