package com.example.demo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import 	android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class MenuFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference ref;
    FirebaseRecyclerOptions<Food> options;
    FirebaseRecyclerAdapter<Food, ViewHolder> adapter;
    FloatingActionButton fb;
    StorageReference store;
    Uri uri;

    EditText edit_description, edit_discount, edit_name, edit_price;
    ImageButton edit_img;
    Food newFood;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_menu, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.foodList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        ref = FirebaseDatabase.getInstance().getReference().child("Food");
        store = FirebaseStorage.getInstance().getReference();
        ref.keepSynced(true);

        loadFoodList();
        fb = view.findViewById(R.id.fab);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuFragment.this.getContext());
                alertDialog.setTitle("Add new food");

                LayoutInflater inflater1 = getActivity().getLayoutInflater();
                View dialog = inflater1.inflate(R.layout.edit_dialog, null);
                edit_description = dialog.findViewById(R.id.edit_description);
                edit_discount = dialog.findViewById(R.id.edit_discount);
                edit_name = dialog.findViewById(R.id.edit_name);
                edit_price = dialog.findViewById(R.id.edit_price);
                edit_img = dialog.findViewById(R.id.edit_img);

                edit_img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        chooseImg();
                        uploadImg();
                    }
                });

                alertDialog.setView(dialog);

                alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (newFood != null) ref.push().setValue(newFood);
                    }
                });

                alertDialog.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.show();
            }
        });




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

public class MenuFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    private void chooseImg(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Select Image"), 71);
    }

    private void uploadImg(){
        if (uri != null){
            final ProgressDialog mDialog = new ProgressDialog(this.getContext());
            mDialog.setMessage("Uploading...");
            mDialog.show();

            String imgName = UUID.randomUUID().toString();
            final StorageReference imgFolder = FirebaseStorage.getInstance().getReference().child("images/" + imgName);
            imgFolder.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(MenuFragment.this.getContext(), "Uploaded!", Toast.LENGTH_LONG).show();
                    imgFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            newFood = new Food(edit_description.getText().toString(), edit_discount.getText().toString(), uri.toString(), edit_name.getText().toString(), edit_price.getText().toString(), UserInfo.instance.id);
                        }
                    });
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            mDialog.dismiss();
                            Toast.makeText(MenuFragment.this.getContext(), ""+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                            double progess = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mDialog.setMessage("Uploading " + progess +"%");
                        }
                    });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 71 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){
            uri = data.getData();
            Picasso.with(getContext()).load(uri).into(edit_img);
        }
    }
    private void loadFoodList() {
        options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(ref.orderByChild("vendor").equalTo(UserInfo.instance.getId()), Food.class).build();
        adapter = new FirebaseRecyclerAdapter<Food, ViewHolder>(options) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new ViewHolder(LayoutInflater.from(MenuFragment.this.getContext()).inflate(R.layout.food_item, parent, false));
            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i, @NonNull final Food food) {
                viewHolder._name.setText(food.getName());
                viewHolder._price.setText(food.getPrice());
                Picasso.with(getContext()).load(food.getImage()).into(viewHolder._image);
                viewHolder._detail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MenuFragment.this.getContext());
                        alertDialog.setTitle("Information");

                        LayoutInflater inflater1 = getActivity().getLayoutInflater();
                        final View dialog = inflater1.inflate(R.layout.detail_dialog, null);

                        //show info
                        final TextView info_description = dialog.findViewById(R.id.info_description);
                        info_description.setText(food.getDescription());
                        final TextView info_discount = dialog.findViewById(R.id.info_discount);
                        info_discount.setText(food.getDiscount());
                        final TextView info_name = dialog.findViewById(R.id.info_name);
                        info_name.setText(food.getName());
                        final TextView info_price = dialog.findViewById(R.id.info_price);
                        info_price.setText(food.getPrice());
                        final ImageView info_img = dialog.findViewById(R.id.info_img);
                        Picasso.with(getContext()).load(food.getImage()).into(info_img);

                        alertDialog.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        alertDialog.setView(dialog);
                        alertDialog.show();
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
