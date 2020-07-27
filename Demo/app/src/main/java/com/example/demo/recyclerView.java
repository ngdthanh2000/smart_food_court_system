package com.example.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class recyclerView extends AppCompatActivity {
    private RecyclerView recycler;
    private ArrayList<Food> arrayList;
    private FirebaseRecyclerOptions<Food> options;
    private FirebaseRecyclerAdapter<Food, FoodAdapter.ViewHolder> adapter;
    private DatabaseReference ref;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu);
        recycler = findViewById(R.id.foodList);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        ref = FirebaseDatabase.getInstance().getReference().child("Food");
        ref.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(ref, Food.class).build();
        adapter = new FirebaseRecyclerAdapter<Food, FoodAdapter.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodAdapter.ViewHolder viewHolder, int i, @NonNull Food food) {
                viewHolder.foodname.setText(food.getName());
                viewHolder.foodprice.setText(food.getPrice());
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

            }

            @NonNull
            @Override
            public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new FoodAdapter.ViewHolder(LayoutInflater.from(recyclerView.this).inflate(R.layout.food_item, parent,false));
            }
        };

        recycler.setAdapter(adapter);
    }
}
