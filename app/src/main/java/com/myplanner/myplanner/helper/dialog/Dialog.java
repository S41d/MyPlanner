package com.myplanner.myplanner.helper.dialog;

import android.app.AlertDialog;
import android.content.Context;

import com.myplanner.myplanner.R;

public class Dialog {
    public static void showDialog(Context context, DialogType type, DialogSuccess success) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        switch (type) {
            case AJOUT:
                builder.setMessage(R.string.ajout_dialog);
                break;
            case SUPPRESSION:
                builder.setMessage(R.string.suppression_dialog);
                break;
            case MODIFICATION:
                builder.setMessage(R.string.modification_dialog);
                break;
            default:
                builder.setMessage("");
                break;
        }
        builder.setPositiveButton(R.string.oui, (dialog, id) -> success.successMethod());
        builder.setNegativeButton(R.string.non, (dialog, id) -> dialog.cancel());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
