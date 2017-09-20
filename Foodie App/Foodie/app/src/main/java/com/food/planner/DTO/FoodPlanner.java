package com.food.planner.DTO;

import java.util.Date;


public class FoodPlanner {
    public Date myDate ;
    public  int planId;
    public String date;
    public Lunch lunch;
    public  Breakfast breakfast;
    public  Dinner dinner ;
    public Others others;

    @Override
    public String toString() {
        return "FoodPlanner{" +
                "planId=" + planId +
                ", date='" + date + '\'' +
                ", lunch=" + lunch +
                ", breakfast=" + breakfast +
                ", dinner=" + dinner +
                ", others=" + others +
                '}';
    }
}
