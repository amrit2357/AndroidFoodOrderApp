package com.example.orderfoodnow;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodnow.Common.Common;
import com.example.orderfoodnow.Database.DataBase;
import com.example.orderfoodnow.Model.Order;
import com.example.orderfoodnow.Model.Request;
import com.example.orderfoodnow.ViewHolder.cartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class cart extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference requests;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    TextView txtTotal;
    FButton btnPlace;
    Button clear_cart;

    cartAdapter adapter;
    List<Order> cart = new ArrayList<>( );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cart );


        database = FirebaseDatabase.getInstance();
        requests = database.getReference("requests");

        // Init

       recyclerView = (RecyclerView)findViewById( R.id.listcart );
       recyclerView.setHasFixedSize( true );

       layoutManager = new LinearLayoutManager( this );
       recyclerView.setLayoutManager( layoutManager );

       txtTotal =(TextView)findViewById( R.id.total );

       clear_cart = (Button) findViewById( R.id.clear_cart );
       clear_cart.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new DataBase(getBaseContext()).clearCart();
               LoadOrders();
           }
       } );
       btnPlace = (FButton)findViewById( R.id.btnPlaceOrder );

       btnPlace.setOnClickListener( new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               showAlertDialog();
           }
       } );
       LoadOrders();

    }
    private  void showAlertDialog(){

        // Dynamically create alert dialogbox
        AlertDialog.Builder alertDialog = new AlertDialog.Builder( cart.this );
        alertDialog.setTitle( "One more step");
        alertDialog.setMessage( "Enter your address" );
        final EditText editText = new EditText( cart.this );
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editText.setLayoutParams( lp );
        alertDialog.setView( editText );
        alertDialog.setIcon( R.drawable.ic_shopping_cart_black_24dp );
        alertDialog.setPositiveButton( "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // create a new request
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        editText.getText().toString(),
                        txtTotal.getText().toString(),
                        "2",
                        cart);
                requests.child( String.valueOf( System.currentTimeMillis() ) ).setValue( request );
                new DataBase( getBaseContext() ).clearCart();
                Toast.makeText(cart.this,"Thank you , your order placed", Toast.LENGTH_SHORT).show();
                finish();
            }
        } );
        alertDialog.setNegativeButton( "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        } );
        alertDialog.show();

    }
    private void LoadOrders(){

        cart = new DataBase(this).getCarts();
        adapter = new cartAdapter( cart, this );

        recyclerView.setAdapter( adapter );

        int total = 0;
        for(Order order :cart){
            Log.d("price",""+order.getPrice());
            Log.d( "Quantity",""+order.getQuantity() );
            total +=(Integer.parseInt( order.getPrice() ))*(Integer.parseInt( order.getQuantity() ));
        }
        Locale locale = new Locale( "en","US" );
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotal.setText( (fmt.format( total ) ) );

    }
}
