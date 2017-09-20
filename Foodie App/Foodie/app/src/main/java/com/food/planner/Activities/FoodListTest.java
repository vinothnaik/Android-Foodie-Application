package com.food.planner.Activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class FoodListTest extends AppCompatActivity {


    ArrayList<FoodDTO> foodDTOArrayList = new ArrayList<FoodDTO>();
    FoodListAdapter foodListAdapter;
    private static final int LaunchCamera = 1;
    private static final int SELECT_PICTURE = 100;
    ListView listView;
    FoodCRUD_DatabaseHandler db;

    // Choose an image from Gallery
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }


    private String pictureImagePath = "";
    private void openBackCamera() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + ".jpg";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, LaunchCamera);
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_food);

        listView = (ListView) findViewById(R.id.list);
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Food Lists");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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
            Toast.makeText(FoodListTest.this,"Add Food",Toast.LENGTH_LONG).show();

        }



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBackCamera();



            }
        });



        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case LaunchCamera:


            {


                    File imgFile = new  File(pictureImagePath);
                    if(imgFile.exists()){
                        Bitmap imageCaptured= BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imageCaptured= ThumbnailUtils.extractThumbnail(imageCaptured,300,300);
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        imageCaptured.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte toByteArray[] = byteArrayOutputStream.toByteArray();

                        int id= db.addFood(new FoodDTO( toByteArray));
                        Intent i = new Intent(FoodListTest.this,
                                AddFood.class);

                        i.putExtra("id",id);
                        startActivity(i);
                        finish();

                }
                }
                break;


            case SELECT_PICTURE :

                Uri selectedImageUri = data.getData();

                try {
                    if (null != selectedImageUri) {
                      Log.i("Path From Gallery ", selectedImageUri.getPath()) ;
                        saveImageToDB(selectedImageUri);

                    }
                }catch (Exception e){

                }

                break;

        }
    }


    void saveImageToDB(Uri selectedImageUri){
        try{ Bitmap imageSelected = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
        imageSelected= ThumbnailUtils.extractThumbnail(imageSelected,200,200);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageSelected.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte toByteArray[] = byteArrayOutputStream.toByteArray();

        int id= db.addFood(new FoodDTO( toByteArray));
        Intent i = new Intent(FoodListTest.this,
                AddFood.class);

        i.putExtra("id",id);
        startActivity(i);
        finish();
        }catch(Exception e){

        }

    }





    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(FoodListTest.this, DashBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

}
