package com.example.ankit.attendance;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
public class LoginFragment extends Fragment implements View.OnClickListener {
    View v;
    TextView btn_go_register;
    Button btn_login;
    EditText login_tid, login_password;
    ProgressDialog mProgressView;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_login, container, false);
        btn_go_register = (TextView) v.findViewById(R.id.btn_goto_register);
        btn_login = (Button) v.findViewById(R.id.button_login);
        btn_go_register.setPaintFlags(btn_go_register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        login_tid = (EditText) v.findViewById(R.id.login_id);
        login_password = (EditText) v.findViewById(R.id.login_password);
        mProgressView = new ProgressDialog(getActivity());
        btn_go_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        switch (v.getId()) {
            case R.id.btn_goto_register:
                fragmentManager.beginTransaction().replace(R.id.content, new RegisterFragment()).commit();
                break;
            case R.id.button_login:
                mProgressView.setMessage("Logging in...");
                mProgressView.show();
                String username = login_tid.getText().toString().trim();
                String password = login_password.getText().toString().trim();
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (username.equals("") || password.equals("")) {
                        mProgressView.dismiss();
                        Toast.makeText(getActivity(), "Please fill in all fields!!", Toast.LENGTH_SHORT).show();
                    } else {
                        loginUser(username, password);
                    }

                } else {
                    mProgressView.dismiss();    
                    Toast.makeText(getActivity(), "Cannot connect to Internet. Please check your connection!!", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void loginUser(final String mUsername, final String mPassword) {

        StringRequest stringLoginRequest = new StringRequest(Request.Method.POST, Constants.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mProgressView.dismiss();

                Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                if(response.contains("Login Successfull!!!")){
                   gotoGrid();
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
                params.put(Constants.KEY_TEACHERS_ID, mUsername);
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

        MySingleton.getInstance(getActivity()).addToRequestQueue(stringLoginRequest);
    }

    private void gotoGrid() {
        startActivity(new Intent(getActivity(), GridActivity.class));
    }

}

