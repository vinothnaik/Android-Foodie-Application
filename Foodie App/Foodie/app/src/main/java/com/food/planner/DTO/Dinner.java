package com.food.planner.DTO;

import java.util.ArrayList;


public class Dinner  {
    public static final String TYPE="DINNER";

    public ArrayList<FoodDTO> foodSelected;

    public ArrayList<FoodDTO> getFoodSelected() {
        return foodSelected;
    }

    public void setFoodSelected(ArrayList<FoodDTO> foodSelected) {
        this.foodSelected = foodSelected;
    }

    @Override
    public String toString() {
        return "Dinner{" +
                "foodSelected=" + foodSelected +
                '}';
    }
}
