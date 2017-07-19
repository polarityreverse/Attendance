package com.example.ankit.attendance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static com.example.ankit.attendance.R.id.tuesday;


public class AddSubject extends AppCompatActivity {

    private EditText mTeacherId;
    private EditText mSubject;
    private CheckBox mMonday;
    private CheckBox mTuesday;
    private CheckBox mWednesday;
    private CheckBox mThursday;
    private CheckBox mFriday;
    private CheckBox mSaturday;
    public  String Mondaytime = "";
    public  String Tuesdaytime = "";
    public  String Wednesdaytime = "";
    public  String Thursdaytime = "";
    public  String Fridaytime ="";
    public  String Saturdaytime ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        mTeacherId = (EditText)findViewById(R.id.teacherId);
        mSubject = (EditText)findViewById(R.id.add_subject);
        mMonday= (CheckBox)findViewById(R.id.monday);
        mTuesday= (CheckBox)findViewById(tuesday);
        mWednesday= (CheckBox)findViewById(R.id.wednesday);
        mThursday= (CheckBox)findViewById(R.id.thursday);
        mFriday= (CheckBox)findViewById(R.id.friday);
        mSaturday= (CheckBox)findViewById(R.id.saturday);


        Spinner monSpinner = (Spinner) findViewById(R.id.monday_timings_spinner);
        ArrayAdapter<CharSequence> monAdapter = ArrayAdapter.createFromResource(this,
                R.array.timings, android.R.layout.simple_spinner_item);
        monAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monSpinner.setAdapter(monAdapter);

        monSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               Mondaytime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner tueSpinner = (Spinner) findViewById(R.id.tuesday_timings_spinner);
        ArrayAdapter<CharSequence> tueAdapter = ArrayAdapter.createFromResource(this,
                R.array.timings, android.R.layout.simple_spinner_item);
        tueAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tueSpinner.setAdapter(tueAdapter);

        tueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              Tuesdaytime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner wedSpinner = (Spinner) findViewById(R.id.wednesday_timings_spinner);
        ArrayAdapter<CharSequence> wedAdapter = ArrayAdapter.createFromResource(this,
                R.array.timings, android.R.layout.simple_spinner_item);
        wedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wedSpinner.setAdapter(wedAdapter);

        wedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 Wednesdaytime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner thuSpinner = (Spinner) findViewById(R.id.thursday_timings_spinner);
        ArrayAdapter<CharSequence> thuAdapter = ArrayAdapter.createFromResource(this,
                R.array.timings, android.R.layout.simple_spinner_item);
        thuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        thuSpinner.setAdapter(thuAdapter);

        thuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Thursdaytime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner friSpinner = (Spinner) findViewById(R.id.friday_timings_spinner);
        ArrayAdapter<CharSequence> friAdapter = ArrayAdapter.createFromResource(this,
                R.array.timings, android.R.layout.simple_spinner_item);
        friAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        friSpinner.setAdapter(friAdapter);

        friSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Fridaytime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner satSpinner = (Spinner) findViewById(R.id.saturday_timings_spinner);
        ArrayAdapter<CharSequence> satAdapter = ArrayAdapter.createFromResource(this,
                R.array.timings, android.R.layout.simple_spinner_item);
        satAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        satSpinner.setAdapter(satAdapter);

        satSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Saturdaytime = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button mDone = (Button) findViewById(R.id.done);
        mDone.setOnClickListener(new  View.OnClickListener(){

            @Override
            public void onClick(View v) {
                insertData();
                finish();

            }


        });

    }

    private void insertData() {
        final String teacherId = mTeacherId.getText().toString().trim();
        final String subject = mSubject.getText().toString().trim();
        int monday = 0;
        int tuesday = 0;
        int wednesday = 0;
        int thursday = 0;
        int friday = 0;
        int saturday = 0;
        if(mMonday.isChecked()){
            monday = 1;
        }if(mTuesday.isChecked()){
            tuesday = 1;
        }if(mWednesday.isChecked()){
            wednesday = 1;
        }if(mThursday.isChecked()){
            thursday = 1;
        }if(mFriday.isChecked()){
            friday = 1;
        }if(mSaturday.isChecked()){
            saturday =1;
        }

        final int finalMonday = monday;
        final int finalSaturday = saturday;
        final int finalFriday = friday;
        final int finalThursday = thursday;
        final int finalWednesday = wednesday;
        final int finalTuesday = tuesday;
        StringRequest stringRegisterRequest = new StringRequest(Request.Method.POST, Constants.URL_ADD_SUBJECT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

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
                } else if (error instanceof NoConnectionError) {
                    message = "No internet connection!!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put(Constants.KEY_TEACHERS_ID,teacherId);
                params.put(Constants.KEY_SUBJECT, subject);
                params.put(Constants.KEY_MONDAY, Integer.toString(finalMonday));
                params.put(Constants.KEY_TUESDAY, Integer.toString(finalTuesday));
                params.put(Constants.KEY_WEDNESDAY, Integer.toString(finalWednesday));
                params.put(Constants.KEY_THURSDAY, Integer.toString(finalThursday));
                params.put(Constants.KEY_FRIDAY, Integer.toString(finalFriday));
                params.put(Constants.KEY_SATURDAY, Integer.toString(finalSaturday));
                params.put(Constants.KEY_MONDAY_TIME, Mondaytime);
                params.put(Constants.KEY_TUESDAY_TIME, Tuesdaytime);
                params.put(Constants.KEY_WEDNESDAY_TIME, Wednesdaytime);
                params.put(Constants.KEY_THURSDAY_TIME, Thursdaytime);
                params.put(Constants.KEY_FRIDAY_TIME, Fridaytime);
                params.put(Constants.KEY_SATURDAY_TIME, Saturdaytime);
                return params;
            }


        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRegisterRequest);
    }
}
