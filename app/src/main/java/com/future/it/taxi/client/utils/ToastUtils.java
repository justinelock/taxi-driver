package com.future.it.taxi.client.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.IntDef;
import androidx.appcompat.app.AlertDialog;

import com.future.it.taxi.client.App;


/**
 * Created by altaf.sil on 12/12/17.
 */

public class ToastUtils {

    public static Toast mToast;
    /*public static void shortToast(@StringRes int text) {
        shortToast(App.getInstance().getString(text));
    }*/

    public static void shortToast(String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /*public static void longToast(@StringRes int text) {
        longToast(App.getInstance().getString(text));
    }*/

    public static void longToast(String text) {
        show(text, Toast.LENGTH_LONG);
    }

    private static Toast makeToast(String text, @ToastLength int length) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getInstance(), text, length);
        } else {
            mToast.setText(text);
        }
        return mToast;
    }

    private static void show(String text, @ToastLength int length) {
        makeToast(text, length).show();
    }

    @IntDef({ Toast.LENGTH_LONG, Toast.LENGTH_SHORT })
    private @interface ToastLength {

    }


    public static void displayConfirmationPopupForWorkPlan(final Activity activity){
        final AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setCancelable(false);
        // alert.setTitle(getString(R.string.logout));
        alert.setMessage(StringConstants.WITHOUT_SAVE_WP_CONF_MSG);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
                //WPUtils.IS_CHANGED = false;
                activity.onBackPressed();

            }
        });

        alert.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
        alert.show();
    }
}
