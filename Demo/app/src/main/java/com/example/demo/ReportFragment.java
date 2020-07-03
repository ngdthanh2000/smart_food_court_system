package com.example.demo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.DataOutput;
import java.sql.Array;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReportFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_recycler_view, container, false);

        final ArrayList<DateObject> objects = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Vendors").child(UserInfo.instance.getUserName()).child("completed_orders");

        final RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);



        readData(new MyCallback() {
            ArrayList<DateObject> value1 = new ArrayList<DateObject>();
            ArrayList<FoodReport> value2 = new ArrayList<FoodReport>();
            @Override
            public void onCallBack1(ArrayList<DateObject> value) {
                ObjectAdapter adapter = new ObjectAdapter(value);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCallBack2(ArrayList<FoodInfo> value) {
                UserInfo.instance.setFood(value);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        //setTotalOrder(textViewNumberOfOrder, textViewDate, UserInfo.instance.getUserName());

        return view;

    }

    private void readData(final MyCallback myCallback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Vendors").child(UserInfo.instance.getUserName()).child("completed_orders");
        ref.orderByChild("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getChildrenCount() > 0) {
                    ArrayList<DateObject> tempObjects = new ArrayList<DateObject>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        // Date date = (Date) data.child("dated").getValue().toString();
                        Order order = data.getValue(Order.class);
                        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = sdf1.parse(order.getDate());

                            final String strDate = sdf2.format(date);

                            boolean isExists = false;

                            for (DateObject object : tempObjects) {
                                if (strDate.equals(object.getDate())) {
                                    isExists = true;
                                    object.setNumberOfOrder(object.getNumberOfOrder() + 1);
                                    for (OrderFood food : order.getFoods()) {
                                        object.setRevenue(object.getRevenue() + Integer.parseInt(food.getPrice()) * Integer.parseInt(food.getQuantity()));
                                    }
                                    break;
                                }
                            }

                            if (!isExists) {

                                DateObject object1 = new DateObject(strDate, 1, 0);

                                for (OrderFood food : order.getFoods()) {
                                    object1.setRevenue(object1.getRevenue() + Integer.parseInt(food.getPrice()) * Integer.parseInt(food.getQuantity()));
                                }

                                tempObjects.add(object1);
                            }
                        }
                        catch (Exception e) {
                            Log.d("ERROR", e.getMessage());
                        }
                    }
                    myCallback.onCallBack1(tempObjects);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Food");
        reference.orderByChild("Vendor").equalTo(UserInfo.instance.getUserName().substring(UserInfo.instance.getUserName().length() - 2)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.getChildrenCount() > 0) {
                    ArrayList<FoodInfo> tempFoods = new ArrayList<FoodInfo>();
                    for (DataSnapshot data : snapshot.getChildren()) {
                        FoodInfo foodInfo = data.getValue(FoodInfo.class);
                        tempFoods.add(foodInfo);
                    }

                    myCallback.onCallBack2(tempFoods);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}