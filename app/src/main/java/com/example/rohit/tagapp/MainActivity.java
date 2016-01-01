package com.example.rohit.tagapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcel;
import android.support.annotation.ColorInt;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    String TAG = "MainActivity";
    Context context = null;
    int drawerClickFlag = 0;
    int drawerOpenFlag = 0;
    int runOnceFlag = 0;
    public PackageManager packageManager = null;
    ArrayList <MyAppInfo>finalData = new ArrayList<MyAppInfo>();
    HashSet<String> newLabelsList = new HashSet<>();
    ListView listView;
    myAdapter myAdapter;
  //  LayoutInflater layoutInflater;
    View dialogView;
    AlertDialog.Builder dialogBuilder;
    Button oldConfirmButton = null, newConfirmButton = null;
    ImageButton newTag = null;
    ImageButton oldTag = null;
    ImageButton backButton = null;
    ImageButton navButton = null;
    DialogActions dialogActions;
    RadioGroup radioGroup;
    CustomActionBar customActionBar = new CustomActionBar();
    SqlActions sqlActions;
    TextView barTitle = null;
    EditText newTagText = null;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    ArrayAdapter drawerAdapter;
    ArrayList<MyAppInfo> filteredArrayList = new ArrayList<MyAppInfo>();

    ArrayList<String> drawerArray= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#000000"));
        }
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
        backButton = (ImageButton)findViewById(R.id.backButtonId);
        navButton = (ImageButton)findViewById(R.id.navButtonId);
        barTitle = (TextView) findViewById(R.id.textViewTitle);

        drawerArray.add("Bank");
        drawerArray.add("Browsers");
        drawerArray.add("Messaging");
        drawerArray.add("Music");
        drawerArray.add("Online shopping");
        drawerArray.add("Photo editor");

        mDrawerLayout = (DrawerLayout) findViewById(R.id.myDrawerLayout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_listView);

        drawerAdapter = new ArrayAdapter<String>(context, R.layout.drawerlayoutsinglelist, drawerArray);
        mDrawerList.setAdapter(drawerAdapter);
        //mDrawerList.setScrimColor(Color.TRANSPARENT);

        newLabelsList.add("Bank");
        newLabelsList.add("Browsers");
        newLabelsList.add("Messaging");
        newLabelsList.add("Music");
        newLabelsList.add("Online shopping");
        newLabelsList.add("Photo editor");


     //   backButton.setImageResource(R.mipmap.ic_drawer);
        backButton.setVisibility(View.INVISIBLE);
     //   radioGroup = (RadioGroup) dialogActions.getRadioGroupId();



    }


public class getApplications extends AsyncTask<Void, Void, Void>{

