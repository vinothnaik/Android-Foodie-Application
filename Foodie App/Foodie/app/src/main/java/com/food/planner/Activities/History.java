package com.food.planner.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.food.planner.DTO.FoodPlanner;
import com.food.planner.DTO.FoodDTO;
import com.food.planner.DatabaseUtils.FoodPlanningDatabaseOperations;
import com.food.planner.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class History extends AppCompatActivity {
    FoodPlanningDatabaseOperations operations;
    ImageView back , next ;
    final int min=0;
    int max =0;
    int current =0;
    int sizeOfPlannedList=0;
    ArrayList<FoodPlanner> allPlannedFoodList;
    LinearLayout breakfast,lunch,dinner,others;
    TextView breakfastText,lunchText,dinnerText,othersText,date;
    String plannedString=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().setTitle("Planned History");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        back=(ImageView)findViewById(R.id.back);
        next=(ImageView)findViewById(R.id.next);

        //layouts
        breakfast=(LinearLayout)findViewById(R.id.breakfastLayout);
        lunch=(LinearLayout)findViewById(R.id.lunchLayout);
        dinner=(LinearLayout)findViewById(R.id.dinnerLayout);
        others =(LinearLayout)findViewById(R.id.othersLayout);

        breakfastText=(TextView)findViewById(R.id.breakfastText);
        lunchText=(TextView)findViewById(R.id.lunchText);
        dinnerText=(TextView)findViewById(R.id.dinnerText);
        othersText =(TextView)findViewById(R.id.othersText);
        date =(TextView)findViewById(R.id.date);

        operations=new FoodPlanningDatabaseOperations(History.this);
        allPlannedFoodList=new ArrayList<>();
        allPlannedFoodList=operations.getAllFoodPlanned();

        if((allPlannedFoodList!=null))
            sizeOfPlannedList=allPlannedFoodList.size();

        if(!(sizeOfPlannedList>=1)){
            next.setVisibility(View.INVISIBLE);
            findViewById(R.id.share).setVisibility(View.INVISIBLE);
            ((LinearLayout)findViewById(R.id.scrollView)).setVisibility(View.INVISIBLE);
            Snackbar.make(next,"No Planned History",Snackbar.LENGTH_INDEFINITE).show();
        }
        else{

         ArrayList<FoodPlanner> temp= allPlannedFoodList;

            try {
                for (int i = 0; i < sizeOfPlannedList; i++) {

                    FoodPlanner planner = temp.get(i);

                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    Date inputDate = dateFormat.parse(planner.date);

                    allPlannedFoodList.get(i).myDate = inputDate;
                }

            }catch(Exception e){

            }


            Collections.sort(allPlannedFoodList,byDate);
            next.setVisibility(View.VISIBLE);
            max=sizeOfPlannedList-1;
            updateView(current);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(current<max){
                    current++;
                    updateView(current);
                }
                if(current==max){
                    next.setVisibility(View.INVISIBLE);
                }
                if(current>=1){
                    back.setVisibility(View.VISIBLE);
                }
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                current--;
                if(current>=0) {
                    updateView(current);
                    next.setVisibility(View.VISIBLE);
                }
                if(current==0) {
                    back.setVisibility(View.INVISIBLE);

                    next.setVisibility(View.VISIBLE);

                }

            }
        });
        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() { //Share
            @Override
            public void onClick(View v) {
                share();
            }
        });

    }



    void updateView(int index){

        FoodPlanner foodPlanner=allPlannedFoodList.get(index);

        plannedString="**********"+foodPlanner.date+"**********";
        date.setText(foodPlanner.date);
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


    private void share(){
        final Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, plannedString);

        try {
            startActivity(Intent.createChooser(intent, "Select an action"));
        } catch (android.content.ActivityNotFoundException ex) {

        }
    }



    static final Comparator<FoodPlanner> byDate = new Comparator<FoodPlanner>() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        public int compare(FoodPlanner ord1, FoodPlanner ord2) {
            Date d1 = null;
            Date d2 = null;
            try {
                d1 =sdf.parse(ord1.date);
                d2 = sdf.parse(ord2.date);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return (d1.getTime() > d2.getTime() ? -1 : 1);     //descending
            //  return (d1.getTime() > d2.getTime() ? 1 : -1);     //ascending
        }
    };


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(History.this, DashBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

}

