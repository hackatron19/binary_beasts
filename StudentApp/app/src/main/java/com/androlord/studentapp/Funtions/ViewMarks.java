package com.androlord.studentapp.Funtions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.androlord.studentapp.Adapter.MarksSheetAdapter;
import com.androlord.studentapp.Data.SubjectMarks;
import com.androlord.studentapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewMarks extends AppCompatActivity {
    RecyclerView recyclerView;
    MarksSheetAdapter adapter;
    ArrayList<SubjectMarks> list=new ArrayList<SubjectMarks>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_marks);
        Intent intent=getIntent();
        if(intent.hasExtra("studentcode"))
        {
            String studentcode=intent.getStringExtra("studentcode");
            init(studentcode);
        }
    }

    private void init(String studentcode) {
        recyclerView=findViewById(R.id.marksheet);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewMarks.this));
        adapter =new MarksSheetAdapter(list);
        recyclerView.setAdapter(adapter);
        setdata(studentcode);
    }
    private void setdata(final String student) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Students/"+student+"/Courses");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    getMarks(dataSnapshot1.getKey(),student);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getMarks(final String key, final String student) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("Courses/"+key+"/Students/"+student+"/Exams");

        myRef1.addValueEventListener(new ValueEventListener() {

            SubjectMarks subjectMarks=new SubjectMarks(key,"NA","NA");
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Log.d("ak47", "onDataChange: "+dataSnapshot1);
                    if(dataSnapshot1.getKey().equalsIgnoreCase("MidSem"))
                        subjectMarks.midsem=String.valueOf( (long)dataSnapshot1.getValue());
                    if(dataSnapshot1.getKey().equalsIgnoreCase("EndSem"))
                        subjectMarks.endsem=String.valueOf((long) dataSnapshot1.getValue());

                }
                int f=0;
                for(int i=0;i<list.size();i++)
                {
                    if(list.get(i).subjectcode.equalsIgnoreCase(key))
                    {
                        f=1;
                        list.get(i).endsem=subjectMarks.endsem;
                        list.get(i).midsem=subjectMarks.midsem;
                    }
                }
                if(f==0)
                list.add(subjectMarks);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
