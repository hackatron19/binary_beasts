package com.androlord.studentapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.studentapp.Data.AttendaceRecord;
import com.androlord.studentapp.Data.SubjectMarks;
import com.androlord.studentapp.MainActivity;
import com.androlord.studentapp.R;

import java.util.ArrayList;

public class MarksSheetAdapter extends RecyclerView.Adapter<MarksSheetAdapter.MyViewHolder>{
    @NonNull
    ArrayList<SubjectMarks> list;
    public MarksSheetAdapter(ArrayList<SubjectMarks> list){
        this.list=list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_marksheet_layout,parent,false);
        MyViewHolder viewHolder=new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.subjectcode.setText(list.get(position).subjectcode);
        holder.endsem.setText(list.get(position).endsem);
        holder.midsem.setText(list.get(position).midsem);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView midsem,endsem,subjectcode;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            midsem=itemView.findViewById(R.id.marks_obtained_mid_sem);
            endsem=itemView.findViewById(R.id.marks_obtained_end_sem);
            subjectcode=itemView.findViewById(R.id.subject_code_single_result);
        }
    }
}
