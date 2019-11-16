package com.androlord.studentapp.Support;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.androlord.studentapp.Adapter.SubjectListAdapter;
import com.androlord.studentapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubjectList extends AppCompatActivity {
    RecyclerView recyclerView;
    SubjectListAdapter adapter;
    ArrayList<String> list=new ArrayList<String>();
    int ch;
    public static String studentid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_list);
        Intent intent=getIntent();
        if(intent.hasExtra("ch"))
        {
            ch=Integer.valueOf(intent.getStringExtra("ch"));

        }
        if(intent.hasExtra("Data"))
        {
            studentid=intent.getStringExtra("Data");
            init(studentid);
            setdata(studentid);
        }


    } @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;

    }


    private void setdata(String studentid) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Students/"+studentid+"/Courses");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    list.add(dataSnapshot1.getKey());
                    Log.d("ak47", "onDataChange: "+dataSnapshot1.getKey());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void init(String studentid) {
        recyclerView=findViewById(R.id.subjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(SubjectList.this));
        adapter =new SubjectListAdapter(SubjectList.this,ch,list);
        recyclerView.setAdapter(adapter);
    }
}
