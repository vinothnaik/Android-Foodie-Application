package com.food.planner.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.food.planner.DTO.FoodDTO;
import com.food.planner.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;


public class FoodListAdapter extends ArrayAdapter<FoodDTO>{
    Context context;
    int layoutResourceId;
    ArrayList<FoodDTO> data=new ArrayList<FoodDTO>();
    public FoodListAdapter(Context context, int layoutResourceId, ArrayList<FoodDTO> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ImageHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new ImageHolder();
            holder.textView = (TextView)row.findViewById(R.id.foodName);
            holder.imageView = (ImageView)row.findViewById(R.id.moviePoster);
            row.setTag(holder);
        }
        else
        {
            holder = (ImageHolder)row.getTag();
        }

        FoodDTO picture = data.get(position);
        holder.textView.setText(picture.foodName);
        byte[] posterImage=picture._image;
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(posterImage);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);

        holder.imageView.setImageBitmap(bitmap);

        return row;

    }

    static class ImageHolder
    {
        ImageView imageView;
        TextView textView;
    }
}