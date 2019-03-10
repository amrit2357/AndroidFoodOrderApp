package com.example.orderfoodnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.orderfoodnow.Common.Common;
import com.example.orderfoodnow.Model.Request;
import com.example.orderfoodnow.ViewHolder.orderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class orderStatus extends AppCompatActivity {


    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Request, orderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_order_status );


        database = FirebaseDatabase.getInstance();
        requests = database.getReference("requests");

        recyclerView = (RecyclerView) findViewById( R.id.listOrderStatus );
        recyclerView.setHasFixedSize( true );
        layoutManager = new LinearLayoutManager( this );
        recyclerView.setLayoutManager( layoutManager );
        loadOrders(Common.currentUser.getPhone());
        Toast.makeText(orderStatus.this,""+Common.currentUser.getName() +Common.currentUser.getPhone(),Toast.LENGTH_SHORT).show();
    }

    private void loadOrders(final String phoneNumber){
        adapter = new FirebaseRecyclerAdapter<Request, orderViewHolder>(Request.class,R.layout.order_layout,orderViewHolder.class,
                requests.orderByChild( "phone" ).equalTo( phoneNumber )) {
            @Override
            protected void populateViewHolder(orderViewHolder viewHolder, Request model, int position) {
                Log.d("phone",model.getPhone());
                viewHolder.txtOrderId.setText(adapter.getRef( position ).getKey());
                viewHolder.txtOrderStatus.setText( ConvertCodeToStatus( model.getStatus()) );
                viewHolder.txtOrderPhone.setText( model.getPhone() );
                viewHolder.txtOrderAddress.setText( model.getAddress() );
            }
        };
        recyclerView.setAdapter( adapter );
    }
    private String ConvertCodeToStatus(String status) {
        if(status.equals("0")){
            return "Placed";
        }else if(status.equals("1")){
            return "On the Way";
        }else{
            return "Shipped";
        }

    }
}
