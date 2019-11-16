package com.androlord.studentapp.Support;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.androlord.studentapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class GoodsUpload extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    EditText descrition, mobile;
    Button Submit;
    int flag;
    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    private DatabaseReference myRef, databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flag = 0;
        setContentView(R.layout.activity_goods_upload);
        imageView = findViewById(R.id.ImageOfAssets);
        descrition = findViewById(R.id.DescriptionOfAsset);
        mobile = findViewById(R.id.MobileNo);
        Submit = findViewById(R.id.SubmitAssets);
        imageView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                capture();
            }
        });
        
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(descrition.getText().toString().trim())) {
                    Toast.makeText(GoodsUpload.this, "Enter Descrription", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(mobile.getText().toString().trim())||mobile.getText().toString().trim().length()!=10) {
                    Toast.makeText(GoodsUpload.this, "Enter Mobile No", Toast.LENGTH_SHORT).show();
                } else if (flag != 10) {
                    Toast.makeText(GoodsUpload.this, "Image not set", Toast.LENGTH_SHORT).show();
                }
                else {
                    upload();
                    
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void capture() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
        }
        else
        {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
        {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            flag=10;
        }
    }

    private void upload() {
        mStorageRef= FirebaseStorage.getInstance().getReference("Assets");
        database = FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Assets");

        Bitmap mainImage=((BitmapDrawable)imageView.getDrawable()).getBitmap();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        mainImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        final String key=String.valueOf(System.currentTimeMillis());

        final UploadTask mainImageUpload=mStorageRef.child(key).putBytes(data);
        mainImageUpload.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(GoodsUpload.this,"Upload Success",Toast.LENGTH_SHORT).show();
                final String[] URI = {""};
                mStorageRef.child(key).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Map<String,String> data=new HashMap<String,String>();
                       URI[0] =String.valueOf(uri);
                       data.put("URL",URI[0]);
                       data.put("Description",descrition.getText().toString().trim());
                       data.put("PhoneNo",descrition.getText().toString().trim());
                       databaseReference.push().setValue(data);
                    }
                });
            }
        });
    }



}
