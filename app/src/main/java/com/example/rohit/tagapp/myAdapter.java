package com.example.rohit.tagapp;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rohit on 12/17/2015.
 */
public class myAdapter extends BaseAdapter {

    TextView appName;
    ImageView appImage;
    //ArrayList<ApplicationInfo> appList = new ArrayList<ApplicationInfo>();
    ArrayList<MyAppInfo> appList = new ArrayList<MyAppInfo>();
    Context context;
    PackageManager packageManager;
    ApplicationInfo applicationInfo = null;
    MyAppInfo myAppInfo = null;
    myAdapter(ArrayList appList,Context context ){

        this.context = context;
        this.appList = appList;
        packageManager = context.getPackageManager();
    }

    @Override
    public int getCount() {
        return appList.size();
    }

    @Override
    public Object getItem(int position) {
        return appList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
           convertView = LayoutInflater.from(context).inflate(R.layout.singlelist, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

       // applicationInfo = appList.get(position);

        myAppInfo = appList.get(position);
      //  viewHolder.imageView.setImageDrawable(applicationInfo.loadIcon(packageManager)); // load app icon from object applicationInfo
      //  viewHolder.appName.setText(applicationInfo.loadLabel(packageManager)); // to fetch app name from object applicationInfo

        viewHolder.imageView.setImageDrawable(myAppInfo.appIcon);
        viewHolder.appName.setText(myAppInfo.appName);
        return convertView;
    }



    public class ViewHolder
    {
        TextView appName;
        ImageView imageView;
        public ViewHolder(View item){
            appName = (TextView)item.findViewById(R.id.textView);
            imageView = (ImageView) item.findViewById(R.id.imageView);
        }
    }



}
