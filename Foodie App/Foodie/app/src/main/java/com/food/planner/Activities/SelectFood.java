package com.food.planner.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.food.planner.Adapter.DisplayFoodListAdapter;
import com.food.planner.Adapter.SelectTagsAdapter;
import com.food.planner.DTO.Breakfast;
import com.food.planner.DTO.Dinner;
import com.food.planner.DTO.FoodPlanner;
import com.food.planner.DTO.Lunch;
import com.food.planner.DTO.FoodDTO;
import com.food.planner.DTO.Others;
import com.food.planner.DTO.Tag;
import com.food.planner.DatabaseUtils.FoodPlanningDatabaseOperations;
import com.food.planner.DatabaseUtils.FoodCRUD_DatabaseHandler;
import com.food.planner.R;

import java.util.ArrayList;
import java.util.List;

public class SelectFood extends AppCompatActivity {
    public static ArrayList<Integer> idsOfFoodSelected;
    ArrayList<FoodDTO> foodsSelected ;
    public static List<String> tagsSelected ;
    ArrayList<FoodDTO> foodList;
    ArrayList<String> allTags ;
    ArrayList<FoodDTO> displayFoodList;
    FoodCRUD_DatabaseHandler databaseHandler;
    AlertDialog mAlertDialog;
    DisplayFoodListAdapter displayFoodListAdapter;
    ListView foodListView;
    FloatingActionButton fab;
    String date=null;
    int planType=0;
    String planTypeString=null;
    TextView titleView;
    ImageView filter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle=getIntent().getExtras();
        date=bundle.getString("date");
        planType=bundle.getInt("planType");

        switch (planType){

            case 1: planTypeString = "Breakfast";
                break;

            case 2: planTypeString = "Lunch";
                break;
            case 3: planTypeString = "Dinner";
                break;
            case 4: planTypeString = "Others";
                break;

        }
        //  getSupportActionBar().setTitle(date+"\n"+planTypeString);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        titleView=(TextView)findViewById(R.id.textView);
        filter=(ImageView)findViewById(R.id.imageView3);
        titleView.setText("Select Food\n"+"Date:"+date+"\n"+planTypeString);


        tagsSelected=new ArrayList<>();
        idsOfFoodSelected=new ArrayList<>();

