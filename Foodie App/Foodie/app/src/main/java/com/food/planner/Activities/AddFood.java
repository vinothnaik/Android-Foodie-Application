package com.food.planner.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.food.planner.DTO.FoodDTO;
import com.food.planner.DatabaseUtils.FoodCRUD_DatabaseHandler;
import com.food.planner.R;

import java.io.ByteArrayInputStream;

public class AddFood extends AppCompatActivity {

    FoodDTO foodDTO;
    String bookingObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_food);
        FoodCRUD_DatabaseHandler FoodCRUD_DatabaseHandler =  new FoodCRUD_DatabaseHandler(this);
        Bundle bundle=getIntent().getExtras();
        bookingObj=bundle.getString("bookingObj");
        int id=bundle.getInt("id");

        foodDTO = FoodCRUD_DatabaseHandler.getFoodById(id);
        byte[] outImage= foodDTO._image;
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        ((ImageView)findViewById(R.id.foodImage)).setImageBitmap(theImage);

        getSupportActionBar().setTitle("Add Food Name");



        findViewById(R.id.saveFood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodName=((EditText)findViewById(R.id.foodName)).getText().toString();




                if((foodName!=null)){

                    FoodDTO newFoodDTO =new FoodDTO();

                    newFoodDTO.foodName =foodName;
                    newFoodDTO._id= foodDTO._id;
                    new FoodCRUD_DatabaseHandler(AddFood.this).updateFood(newFoodDTO);
                    Toast.makeText(AddFood.this,"Food Added !",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(AddFood.this,AddTags.class);
                    intent.putExtra("id", newFoodDTO._id);
                    intent.putExtra("foodName", newFoodDTO.foodName);
                    startActivity(intent);
                    finish();

                }else{
                    Toast.makeText(AddFood.this,"Enter Food Name",Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
