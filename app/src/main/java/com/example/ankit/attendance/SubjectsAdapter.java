package com.example.ankit.attendance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by ANKIT on 09-07-2017.
 */

public class SubjectsAdapter extends ArrayAdapter<Subject>{


        public SubjectsAdapter(Context context, List<Subject> subjects) {
            super(context, 0, subjects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Check if there is an existing list item view (called convertView) that we can reuse,
            // otherwise, if convertView is null, then inflate a new list item layout.
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.subject_item, parent, false);
            }
            Subject currentSubject = getItem(position);
            String sub = currentSubject.getSubject();
            String time = currentSubject.getTime();
            TextView txtSubject = (TextView) listItemView.findViewById(R.id.subject);
            TextView txtTime = (TextView) listItemView.findViewById(R.id.timings);
            txtSubject.setText(sub);
            txtTime.setText(time);
            LinearLayout back = (LinearLayout)listItemView.findViewById(R.id.subject_item);
            return listItemView;
        }

}
