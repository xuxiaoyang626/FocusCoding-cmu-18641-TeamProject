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

import cmu.procrastination.focuscoding.ui.UI_MainActivity;

/**
 * Created by xuxiaoyang on 4/28/16.
 */
public class LoginLeetCode implements Runnable {
    private static final String DEFAULT_URL = "http://10.0.2.2:8080/Servlet?";
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
        String data = "username=" + URLEncoder.encode(username) + "&password="+ URLEncoder.encode(password);
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
            Intent intent = new Intent(activity, UI_MainActivity.class);
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