        //fab = (FloatingActionButton) findViewById(R.id.fab);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterTag();
            }
        });
        foodsSelected=new ArrayList<>();
        foodList=new ArrayList<>();
        databaseHandler=new FoodCRUD_DatabaseHandler(SelectFood.this);

        foodListView=(ListView)findViewById(R.id.foodList);
        Handler handlerTimer = new Handler();
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading Food List");
        progressDialog.show();
        handlerTimer.postDelayed(new Runnable(){
            public void run() {
                foodList=(ArrayList<FoodDTO>) databaseHandler.getAllFood();
                displayFoodList=new ArrayList<>();
                displayFoodList=foodList;
                displayFoodListAdapter=new DisplayFoodListAdapter(SelectFood.this,foodList);
                foodListView.setAdapter(displayFoodListAdapter);
                Log.i("FoodList",displayFoodList.toString());
                progressDialog.dismiss();
            }}, 2000);



        foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodDTO foodDTO =displayFoodList.get(position);

                if(idsOfFoodSelected.contains(foodDTO._id)){
                    int positionOfAddition =idsOfFoodSelected.indexOf(foodDTO._id);
                    idsOfFoodSelected.remove(positionOfAddition);
                    foodsSelected.remove(positionOfAddition);
                    ((ImageView)view.findViewById(R.id.selected)).setImageResource(R.drawable.ic_can_add);

                }else{
                    idsOfFoodSelected.add(foodDTO._id);
                    foodsSelected.add(foodDTO);
                    ((ImageView)view.findViewById(R.id.selected)).setImageResource(R.drawable.ic_added);
                }
                // displayFoodListAdapter.notifyDataSetChanged();
            }
        });


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idOfPlanner=0;
                FoodPlanningDatabaseOperations operations=new FoodPlanningDatabaseOperations(SelectFood.this);
                ArrayList<FoodDTO> foodDTOArrayList = new ArrayList<FoodDTO>();

                if(foodsSelected.size()>0) {

                    for (FoodDTO foodDTO : foodsSelected) {
                        foodDTOArrayList.add(new FoodDTO(foodDTO._id, foodDTO.foodName));
                    }
                }else{
                    Snackbar.make(filter,"No Food(s) Selected",Snackbar.LENGTH_SHORT).show();
                }


                FoodPlanner foodPlanner=operations.getFoodPlanForGivenDate(date);
                if(foodPlanner!=null){
                    // update it
                    idOfPlanner=foodPlanner.planId;
                    ArrayList<FoodDTO> toBeAdded=new ArrayList<FoodDTO>();
                    switch (planType){

                        case 1:

                            Breakfast breakfast=new Breakfast();
                            toBeAdded= foodDTOArrayList;
                            breakfast.foodSelected= foodDTOArrayList;
                            foodPlanner.breakfast=breakfast;
                            break;

                        case 2:
                            Lunch lunch=new Lunch();
                            lunch.foodSelected= foodDTOArrayList;
                            foodPlanner.lunch=lunch;
                            break;
                        case 3:
                            Dinner dinner=new Dinner();
                            dinner.foodSelected= foodDTOArrayList;
                            foodPlanner.dinner=dinner;
                            break;
                        case 4:
                            Others others=new Others();
                            others.foodSelected= foodDTOArrayList;
                            foodPlanner.others=others;
                            break;

                    }

                    operations.updateFoodPlanner(foodPlanner.planId,new Gson().toJson(foodPlanner));
                }else{

                    //insert it

                    FoodPlanner planner=new FoodPlanner();
                    planner.date=date;

                    switch (planType){

                        case 1:

                            Breakfast breakfast=new Breakfast();
                          /*  toBeAdded=foodDTOArrayList;*/
                            breakfast.foodSelected= foodDTOArrayList;
                            planner.breakfast=breakfast;
                            break;

                        case 2:
                            Lunch lunch=new Lunch();
                            lunch.foodSelected= foodDTOArrayList;
                            planner.lunch=lunch;
                            break;
                        case 3:
                            Dinner dinner=new Dinner();
                            dinner.foodSelected= foodDTOArrayList;
                            planner.dinner=dinner;
                            break;
                        case 4:
                            Others others=new Others();
                            others.foodSelected= foodDTOArrayList;
                            planner.others=others;
                            break;

                    }

                    idOfPlanner= operations.insertFoodPlanning(planner);



                }

                Intent intent=new Intent(SelectFood.this,ShareOrContinuePlanning.class);
                intent.putExtra("idOfPlan",idOfPlanner);
                intent.putExtra("date",date);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    protected void filterTag(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filter foods based on Tags");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.select_tags_list, null);
        builder.setView(dialogView);
        final ListView listView=(ListView) dialogView.findViewById(R.id.selectTagsList);

        final SelectTagsAdapter selectTagsAdapter=new SelectTagsAdapter(SelectFood.this, Tag.getTags());
        listView.setAdapter(selectTagsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String tagCLicked=Tag.getTags().get(position);
                if(tagsSelected.contains(tagCLicked)){
                    tagsSelected.remove(tagsSelected.indexOf(tagCLicked));
                }else{
                    tagsSelected.add(tagCLicked);
                }
                selectTagsAdapter.notifyDataSetChanged();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //start the filtering and refresh the food items being displayed
                applyFilterAccordingToTagsOnFood();
            }
        });

        builder.setNegativeButton("Clear", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                tagsSelected=new ArrayList<String>();
                selectTagsAdapter.notifyDataSetChanged();
                applyFilterAccordingToTagsOnFood();
                //  mAlertDialog.dismiss();

            }
        });
        if (mAlertDialog != null) {
            mAlertDialog.dismiss();
        }
        mAlertDialog = builder.create();
        mAlertDialog.setCancelable(false);
        mAlertDialog.show();
    }



    public void applyFilterAccordingToTagsOnFood(){
        displayFoodList=new ArrayList<>();
        for(FoodDTO foodDTO :foodList){


            if(foodDTO.tags!=null)  {
                if (foodDTO.tags.containsAll(tagsSelected)) {
                    displayFoodList.add(foodDTO);
                }
            }


        }
        if(displayFoodList.size()<1){
            Snackbar.make(filter,"No Such Food Combos",Snackbar.LENGTH_SHORT).show();

        }
        Log.i("FIlter Result",displayFoodList.toString());
        displayFoodListAdapter.updateList(displayFoodList);

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
