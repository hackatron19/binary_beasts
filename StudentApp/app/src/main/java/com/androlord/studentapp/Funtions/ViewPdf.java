package com.androlord.studentapp.Funtions;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.studentapp.Adapter.PdfAdapter;
import com.androlord.studentapp.Data.PdfModel;
import com.androlord.studentapp.Funtions.UploadPdf;
import com.androlord.studentapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import com.jaredrummler.materialspinner.MaterialSpinner;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dmax.dialog.SpotsDialog;

public class ViewPdf extends AppCompatActivity implements  PdfAdapter.Onitemclicklistner {
    RecyclerView pdflistrecycler;
    private List<PdfModel> pdf_list;
    FirebaseAuth mAuth;
    String userid2;
    ConstraintLayout root;
    String course;

    FirebaseFirestore firebaseFirestore;
    PdfAdapter pdfAdapter;
    FloatingActionButton floatingActionButton;

    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        floatingActionButton=findViewById(R.id.addPdfToSubject);
        pdflistrecycler = findViewById(R.id.pdfrecyclerview);
        final Intent intent=getIntent();
        if(intent.hasExtra("Data"))
        {
            course=intent.getStringExtra("Data");
            Log.d("ak47", "onStart: "+course);
        }
        else
        {
            Log.d("ak47", "onStart: "+"No");
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ViewPdf.this,UploadPdf.class);
                intent.putExtra("Data",course);
                startActivity(intent1);
            }
        });
        pdf_list = new ArrayList<>();
        floatingActionButton=findViewById(R.id.addPdfToSubject);

        layoutManager = new LinearLayoutManager(getApplicationContext());//   RecyclerView.LayoutManager layoutManager;
        pdflistrecycler.setLayoutManager(layoutManager);
        //passing image array and assiging adapter  ,,, adapter is object of recyler_adapter class
        pdfAdapter = new PdfAdapter(pdf_list,this);
        pdflistrecycler.setAdapter(pdfAdapter);
        final SpotsDialog waitingdilogp = new SpotsDialog(ViewPdf.this);
        waitingdilogp.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                waitingdilogp.dismiss();
            }
        }).start();


        firebaseFirestore = FirebaseFirestore.getInstance();


        //     blogRecyclerAdapter.notifyDataSetChanged();

    }


    @Override
    public void onNoteclick(int postion) {
        Toast.makeText(this, ""+pdf_list.get(postion).getImageurl() , Toast.LENGTH_SHORT).show();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf_list.get(postion).getImageurl()));
        startActivity(browserIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();


                    Query firstquery = firebaseFirestore.collection("PDF").orderBy("timestamp", Query.Direction.DESCENDING);
                    firstquery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                            pdf_list.clear();

                            for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    String blogpostid = doc.getDocument().getId();
                                    PdfModel pdfModel = doc.getDocument().toObject(PdfModel.class);
                                    String course1=doc.getDocument().getString("course");
                                    if(course1.equals(course))
                                        pdf_list.add(pdfModel);
                                    pdfAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    });




    }
}

