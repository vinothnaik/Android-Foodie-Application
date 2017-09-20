package com.food.planner.DTO;

import java.util.ArrayList;

public class Others {
    public static final String TYPE="OTHERS";

    public ArrayList<FoodDTO> foodSelected;

    public ArrayList<FoodDTO> getFoodSelected() {
        return foodSelected;
    }

    public void setFoodSelected(ArrayList<FoodDTO> foodSelected) {
        this.foodSelected = foodSelected;
    }

    @Override
    public String toString() {
        return "Others{" +
                "foodSelected=" + foodSelected +
                '}';
    }
}
