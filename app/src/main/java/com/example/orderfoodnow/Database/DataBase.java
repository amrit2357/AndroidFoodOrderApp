package com.example.orderfoodnow.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.example.orderfoodnow.Model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBase extends SQLiteAssetHelper {
    private static final String DN_NAME = "orders.db";
    private static final int DB_VER =1;

    public DataBase(Context context) {
        super( context, DN_NAME, null, DB_VER );
    }

    public List<Order> getCarts(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String [] sqlSelect= {"ProductName" ,"productId","Quantity", "Price","Discount"};
        String table = "orders";
        qb.setTables(table );
        Cursor c = qb.query( db,sqlSelect,null, null,null,null,null);


        final List<Order> result = new ArrayList<>( );
        if(c.moveToFirst()){
            do{
                result.add(new Order(c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("ProductId")),
                         c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount"))
                        ));
            }while(c.moveToNext());

        }
        return result;
    }


    /* this fnction will add the order to the database*/
    public void addToCart(Order order){

        SQLiteDatabase db = getReadableDatabase();
        String query = String.format( "INSERT INTO orders(ProductName,ProductId,Quantity,Price,Discount) VALUES ('%s','%s','%s','%s','%s')",
                order.getProductName(),order.getProductId(),order.getQuantity(),order.getPrice(),order.getDiscount());
        db.execSQL(query);
    }
    public void clearCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM orders");
        db.execSQL(query);
    }
}
