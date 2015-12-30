package com.example.rohit.tagapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rohit on 12/22/2015.
 */
public class DialogActions {

    Dialog oldTagsDialog, newTagsDialog;
    Button button;

    Context context;
    Activity activity;

    DialogActions(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
    }
    public void showOldTagsDialog(Context context, String appName, HashSet<String> newLabelsList)
    {
        oldTagsDialog = new Dialog(context);
        oldTagsDialog.setContentView(R.layout.labeldialog);
        RadioGroup group = (RadioGroup) oldTagsDialog.findViewById(R.id.radioGroupdId);
        group.clearCheck(); // to remove all previous radio buttons
        group.removeAllViews();
        RadioButton button;
        int cnt = 0;
        //HashSet<String> uniqueTags = new HashSet<String>(newLabelsList); // find unique tags and only display them.

//        for(Object eachLabel : newLabelsList){
//            String temp = eachLabel.toString();
//           // uniqueTags.add(temp);
//            Log.i("added in hashset", eachLabel.toString());
//        }

        Log.i("length= ", String.valueOf(newLabelsList.size()));

        for(Object eachLabel : newLabelsList){
            Log.i("should be unique",eachLabel.toString());
            button = new RadioButton(context);
            button.setText(eachLabel.toString());
          //  button.setId(Integer.parseInt("radioButton"+cnt));
            group.addView(button);
            cnt++;
        }

//        for(int i = 0; i < 3; i++) {
//            button = new RadioButton(context);
//            button.setText("Button " + i);
//            group.addView(button);
//        }
        oldTagsDialog.setTitle(appName);
        oldTagsDialog.show();
        //context = context;
    }
    public void showNewTagDialog(Context context, String appName)
    {
        newTagsDialog = new Dialog(context);
        newTagsDialog.setContentView(R.layout.newlabledialog);
        newTagsDialog.setTitle(appName);
        newTagsDialog.show();
        //context = context;
    }

    View getEditTextid()
    {
       return  newTagsDialog.findViewById(R.id.newTagText);
    }
    View getOldDialogButtonId(){
        return oldTagsDialog.findViewById(R.id.OldConfirmButton);
    }
    View getNewDialogButtonId(){
        return newTagsDialog.findViewById(R.id.newConfirmButton);
    }

    View getRadioGroupId(){
        return oldTagsDialog.findViewById(R.id.radioGroupdId);
    }

    String checkedRadioButtonText(RadioGroup radioGroup)
    {
        return ((RadioButton)oldTagsDialog.findViewById(radioGroup.getCheckedRadioButtonId())).getText().toString();
    }

    void dismissOldDialog()
    {
        oldTagsDialog.dismiss();
    }

    void dismissNewDialog()
    {
        newTagsDialog.dismiss();
    }

//    void addRadioButton(String radioButtonName, Context context){
//       // RadioGroup radioGroup = new RadioGroup(context);
//        radioGroup = (RadioGroup)oldTagsDialog.findViewById(R.id.radioGroupdId);
//        RadioButton radioButton = new RadioButton(context);
//
//        radioButton.setText(radioButtonName);
//        radioGroup.addView(radioButton);
//        //radioButton.setId("");
//
//
//    }
    


}
