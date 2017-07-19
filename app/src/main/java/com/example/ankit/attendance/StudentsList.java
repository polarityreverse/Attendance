package com.example.ankit.attendance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
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

import java.util.ArrayList;

public class StudentsList extends AppCompatActivity {
    ProgressDialog mProgressView;
    StudentsAdapter mStudAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);
        mProgressView = new ProgressDialog(this);
        mProgressView.setMessage("Fetching students list...");
        mProgressView.show();
        studentslist();

        mStudAdapter = new StudentsAdapter(this, new ArrayList<students>());
        final ListView subjectListView = (ListView) findViewById(R.id.stud_list);
        subjectListView.setAdapter(mStudAdapter);

        FloatingActionButton addStud =  (FloatingActionButton)findViewById(R.id.fab);
        addStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentsList.this, AddStudent.class));
            }
        });

    }

    private void studentslist() {

        final ArrayList<students> mStudents = new ArrayList<>();
        StringRequest subjectArrayRequest = new StringRequest(Request.Method.GET, Constants.LIST_STUDENTS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    mProgressView.dismiss();
                    JSONArray baseArray = new JSONArray(response);
                    for (int i = 0; i < baseArray.length(); i++) {
                        JSONObject baseObject = baseArray.getJSONObject(i);
                        String name = baseObject.getString("name");
                        String rollno = baseObject.getString("roll_no");
                        String email = baseObject.getString("email");
                        String phone = baseObject.getString("phone_no");
                        students stud = new students(name, rollno, email , phone);
                        mStudents.add(stud);
                    }
                    mStudAdapter.clear();
                    mStudAdapter.addAll(mStudents);

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
        });
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(subjectArrayRequest);
    }
}
