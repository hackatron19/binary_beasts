package com.androlord.teacherapp.Support;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.androlord.teacherapp.R;

public class UploadAssets extends AppCompatActivity {
    ImageView imageView;
    EditText description;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_assets);
        
    }
}
