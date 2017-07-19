package com.example.ankit.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ANKIT on 13-07-2017.
 */

public class AttendanceAdapter extends ArrayAdapter<attend> {

    Context mContext;
    public static String subj;


    public AttendanceAdapter(Context context, ArrayList<attend> mAttend) {
        super(context,0,mAttend);
        mContext = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.attendance_item, parent, false);
        }
        attend currentAttend = getItem(position);
        final String mName = currentAttend.getStudName();
        final String mRoll = currentAttend.getRollNo();
        TextView txtName = (TextView) listItemView.findViewById(R.id.student_name);
        TextView txtRoll = (TextView) listItemView.findViewById(R.id.student_roll_no);
        txtName.setText(mName);
        txtRoll.setText(mRoll);


        RadioButton pres = (RadioButton)listItemView.findViewById(R.id.present);
        pres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringPresentSaveRequest = new StringRequest(Request.Method.POST, Constants.SAVE_ATTENDANCE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        TakeAttendance.resp = response;
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
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                }) {



                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put(Constants.KEY_STUD_NAME, mName);
                        params.put(Constants.KEY_STUD_ROLL, mRoll);
                        params.put(Constants.KEY_SUBJECT, subj);
                        params.put(Constants.KEY_PRESENT, Integer.toString(1));
                        params.put(Constants.KEY_ABSENT, Integer.toString(0));
                        params.put(Constants.KEY_LEAVE, Integer.toString(0));
                        return params;
                    }

                };


                MySingleton.getInstance(mContext).addToRequestQueue(stringPresentSaveRequest);
            }
        });


        RadioButton absent = (RadioButton)listItemView.findViewById(R.id.absent);
        absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringAbsentSaveRequest = new StringRequest(Request.Method.POST, Constants.SAVE_ATTENDANCE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    TakeAttendance.resp = response;
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
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                }) {



                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put(Constants.KEY_STUD_NAME, mName);
                        params.put(Constants.KEY_STUD_ROLL, mRoll);
                        params.put(Constants.KEY_SUBJECT, subj);
                        params.put(Constants.KEY_PRESENT, Integer.toString(0));
                        params.put(Constants.KEY_ABSENT, Integer.toString(1));
                        params.put(Constants.KEY_LEAVE, Integer.toString(0));
                        return params;
                    }

                };


                MySingleton.getInstance(mContext).addToRequestQueue(stringAbsentSaveRequest);

            }
        });


        RadioButton leave = (RadioButton)listItemView.findViewById(R.id.leave);
        leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringLeaveSaveRequest = new StringRequest(Request.Method.POST, Constants.SAVE_ATTENDANCE, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        TakeAttendance.resp = response;
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
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                }) {



                    @Override
                    public Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put(Constants.KEY_STUD_NAME, mName);
                        params.put(Constants.KEY_STUD_ROLL, mRoll);
                        params.put(Constants.KEY_SUBJECT, subj);
                        params.put(Constants.KEY_PRESENT, Integer.toString(0));
                        params.put(Constants.KEY_ABSENT, Integer.toString(0));
                        params.put(Constants.KEY_LEAVE, Integer.toString(1));
                        return params;
                    }

                };


                MySingleton.getInstance(mContext).addToRequestQueue(stringLeaveSaveRequest);

            }
        });


        return listItemView;
    }

}

