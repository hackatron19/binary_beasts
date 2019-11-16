package com.androlord.studentapp.Funtions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.androlord.studentapp.Adapter.AssetsAdapter;
import com.androlord.studentapp.Data.Assets;
import com.androlord.studentapp.R;
import com.androlord.studentapp.Support.GoodsUpload;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Goods extends AppCompatActivity {
    RecyclerView recyclerView;
    AssetsAdapter adapter;
    LottieAnimationView whatsaap,phone;
    FloatingActionButton floatingActionButton;
    ArrayList<Assets> list=new ArrayList<Assets>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        init();
        setData();

    }


    private void setData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Assets");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Assets assets=new Assets("","","");
                    for (DataSnapshot dataSnapshot11:dataSnapshot1.getChildren())
                    {

                        if(dataSnapshot11.getKey().equalsIgnoreCase("Description"))
                            assets.desc=(String) dataSnapshot11.getValue();
                        if(dataSnapshot11.getKey().equalsIgnoreCase("URL"))
                            assets.url=(String) dataSnapshot11.getValue();
                        if(dataSnapshot11.getKey().equalsIgnoreCase("PhoneNo"))
                            assets.phoneno=(String) dataSnapshot11.getValue();
                        if(dataSnapshot11.getKey().equalsIgnoreCase("ID"))
                            assets.id=(String) dataSnapshot11.getValue();



                    }
                    Log.d("ak47", "onDataChange: "+dataSnapshot1.getValue());
                    list.add(assets);
                }
                adapter.notifyDataSetChanged();

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void init() {
        floatingActionButton=findViewById(R.id.addAssets);
        recyclerView=findViewById(R.id.AssetsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new AssetsAdapter(Goods.this,list);
        recyclerView.setAdapter(adapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Goods.this, GoodsUpload.class);
                startActivity(intent);
            }
        });
    }
}
