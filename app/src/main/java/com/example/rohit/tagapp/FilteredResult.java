package com.example.rohit.tagapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Rohit on 12/30/2015.
 */
public class FilteredResult extends AppCompatActivity {


    ArrayList<MyAppInfo> arrayListResult;
    Context context;
    CustomActionBar customActionBar = new CustomActionBar();
    ArrayList<MyAppInfo> filteredLabels;
    String selectedLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        context = this;
        customActionBar.customActionBar(getSupportActionBar(), context);
        customActionBar.setActionBarColor("#831919");
       // Intent intent = new Intent();
        arrayListResult = getIntent().getParcelableArrayListExtra("myAppInfoArray");
        selectedLabel = getIntent().getStringExtra("selectedLabel");
        Log.i("selectedLabel", selectedLabel);
        for(int i = 0;i<arrayListResult.size(); i++)
            Log.i("arrayitem", arrayListResult.get(i).appName);


    }

}


