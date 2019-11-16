package com.androlord.teacherapp.Support;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.androlord.teacherapp.Adapters.CourseListAdapter;
import com.androlord.teacherapp.Data.Course;
import com.androlord.teacherapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MarksCourseList extends AppCompatActivity {
    CourseListAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Course> list=new ArrayList<Course>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_course_list);
        init();
        Intent intent=getIntent();
        if(intent.hasExtra("Code"))
        {
            setData(intent.getStringExtra("Code"));
        }
    }
    private void setData(String code) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Teachers/"+code);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("ak47", "onDataChange: "+dataSnapshot.getKey()+" "+dataSnapshot.getValue());
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    list.add(new Course(dataSnapshot1.getKey()));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void init() {

        recyclerView=findViewById(R.id.course_List_marks_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(MarksCourseList.this));
        adapter=new CourseListAdapter(MarksCourseList.this,list,2);
        recyclerView.setAdapter(adapter);
    }
}
