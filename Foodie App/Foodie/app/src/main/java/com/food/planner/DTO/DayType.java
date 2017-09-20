package com.food.planner.DTO;

import java.util.ArrayList;


public class DayType {

    public ArrayList<FoodDTO> foodSelected;

    public ArrayList<FoodDTO> getFoodSelected() {
        return foodSelected;
    }

    public void setFoodSelected(ArrayList<FoodDTO> foodSelected) {
        this.foodSelected = foodSelected;
    }

    @Override
    public String toString() {
        return "DayType{" +
                "foodSelected=" + foodSelected +
                '}';
    }
}
