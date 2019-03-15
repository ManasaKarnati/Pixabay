package com.example.acer.pixabay;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PixabayAdapter extends RecyclerView.Adapter<PixabayAdapter.MyViewHolder>
{
    Context context;
    ArrayList<ImageModels> images;

    public PixabayAdapter(ImageDetails imageDetails, ArrayList<ImageModels> imageModels)
    {
        this.context=imageDetails;
        this.images=imageModels;
    }


    @NonNull
    @Override
    public PixabayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {

        View v=LayoutInflater.from(context).inflate(R.layout.design,viewGroup,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PixabayAdapter.MyViewHolder myViewHolder, int i)
    {
        ImageModels search=images.get(i);
        Picasso.with(context).load(search.pic).into(myViewHolder.iv);
    }

    @Override
    public int getItemCount() {
        return (images==null) ? 0:images.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView iv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.image);
        }
    }

}
