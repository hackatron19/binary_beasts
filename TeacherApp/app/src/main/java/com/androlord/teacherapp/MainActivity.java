package com.androlord.teacherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androlord.teacherapp.Authentication.Login;
import com.androlord.teacherapp.Support.AttendenceCourseList;
import com.androlord.teacherapp.Support.MarksCourseList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String teacherCode="";
    Button takeAttendence,entermarks;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.Logout:
                signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOut() {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this,Login.class));
        finish();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu,menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null) {
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }else {
            init();
        }
    }

    private void init() {
        entermarks=findViewById(R.id.takemarksmainActivity);
        takeAttendence=findViewById(R.id.takeattendencemainActivity);
        mAuth = FirebaseAuth.getInstance();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String email=mAuth.getCurrentUser().getEmail().toString();
        DatabaseReference myRef = database.getReference("TeacherList/"+email.substring(0,email.indexOf('@')));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teacherCode=(String)dataSnapshot.getValue();
                getData(teacherCode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        takeAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teacherCode.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this,"Try in a Bit",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, AttendenceCourseList.class);
                    intent.putExtra("Code",teacherCode);
                    startActivity(intent);
                }

            }
        });
        entermarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(teacherCode.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this,"Try in a Bit",Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, MarksCourseList.class);
                    intent.putExtra("Code",teacherCode);
                    startActivity(intent);
                }

            }
        });
    }

    private void getData(String teacherCode) {
        Toast.makeText(MainActivity.this,teacherCode,Toast.LENGTH_LONG).show();
        Log.d("ak47", "getData: "+teacherCode);
    }
}
