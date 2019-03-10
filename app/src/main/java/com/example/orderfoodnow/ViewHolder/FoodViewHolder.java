package com.example.orderfoodnow.ViewHolder;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orderfoodnow.Interface.ItemClickListener;
import com.example.orderfoodnow.R;

public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView food_image;
    public TextView food_name;
    public FloatingActionButton food_add;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private ItemClickListener itemClickListener;

    public FoodViewHolder(View itemView) {
        super(itemView);

        food_name = (TextView) itemView.findViewById(R.id.food_name);
        food_image = (ImageView) itemView.findViewById(R.id.food_image);
        food_add = (FloatingActionButton) itemView.findViewById(R.id.food_add);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
