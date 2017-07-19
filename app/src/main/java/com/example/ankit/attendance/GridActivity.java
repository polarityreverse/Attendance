package com.example.ankit.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class GridActivity extends AppCompatActivity {

    GridView grid;
    String[] task = {
            "Attendance", "Edit Attendance", "Notes", "Add New Subject" , "Students"
    } ;
    int[] imageId = {
            R.drawable.ic_todays_attendance,
            R.drawable.ic_view_edit,
            R.drawable.ic_notes,
            R.drawable.ic_add_subject,
            R.drawable.ic_student_profile
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        customGrid adapter = new customGrid(GridActivity.this, task, imageId);
        grid=(GridView)findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               if(task[+ position]=="Attendance"){
                   startActivity(new Intent(GridActivity.this, SubjectsToday.class));
               }if(task[+ position]=="Add New Subject"){
                    startActivity(new Intent(GridActivity.this, AddSubject.class));
               }if(task[+position]=="Students"){
                    startActivity(new Intent(GridActivity.this, StudentsList.class));
                }


            }
        });

    }
}
