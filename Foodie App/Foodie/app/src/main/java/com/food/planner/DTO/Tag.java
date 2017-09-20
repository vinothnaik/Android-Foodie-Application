package com.food.planner.DTO;

import java.util.ArrayList;


public class Tag {
    public static ArrayList<String> getTags(){
        final java.lang.String TAG1="#Low-cal";
        final java.lang.String TAG2="#Sinful";
        final java.lang.String TAG3="#Favorite";
        final java.lang.String TAG4="#Snacks";
        final java.lang.String TAG5="#Party";
        final java.lang.String TAG6="#Quick";
        final java.lang.String TAG7="#Kid";
        final java.lang.String TAG8="#Desert";
        final java.lang.String TAG9="#Tiffin";
        ArrayList<java.lang.String> tags=new ArrayList<>();
        tags.add(TAG1);
        tags.add(TAG2);
        tags.add(TAG3);
        tags.add(TAG4);
        tags.add(TAG5);
        tags.add(TAG6);
        tags.add(TAG7);
        tags.add(TAG8);
        tags.add(TAG9);
        return tags;

    }
}
