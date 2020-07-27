package com.example.orderapp.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.IntDef;
import androidx.core.app.NotificationCompat;

import com.example.orderapp.Common.Common;
import com.example.orderapp.Model.Food;
import com.example.orderapp.Model.Order;
import com.example.orderapp.Model.PlacedOrder;
import com.example.orderapp.Model.Request;
import com.example.orderapp.OrderStatus;
import com.example.orderapp.R;
import com.example.orderapp.ViewHolder.OrderDetailAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ListienOrder extends Service implements ChildEventListener {
    FirebaseDatabase db;
    DatabaseReference placedOrder;
    public ListienOrder() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db =FirebaseDatabase.getInstance();
        placedOrder = db.getReference("PlacedOrder/");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        placedOrder.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Request placedOrder = dataSnapshot.getValue(Request.class);
        showNotification(dataSnapshot.getKey(), placedOrder);
    }

    private void showNotification(String key, Request placedOrder) {
        Intent intent = new Intent(getBaseContext(), OrderStatus.class);
        intent.putExtra("userPhone",placedOrder.getPhone());
        PendingIntent contentIntent = PendingIntent.getActivity(getBaseContext(),0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());


        OrderDetailAdapter adapter = new OrderDetailAdapter(Common.currentRequest.getFoods());
        Order food = (Order) Common.currentRequest.getFoods();
        builder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setContentInfo("Your order was updated!")

                .setContentIntent(contentIntent)
                .setContentInfo("Info").setSmallIcon(R.mipmap.ic_launcher);
        NotificationManager notificationManager = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
