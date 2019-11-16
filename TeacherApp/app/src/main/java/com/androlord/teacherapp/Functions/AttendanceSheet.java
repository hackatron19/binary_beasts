package com.androlord.teacherapp.Functions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androlord.teacherapp.Adapters.AttendenceListAdapter;
import com.androlord.teacherapp.Data.StudentAttendence;
import com.androlord.teacherapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class AttendanceSheet extends AppCompatActivity {
    static String subjectcode;
    RecyclerView recyclerView;
    ArrayList<StudentAttendence> list=new ArrayList<StudentAttendence>();
    AttendenceListAdapter adapter;
    public static void markPresent(String studentCode,long prev){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Courses/"+subjectcode+"/Students/"+studentCode+"/Present");

        myRef.setValue(prev+1);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_sheet);
        Intent intent=getIntent();
        if(intent.hasExtra("Data"))
        {
            subjectcode=intent.getStringExtra("Data");
            Toast.makeText(AttendanceSheet.this,subjectcode,Toast.LENGTH_SHORT).show();
            getData(subjectcode);
        }
        init();
    }

    private void getData(String subjectcode) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Courses/"+subjectcode+"/Students");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    String studentregno=dataSnapshot1.getKey();
                    long present=0;
                    for (DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                    {
                        if(dataSnapshot2.getKey().equalsIgnoreCase("Present"))
                        {
                            present=(Long) dataSnapshot2.getValue();
                        }
                    }
                    list.add(new StudentAttendence(studentregno,present));
                    Log.d("ak47", "onDataChange: "+present+" "+studentregno);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void init() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Courses/"+subjectcode+"/Total");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myRef.setValue((long)dataSnapshot.getValue()+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recyclerView=findViewById(R.id.attendenceSheetRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(AttendanceSheet.this));
        adapter=new AttendenceListAdapter(list);
        recyclerView.setAdapter(adapter);
    }
}
