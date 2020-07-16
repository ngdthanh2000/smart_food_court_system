package com.example.demo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.kernel.geom.Line;
import com.karumi.dexter.Dexter;

import org.w3c.dom.Text;

import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjectAdapter extends RecyclerView.Adapter<ObjectAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewNoDate;
        public TextView textViewMonth;
        public TextView textViewTotalOrder;
        public TextView textViewRevenue;
        public LinearLayout parentLinearLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNoDate = (TextView) itemView.findViewById(R.id.textViewNoDate);
            textViewMonth = (TextView) itemView.findViewById(R.id.textViewMonth);
            textViewRevenue = (TextView) itemView.findViewById(R.id.textViewRevenue);
            textViewTotalOrder = (TextView) itemView.findViewById(R.id.textViewTotalOrder);
            parentLinearLayout = (LinearLayout) itemView.findViewById(R.id.parentLinearLayout);

        }
    }

    private List<DateObject> mObjects;
    public ObjectAdapter(List<DateObject> objects) {
        mObjects = objects;
    }

    @NonNull
    @Override
    public ObjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        // Inflate the custom layout
        View objectView = inflater.inflate(R.layout.recycler_item, parent, false);


        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(objectView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ObjectAdapter.ViewHolder holder, int position) {
        DateObject object = mObjects.get(position);
        List<FoodReport> foodReports = UserInfo.instance.getFoodReports();
        FoodReport foodReport = null;

        for (FoodReport foodReport1 : foodReports) {
            if (foodReport1.getDate().equals(object.getDate())) {
                foodReport = foodReport1;
            }
        }

        final NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);

        // Set item views based on your views and data model
        final TextView textView = holder.textViewNoDate;
        textView.setText(object.getDate().substring(8));
        TextView textView1 = holder.textViewMonth;
        textView1.setText(new DateFormatSymbols().getMonths()[Integer.parseInt(object.getDate().substring(5,7)) - 1]);
        TextView textView2 = holder.textViewRevenue;
        textView2.setText(numberFormat.format(object.getRevenue()));
        TextView textView3 = holder.textViewTotalOrder;
        textView3.setText("Total Orders: " + String.valueOf(object.getNumberOfOrder()));



                LinearLayout linearLayout = holder.parentLinearLayout;
                    for (int j = 0; j < foodReport.getFoods().size(); j++) {

                        LinearLayout linearLayout1 = new LinearLayout(linearLayout.getContext());
                        linearLayout1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

                        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1f);
                        params.setMargins(10, 15, 0, 15);
                        TextView textViewFoodName = new TextView(linearLayout1.getContext());
                        textViewFoodName.setLayoutParams(params);
                        textViewFoodName.setText(foodReport.getFoods().get(j).getName());
                        linearLayout1.addView(textViewFoodName);

                        TableRow.LayoutParams params1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                        params1.setMargins(0, 15, 10, 15);
                        TextView textViewRevenue = new TextView(linearLayout1.getContext());
                        textViewRevenue.setLayoutParams(params1);
                        textViewRevenue.setText(numberFormat.format(Integer.parseInt(foodReport.getFoods().get(j).getPrice()) * foodReport.getFoods().get(j).getQuantity()));
                        linearLayout1.addView(textViewRevenue);

                        linearLayout.addView(linearLayout1);
                    }


        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfo.instance.setDate(mObjects.get(holder.getAdapterPosition()).getDate());
                Intent intent = new Intent(view.getContext(), CreateReport.class);
                view.getContext().startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        if (mObjects != null) return mObjects.size();
        else return 0;
    }
}
