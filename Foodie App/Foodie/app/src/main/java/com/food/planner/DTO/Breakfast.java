package com.food.planner.DTO;

import java.util.ArrayList;

public class Breakfast  {
    public static final String TYPE="BREAKFAST";

    public ArrayList<FoodDTO> foodSelected;

    public ArrayList<FoodDTO> getFoodSelected() {
        return foodSelected;
    }

    public void setFoodSelected(ArrayList<FoodDTO> foodSelected) {
        this.foodSelected = foodSelected;
    }

    @Override
    public String toString() {
        return "Breakfast{" +
                "foodSelected=" + foodSelected +
                '}';
    }
}
