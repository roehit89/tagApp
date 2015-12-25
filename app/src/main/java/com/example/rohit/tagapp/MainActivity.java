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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

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
  //  LayoutInflater layoutInflater;
    View dialogView;
    AlertDialog.Builder dialogBuilder;
    Button oldConfirmButton = null, newConfirmButton = null;
    ImageButton newTag = null;
    ImageButton oldTag = null;
    DialogActions dialogActions;
    RadioGroup radioGroup;
    CustomActionBar customActionBar = new CustomActionBar();
    SqlActions sqlActions;
    TextView barTitle = null;
    EditText newTagText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        sqlActions = new SqlActions(context);
        dialogActions = new DialogActions(this, context);
        //DialogActions(context);
         packageManager = getPackageManager();
         new getApplications().execute();
        customActionBar.customActionBar(getSupportActionBar(), context);
        newTag = (ImageButton)findViewById(R.id.newTagId);
        oldTag = (ImageButton)findViewById(R.id.oldTagId);
        newTag.setVisibility(View.GONE);
        oldTag.setVisibility(View.GONE);
        barTitle = (TextView) findViewById(R.id.textViewTitle);

     //   radioGroup = (RadioGroup) dialogActions.getRadioGroupId();
    }


public class getApplications extends AsyncTask<Void, Void, Void>{

    ArrayList <ApplicationInfo> appList = new ArrayList<ApplicationInfo>();

    @Override
    protected Void doInBackground(Void... params) {
        appList = (ArrayList<ApplicationInfo>) packageManager.getInstalledApplications(packageManager.GET_META_DATA);
      //  Collections.sort(appList, appInfo.name); // try and sort list
        for( ApplicationInfo appInfo : appList)
        {
            if(packageManager.getLaunchIntentForPackage(appInfo.packageName)!=null) { // display only installed apps.
                MyAppInfo myAppInfo = new MyAppInfo();
                myAppInfo.appName = (String) appInfo.loadLabel(packageManager);
                myAppInfo.appIcon = appInfo.loadIcon(packageManager);
                myAppInfo.appTag = ""; // write query here to fetch tag from database.
              //  myAppInfo.appTag =  sqlActions.fetchSqlDataByTag(myAppInfo.appName);

                sqlActions.insertValues(myAppInfo.appName,myAppInfo.appIcon.toString(),myAppInfo.appTag);
                finalData.add(myAppInfo);
            }
        }
        myAdapter = new myAdapter(finalData, context);
        listView = (ListView)findViewById(R.id.fullListView);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.i(TAG + " came in post execute", "main");
        listView.setAdapter(myAdapter);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                newTag.setVisibility(View.INVISIBLE);
                oldTag.setVisibility(View.INVISIBLE);
                barTitle.setText("TagApp");
                // view.setBackground(new ColorDrawable(Color.parseColor("#ffffff")));
                customActionBar.setActionBarColor("#831919");
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //   view.setBackground(new ColorDrawable(Color.parseColor("#f3f1f3")));
                final MyAppInfo myAppInfo = finalData.get(position);

                newTag.setVisibility(View.VISIBLE);
                oldTag.setVisibility(View.VISIBLE);
                //Intent intent = packageManager.getLaunchIntentForPackage(app.packageName); // this is used to launch app.
                //  startActivity(intent);
                barTitle.setText(myAppInfo.appName);
                // barTitle.setBackgroundColor(Color.parseColor("#00001a"));

                customActionBar.setActionBarColor("#29293d");


                oldTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogActions.showOldTagsDialog(context, myAppInfo.appName); // show dialog box for tags/labels

                        oldConfirmButton = (Button) dialogActions.getOldDialogButtonId();
                        oldConfirmButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                radioGroup = (RadioGroup) dialogActions.getRadioGroupId();
                                Log.i(TAG, dialogActions.checkedRadioButtonText(radioGroup));
                                //  Log.i(TAG, String.valueOf(dialogActions.getCh);
                                //    String radiovalue = ((RadioButton)findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
                                myAppInfo.appTag = dialogActions.checkedRadioButtonText(radioGroup);
                                sqlActions.updateTable(myAppInfo.appTag, myAppInfo.appName);// update tag
                                //   sqlActions.fetchSqlDataByTag("browsers");
                                myAdapter.notifyDataSetChanged(); // notify that data was changed.
                                dialogActions.dismissOldDialog();
                                newTag.setVisibility(View.INVISIBLE);
                                oldTag.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });

                newTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogActions.showNewTagDialog(context, myAppInfo.appName);
                       // setContentView(R.layout.newlabledialog);
                        newTagText = (EditText) dialogActions.getEditTextid();
                        newConfirmButton = (Button) dialogActions.getNewDialogButtonId();
                        newConfirmButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String newTag = newTagText.getText().toString();
                                Log.i("new tag value = ",newTag);
                                myAppInfo.appTag = newTag;
                                dialogActions.dismissNewDialog();


                            }
                        });


                    }
                });

//                dialogView = layoutInflater.inflate(R.layout.labeldialog,null);
//                dialogBuilder = new AlertDialog.Builder(context);
//
//                dialogBuilder.setTitle("my dialog");
//                dialogBuilder.setView(dialogView);
//                dialogBuilder.create();
//                dialogBuilder.show();
                //newTag.setVisibility(View.GONE);
                return false;
            }
        });
        newTag.setVisibility(View.INVISIBLE);
        ArrayList<MyAppInfo> test = sqlActions.getAllApps();
    }
}
}
