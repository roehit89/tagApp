package com.example.rohit.tagapp;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Rohit on 12/22/2015.
 */
public class MyAppInfo implements Parcelable {

    String appName;
    Drawable appIcon;
    String appTag;
    String launchIntent;

    protected MyAppInfo(Parcel in) {
        appName = in.readString();
        appTag = in.readString();
        launchIntent = in.readString();
    }

    public static final Creator<MyAppInfo> CREATOR = new Creator<MyAppInfo>() {
        @Override
        public MyAppInfo createFromParcel(Parcel in) {
            return new MyAppInfo(in);
        }

        @Override
        public MyAppInfo[] newArray(int size) {
            return new MyAppInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(appName);
        dest.writeString(appTag);
        dest.writeString(launchIntent);
    }
}
