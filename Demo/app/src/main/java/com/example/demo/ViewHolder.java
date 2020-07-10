package com.example.demo;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    public TextView _name, _price;
    public ImageView _image;
    public Button _detail;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        _name = itemView.findViewById(R.id.foodName);
        _price = itemView.findViewById(R.id.foodPrice);
        _image = itemView.findViewById(R.id.foodView);
        _detail = itemView.findViewById(R.id.detailBut);
    }
}
