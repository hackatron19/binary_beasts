package com.androlord.studentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.androlord.studentapp.Authentication.LoginActivity;
import com.androlord.studentapp.Funtions.ChatActivity;
import com.androlord.studentapp.Funtions.ComplainBox;
import com.androlord.studentapp.Funtions.Goods;
import com.androlord.studentapp.Funtions.UploadPdf;
import com.androlord.studentapp.Funtions.ViewAttendence;
import com.androlord.studentapp.Funtions.ViewMarks;
import com.androlord.studentapp.Support.GoodsUpload;
import com.androlord.studentapp.Support.SubjectList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String studentcode="";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.Logout:
                signOut();
                break;

            case R.id.schedule:
            schedule();
            break;
            case R.id.ComplainBox:
                Intent intent=new Intent(MainActivity.this, ComplainBox.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void schedule() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final AlertDialog dialog = builder.create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.schedule_students, null);
        dialog.setView(dialogLayout);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.show();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface d) {
                ImageView image = (ImageView) dialog.findViewById(R.id.goProDialogImage);
                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.ic_launcher_background);
                float imageWidthInPX = (float)image.getWidth();

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
                        Math.round(imageWidthInPX * (float)icon.getHeight() / (float)icon.getWidth()));
                image.setLayoutParams(layoutParams);


            }
        });
    }

    private void signOut() {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        check();

    }

    private void check() {
        mAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else
        {
            init();
            tempcode();
        }

    }

    private void tempcode() {

        LinearLayout b=findViewById(R.id.view_attendence),b2=findViewById(R.id.view_marks);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentcode==null||studentcode.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this,"Could not Load Your Student Data",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this, ViewAttendence.class);
                    intent.putExtra("studentcode",studentcode);
                    startActivity(intent);
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentcode==null||studentcode.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this,"Could not Load Your Student Data",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this, ViewMarks.class);
                    intent.putExtra("studentcode",studentcode);
                    startActivity(intent);
                }
            }
        });
        final LinearLayout studymaterial=findViewById(R.id.studymaterial);
        studymaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(studentcode==null||studentcode.equalsIgnoreCase(""))
                {
                    Toast.makeText(MainActivity.this,"Could not Load Your Student Data",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Intent intent=new Intent(MainActivity.this, SubjectList.class);
                    intent.putExtra("Data",studentcode);
                    intent.putExtra("ch","1");
                    startActivity(intent);
                }

            }
        });
        LinearLayout asset=findViewById(R.id.Assets);
        asset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Goods.class));
            }
        });

        LinearLayout doubtsession = findViewById(R.id.doubt_session);
        doubtsession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SubjectList.class);
                intent.putExtra("Data",studentcode);
                intent.putExtra("ch","2");
                startActivity(intent);

            }
        });
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        String email="";
        email = mAuth.getCurrentUser().getEmail().toString();
        Log.d("ak47", "init: "+"StudentList/"+email.substring(0,email.indexOf('@')));
        DatabaseReference myRef = database.getReference("StudentList/"+email.substring(0,email.indexOf('@')));

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getKey()==null)
                {
                    Toast.makeText(getApplicationContext(),"You Are not Registered",Toast.LENGTH_LONG).show();
                    signOut();
                }
                studentcode=(String) dataSnapshot.getValue();

                startExtraction((String) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void startExtraction(String key) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("Students/"+key);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.e("ak47",dataSnapshot.getKey()+"-->> "+dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
