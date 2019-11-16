package com.androlord.studentapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.studentapp.Data.AttendaceRecord;
import com.androlord.studentapp.R;

import java.util.ArrayList;

public class AttendanceSheetAdapter extends RecyclerView.Adapter<AttendanceSheetAdapter.MyVIewHolder>{
    @NonNull
    ArrayList<AttendaceRecord> list;
    public AttendanceSheetAdapter(ArrayList<AttendaceRecord> list)
    {
        this.list=list;
    }

    @Override
    public MyVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_attendence_layout,parent,false);
        MyVIewHolder vIewHolder=new MyVIewHolder(v);
        return vIewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyVIewHolder holder, int position) {
        holder.subject_code.setText(list.get(position).coursecode);


        String att = String.valueOf(list.get(position).present);
        String tot = String.valueOf(list.get(position).total);

        holder.attendance.setText(att + "/" + tot);
        int result = status((int)list.get(position).present, (int)list.get(position).total);
        String r = String.valueOf(result);
        if(result>0)
            holder.status.setText("Status : Attend next" + " " + r + " classes to get back on track");
        else
            holder.status.setText("Status : You are on track");
        holder.tv.setText(String.valueOf((list.get(position).present*100)/list.get(position).total)+"%");



    }

    public int status (int attended, int total){

        double minimum_classes = ((3.0/4.0)*(total));
        int to_be_attended = ((int)(minimum_classes)-attended);
        return to_be_attended;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyVIewHolder extends RecyclerView.ViewHolder {
        TextView subject_code, attendance, status;
        TextView tv;
        public MyVIewHolder(@NonNull View itemView) {
            super(itemView);
            subject_code = itemView.findViewById(R.id.subject_code);
            attendance = itemView.findViewById(R.id.attendance);
            status = itemView.findViewById(R.id.status);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
