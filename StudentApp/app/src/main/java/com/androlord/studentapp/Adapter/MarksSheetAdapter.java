package com.androlord.studentapp.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        holder.subjectcode.setText(list.get(position).subjectcode);
        holder.endsem.setText(list.get(position).endsem);
        holder.midsem.setText(list.get(position).midsem);


        holder.subject_code_arrow_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.subject_code_arrow_down.setVisibility(View.GONE);
                holder.subject_code_arrow_up.setVisibility(View.VISIBLE);
                holder.mid_sem_text.setVisibility(View.VISIBLE);
                holder.end_sem_text.setVisibility(View.VISIBLE);
                holder.mid_sem.setVisibility(View.GONE);
                holder.end_sem.setVisibility(View.GONE);
                holder.arrow_end_sem_down.setVisibility(View.VISIBLE);

                holder.arrow_mid_sem_down.setVisibility(View.VISIBLE);
            }
        });

        holder.subject_code_arrow_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.subject_code_arrow_down.setVisibility(View.VISIBLE);
                holder.subject_code_arrow_up.setVisibility(View.GONE);
                holder.mid_sem_text.setVisibility(View.GONE);
                holder.end_sem_text.setVisibility(View.GONE);
                holder.mid_sem.setVisibility(View.GONE);
                holder.end_sem.setVisibility(View.GONE);

                holder.arrow_mid_sem_up.setVisibility(View.GONE);
                holder.arrow_mid_sem_down.setVisibility(View.GONE);
                holder.arrow_end_sem_down.setVisibility(View.GONE);
                holder.arrow_end_sem_up.setVisibility(View.GONE);
            }
        });

        holder.arrow_mid_sem_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.subject_code_arrow_down.setVisibility(View.GONE);
                holder.subject_code_arrow_up.setVisibility(View.VISIBLE);
                holder.mid_sem.setVisibility(View.VISIBLE);
                holder.arrow_mid_sem_down.setVisibility(View.GONE);
                holder.arrow_mid_sem_up.setVisibility(View.VISIBLE);
            }
        });

        holder.arrow_mid_sem_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.arrow_mid_sem_up.setVisibility(View.GONE);
                holder.arrow_mid_sem_down.setVisibility(View.VISIBLE);
                holder.mid_sem.setVisibility(View.GONE);
            }
        });

        holder.arrow_end_sem_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.arrow_end_sem_down.setVisibility(View.GONE);
                holder.arrow_end_sem_up.setVisibility(View.VISIBLE);
                holder.end_sem.setVisibility(View.VISIBLE);


            }
        });

        holder.arrow_end_sem_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.arrow_end_sem_up.setVisibility(View.GONE);
                holder.arrow_end_sem_down.setVisibility(View.VISIBLE);
                holder.end_sem.setVisibility(View.GONE);

            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView midsem,endsem,subjectcode;
         TextView marks_obtained_mid_sem, marks_otained_end_sem, subject_code;

        ImageView arrow_mid_sem_down,arrow_mid_sem_up,arrow_end_sem_down,arrow_end_sem_up, subject_code_arrow_up, subject_code_arrow_down;

         RelativeLayout mid_sem_text, end_sem_text, mid_sem, end_sem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            midsem=itemView.findViewById(R.id.marks_obtained_mid_sem);
            endsem=itemView.findViewById(R.id.marks_obtained_end_sem);
            subjectcode=itemView.findViewById(R.id.subject_code);
            marks_obtained_mid_sem = itemView.findViewById(R.id.marks_obtained_mid_sem);

            marks_otained_end_sem = itemView.findViewById(R.id.marks_obtained_end_sem);

            mid_sem_text = itemView.findViewById(R.id.layout_7);

            mid_sem = itemView.findViewById(R.id.mid_sem);

            end_sem = itemView.findViewById(R.id.end_sem);

            arrow_end_sem_up = itemView.findViewById(R.id.arrow_end_sem_up);
            arrow_end_sem_down = itemView.findViewById(R.id.arrow_end_sem_down);

            arrow_mid_sem_down = itemView.findViewById(R.id.arrow_mid_sem_down);

            arrow_mid_sem_up = itemView.findViewById(R.id.arrow_mid_sem_up);


            subject_code_arrow_up = itemView.findViewById(R.id.subject_code_arrow_up);

            subject_code_arrow_down = itemView.findViewById(R.id.subject_code_arrow_down);

            end_sem_text = itemView.findViewById(R.id.layout3);
        }
    }
}
