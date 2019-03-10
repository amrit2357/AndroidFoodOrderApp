package com.example.orderfoodnow.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.orderfoodnow.Interface.ItemClickListener;
import com.example.orderfoodnow.R;

public class orderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId , txtOrderStatus , txtOrderPhone , txtOrderAddress;

    private ItemClickListener itemClickListener;

    public orderViewHolder(View itemView, ItemClickListener itemClickListener) {
        super( itemView );
        this.itemClickListener = itemClickListener;
    }

    public orderViewHolder(View itemView) {
        super( itemView );
        txtOrderId = (TextView)itemView.findViewById( R.id.order_id );
        txtOrderStatus  = (TextView)itemView.findViewById( R.id.order_status );
        txtOrderPhone  = (TextView)itemView.findViewById( R.id.order_phone );
        txtOrderAddress =  (TextView)itemView.findViewById( R.id.order_address );

        itemView.setOnClickListener( this);

    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick( view,getAdapterPosition(),false );
    }
}