    ArrayList <ApplicationInfo> appList = new ArrayList<ApplicationInfo>();
    ProgressDialog progressDialog = null;
    @Override
    protected void onPreExecute() {
        progressDialog = progressDialog.show(MainActivity.this, null, "Loading installed apps.. "); //  added progress bar until info loads.
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Void... params) {
        final String PREFS_NAME = "MyPrefsFile";
        appList = (ArrayList<ApplicationInfo>) packageManager.getInstalledApplications(packageManager.GET_META_DATA);
      //  Collections.sort(appList, appInfo.name); // try and sort list
        for( ApplicationInfo appInfo : appList)
        {
            if(packageManager.getLaunchIntentForPackage(appInfo.packageName)!=null) { // display only installed apps.
                MyAppInfo myAppInfo = new MyAppInfo(Parcel.obtain());
                myAppInfo.appName = (String) appInfo.loadLabel(packageManager);
                myAppInfo.appIcon = appInfo.loadIcon(packageManager);
                myAppInfo.launchIntent = appInfo.packageName;
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);

              //  myAppInfo.appTag = ""; // write query here to fetch tag from database.
                myAppInfo.appTag = sqlActions.fetchSqlDataByTag(myAppInfo.appName);


          //      sqlActions.insertValues(myAppInfo.appName,myAppInfo.appIcon.toString(),myAppInfo.appTag);
                sqlActions.insertValues(myAppInfo.appName,myAppInfo.appTag);
                finalData.add(myAppInfo);
            }
        }
        Collections.sort(finalData, new Comparator<MyAppInfo>() { // sort all apps alphabetically.
            public int compare(MyAppInfo v1, MyAppInfo v2) {
                return v1.appName.compareToIgnoreCase(v2.appName);
            }
        });
        myAdapter = new myAdapter(finalData, context);
        listView = (ListView)findViewById(R.id.fullListView);
     //   mDrawerList = (ListView)findViewById(R.id.left_drawer);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // to launch app
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MyAppInfo myAppInfo;
                if (drawerClickFlag == 1) { // to check if longclick was done in main window or after being selected from tag in navigation drawer.
                    myAppInfo = filteredArrayList.get(position);
                    //drawerClickFlag = 0;
                    Log.i("went in if", "object =" + myAppInfo.appName);
                } else {
                    drawerClickFlag = 0;
                    myAppInfo = finalData.get(position);
                }
                Intent intent = packageManager.getLaunchIntentForPackage(myAppInfo.launchIntent); // this is used to launch app.
                  startActivity(intent);


            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final MyAppInfo myAppInfo;
                //   view.setBackground(new ColorDrawable(Color.parseColor("#f3f1f3")));
                if (drawerClickFlag == 1) { // to check if longclick was done in main window or after being selected from tag in navigation drawer.
                    myAppInfo = filteredArrayList.get(position);
                    //drawerClickFlag = 0;
                    Log.i("went in if", "object =" + myAppInfo.appName);
                } else {
                    drawerClickFlag = 0;
                    myAppInfo = finalData.get(position);
                }
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
                        dialogActions.showOldTagsDialog(context, myAppInfo.appName, newLabelsList); // show dialog box for tags/labels

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
                                drawerAdapter.notifyDataSetChanged(); // notify that drawer data was changed.
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
                                Log.i("new tag value = ", newTag);
                                if (newTag.endsWith(" ")) { // remove all spaces at the end of the label
                                    while (newTag.endsWith(" ")) {
                                        Log.i("has space", newTag);
                                        newTag = newTag.substring(0, newTag.length() - 1);
                                    }
                                }
                                myAppInfo.appTag = newTag;
                                sqlActions.updateTable(myAppInfo.appTag, myAppInfo.appName);// update tag
                                dialogActions.dismissNewDialog();
                                int length1 = newLabelsList.size();
                                newLabelsList.add(newTag);
                                int length2 = newLabelsList.size();
                                if (length2 > length1) // only if unique tag is added should it be added to drawerarray. drawerArray is an arrayList so duplicate elements might get inserted.
                                    drawerArray.add(newTag);

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
                return true; // returned true and not false otherwise onlongClick also did actions associated with onclick.
            }
        });
        newTag.setVisibility(View.INVISIBLE);
       // ArrayList<MyAppInfo> test = sqlActions.getAllApps();

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                               @Override
                                               public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                   //  Intent intent = new Intent(context,FilteredResult.class);
                                                   //intent.putExtra("myAppInfoArray",myAppInfo);

                                                   // intent.putStringArrayListExtra("myAppInfoArray",finalData);
