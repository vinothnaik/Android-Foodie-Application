package com.food.planner.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.food.planner.R;

public class DashBoard extends AppCompatActivity {
    int reqCode=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        getSupportActionBar().hide();

        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,History.class));
            }
        });
        findViewById(R.id.addFood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,FoodListTest.class));
            }
        });

        findViewById(R.id.planFood).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard.this,SelectDate.class));
            }
        });

        checkPermissions(findViewById(R.id.addFood));

    }
    public void checkPermissions(View view) {
        // Check if the  permission is already available.
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)||(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)){

            requestPermission();

        } else {

        }
    }
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS)) {

            Snackbar.make(findViewById(R.id.addFood), "Grant Permission",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(DashBoard.this,
                                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    reqCode);
                        }
                    })
                    .show();
        } else {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    reqCode);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        Log.i("ReqCode",String.valueOf(requestCode));
        if (requestCode == reqCode
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }else{
            Snackbar.make(findViewById(R.id.addFood), "Grant Permission",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(DashBoard.this,
                                    new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    reqCode);
                        }
                    })
                    .show();
        }
    }


}
