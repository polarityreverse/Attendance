package com.example.ankit.attendance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class AddStudent extends AppCompatActivity {

    EditText name;
    EditText roll;
    EditText email;
    EditText phone;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        name = (EditText)findViewById(R.id.StudName);
        roll =(EditText)findViewById(R.id.StudRoll);
        email = (EditText)findViewById(R.id.StudEmail);
        phone = (EditText)findViewById(R.id.StudPhone);

        add =(Button)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertStudent();

                finish();
            }
        });

    }

    private void insertStudent() {

        StringRequest stringStudAddRequest = new StringRequest(Request.Method.POST, Constants.ADD_STUDENT, new Response.Listener<String>() {
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
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }) {



            @Override
            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(Constants.KEY_STUD_NAME, name.getText().toString().trim() );
                params.put(Constants.KEY_STUD_ROLL, roll.getText().toString().trim());
                params.put(Constants.KEY_STUD_PHONE, phone.getText().toString().trim());
                params.put(Constants.KEY_STUD_EMAIL, email.getText().toString().trim());
                return params;
            }

        };


        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringStudAddRequest);
    }
}

