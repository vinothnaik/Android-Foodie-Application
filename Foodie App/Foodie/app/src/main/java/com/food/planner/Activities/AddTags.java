package com.food.planner.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.food.planner.DTO.Tag;
import com.food.planner.DatabaseUtils.FoodCRUD_DatabaseHandler;
import com.food.planner.R;

import java.util.ArrayList;
import java.util.List;

public class AddTags extends AppCompatActivity {
    ListView listView;
    TextView textView;
    ArrayList<java.lang.String> tagsSelected;
    List<java.lang.String> listOfTags ;
    int idToBeUpdated;
    FoodCRUD_DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tags);
        Bundle bundle=getIntent().getExtras();
        idToBeUpdated=bundle.getInt("id");
        databaseHandler= new FoodCRUD_DatabaseHandler(AddTags.this);

        getSupportActionBar().setTitle("Add Tags");

        ((TextView)findViewById(R.id.foodName)).setText(bundle.get("foodName").toString());
        getSupportActionBar().setTitle("Food Lists");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        listView=(ListView)findViewById(R.id.listView);
        textView=(TextView)findViewById(R.id.tags);
        tagsSelected=new ArrayList<>();

        listOfTags= Tag.getTags();
        ArrayAdapter<java.lang.String> arrayAdapter = new ArrayAdapter<java.lang.String>(
                this,
                android.R.layout.simple_list_item_1,
                listOfTags );

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                java.lang.String tagSelectedFromList=listOfTags.get(position);
                if(tagsSelected.contains(tagSelectedFromList)){
                    int positionOfTag =tagsSelected.indexOf(tagSelectedFromList);
                    tagsSelected.remove(positionOfTag);
                    //     Toast.makeText(AddTags.this,tagSelectedFromList+" Removed",Toast.LENGTH_SHORT).show();
                    java.lang.String textViewText="";
                    for(java.lang.String tag:tagsSelected){
                        textViewText =" "+textViewText+tag;
                    }
                    textView.setText(textViewText);
                }else{
                    tagsSelected.add(tagSelectedFromList);
                    //  Toast.makeText(AddTags.this,tagSelectedFromList+" Added",Toast.LENGTH_SHORT).show();
                    textView.setText((textView.getText().toString())+" "+tagSelectedFromList);
                }

            }
        });

        findViewById(R.id.saveTags).setOnClickListener(new View.OnClickListener()
  {
                                                           @Override
                                                           public void onClick(View v) {
                                                               if(tagsSelected.size()>0) {databaseHandler.updateTags(idToBeUpdated,new Gson().toJson(tagsSelected));
                                                                   Toast.makeText(AddTags.this,"Tags Added",Toast.LENGTH_SHORT).show();
                                                                   Intent intent = new Intent(AddTags.this, FoodListTest.class);
                                                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                   startActivity(intent);
                                                                   finish();
                                                               }else{
                                                                   Toast.makeText(AddTags.this," No Tags Selected",Toast.LENGTH_SHORT).show();
                                                               }
                                                           }
                                                       }
        );
    }
}
