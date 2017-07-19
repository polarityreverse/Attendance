package com.example.ankit.attendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SubjectsToday extends AppCompatActivity {

    ProgressDialog mProgressView;
    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    Date date = new Date();
    String dayOfTheWeek = sdf.format(date);
    private SubjectsAdapter mAdapter;
    RelativeLayout header;
    TextView sundayView, headerDate;
    public final ArrayList<Subject> subjects = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects_today);
        header = (RelativeLayout) findViewById(R.id.header);
        sundayView = (TextView) findViewById(R.id.sunday);
        headerDate = (TextView) findViewById(R.id.date);
        headerDate.setText(giveDate());
        mProgressView = new ProgressDialog(this);

        sundayView.setVisibility(View.GONE);
        if (dayOfTheWeek.equals("Sunday")) {
            header.setVisibility(View.GONE);
            sundayView.setVisibility(View.VISIBLE);
        } else {
            mProgressView.setMessage("Loading your schedule...");
            mProgressView.show();
            SubjectList();
            mAdapter = new SubjectsAdapter(this, new ArrayList<Subject>());
            final ListView subjectListView = (ListView) findViewById(R.id.list);
            subjectListView.setAdapter(mAdapter);
            subjectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String subject = subjects.get(position).getSubject();
                    checkAttendance(subject);
                }
            });
        }
    }


    private void checkAttendance(final String gSubject) {

        StringRequest checkRequest = new StringRequest(Request.Method.POST, Constants.CHECK_ATTENDANCE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("1")){
                    Intent i = new Intent(getApplicationContext(), TakeAttendance.class);
                    i.putExtra("sub", gSubject);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(), response , Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressView.dismiss();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet. Please check your connection!!";
                } else if (error instanceof ServerError) {
                    message = "Server down. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Authentication error!!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.KEY_SUBJECT, gSubject);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(checkRequest);
    }


    public void SubjectList() {

        StringRequest subjectArrayRequest = new StringRequest(Request.Method.POST, Constants.URL_TODAY_SUBJECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    mProgressView.dismiss();
                    JSONArray baseArray = new JSONArray(response);
                    for (int i = 0; i < baseArray.length(); i++) {
                        JSONObject baseObject = baseArray.getJSONObject(i);
                        String subject = baseObject.getString("subject");
                        Log.v("Subject ", subject);
                        String timing = baseObject.getString(dayOfTheWeek+"time");
                        Subject mSubject = new Subject(subject, timing);
                        Log.v("Time ", timing);
                        subjects.add(mSubject);
                    }
                    mAdapter.clear();
                    mAdapter.addAll(subjects);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressView.dismiss();
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet. Please check your connection!!";
                } else if (error instanceof ServerError) {
                    message = "Server down. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Authentication error!!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.KEY_DAY, dayOfTheWeek);
                return params;
            }
        };
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectArrayRequest);
    }

    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        return sdf.format(cal.getTime());
    }
}
