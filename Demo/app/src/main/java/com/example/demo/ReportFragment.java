package com.example.demo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.tabs.TabLayout;
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
import java.time.Month;
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

    ViewPager viewPager;
    TabLayout tabLayout;
    ArrayList<Fragment> fragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<FoodReport> foodReports = UserInfo.instance.getFoodReports();

        View view = inflater.inflate(R.layout.fragment_report, container, false);

        viewPager  = (ViewPager) view.findViewById(R.id.pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);

        fragments = new ArrayList<>();

        String currentMonth = foodReports.get(0).getDate().substring(5,7);

        for (int i = 0; i < 12; i++) {
            if (i < 9) {
                fragments.add(MonthFragment.newInstance("0" + String.valueOf(i + 1), ""));
            }
            else {
                fragments.add(MonthFragment.newInstance(String.valueOf(i + 1), ""));
            }
        }


        FragmentAdapter pagerAdapter = new FragmentAdapter(getFragmentManager(), getContext(), fragments);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        int i = 0;

        while (i <= 11) {
            if (i < 9) {
                tabLayout.getTabAt(i).setText("0" + String.valueOf(i + 1) + "/2020");
            }
            else {
                tabLayout.getTabAt(i).setText(String.valueOf(i + 1) + "/2020");
            }
            ++i;
        }

        viewPager.setCurrentItem(Integer.parseInt(currentMonth) - 1);
        tabLayout.getTabAt(Integer.parseInt(currentMonth) - 1).select();
        // Inflate the layout for this fragment
        /*final View view = inflater.inflate(R.layout.activity_recycler_view, container, false);

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
            public void onCallBack2(List<FoodInfo> value) {
                //UserInfo.instance.setFood(value);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        //setTotalOrder(textViewNumberOfOrder, textViewDate, UserInfo.instance.getUserName());*/

        return view;

    }

}