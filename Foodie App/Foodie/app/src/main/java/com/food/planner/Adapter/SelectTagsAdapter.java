package com.food.planner.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.food.planner.Activities.SelectFood;
import com.food.planner.R;


import java.util.ArrayList;
import java.util.List;



public class SelectTagsAdapter extends BaseAdapter

{
    private List<String> tagsList = new ArrayList<>();
    static public ArrayList<String> list = new ArrayList<>();
    Context context;

    private static LayoutInflater inflater = null;

    public SelectTagsAdapter(Context contextx, ArrayList<String> list) {
        tagsList =list;

        SelectTagsAdapter.list = list;

        context = contextx;
       
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return tagsList.size();
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
        TextView tagName;
        ImageView isSelected;

        public Holder(View rowView){

            tagName = (TextView) rowView.findViewById(R.id.tagName);
            isSelected = (ImageView) rowView.findViewById(R.id.selected);

        }
    }

    @Override
    public View getView(final int position, View rowView, ViewGroup parent) {

        Holder holder;

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.select_tag_row, parent, false);
            holder = new Holder(rowView);
            rowView.setTag(holder);
        }
        else {
            holder = (Holder) rowView.getTag();
        }

         String tag= tagsList.get(position);
        holder.tagName.setText(tag);
        if(SelectFood.tagsSelected.contains(tag)){
            holder.isSelected.setVisibility(View.VISIBLE);
        }else {
            holder.isSelected.setVisibility(View.INVISIBLE);
        }

        return rowView;

    }


}
