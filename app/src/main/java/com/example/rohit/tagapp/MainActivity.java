package com.example.rohit.tagapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    String TAG = "MainActivity";
    Context context = null;
    public PackageManager packageManager = null;
    ArrayList <MyAppInfo>finalData = new ArrayList<MyAppInfo>();
    ListView listView;
    myAdapter myAdapter;
    LayoutInflater layoutInflater;
    View dialogView;
    AlertDialog.Builder dialogBuilder;
    DialogActions dialogActions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

         packageManager = getPackageManager();
         new getApplications().execute();
        customActionBar();

//        final Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.labeldialog);
//        dialog.setTitle("hello");
//        dialog.show();

        }
    private void customActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
     //   actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(255, 255, 255, 255)));
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false); /// used to get back button on actionbar
        actionBar.setDisplayShowTitleEnabled(false);// to show name of app as title
        actionBar.setDisplayShowCustomEnabled(true);


         layoutInflater = LayoutInflater.from(this);
        View customView = layoutInflater.inflate(R.layout.custom_toi, null);
        actionBar.setCustomView(customView);



      //  ActionBar.LayoutParams layout = new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT,
    //            AbsListView.LayoutParams.FILL_PARENT);
      //  ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_toi, null);
      //  actionBar.setCustomView(actionBarLayout, layout);
    }


 //   }

public class getApplications extends AsyncTask<Void, Void, Void>{

  //  PackageManager packageManager = ;
    ArrayList <ApplicationInfo> appList = new ArrayList<ApplicationInfo>();

    @Override
    protected Void doInBackground(Void... params) {
      //  packageManager = getPackageManager();
        // appInfo;

        appList = (ArrayList<ApplicationInfo>) packageManager.getInstalledApplications(packageManager.GET_META_DATA);
        //Collections.sort(appList, appInfo.name); // try and sort list

        for( ApplicationInfo appInfo : appList)
        {
            MyAppInfo myAppInfo = new MyAppInfo();
            myAppInfo.appName = (String) appInfo.loadLabel(packageManager);
            myAppInfo.appIcon = appInfo.loadIcon(packageManager);

          //  finaldata.add(appInfo);
            finalData.add(myAppInfo);
        }
//        Collections.sort(finaldata);

        myAdapter = new myAdapter(finalData, context);

        listView = (ListView)findViewById(R.id.fullListView);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i(TAG + " came in post execute", "main");
        listView.setAdapter(myAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              //  ApplicationInfo app = (ApplicationInfo) finaldata.get(position);

                MyAppInfo myAppInfo = finalData.get(position);

                //Intent intent = packageManager.getLaunchIntentForPackage(app.packageName); // this is used to launch app.
              //  startActivity(intent);
                TextView barTitle = (TextView) findViewById(R.id.textViewTitle);
                //barTitle.setText(app.loadLabel(packageManager)); // to get app name.
                barTitle.setText(myAppInfo.appName);

                dialogActions.showDialog(context);
//
//        final Dialog dialog = new Dialog(context);
//        dialog.setContentView(R.layout.labeldialog);
//        dialog.setTitle("hello");
//        dialog.show();

//                dialogView = layoutInflater.inflate(R.layout.labeldialog,null);
//                dialogBuilder = new AlertDialog.Builder(context);
//
//                dialogBuilder.setTitle("my dialog");
//                dialogBuilder.setView(dialogView);
//                dialogBuilder.create();
//                dialogBuilder.show();

                return false;
            }
        });
    }
}



}
