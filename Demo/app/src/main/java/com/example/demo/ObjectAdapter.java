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

public class ObjectAdapter extends RecyclerView.Adapter<ObjectAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDate;
        public TextView textViewNumberOfOrder;
        public TextView textViewRevenue;
        public Button btnReport;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDate = (TextView) itemView.findViewById(R.id.textViewDate);
            textViewNumberOfOrder = (TextView) itemView.findViewById(R.id.textViewNumberOfOrder);
            textViewRevenue = (TextView) itemView.findViewById(R.id.textViewRevenue);
            btnReport = (Button) itemView.findViewById(R.id.btnReport);
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
    public void onBindViewHolder(@NonNull ObjectAdapter.ViewHolder holder, int position) {
        DateObject object = mObjects.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.textViewDate;
        textView.setText(object.getDate());
        TextView textView1 = holder.textViewNumberOfOrder;
        textView1.setText(String.valueOf(object.getNumberOfOrder()));
        TextView textView2 = holder.textViewRevenue;
        textView2.setText(String.valueOf(object.getRevenue()));
        Button button = holder.btnReport;
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }
}
