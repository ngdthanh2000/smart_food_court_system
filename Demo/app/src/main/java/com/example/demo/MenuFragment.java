package com.example.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference ref;
    FirebaseRecyclerOptions<Food> options;
    FirebaseRecyclerAdapter<Food, ViewHolder> adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.foodList);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        ref = FirebaseDatabase.getInstance().getReference().child("Food");
        ref.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(ref.orderByChild("Vendor").equalTo(UserInfo.instance.getId()), Food.class).build();

        adapter = new FirebaseRecyclerAdapter<Food, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull Food food) {
                viewHolder._name.setText(food.getName());
                viewHolder._price.setText(food.getPrice());
                Picasso.with(getContext()).load(food.getImage()).into(viewHolder._image);
                viewHolder._detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(MenuFragment.this.getContext()).inflate(R.layout.food_item, parent, false));
            }
        };
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
