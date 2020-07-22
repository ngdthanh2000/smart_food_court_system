package com.example.orderapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.orderapp.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import java.net.PortUnreachableException;
import java.util.PropertyResourceBundle;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME="QuynhOrderDetail.db";
    private static final int DB_VER=1;
   // private String DB_PATH = "/data/data/com.test/databases/";
    private Context context;

     public Database (Context context) {
        super(context, DB_NAME, null, DB_VER);
        this.context = context;

    }



 public List<Order> getCarts(){
        SQLiteDatabase db = getReadableDatabase();
         SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

         String[] sqlSelect = {"ProductName", "ProductId", "Quantity", "Price", "Discount","Vendor"};
         String sqlTable = "OrderDetail";

         qb.setTables(sqlTable);
         Cursor c = qb.query(db,sqlSelect,null,null,null,null,null,null);

         final List<Order> result = new ArrayList<>();

         if(c.moveToFirst()){
             do{
                 result.add(new Order(c.getString(c.getColumnIndex("ProductId")),
                         c.getString(c.getColumnIndex("ProductName")),
                         c.getString(c.getColumnIndex("Quantity")),
                         c.getString(c.getColumnIndex("Price")),
                         c.getString(c.getColumnIndex("Discount")),
                         c.getString(c.getColumnIndex("Vendor"))

                 ));
             } while (c.moveToNext());
         }
         return result;
     }

     public void addToCart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO OrderDetail(ProductId,ProductName,Quantity,Price,Discount,Vendor)VALUES('%s','%s','%s','%s','%s','%s');",
                order.getProductId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount(),
                order.getVendor());
        db.execSQL(query);
     }

     public void cleanCart(){
         SQLiteDatabase db = getReadableDatabase();
         String query =String.format("DELETE FROM OrderDetail");
         db.execSQL(query);
                 }
}
