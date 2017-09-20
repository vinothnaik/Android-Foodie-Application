package com.food.planner.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.food.planner.Activities.SelectFood;
import com.food.planner.DTO.FoodDTO;
import com.food.planner.R;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;


public class DisplayFoodListAdapter extends BaseAdapter

{
    public void updateList(ArrayList<FoodDTO> dataUpdated){
        foodDTOs =dataUpdated;
        list=dataUpdated;
        notifyDataSetChanged();
    }

    private List<FoodDTO> foodDTOs = new ArrayList<>();
    static public ArrayList<FoodDTO> list = new ArrayList<>();

    Context context;
    Animation anim;
    private static LayoutInflater inflater = null;

    public DisplayFoodListAdapter(Context contextx, ArrayList<FoodDTO> foods) {
        foodDTOs =foods;

        list = foods;

        context = contextx;
       
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return foodDTOs.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        ImageView imageView,isSelected;
        TextView foodName,tags;

        public Holder(View rowView){


            foodName = (TextView)rowView.findViewById(R.id.tagName);
            tags = (TextView)rowView.findViewById(R.id.tags);
            imageView = (ImageView)rowView.findViewById(R.id.imageView);
            isSelected=(ImageView)rowView.findViewById(R.id.selected);

        }
    }

    @Override
    public View getView(final int position, View rowView, ViewGroup parent) {

        Holder holder;

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.food_display_row, parent, false);
            holder = new Holder(rowView);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder) rowView.getTag();
        }

        // String tag=foodDTOs.get(position);

        FoodDTO picture = foodDTOs.get(position);
        holder.foodName.setText(picture.foodName);
        byte[] posterImage=picture._image;
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(posterImage);
         Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        holder.imageView.setImageBitmap(bitmap);
        if(SelectFood.idsOfFoodSelected.contains(picture._id)){

            holder.isSelected.setImageResource(R.drawable.ic_added);
        }else{
            holder.isSelected.setImageResource(R.drawable.ic_can_add);
        }
        String tagList="";
if(picture.tags!=null) {
    Log.i("Tags", picture.tags.toString());
    for (String tag : picture.tags) {
        tagList = tagList + tag + " ";
    }
    holder.tags.setText(tagList);
}
        return rowView;

    }


}