//                                                   intent.putParcelableArrayListExtra("myAppInfoArray",finalData);
//                                                   intent.putExtra("selectedLabel",drawerArray.get(position));
//                                                   startActivity(intent);
                                                   customActionBar.setActionBarColor("#831919");
                                                   filteredArrayList.clear(); // clear previous contents
                                                   Log.i("hello", drawerArray.get(position));
                                                   mDrawerLayout.closeDrawers();
                                                   for (int i = 0; i < finalData.size(); i++) {
                                                       //    Log.i("arrayitemName", finalData.get(i).appName);
                                                       //    Log.i("arrayitemLabel", finalData.get(i).appTag);
                                                       if (finalData.get(i).appTag.equals(drawerArray.get(position))) {
                                                           filteredArrayList.add(finalData.get(i));
                                                           Log.i("added", finalData.get(i).appTag + " " + finalData.get(i).appName);
                                                       }
                                                   }
                                                   for (MyAppInfo myAppInfo : filteredArrayList) {
                                                       Log.i("filtered list", myAppInfo.appTag + " " + myAppInfo.appName);
                                                   }

                                                   myAdapter = new myAdapter(filteredArrayList, context);
                                                   drawerClickFlag = 1;
                                                   //  listView.deferNotifyDataSetChanged();
                                                   //  myAdapter.notifyDataSetChanged();
                                                   listView.setAdapter(myAdapter);
                                                   barTitle.setText(drawerArray.get(position));
                                                   navButton.setVisibility(View.INVISIBLE);
                                                   backButton.setVisibility(View.VISIBLE);


                                                   // listView.deferNotifyDataSetChanged();

                                                   for (int i = 0; i < filteredArrayList.size(); i++) {
                                                       Log.i("selected label ", filteredArrayList.get(i).appTag);
                                                   }
                                               }
                                           }
        );

        mDrawerList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // to remove tag
                                                   @Override
                                                   public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                                                       String selectedTag = drawerArray.get(position);
                                                       for (int i = 0; i < drawerArray.size(); i++) {
                                                           Log.i("drawerArray", drawerArray.get(i));
                                                       }
                                                       for (Object eachLabel : newLabelsList) {
                                                           Log.i("new label list", eachLabel.toString());
                                                       }


                                                       newLabelsList.remove(position); // remove data from dialog box


                                                       Log.i("drawerArray", "after remove");
                                                       for (int i = 0; i < drawerArray.size(); i++) {
                                                           Log.i("drawerArray", drawerArray.get(i));
                                                       }
                                                       int cnt = 0;
                                                       for (Object eachLabel : newLabelsList) {
                                                           if (eachLabel.toString().equalsIgnoreCase(drawerArray.get(position))) {
                                                               Log.i("milala re", eachLabel.toString());
                                                               newLabelsList.remove(eachLabel);// remove from label dialog box
                                                               break;
                                                           }
                                                           cnt++;
                                                       }
                                                       drawerArray.remove(position); /// remove from drawer array

                                                       for (Object eachLabel : newLabelsList) {
                                                           //    if (eachLabel.toString().equalsIgnoreCase(drawerArray.get(position))) {
                                                           Log.i("milalya nantar", eachLabel.toString());
                                                           //  }
                                                       }
                                                       for (MyAppInfo myAppInfo : finalData) {
                                                           if (myAppInfo.appTag.equalsIgnoreCase(selectedTag)) {
                                                               myAppInfo.appTag = "";
                                                               sqlActions.updateTable(myAppInfo.appTag, myAppInfo.appName);// update tag
                                                           }
                                                       }
                                                       //  myAdapter = new myAdapter(drawerArray, context);
                                                       drawerAdapter = new ArrayAdapter<String>(context, R.layout.drawerlayoutsinglelist, drawerArray);
                                                       mDrawerList.setAdapter(drawerAdapter);

                                                       mDrawerList.deferNotifyDataSetChanged();
                                                       //listView.deferNotifyDataSetChanged()

                                                       listView.setAdapter(myAdapter);
                                                       mDrawerLayout.closeDrawers();
                                                       Toast.makeText(context, selectedTag + " removed", Toast.LENGTH_SHORT).show();
                                                       return false;
                                                   }
                                               }

        );

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (drawerOpenFlag == 0) {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                    drawerOpenFlag = 1;
                } else {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    drawerOpenFlag = 0;
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener()

                                      {
                                          @Override
                                          public void onClick(View v) {
                                              backButton.setVisibility(View.GONE);
                                              myAdapter = new myAdapter(finalData, context);
                                              listView.deferNotifyDataSetChanged();
                                              listView.setAdapter(myAdapter);
                                              customActionBar.setActionBarColor("#831919");
                                              barTitle.setText("TagApp");
                                              oldTag.setVisibility(View.GONE);
                                              newTag.setVisibility(View.GONE);
                                              drawerClickFlag = 0;
                                              backButton.setVisibility(View.INVISIBLE);
                                              navButton.setVisibility(View.VISIBLE);
                                          }
                                      }

        );
            }
        }
    }
