package com.example.rohit.tagapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Rohit on 12/22/2015.
 */
public class DialogActions {

    Dialog dialog;
    Button button;

    Context context;
    Activity activity;

    DialogActions(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
    }
    public void showDialog(Context context, String appName)
    {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.labeldialog);
        dialog.setTitle(appName);
        dialog.show();
        //context = context;
    }
    View getDialogButtonId(){
        return dialog.findViewById(R.id.ConfirmButton);
    }

    View getRadioGroupId(){
        return dialog.findViewById(R.id.radioGroupdId);
    }

    String checkedRadioButtonText(RadioGroup radioGroup)
    {
        return ((RadioButton)dialog.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
    }

    void dismissDialog()
    {
        dialog.dismiss();
    }

    


}
