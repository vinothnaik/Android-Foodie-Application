package com.food.planner.DTO;

import java.util.ArrayList;

public class Lunch {
    public static  String TYPE="LUNCH";

    public static String getTYPE() {
        return TYPE;
    }

    public static void setTYPE(String TYPE) {
        Lunch.TYPE = TYPE;
    }

    public ArrayList<FoodDTO> foodSelected;

    public ArrayList<FoodDTO> getFoodSelected() {
        return foodSelected;
    }

    public void setFoodSelected(ArrayList<FoodDTO> foodSelected) {
        this.foodSelected = foodSelected;
    }

    @Override
    public String toString() {
        return "Lunch{" +
                "foodSelected=" + foodSelected +
                '}';
    }
}
