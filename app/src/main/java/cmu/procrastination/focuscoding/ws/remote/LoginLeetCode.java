package cmu.procrastination.focuscoding.ws.remote;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import cmu.procrastination.focuscoding.entities.Task;
import cmu.procrastination.focuscoding.entities.User;
import cmu.procrastination.focuscoding.ui.UI_MainActivity;

/**
 * Created by xuxiaoyang on 4/28/16.
 */
public class LoginLeetCode implements Runnable {
    private static final String DEFAULT_URL = "http://128.237.182.19:8080/FocusCodingServer/LCServlet?";
    public static String response = "";
    private Activity activity;
    private View view;
    private String username;
    private String password;
    public LoginLeetCode(Activity activity, View view, String username, String password) {
        this.activity = activity;
        this.view = view;
        this.username = username;
        this.password = password;
    }
    public boolean login(String username, String password) {
        HttpURLConnection conn = null;
        String data = "username=" + URLEncoder.encode(username) + "&pwd="+ URLEncoder.encode(password);
        String url = DEFAULT_URL+ data;
        try {
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String line = null;
                while((line = br.readLine())!=null) {
                    response = line;
                }
                if (Integer.parseInt(response) == -1) {
                    Log.i("WRONG PASSWORD", "LOGIN FAILED" + line);
                    return false;
                }
            } else {
                Log.i("RESPONSE", "LOGIN FAILED" + responseCode);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return true;
    }
//    public int getACCount() {
//        return Integer.parseInt(response);
//    }
    public void run() {
        if (login(username, password)) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, "Login success!", Toast.LENGTH_SHORT).show();
                }
            });
            User curUser = new User();
            curUser.setMyAccount("default");
            curUser.setMyLCname(username);
            curUser.setMyLCpwd(password);
            curUser.setMyTotal(new GetACNum(activity, view).getACCount());
            // 2 to do by default:
            curUser.setMyTask(new Task(curUser, curUser.getMyTotal()));
            // 0 progress at first
            curUser.setMyProgress(0);

            Intent intent = new Intent(activity, UI_MainActivity.class);
            intent.putExtra("curUser", curUser);
            activity.startActivity(intent);
        } else {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(activity, "Login fail!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
