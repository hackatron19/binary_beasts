package com.androlord.teacherapp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.teacherapp.Data.StudentAttendence;
import com.androlord.teacherapp.Functions.AttendanceSheet;
import com.androlord.teacherapp.R;

import java.util.ArrayList;

public class AttendenceListAdapter extends RecyclerView.Adapter<AttendenceListAdapter.MyViewHolderAttendence>{
    ArrayList<StudentAttendence> list;
    public AttendenceListAdapter(ArrayList<StudentAttendence> list){
        this.list=list;
    }
    @NonNull
    @Override
    public MyViewHolderAttendence onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_student_attendence_marker,parent,false);
        MyViewHolderAttendence myViewHolderAttendence=new MyViewHolderAttendence(view);
        return myViewHolderAttendence;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolderAttendence holder, final int position) {
        holder.regno.setText(list.get(position).regno);
        holder.Present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttendanceSheet.markPresent(list.get(position).regno,list.get(position).present);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolderAttendence extends RecyclerView.ViewHolder {
        TextView regno;
        Button Present,Absent;
        public MyViewHolderAttendence(@NonNull View itemView) {
            super(itemView);
            regno=itemView.findViewById(R.id.regnosinglestudentattendencemarker);
            Present=itemView.findViewById(R.id.StudentPresent);
            Absent=itemView.findViewById(R.id.StudentAbsent);
        }
    }
}
