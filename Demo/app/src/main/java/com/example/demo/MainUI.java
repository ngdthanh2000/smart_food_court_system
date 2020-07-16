package com.example.demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MainUI extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{
    private DrawerLayout drawer;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_u_i);

        readData(new MyCallback2() {
            @Override
            public void onCallBack3(List<FoodReport> value) {
                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Collections.sort(value, new Comparator<FoodReport>() {
                    @Override
                    public int compare(FoodReport foodReport, FoodReport t1) {
                        int ret = 0;
                        try {
                            ret = sdf.parse(foodReport.getDate()).compareTo(sdf.parse(t1.getDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return ret;
                    }
                });

                Collections.reverse(value);

                UserInfo.instance.setFoodReports(value);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_menu);
        }
    }

    private void readData(final MyCallback2 myCallback) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Vendors").child(UserInfo.instance.getUserName()).child("completed_orders");
        ref.orderByChild("date").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<FoodReport> foodReports = new ArrayList<FoodReport>();
                List<FoodInfo> foodInfos = UserInfo.instance.getFood();
                if (snapshot.getChildrenCount() > 0) {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        Order order = data.getValue(Order.class);
                        for (OrderFood orderFood : order.getFoods()) {
                            Log.d("ORDERFOOD", order.getDate() + " " + orderFood.getName() + " " + orderFood.getQuantity());
                        }
                        SimpleDateFormat sdf1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            Date date = sdf1.parse(order.getDate());
                            String strDate = sdf2.format(date);

                            boolean isExists = false;
                            FoodReport foodReport = null;

                            for (FoodReport food : foodReports) {
                                if (strDate.equals(food.getDate())) {
                                    isExists = true;
                                    foodReport = food;
                                }
                            }

                            if (!isExists) {
                                List<FoodInfo> foodInfos1 = new ArrayList<>();
                                for (int i = 0 ; i < foodInfos.size(); i++) {
                                    foodInfos1.add(new FoodInfo(foodInfos.get(i)));
                                }
                                foodReport = new FoodReport(strDate, foodInfos1);
                                foodReports.add(foodReport);
                            }

                            for (OrderFood orderFood : order.getFoods()) {
                                for (FoodInfo foodInfo : foodReport.getFoods()) {
                                    if (orderFood.getName().equals(foodInfo.getName())) {
                                        foodInfo.setQuantity(foodInfo.getQuantity() + Integer.parseInt(orderFood.getQuantity()));
                                    }
                                }
                            }

                            Log.d("FOODSIZE", String.valueOf(foodReports.size()));

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    myCallback.onCallBack3(foodReports);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_menu:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();
                break;
            case R.id.nav_staff:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StaffFragment()).commit();
                break;
            case R.id.nav_report:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReportFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}