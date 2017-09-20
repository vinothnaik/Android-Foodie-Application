package com.food.planner.DTO;

import java.util.ArrayList;


public class FoodDTO
{
    public FoodDTO(String foodName) {
        this.foodName = foodName;
    }

    // private variables
    public   int _id;
    public String _name;
    public byte[] _image;
    public String foodName, _foodDescription, _fDate;
    public ArrayList<String> tags;

    // Empty constructor
    public FoodDTO() {

    }

    public FoodDTO(int _id, String foodName) {
        this._id = _id;
        this.foodName = foodName;
    }

    public FoodDTO(ArrayList<String> tags, String _name) {
        this.tags = tags;
        this.foodName = _name;
    }

    public String get_foodDescription() {
        return _foodDescription;
    }

    public void set_foodDescription(String _foodDescription) {
        this._foodDescription = _foodDescription;
    }

    public String getMovieName() {
        return foodName;
    }

    public void setMovieName(String movieName) {
        this.foodName = movieName;
    }

    public String get_fDate() {
        return _fDate;
    }

    public void set_fDate(String _fDate) {
        this._fDate = _fDate;
    }

    public FoodDTO(String foodName, String _foodDescription, String _fDate) {

        this.foodName = foodName;
        this._foodDescription = _foodDescription;
        this._fDate = _fDate;

    }

    public FoodDTO(int keyId, String name, byte[] image) {
    this._id = keyId;
    this._name = name;
    this._image = image;

}
    public FoodDTO(byte[] image) {
        // this._name = name;
        this._image = image;

    }
    public FoodDTO(int keyId) {
        this._id = keyId;

    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    // getting phone number
    public byte[] getImage() {
        return this._image;
    }

    // setting phone number
    public void setImage(byte[] image) {
        this._image = image;
    }

    @Override
    public String toString() {

        return "FoodDTO{" +
                "_id=" + _id +
                ", foodName='" + foodName + '\'' +
                ", tags=" + tags +
                '}';
    }
}