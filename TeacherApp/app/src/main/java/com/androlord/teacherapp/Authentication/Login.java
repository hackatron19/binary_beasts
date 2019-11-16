package com.androlord.teacherapp.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androlord.teacherapp.MainActivity;
import com.androlord.teacherapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText email,password;
    Button SignIn;
    TextView SignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        init();
    }
    private void init() {
        email=findViewById(R.id.emailID);
        password=findViewById(R.id.Password);
        SignIn=findViewById(R.id.SignIn);
        SignUp=findViewById(R.id.GotoSignUp);

        mAuth = FirebaseAuth.getInstance();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,SignUp.class));
                finish();
            }
        });

        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText().toString().trim())||TextUtils.isEmpty(password.getText().toString().trim()))
                {
                    Toast.makeText(Login.this,"Fill Up",Toast.LENGTH_LONG).show();
                }
                else
                {
                    mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(Login.this, MainActivity.class));
                                finish();

                            } else {
                                String errormessage = task.getException().getMessage();
                                Toast.makeText(Login.this, "ERROR:" + errormessage, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });


    }
}
