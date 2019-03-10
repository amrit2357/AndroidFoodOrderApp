package com.example.orderfoodnow.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.orderfoodnow.Interface.ItemClickListener;
import com.example.orderfoodnow.Model.Order;
import com.example.orderfoodnow.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class CartViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView cart_item_name , cart_item_price;
        public ImageView cart_item_count;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        }

    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView) {
        super( itemView );
        cart_item_name = (TextView)itemView.findViewById( R.id.cart_item_name);
        cart_item_price = (TextView) itemView.findViewById( R.id.cart_item_price );
        cart_item_count = (ImageView)itemView.findViewById( R.id.cart_item_count );
        }

    @Override
    public void onClick(View view) {


    }
  }

  public class cartAdapter extends RecyclerView.Adapter<CartViewHolder>{
        private List<Order> listData = new ArrayList<>();
        private Context context;

        public cartAdapter( List<Order> listData , Context context) {
           this.listData = listData;
           this.context  = context;
        }

        @Override
        public CartViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from( context );
            View itemView = layoutInflater.inflate( R.layout.cartlayout ,parent,false);

            return new CartViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder( CartViewHolder holder, int position) {
            TextDrawable drawable = (TextDrawable) TextDrawable.builder().buildRound(""+ listData.get( position ).getQuantity(), Color.rgb(5, 151, 170 ));
            holder.cart_item_count.setImageDrawable( drawable );

            Locale locale = new Locale( "en","US" );
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

            int price = (Integer.parseInt( listData.get(position).getPrice() ))*(Integer.parseInt( listData.get( position ).getQuantity() ));
            holder.cart_item_price.setText( fmt.format(price ));

            holder.cart_item_name.setText( listData.get( position ).getProductName() );

        }

        @Override
        public int getItemCount() {
            return listData.size();
        }
    }




