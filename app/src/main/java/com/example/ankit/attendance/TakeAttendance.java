package com.example.ankit.attendance;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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

public class TakeAttendance extends AppCompatActivity {

    TextView mSub;
    TextView mDate;
    AttendanceAdapter adapter;
    Button sumbit;
    ProgressDialog mProgressView;
    public static String resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        mSub = (TextView)findViewById(R.id.attendance_subject);
        mDate = (TextView)findViewById(R.id.attendance_date);
        sumbit = (Button)findViewById(R.id.submit);
        mProgressView = new ProgressDialog(this);
        mProgressView.setMessage("Loading students...");
        mProgressView.show();
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        mDate.setText(giveDate());
        mSub.setText(getIntent().getStringExtra("sub"));
        studentslist();
        adapter = new AttendanceAdapter(this, new ArrayList<attend>());
        final ListView studentListView = (ListView) findViewById(R.id.list_attendance);
        studentListView.setAdapter(adapter);
       AttendanceAdapter.subj = getIntent().getStringExtra("sub");


    }

    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        return sdf.format(cal.getTime());
    }


    private void studentslist() {

        final ArrayList<attend> mStudents = new ArrayList<>();
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
                        attend mAttend =  new attend(name, rollno);
                        mStudents.add(mAttend);
                    }
                    adapter.clear();
                    adapter.addAll(mStudents);

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
