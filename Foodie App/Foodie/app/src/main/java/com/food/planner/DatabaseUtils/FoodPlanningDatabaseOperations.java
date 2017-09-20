package com.food.planner.DatabaseUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import com.food.planner.DTO.FoodPlanner;


import java.util.ArrayList;


public class FoodPlanningDatabaseOperations {

    private SQLiteDatabase db;
    private PlanningDatabaseUtil dbH;
    private ContentValues values;
    public FoodPlanningDatabaseOperations(Context context) {
        dbH = new PlanningDatabaseUtil(context);
    }
    private Gson gson=null;


    public FoodPlanner getFoodPlanForGivenDate(String date) {
        db = dbH.getReadableDatabase();
        String gsonObj=null;
        FoodPlanner foodPlanner=null;
        String selectQuery = "SELECT  * FROM " + PlanningDatabaseUtil.PLANNING;
               /* + " WHERE "
                + " date " + " = " + date;*/
        Cursor c = db.rawQuery(selectQuery, null);
        int id=0;
        if ( c.moveToFirst()) {

            do{
                gsonObj = c.getString(c.getColumnIndex("gsonObj"));
            id=c.getInt(c.getColumnIndex("bookingId"));
            Log.i("Jsom",gsonObj);
            if((c.getString(c.getColumnIndex("date"))).equals(date)){
            Gson gson=new Gson();
            foodPlanner=gson.fromJson(gsonObj,FoodPlanner.class);
            foodPlanner.planId=id;
            }
        }while (c.moveToNext());}
db.close();
        return foodPlanner;
    }



    public int insertFoodPlanning(FoodPlanner foodPlanner) {
         db = dbH.getWritableDatabase();
        values = new ContentValues();
        values.put("date",foodPlanner.date);
        values.put("gsonObj",new Gson().toJson(foodPlanner));

        // Inserting Row
        int ID =(int) db.insert(PlanningDatabaseUtil.PLANNING, null, values);
        Log.i("Id Inserted", String.valueOf(ID));
        db.close(); // Closing database connection
        return ID;
    }


    public int updateFoodPlanner(int id,String gsonStringOfFoodPlanner) {
        db = dbH.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("gsonObj", gsonStringOfFoodPlanner);

        // updating row for TAGS
        int idUpdate= db.update(PlanningDatabaseUtil.PLANNING, values, "bookingId" + " = ?",
                new String[] { String.valueOf(id) });
        Log.i("IdUpdated",idUpdate+"");
        db.close();
        return idUpdate;
    }



    public ArrayList<FoodPlanner> getAllFoodPlanned() {
        db = dbH.getReadableDatabase();
        String gsonObj=null;
        Gson gson=new Gson();
        ArrayList<FoodPlanner> listOfFoodPlanned=new ArrayList<>();
        FoodPlanner foodPlanner=null;
        String selectQuery = "SELECT  * FROM " + PlanningDatabaseUtil.PLANNING;
        Cursor c = db.rawQuery(selectQuery, null);
        int id=0;
        if ( c.moveToFirst()) {

            do{
                gsonObj = c.getString(c.getColumnIndex("gsonObj"));
                id=c.getInt(c.getColumnIndex("bookingId"));
                Log.i("Jsom",gsonObj);
                {
                    foodPlanner=gson.fromJson(gsonObj,FoodPlanner.class);
                    foodPlanner.planId=id;
                    listOfFoodPlanned.add(foodPlanner);

                }
            }while (c.moveToNext());}
        db.close();
        return listOfFoodPlanned;
    }




}
