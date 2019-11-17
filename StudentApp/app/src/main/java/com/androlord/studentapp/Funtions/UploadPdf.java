package com.androlord.studentapp.Funtions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.androlord.studentapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.jaredrummler.materialspinner.MaterialSpinner;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class UploadPdf extends AppCompatActivity {
    CircleImageView uploadpdf;
    int PICK_PDF_CODE=1;
    FirebaseFirestore firebaseFirestore;
    String downloadurl;
    StorageReference storagerefrence;
    EditText docdesc;
    MaterialSpinner dropdownview;
    ProgressBar uploadbar;
    String course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);
        storagerefrence= FirebaseStorage.getInstance().getReference();
        firebaseFirestore=FirebaseFirestore.getInstance();
        dropdownview=findViewById(R.id.dropdownview2);

        Intent intent=getIntent();
        course=intent.getStringExtra("Data");
        docdesc=findViewById(R.id.docdesc);
        uploadbar=findViewById(R.id.uploaddilog);

        CircleImageView viewpdf;

        MaterialSpinner spinner = (MaterialSpinner)findViewById(R.id.dropdownview2);







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null ) {
                //uploading the file
                uploadbar.setVisibility(View.VISIBLE);
                final String  randomName= UUID.randomUUID().toString();
                // Toast.makeText(this, "File Chosen", Toast.LENGTH_SHORT).show();s
                UploadTask filepath=storagerefrence.child("PDF").child(randomName+".jpg").putFile(data.getData());
                filepath.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful()){

                            task.getResult().getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    downloadurl=uri.toString();

                                    Map<String,Object> postmap=new HashMap<>();
                                    postmap.put("imageurl",downloadurl);
                                    postmap.put("desc",docdesc.getText().toString());
                                    postmap.put("course",course);
                                    postmap.put("author","::Student");
                                    postmap.put("timestamp", FieldValue.serverTimestamp());
                                    //  postmap.put("timestamp", FieldValue.serverTimestamp());
                                    firebaseFirestore.collection("PDF").add(postmap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {

                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(UploadPdf.this, " Firestore Uploaded",Toast.LENGTH_LONG).show();
                                                uploadbar.setVisibility(View.INVISIBLE);
                                                // Intent i=new Intent(NewpostActivity.this,MainActivity.class);
                                                // startActivity(i);
                                                // finish();

                                            }
                                            else
                                            { String error=task.getException().getMessage();
                                                Toast.makeText(UploadPdf.this, " Firestore Uploaded",Toast.LENGTH_LONG).show();
                                            }
                                            //  progressBar2.setVisibility(View.INVISIBLE);
                                        }
                                    });

                                }
                            });



                        }
                        else
                        {
                            //  progressBar2.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }else{
                Toast.makeText(this, "No file chosen Or No Description", Toast.LENGTH_SHORT).show();
            }
        }
    }
//   @Override
//   // public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_uploadpdf:
                if( TextUtils.isEmpty(docdesc.getText().toString()) )
                {
                    Toast.makeText(UploadPdf.this, "Please! Write a Description ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(UploadPdf.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.parse("package:" + getPackageName()));
                        startActivity(intent);
                    }

                    //creating an intent for file chooser
                    Intent intent = new Intent();
                    intent.setType("application/pdf");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Document"), PICK_PDF_CODE);


                }


                return true;
            case  R.id.action_viewpdf:
                Intent intent=new Intent(UploadPdf.this,ViewPdf.class);
                startActivity(intent);
                return  true;

            default:
                return false;


        }
    }

}
