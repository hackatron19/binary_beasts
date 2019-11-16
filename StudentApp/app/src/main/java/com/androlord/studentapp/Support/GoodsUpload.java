package com.androlord.studentapp.Support;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androlord.studentapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

public class GoodsUpload extends AppCompatActivity {
    ImageView imageView;
    EditText descrition,mobile;
    Button Submit;

    private StorageReference mStorageRef;
    private FirebaseDatabase database ;
    private DatabaseReference myRef,databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_upload);
        imageView=findViewById(R.id.ImageOfAssets);
        descrition=findViewById(R.id.DescriptionOfAsset);
        mobile=findViewById(R.id.MobileNo);
        Submit=findViewById(R.id.SubmitAssets);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(descrition.getText().toString().trim()))
                {
                    Toast.makeText(GoodsUpload.this,"Enter Descrription",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(mobile.getText().toString().trim())){

                }
            }
        });

    }
}
