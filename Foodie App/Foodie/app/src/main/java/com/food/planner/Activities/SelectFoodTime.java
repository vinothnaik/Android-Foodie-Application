package com.food.planner.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.food.planner.DTO.FoodPlanner;
import com.food.planner.DatabaseUtils.FoodPlanningDatabaseOperations;
import com.food.planner.R;

public class SelectFoodTime extends AppCompatActivity {
    int planType=0;
    Button breakfast,lunch,dinner,other;
   final int maxCount=4 ;
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food_time2);
        getSupportActionBar().setTitle("Select Food Time");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Bundle bundle=getIntent().getExtras();
        final String date=bundle.getString("date");

        breakfast=(Button)findViewById(R.id.breakfast);
        lunch=(Button)findViewById(R.id.lunch);
        dinner= (Button)findViewById(R.id.dinner);
        other=(Button)findViewById(R.id.others);



        FoodPlanningDatabaseOperations operations=new FoodPlanningDatabaseOperations(SelectFoodTime.this);
        FoodPlanner foodPlanner=operations.getFoodPlanForGivenDate(date);

        if(foodPlanner!=null){
            Log.i("Plan",foodPlanner.toString());
            if((foodPlanner.breakfast!=null)){

                if(foodPlanner.breakfast.foodSelected.size()>0){
                    findViewById(R.id.breakfast).setVisibility(View.GONE);
                    count++;
                }
            }
            if((foodPlanner.lunch!=null)){

                if(foodPlanner.lunch.foodSelected.size()>0){
                    findViewById(R.id.lunch).setVisibility(View.GONE);
                    count++;
                }
            }
            if((foodPlanner.dinner!=null)){

                if(foodPlanner.dinner.foodSelected.size()>0){
                    findViewById(R.id.dinner).setVisibility(View.GONE);
                    count++;
                }
            }
            if((foodPlanner.others!=null)){

                if(foodPlanner.others.foodSelected.size()>0){
                   other.setVisibility(View.GONE);
                    count++;
                }
            }

            if(count==maxCount){
                findViewById(R.id.textView2).setVisibility(View.INVISIBLE);
                findViewById(R.id.next).setVisibility(View.INVISIBLE);
                Snackbar.make(breakfast,"The day is planned",Snackbar.LENGTH_INDEFINITE).show();
            }

        }

        findViewById(R.id.breakfast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(1);

            }
        });
        findViewById(R.id.lunch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(2);
            }
        });
        findViewById(R.id.dinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setView(3);
            }
        });

        findViewById(R.id.others).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setView(4);
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(planType>0) {
                    Intent intent = new Intent(SelectFoodTime.this, SelectFood.class);
                    intent.putExtra("date", date);
                    intent.putExtra("planType", planType);
                    startActivity(intent);
                }else{
                    Snackbar.make(breakfast,"Please select a Food Time",Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }




    void setView(int planId){
        planType=planId;
        int allColor=SelectFoodTime.this.getResources().getColor(R.color.mycolor) ;
        int selectedColor=SelectFoodTime.this.getResources().getColor(R.color.green) ;
        Drawable img = SelectFoodTime.this.getResources().getDrawable(R.drawable.selected);

        breakfast.setBackgroundColor(allColor);
        lunch.setBackgroundColor(allColor);
        dinner.setBackgroundColor(allColor);
        other.setBackgroundColor(allColor);

        breakfast.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        lunch.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        dinner.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        other.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);

        switch (planId){

            case 1:

                breakfast.setBackgroundColor(selectedColor);
                breakfast.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);
                break ;
            case 2:
                lunch.setBackgroundColor(selectedColor);
                lunch.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);
                break ;
            case 3:
                dinner.setBackgroundColor(selectedColor);
                dinner.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);
                break ;
            case 4:
                other.setBackgroundColor(selectedColor);
                other.setCompoundDrawablesWithIntrinsicBounds(null,null,img,null);
                break ;

        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }


}
