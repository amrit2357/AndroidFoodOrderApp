package com.example.orderfoodnow;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfoodnow.Database.DataBase;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.orderfoodnow.Model.Food;
import com.example.orderfoodnow.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class food_detail extends AppCompatActivity {


    TextView food_name , food_description , food_price;
    ImageView image_food;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btncart;
    ElegantNumberButton numberButton;
    String foodId ="";
    FirebaseDatabase database;
    DatabaseReference food;

    Food currentFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        database = FirebaseDatabase.getInstance();
        food = database.getReference("food");

        //Init the view

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btncart = (FloatingActionButton) findViewById(R.id.btncart);

        btncart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberButton.getNumber().equalsIgnoreCase( "0" )) {
                    Toast.makeText( food_detail.this, "Please increase the quantity to add", Toast.LENGTH_SHORT ).show();
                } else {
                    new DataBase( getBaseContext() ).addToCart( new Order(
                            currentFood.getName(),
                            foodId,
                            numberButton.getNumber(),
                            currentFood.getPrice(),
                            currentFood.getDiscount()
                    ) );

                    Toast.makeText( food_detail.this, "Added To Cart", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        food_description = (TextView) findViewById(R.id.food_description);
        food_name = (TextView) findViewById(R.id.food_name);
        food_price = ( TextView) findViewById(R.id.food_price);
        image_food = (ImageView) findViewById( R.id.image_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.Collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(R.style.CollapsedAppbar);


        if(getIntent()!= null){
            foodId = getIntent().getStringExtra("FoodId");
        }
        if(!foodId.isEmpty()){
            getDetailsFood(foodId);
        }

    }
    public void  getDetailsFood(String foodId){
        // show case teh details of the food seletec by the user
        food.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
            currentFood = dataSnapshot.getValue(Food.class);

            // set the image
                Log.d("userData",currentFood.getImage());
                Log.d("userData",currentFood.getName());
                Log.d("userData",currentFood.getDiscount());
                Log.d("userData",currentFood.getPrice());
                Log.d("userData",currentFood.getDescription());

                Picasso.get().load(currentFood.getImage()).into(image_food);
                collapsingToolbarLayout.setTitle( currentFood.getName() );
                food_price.setText( currentFood.getPrice());
                food_name.setText( currentFood.getName() );
                food_description.setText( currentFood.getDescription() );
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        }
        );
    }
}
