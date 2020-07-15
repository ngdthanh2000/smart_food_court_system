package com.example.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    //Dữ liệu hiện thị là danh sách sinh viên
    private List<Food> mFood;
    // Lưu Context để dễ dàng truy cập
    private Context mContext;

    public FoodAdapter(List<Food> mFood, Context mContext) {
        this.mFood = mFood;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Nạp layout cho View biểu diễn phần tử sinh viên
        View studentView =
                inflater.inflate(R.layout.food_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(studentView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = mFood.get(position);

        holder.foodname.setText(food.getName());
        holder.foodprice.setText(food.getPrice());


    }

    @Override
    public int getItemCount() {
        return mFood.size();
    }

    /**
     * Lớp nắm giữ cấu trúc view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //private View itemview;
        public TextView foodname;
        public TextView foodprice;
        public Button detailbut;

        public ViewHolder(View itemView) {
            super(itemView);
            //itemview = itemView;
            foodname = itemView.findViewById(R.id.foodName);
            foodprice = itemView.findViewById(R.id.foodPrice);
            detailbut = itemView.findViewById(R.id.detailBut);

            //Xử lý khi nút Chi tiết được bấm
            detailbut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }


}
