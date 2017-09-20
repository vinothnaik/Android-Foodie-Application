package com.food.planner.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.food.planner.Adapter.FoodListAdapter;
import com.food.planner.DTO.FoodDTO;
import com.food.planner.DatabaseUtils.FoodCRUD_DatabaseHandler;
import com.food.planner.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class FoodList extends AppCompatActivity {


    ArrayList<FoodDTO> foodDTOArrayList = new ArrayList<FoodDTO>();
    FoodListAdapter foodListAdapter;
    private static final int ImageFromCamera = 1;
    private static final int ImageFromGallery = 100;
    ListView listView;
    FoodCRUD_DatabaseHandler db;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        listView = (ListView) findViewById(R.id.list);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Food");

        db = new FoodCRUD_DatabaseHandler(this);

        List<FoodDTO> foodDTOs = db.getAllFood();
        for (FoodDTO foodDTO : foodDTOs) {
            foodDTOArrayList.add(foodDTO);

        }

        if(foodDTOs.size()>0){
            foodListAdapter = new FoodListAdapter(this, R.layout.food_view_layout,
                    foodDTOArrayList);
            listView.setAdapter(foodListAdapter);
        }else{
            Toast.makeText(FoodList.this,"Add Food",Toast.LENGTH_LONG).show();

        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, ImageFromCamera);
                intent.setType("image/*");

            }
        });



        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImageFromGalleryMethod();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });



    }



    void saveImageToDB(Uri selectedImageUri){
        try{ Bitmap imageSelected = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
        imageSelected= ThumbnailUtils.extractThumbnail(imageSelected,200,200);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageSelected.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte toByteArray[] = byteArrayOutputStream.toByteArray();

        int id= db.addFood(new FoodDTO( toByteArray));
        Intent i = new Intent(FoodList.this,
                AddFood.class);

        i.putExtra("id",id);
        startActivity(i);
        finish();
        }catch(Exception e){

        }

    }

    // Choose an image ]
    void chooseImageFromGalleryMethod() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), ImageFromGallery);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case ImageFromCamera:

                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap imageCaptured = extras.getParcelable("data");
                    Uri tempUri = getImageUri(getApplicationContext(), imageCaptured);
                    saveImageToDB(tempUri);



                }
                break;


            case ImageFromGallery:

                Uri selectedImageUri = data.getData();

                try {
                    if (null != selectedImageUri) {
                        saveImageToDB(selectedImageUri);

                    }
                }catch (Exception e){

                }

                break;

        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        Log.i("Path",path);
        return Uri.parse(path);
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(FoodList.this, DashBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

}
