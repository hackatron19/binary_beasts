package com.androlord.teacherapp.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.teacherapp.Data.StudentAttendence;
import com.androlord.teacherapp.Functions.MarksSheet;
import com.androlord.teacherapp.R;

import java.util.ArrayList;

public class MarksEntryAdapter extends RecyclerView.Adapter<MarksEntryAdapter.MyViewHolder> {
    ArrayList<StudentAttendence> list;
    Context context;
    public MarksEntryAdapter(Context context,ArrayList<StudentAttendence> list){
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_marks_entry,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.studentrgno.setText(list.get(position).regno);
        int mark=-1;
        holder.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(holder.marks.getText().toString().trim()))
                {
                    Toast.makeText(context,"Please enter marks",Toast.LENGTH_SHORT).show();
                }
                else {
                    MarksSheet.entermarks(list.get(position).regno,Integer.valueOf(holder.marks.getText().toString().trim()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentrgno;
        EditText marks;
        Button submit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentrgno=itemView.findViewById(R.id.marksentrystudentid);
            marks=itemView.findViewById(R.id.marksentryValue);
            submit=itemView.findViewById(R.id.submitmarks);
        }
    }
}
