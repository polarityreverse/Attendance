package com.example.ankit.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ANKIT on 12-07-2017.
 */

public class StudentsAdapter extends ArrayAdapter<students>{

        public StudentsAdapter(Context context, List<students> mStud) {
            super(context, 0, mStud);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Check if there is an existing list item view (called convertView) that we can reuse,
            // otherwise, if convertView is null, then inflate a new list item layout.
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.students_view_list, parent, false);
            }
            students currentStudent = getItem(position);
            String mName = currentStudent.getmStudName();
            String mRoll = currentStudent.getmStudRoll();
            String mEmail = currentStudent.getmStudEmail();
            String mPhone = currentStudent.getmStudPhone();
            TextView txtName = (TextView) listItemView.findViewById(R.id.stud_name);
            TextView txtRoll = (TextView) listItemView.findViewById(R.id.stud_roll);
            TextView txtEmail = (TextView)listItemView.findViewById(R.id.stud_email);
            TextView txtPhone = (TextView)listItemView.findViewById(R.id.stud_phone);
            txtName.setText(mName);
            txtRoll.setText(mRoll);
            txtEmail.setText(mEmail);
            txtPhone.setText(mPhone);
            return listItemView;
        }

    }
