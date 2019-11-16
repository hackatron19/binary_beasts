package com.androlord.studentapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.studentapp.Funtions.ChatActivity;
import com.androlord.studentapp.Funtions.ViewPdf;
import com.androlord.studentapp.R;
import com.androlord.studentapp.Support.SubjectList;

import java.util.ArrayList;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.MyViewHolder>{
    ArrayList<String> list=new ArrayList<String>();
    int ch;
    Context context;
    public SubjectListAdapter(Context context,int ch,ArrayList<String> list)
    {
        this.list=list;
        this.context=context;
        this.ch=ch;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.single_subject_item,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.textView.setText(list.get(position));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ak47", "onClick: "+ch);
                if(ch==1)
                {
                    Intent intent=new Intent(context, ViewPdf.class);
                    intent.putExtra("Data",list.get(position));
                    context.startActivity(intent);
                }
                else if(ch==2){
                    Intent intent=new Intent(context, ChatActivity.class);
                    intent.putExtra("Data",list.get(position));
                    intent.putExtra("User", SubjectList.studentid);
                    context.startActivity(intent);

                }



            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.singleSubjectItem);
            textView=itemView.findViewById(R.id.singleSubjectCode);
        }
    }
}
