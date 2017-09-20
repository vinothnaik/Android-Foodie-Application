package com.food.planner.DatabaseUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PlanningDatabaseUtil extends SQLiteOpenHelper {



	private static final int DATABASE_VERSION = 1;
	 
    // Database Name
    private static final String DATABASE_NAME = "food.db";
    //TABLE NAMES
    public static final String PLANNING = "planning";



    public PlanningDatabaseUtil(Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
	@Override
	public void onCreate(SQLiteDatabase db) {


        String CREATE_TABLE_BOOKING=	" CREATE TABLE "
                + PLANNING + "(" +   //TABLE NAME
                "bookingId" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date"+ " TEXT,"+
                "gsonObj"+  " TEXT"  + ")";




        db.execSQL(CREATE_TABLE_BOOKING);

 		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + PLANNING);
       // create new tables
        onCreate(db);
		
	}

}
