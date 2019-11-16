package com.androlord.teacherapp.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.teacherapp.Data.Course;
import com.androlord.teacherapp.Functions.AttendanceSheet;
import com.androlord.teacherapp.Functions.MarksSheet;
import com.androlord.teacherapp.R;
import com.androlord.teacherapp.Support.AttendenceCourseList;
import com.androlord.teacherapp.Support.MarksCourseList;

import java.util.ArrayList;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyViewHolder> {
    public ArrayList<Course> list;
    Context context;
    int target;

    public CourseListAdapter(Context context,ArrayList<Course> list,int target)
    {
        this.list=list;
        this.context=context;
        this.target=target;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_corse_details,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.textView.setText(list.get(position).name);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(target==1) {
                    Intent intent = new Intent(context, AttendanceSheet.class);
                    intent.putExtra("Data",list.get(position).name);
                    context.startActivity(intent);
                }
                else if(target==2)
                {
                    Intent intent = new Intent(context, MarksSheet.class);
                    intent.putExtra("Data",list.get(position).name);
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
            linearLayout=itemView.findViewById(R.id.Single_course_item);
            textView=itemView.findViewById(R.id.courseCode);
        }
    }

}
