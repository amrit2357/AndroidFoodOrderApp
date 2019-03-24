package com.example.orderfoodnow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.orderfoodnow.Interface.ItemClickListener;
import com.example.orderfoodnow.Model.Food;
import com.example.orderfoodnow.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FoodList extends AppCompatActivity {

    /* initialization of all the variables will used in the file*/
    RecyclerView recycler_food;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;
    String categoryId = "";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;


    //searchbar functionality
    FirebaseRecyclerAdapter<Food, FoodViewHolder> searchadapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("food");

        recycler_food = (RecyclerView) findViewById(R.id.recycler_food);
        recycler_food.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recycler_food.setLayoutManager(layoutManager);

        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if (categoryId != null && !categoryId.isEmpty()) {
            LoadListFood(categoryId);
        }
        // search bar functionality
        materialSearchBar = (MaterialSearchBar)findViewById( R.id.search_bar );
        materialSearchBar.setHint( "Enter your food" );
        loadSuggest();
        materialSearchBar.setLastSuggestions( suggestList );
        materialSearchBar.setCardViewElevation( 10 );

        // events when user change the text and after that on cancellation
        materialSearchBar.addTextChangeListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for(String search : suggestList){
                    if(search.toLowerCase().contains( materialSearchBar.getText().toLowerCase() )){
                        suggest.add( search );
                    }
                }
                materialSearchBar.setLastSuggestions( suggest );
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        } );
        materialSearchBar.setOnSearchActionListener( new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                // when search bar closed restore the changes
                if(!enabled)
                    recycler_food.setAdapter( adapter );
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                 // when search finishes show the results of the  search
                startSearchResults(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        } );

    }

    private void startSearchResults(CharSequence text) {
        searchadapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(
                Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild( "name" ).equalTo( text.toString())) {
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.get()
                        .load(R.drawable.mainhd)
                        .into(viewHolder.food_image);

                final Food local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {

          @Override public void onClick(View view, int Position, Boolean isLongClick) {

                       Intent food_detail = new Intent(FoodList.this , food_detail.class);
                       food_detail.putExtra( "FoodId",searchadapter.getRef( Position ).getKey() );
                        startActivity( food_detail );
          }
     }
    );
    }
        };
        recycler_food.setAdapter( searchadapter );
    }

    private void loadSuggest(){
        foodList.orderByChild( "menuId" ).equalTo( categoryId ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapShot : dataSnapshot.getChildren()){
                    Food item = postSnapShot.getValue(Food.class);
                    suggestList.add(item.getName());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    private void LoadListFood(String categoryId) {
      adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,
              R.layout.food_item,
              FoodViewHolder.class,
              foodList.orderByChild("mupaenuId").equalTo(categoryId)) {
          @Override
          protected void populateViewHolder(FoodViewHolder viewHolder, Food model, final int position) {
              viewHolder.food_name.setText(model.getName());
              Picasso.get()
                      .load(R.drawable.mainhd)
                      .into(viewHolder.food_image);

              final Food local = model;
              viewHolder.setItemClickListener(new ItemClickListener() {

                  @Override
                  public void onClick(View view, int Position, Boolean isLongClick) {

                      Intent food_detail = new Intent(FoodList.this , food_detail.class);
                      food_detail.putExtra( "FoodId",adapter.getRef( position ).getKey() );
                      startActivity( food_detail );
                     // Toast.makeText(FoodList.this,""+local.getName(),Toast.LENGTH_SHORT).show();
                  }
              }
              );
          }
      };
      recycler_food.setAdapter(adapter);
    }

}


