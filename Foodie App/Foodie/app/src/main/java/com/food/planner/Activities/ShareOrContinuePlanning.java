package com.food.planner.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.food.planner.DTO.FoodPlanner;
import com.food.planner.DTO.FoodDTO;
import com.food.planner.DatabaseUtils.FoodPlanningDatabaseOperations;
import com.food.planner.R;

public class ShareOrContinuePlanning extends AppCompatActivity {

    String date=null;
    int count =0;
    LinearLayout breakfast,lunch,dinner,others;
    TextView breakfastText,lunchText,dinnerText,othersText,dateText;
    String plannedString=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_or_continue_planning);

        getSupportActionBar().setTitle("Congrats! Meal Planned !");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle bundle=getIntent().getExtras();
        int id=bundle.getInt("idOfPlan");
        date =bundle.get("date").toString();
        Log.i("Date",date);
        breakfast=(LinearLayout)findViewById(R.id.breakfastLayout);
        lunch=(LinearLayout)findViewById(R.id.lunchLayout);
        dinner=(LinearLayout)findViewById(R.id.dinnerLayout);
        others =(LinearLayout)findViewById(R.id.othersLayout);

        breakfastText=(TextView)findViewById(R.id.breakfastText);
        lunchText=(TextView)findViewById(R.id.lunchText);
        dinnerText=(TextView)findViewById(R.id.dinnerText);
        othersText =(TextView)findViewById(R.id.othersText);
        dateText =(TextView)findViewById(R.id.date);

        FoodPlanningDatabaseOperations operations=new FoodPlanningDatabaseOperations(ShareOrContinuePlanning.this);
        FoodPlanner foodPlanner=operations.getFoodPlanForGivenDate(date);
        foodPlanner.date=date;


        if(foodPlanner.breakfast!=null){


            count++;
        }

        if(foodPlanner.lunch!=null){

            count++;
        }

        if(foodPlanner.dinner!=null){

            count++;
        }

        if(foodPlanner.others!=null){


            count++;
        }

        updateView(foodPlanner);

        if(count==4){
            findViewById(R.id.continuePlanning).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.optionsLay)).setWeightSum(2f);
        }

       // ((TextView)findViewById(R.id.planText)).setText(plannedString);

        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() { //Share
            @Override
            public void onClick(View v) {
                share();
            }
        });
        findViewById(R.id.continuePlanning).setOnClickListener(new View.OnClickListener() { //Continue Planning
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShareOrContinuePlanning.this,SelectFoodTime.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() { //Continue Planning
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareOrContinuePlanning.this, DashBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void share(){
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, plannedString);

        try {
            startActivity(Intent.createChooser(intent, "Select an action"));
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }

    void updateView(FoodPlanner foodPlanner){

        //   FoodPlanner foodPlanner=allPlannedFoodList.get(index);

        plannedString="**********"+foodPlanner.date+"**********";
        dateText.setText(foodPlanner.date);
        if(foodPlanner.breakfast!=null){
            String food="";
            breakfast.setVisibility(View.VISIBLE);
            plannedString=plannedString+"\n"+"@Breakfast"+ "\n";

            for(FoodDTO foodDTO :foodPlanner.breakfast.foodSelected){
                food=food+ foodDTO.getMovieName()+"\n";
                plannedString=plannedString+ foodDTO.getMovieName()+"\n";

            }
            breakfastText.setText(food);
        }
        else{
            breakfast.setVisibility(View.GONE);
        }

        if(foodPlanner.lunch!=null){
            String food="";
            plannedString=plannedString+"\n"+"@Lunch"+ "\n";
            lunch.setVisibility(View.VISIBLE);
            for(FoodDTO foodDTO :foodPlanner.lunch.foodSelected){
                plannedString=plannedString+ foodDTO.getMovieName()+"\n";
                food=food+ foodDTO.getMovieName()+"\n";
            }
            lunchText.setText(food);
        }
        else{
            lunch.setVisibility(View.GONE);
        }

        if(foodPlanner.dinner!=null){
            dinner.setVisibility(View.VISIBLE);
            String food="";
            plannedString=plannedString+"\n"+"@Dinner"+ "\n";

            for(FoodDTO foodDTO :foodPlanner.dinner.foodSelected){
                plannedString=plannedString+ foodDTO.getMovieName()+"\n";
                food=food+ foodDTO.getMovieName()+"\n";
            }
            dinnerText.setText(food);
        }
        else{
            dinner.setVisibility(View.GONE);
        }

        if(foodPlanner.others!=null){
            String food="";
            others.setVisibility(View.VISIBLE);
            plannedString=plannedString+"\n"+"@Others"+ "\n";

            for(FoodDTO foodDTO :foodPlanner.others.foodSelected){
                plannedString=plannedString+ foodDTO.getMovieName()+"\n";
                food=food+ foodDTO.getMovieName()+"\n";
            }
            othersText.setText(food);
        }else{

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(ShareOrContinuePlanning.this, DashBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ShareOrContinuePlanning.this, DashBoard.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
