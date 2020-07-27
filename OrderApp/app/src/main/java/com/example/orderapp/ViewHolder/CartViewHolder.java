package com.example.orderapp.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.orderapp.Interface.ItemClickListener;
import com.example.orderapp.R;


 public  class CartViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener, View.OnCreateContextMenuListener{

        public TextView txt_cart_name, txt_price;
        public ElegantNumberButton btn_quantity;

        public RelativeLayout view_background;
        public LinearLayout view_foreground;

        private ItemClickListener itemClickListener;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_cart_name = (TextView)itemView.findViewById(R.id.cart_item_name);
            txt_price = (TextView)itemView.findViewById(R.id.cart_item_price);
            btn_quantity = (ElegantNumberButton) itemView.findViewById(R.id.btn_quantity);
            view_background = (RelativeLayout)itemView.findViewById(R.id.view_background);
            view_foreground = (LinearLayout) itemView.findViewById(R.id.view_foreground);
        }

        @Override
        public void onClick(View view) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {

        }
    }


