package com.androlord.studentapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androlord.studentapp.Data.FriendlyMessage;
import com.androlord.studentapp.Funtions.ChatActivity;
import com.androlord.studentapp.R;
import com.androlord.studentapp.Support.PreferencesUtility;

import java.util.List;

import static android.view.View.GONE;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private List<FriendlyMessage> mData;
    private Context context;

    public MessageAdapter(List<FriendlyMessage> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view= mInflater.inflate(R.layout.item_message,parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

     if(!mData.get(position).getName().equalsIgnoreCase(ChatActivity.sender)){


             holder.right_bubble.setVisibility(GONE);
             holder.left_bubble.setVisibility(View.VISIBLE);

             holder.message_recieve.setText(mData.get(position).getText());
             holder.name_recieve.setText(mData.get(position).getName());


         }
     else
     {
         holder.right_bubble.setVisibility(View.VISIBLE);
         holder.left_bubble.setVisibility(GONE);

         holder.message_send.setText(mData.get(position).getText());
         holder.name_send.setText(mData.get(position).getName());
     }




    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name_send, message_send, name_recieve, message_recieve;

        RelativeLayout left_bubble;
             LinearLayout right_bubble;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_send = itemView.findViewById(R.id.nameTextView_send);
            message_send = itemView.findViewById(R.id.messageTextView_send);

            name_recieve = itemView.findViewById(R.id.nameTextView_recieve);
            message_recieve = itemView.findViewById(R.id.messageTextView_recieve);

            left_bubble = itemView.findViewById(R.id.left_message);
            right_bubble = itemView.findViewById(R.id.right_message);
        }
    }
}




