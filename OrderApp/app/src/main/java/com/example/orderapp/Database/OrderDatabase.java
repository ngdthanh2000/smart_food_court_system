package com.example.orderapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.orderapp.Model.Order;
import com.example.orderapp.Model.PlacedOrder;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderDatabase extends SQLiteAssetHelper {


    private static final String DB_NAME="PlacedOrder.db";
    private static final int DB_VER=2;

    private Context context;

    public OrderDatabase(Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;
    }




    public List<PlacedOrder> getPlacedOrder(){
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"OrderId","OrderStatus","OrderTotal"};
        String sqlTable = "OrderStatus";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db,sqlSelect,null,null,null,null,null);

        final List<PlacedOrder> result = new ArrayList<>();

        if(c.moveToFirst()){
            do{
                result.add(new PlacedOrder(c.getInt(c.getColumnIndex("OrderId")),
                        c.getString(c.getColumnIndex("OrderStatus")),
                        c.getString(c.getColumnIndex("OrderTotal")) ));
            } while (c.moveToNext());
        }
        return result;
    }

    public void addToPlacedOrder(PlacedOrder order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderStatus(OrderId,OrderStatus,OrderTotal)VALUES('%s','%s','%s');",
                order.getOrderId(),
                order.getOrderStatus(),
                order.getOrderTotal());
        db.execSQL(query);
    }

}


