package com.androlord.teacherapp.Functions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.androlord.teacherapp.Adapters.AttendenceListAdapter;
import com.androlord.teacherapp.Adapters.MarksEntryAdapter;
import com.androlord.teacherapp.Data.StudentAttendence;
import com.androlord.teacherapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;

public class MarksSheet extends AppCompatActivity {
    static String subjectcode;
    RecyclerView recyclerView;
    ArrayList<StudentAttendence> list=new ArrayList<StudentAttendence>();
    MarksEntryAdapter adapter;
    static Context context;

    static String exam="";
    public static void entermarks(String studentcode,int marks)

    {
        if (exam==null||exam.equalsIgnoreCase(""))
        {
            Toast.makeText(context, "Choose A Exam", Toast.LENGTH_SHORT).show();
        }
        else {
            Log.d("ak47", studentcode + " " + marks);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Courses/" + subjectcode + "/Students/" + studentcode + "/Exams/" + exam);

            myRef.setValue(marks);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_sheet);
        context=getApplicationContext();

        MaterialSpinner spinner2 = (MaterialSpinner)findViewById(R.id.dropdownview2);
        spinner2.setItems("EndSem", "MidSem");
        spinner2.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                if(item.equals("EndSem"))
                {

                   exam="EndSem";
                }
                else   if(item.equals("MidSem"))
                {
                   exam="MidSem";

                }

            }
        });
        Intent intent=getIntent();
        Log.d("ak47", "onCreate: ");
        if(intent.hasExtra("Data"))
        {

            subjectcode=intent.getStringExtra("Data");
            Toast.makeText(MarksSheet.this,subjectcode,Toast.LENGTH_SHORT).show();
            getData(subjectcode);
        }
        init();
    }

    private void init() {
        recyclerView=findViewById(R.id.marksSheetRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MarksSheet.this));
        adapter=new MarksEntryAdapter(MarksSheet.this,list);
        recyclerView.setAdapter(adapter);
    }

    private void getData(String subjectcode) {
        Log.d("ak47", "getData: ");
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
}
