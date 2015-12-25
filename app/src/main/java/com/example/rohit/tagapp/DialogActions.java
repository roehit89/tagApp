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

    Dialog oldTagsDialog, newTagsDialog;
    Button button;

    Context context;
    Activity activity;

    DialogActions(Activity activity, Context context){
        this.context = context;
        this.activity = activity;
    }
    public void showOldTagsDialog(Context context, String appName)
    {
        oldTagsDialog = new Dialog(context);
        oldTagsDialog.setContentView(R.layout.labeldialog);
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

    


}
