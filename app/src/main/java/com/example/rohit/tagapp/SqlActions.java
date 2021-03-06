package com.example.rohit.tagapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Rohit on 12/22/2015.
 */
public class SqlActions extends SQLiteOpenHelper {

    public static final String dbName = "tagAppDatabase.db";
    public static final String tableName = "allApps";
    public static final String tableName1 = "allTags";
    public static final String id = "id";
    public static final String appName = "appName";
 //   public static final String appIcon = "appIcon";
    public static final String appTag = "appTag";
    ArrayList<String> drawerArray= new ArrayList<String>();

    SqlActions(Context context)
    {
        super(context, dbName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        Log.i("table allApps dropped", "done");
   //     db.execSQL("create table " + tableName + "(id integer primary key, name text, icon text, tag text)");
        db.execSQL("create table " + tableName + "(id integer primary key, name text, tag text)");

        db.execSQL("DROP TABLE IF EXISTS " + tableName1);
        Log.i("table allTags dropped", "done");
        //     db.execSQL("create table " + tableName + "(id integer primary key, name text, icon text, tag text)");
        db.execSQL("create table " + tableName1 + "(id integer primary key, tag text)");


        Log.i("database created", "done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertValuesInAllTags(String tag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

       // contentValues.put("name", name);
        //  contentValues.put("icon",icon);
        contentValues.put("tag",tag);

        db.insert(tableName1,null,contentValues);
        Log.i("values inserted allTags", tag);

        return true;
    }

    //public boolean insertValues(String name, String icon, String tag){
    public boolean insertValuesInAllApps(String name, String tag){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
      //  contentValues.put("icon",icon);
        contentValues.put("tag",tag);

        db.insert(tableName,null,contentValues);
        Log.i("values inserted in allApps", name);

        return true;
    }

    public ArrayList<String> fetchSqlDataByTagFromAllTags()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+tableName1,null);
        ArrayList <String> result = new ArrayList<>();

        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {

//        Log.i("fetched appname by tag", cursor.getString(0)); // fetches id
            Log.i("tagname from AllTags", cursor.getString(1));// name
//        Log.i("fetched appname by tag",cursor.getString(2));// icon
//        Log.i("fetched appname by tag",cursor.getString(3));// tag
            //return cursor.getString(3);
            result.add(cursor.getString(1));
            cursor.moveToNext();
        }
            try{
            return result;
        }
        catch (Exception e){
            return null;
        }
    }
    //public Cursor fetchSqlDataByTag(String tag)
    public String fetchSqlDataByTagFromAllApps(String appName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+tableName+" where name = '"+appName+"'",null);

        cursor.moveToFirst();
//        Log.i("fetched appname by tag", cursor.getString(0)); // fetches id
//        Log.i("fetched appname by tag",cursor.getString(1));// name
//        Log.i("fetched appname by tag",cursor.getString(2));// icon
//        Log.i("fetched appname by tag",cursor.getString(3));// tag
        //return cursor.getString(3);
        try{
            return cursor.getString(2);
        }
        catch (Exception e){
            return "";
        }
    }

    public void deleteTagFromAllTags(String tag)
    {
      //  Log.i("values passed to update", newTag + " " + appName);
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+tableName1+" WHERE tag ='"+tag+"'");
      //  ContentValues contentValues = new ContentValues();
      //  contentValues.put("tag",newTag);

       // db.update(tableName,contentValues,"name = ?", new String[]{appName});
        Log.i("tag deleted", tag);
    }

    public void updateTableAllApps(String newTag, String appName)
    {
        Log.i("values passed to update",newTag +" "+ appName);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tag",newTag);

        db.update(tableName,contentValues,"name = ?", new String[]{appName});
        Log.i("table updated", "done");
    }

    public ArrayList<MyAppInfo> getAllApps(){

        ArrayList<MyAppInfo> arrayList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+tableName,null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false)
        {
            MyAppInfo myAppInfo = new MyAppInfo(Parcel.obtain());
            myAppInfo.appName = cursor.getString(cursor.getColumnIndex("name"));
            myAppInfo.appTag = cursor.getString(cursor.getColumnIndex("tag"));
          //  myAppInfo.appIcon = Drawable.createFromPath(cursor.getString(cursor.getColumnIndex("icon")));
            Log.i("app name from db",myAppInfo.appName);
            Log.i("app tag from db",myAppInfo.appTag);
            arrayList.add(myAppInfo);
            cursor.moveToNext();
        }

        return arrayList;
    }


}
