package com.food.planner.DatabaseUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.food.planner.DTO.FoodDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FoodCRUD_DatabaseHandler extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = " Food_imagedb";

    private static final String FOOD_IMAGE_DB = " FoodImageDB";
    private static final String FOOD_NAME_DETAILS = " FoodNameDetails";

    private static final String ROW_ID = "id";

    private static final String FOOD_IMAGE = "food_image";
    private static final String ROW_FK = "fk";
    private static final String FOODNAME = "foodname";
    private static final String ROW_FOOD_TAGS = "tags";

    public FoodCRUD_DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + FOOD_IMAGE_DB + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FOOD_IMAGE + " BLOB" + ")";
        String CREATE_MEMORY_INFO = "CREATE TABLE " + FOOD_NAME_DETAILS + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ROW_FK + " INTEGER,"
                + FOODNAME +  " TEXT,"
                + ROW_FOOD_TAGS +  " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_MEMORY_INFO);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + FOOD_IMAGE_DB);
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_NAME_DETAILS);

        onCreate(db);
    }



    public int updateFood(FoodDTO foodDTO) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ROW_FK, foodDTO._id);
        values.put(FOODNAME, foodDTO.foodName);
        long id=   db.insert(FOOD_NAME_DETAILS, null, values);
        Log.i("Inserting Memory Info", id + "");
        db.close();
        return (int)id;

    }


    public int addFood(FoodDTO foodDTO) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FOOD_IMAGE, foodDTO._image); // FoodDTO Phone

        long id=db.insert(FOOD_IMAGE_DB, null, values);
        Log.i("Inserting foodDTO", id + "");
        db.close(); // Closing database connection
        return (int)id;
    }


    //Get  details for an ID
    public FoodDTO getFood(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        FoodDTO foodDTO =null;
        String selectQuery = "SELECT  * FROM " + FOOD_NAME_DETAILS + " WHERE "
                + ROW_FK + " = " + id;
        Cursor c = db.rawQuery(selectQuery, null);

        if ((c != null)&& c.moveToFirst()){

            String title=c.getString(c.getColumnIndex(FOODNAME));
            String tagJson=c.getString(c.getColumnIndex(ROW_FOOD_TAGS));
            ArrayList<String> tags=new Gson().fromJson(tagJson, new TypeToken<ArrayList<String>>() {
            }.getType());

            foodDTO =new FoodDTO(tags,title);
        }
        c.close();

        return foodDTO;
    }


    // Getting All
    public List<FoodDTO> getAllFood() {
        List<FoodDTO> foodDTOList = new ArrayList<FoodDTO>();
        String selectQuery = "SELECT  * FROM FoodImageDB";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if((cursor!=null)&&(cursor.moveToFirst())){

            {
                do {
                    FoodDTO foodDTO = new FoodDTO();
                    foodDTO.setID(Integer.parseInt(cursor.getString(0)));
                    foodDTO.setImage(cursor.getBlob(1));
                    if(foodDTO._id>0){
                        FoodDTO foodDTOInfo = getFood((int) foodDTO._id);
                       if(foodDTOInfo !=null) {
                        foodDTO.foodName = foodDTOInfo.foodName;
                        foodDTO.tags= foodDTOInfo.tags;}

                    }
                    foodDTOList.add(foodDTO);

                } while (cursor.moveToNext());
            }}
        cursor.close();
        db.close();

        return foodDTOList;
    }




    public FoodDTO getFoodById(int id) {
        FoodDTO foodDTO =null;

        String selectQuery = "SELECT  * FROM FoodImageDB  WHERE "+ROW_ID +" = "+id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if ((cursor!=null)&&(cursor.moveToFirst()) ){
            do {
                foodDTO = new FoodDTO();
                foodDTO.setID(Integer.parseInt(cursor.getString(0)));
                foodDTO.setImage(cursor.getBlob(1));


            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodDTO;
    }



    // Updating TAGS
    public int updateTags(int id,String gsonStringOfTags) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ROW_FOOD_TAGS, gsonStringOfTags);

        // updating row for TAGS
        int idUpdate= db.update(FOOD_NAME_DETAILS, values, ROW_ID + " = ?",
                new String[] { String.valueOf(id) });
        Log.i("IdUpdated",idUpdate+"");
        db.close();
        return idUpdate;
    }


}