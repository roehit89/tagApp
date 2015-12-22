package com.example.rohit.tagapp;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by Rohit on 12/22/2015.
 */
public class DialogActions {

    Dialog dialog;

   public void showDialog(Context context)
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.labeldialog);
        dialog.setTitle("hello");
        dialog.show();
    }
}
