package cmu.procrastination.focuscoding.exception;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

import cmu.procrastination.focuscoding.entities.User;

/**
 * Created by xuxiaoyang on 4/14/16.
 */
public class ExceptionHandler {

    private User user;

    public void messageBox(Activity activity, String method, String message)
    {
        Log.d("EXCEPTION: " + method, message);

        AlertDialog.Builder messageBox = new AlertDialog.Builder(activity);
        messageBox.setTitle(method);
        messageBox.setMessage(message);
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", null);
        messageBox.show();
    }
}
