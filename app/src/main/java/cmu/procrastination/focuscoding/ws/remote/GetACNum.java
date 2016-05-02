package cmu.procrastination.focuscoding.ws.remote;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by xuxiaoyang on 4/29/16.
 */
public class GetACNum implements Runnable {
    private Activity activity = null;
    private View view = null;
    public GetACNum(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
    }

    public void run() {
        final int ac_count = getACCount();
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, "AC_COUNT" + ac_count, Toast.LENGTH_SHORT).show();
            }
        });
    }
    public int getACCount() {
        return Integer.parseInt(LoginLeetCode.response);
    }
}
