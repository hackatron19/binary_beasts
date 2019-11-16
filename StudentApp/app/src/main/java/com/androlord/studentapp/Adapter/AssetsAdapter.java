package com.androlord.studentapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.androlord.studentapp.Data.Assets;
import com.androlord.studentapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;

import java.util.ArrayList;

public class AssetsAdapter extends RecyclerView.Adapter<AssetsAdapter.MyViewHolder>{
    ArrayList<Assets> list;
    Context context;
    public AssetsAdapter(Context context,ArrayList<Assets> list)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_assets_layout,parent,false);
        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.textView.setText(list.get(position).desc);
        holder.whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ak47", "onClick: ");
                Toast.makeText(context, "phone:"+list.get(position).phoneno, Toast.LENGTH_SHORT).show();
                Uri uri = Uri.parse("smsto:" + list.get(position).phoneno);
                Intent i = new Intent(Intent.ACTION_SENDTO, uri);
                i.setPackage("com.whatsapp");
             context.startActivity(Intent.createChooser(i, ""));
            }
        });

        Glide.with(context).asBitmap().load(list.get(position).url).transform(new RoundedCorners(4)).diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LottieAnimationView whatsapp, phone;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            whatsapp = itemView.findViewById(R.id.click_to_whatsapp);
            phone = itemView.findViewById(R.id.click_to_phone);
            imageView=itemView.findViewById(R.id.AssetsImageSingle);
            textView=itemView.findViewById(R.id.AssetDescriptionSingle);
        }
    }
}
