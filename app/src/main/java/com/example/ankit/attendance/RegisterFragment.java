package com.example.ankit.attendance;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener{

    View v;
    TextView btn_go_login;
    Button btn_register;
    EditText register_id,register_username, register_email, register_password, register_password_confirm;
    ProgressDialog mProgressView;
    //final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
    public RegisterFragment(){
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_register, container, false);
        btn_go_login =(TextView)v.findViewById(R.id.btn_goto_login);
        btn_register= (Button)v.findViewById(R.id.button_register);
        btn_go_login.setPaintFlags(btn_go_login.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        register_id = (EditText)v.findViewById(R.id.register_id);
        register_username = (EditText)v.findViewById(R.id.register_username);
        register_email = (EditText)v.findViewById(R.id.register_email);
        register_password =(EditText)v.findViewById(R.id.register_password);
        register_password_confirm = (EditText)v.findViewById(R.id.register_confirm_password);
        mProgressView = new ProgressDialog(getActivity());
        btn_go_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        switch (v.getId()) {
            case R.id.btn_goto_login:
                fragmentManager.beginTransaction().replace(R.id.content, new LoginFragment()).commit();
                break;
            case R.id.button_register:
                mProgressView.setMessage("Registering user...");
                mProgressView.show();
                String teacher_id = register_id.getText().toString().trim();
                String username = register_username.getText().toString().trim();
                String email = register_email.getText().toString().trim();
                String password = register_password.getText().toString().trim();
                String confirm_password = register_password_confirm.getText().toString().trim();
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (teacher_id.equals("") || username.equals("") || email.equals("") || password.equals("") || confirm_password.equals("")) {
                        mProgressView.dismiss();
                        Toast.makeText(getActivity(), "Please fill in all fields!!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (!password.equals(confirm_password)) {
                            mProgressView.dismiss();
                            Toast.makeText(getActivity(), "Passwords do not match!!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                mProgressView.dismiss();
                                Toast.makeText(getActivity(), "Please enter a valid E-mail address!!", Toast.LENGTH_SHORT).show();
                            } else {
                                registerUser(teacher_id ,username, email, password);
                            }
                        }
                    }

                } else {
                    mProgressView.dismiss();
                    Toast.makeText(getActivity(),"Cannot connect to Internet. Please check your connection!!" , Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void registerUser(final String teacher_id, final String mUsername, final String mEmail, final String mPassword) {

        StringRequest stringRegisterRequest = new StringRequest(Request.Method.POST, Constants.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressView.dismiss();

                if(response.contains("It seems you are already registered!!!")|| response.contains("Please check your email!!!")){
                    switchFragment();
                }
                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();

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
                } else if (error instanceof NoConnectionError) {
                    message = "No internet connection!!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put(Constants.KEY_TEACHERS_ID, teacher_id);
                params.put(Constants.KEY_TEACHERS_NAME, mUsername);
                params.put(Constants.KEY_EMAIL, mEmail);
                params.put(Constants.KEY_PASSWORD, mPassword);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("User-Agent","Attendance");
                return header;
            }
        };

        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRegisterRequest);
    }

    public void switchFragment(){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content, new LoginFragment()).commit();
    }
}
