package com.example.orderapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orderapp.Interface.ItemClickListener;
import com.example.orderapp.Model.Request;
import com.example.orderapp.R;

import org.w3c.dom.Text;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener{

    public TextView txtOrderId, txtOrderTime, txtOrderTotal;

    private ItemClickListener itemClickListener;


    public OrderViewHolder(View itemView) {

        super(itemView);

        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderTime = (TextView)itemView.findViewById(R.id.order_time);
        txtOrderTotal = (TextView)itemView.findViewById(R.id.order_total);


        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {


        itemClickListener.onClick(view, getAdapterPosition(),false);


    }

    @Override
    public boolean onLongClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(),true);
        return true;
    }
}
