package cmu.procrastination.focuscoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cmu.procrastination.focuscoding.R;
import cmu.procrastination.focuscoding.entities.User;
import cmu.procrastination.focuscoding.ws.remote.AccountServices;

public class UI_AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        //initialize the components instance variables
        TextView tv = (TextView) findViewById(R.id.bestLocation);
        String title = tv.getText().toString();

        //Retrieve the current favorite location from server:
        User curUser = (User) getIntent().getSerializableExtra("curUser");
        String username = curUser.getMyAccount();
        try {
            //connect to the local server via HTTP
            String queryUrl = AccountServices.getStatsAddr + "?username=" + username;

            URL url = new URL(queryUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();

            if (urlConn.getResponseCode() != 200) {
                throw new Exception("");
            }
            InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buffer = new BufferedReader(in);
            String inputLine =  buffer.readLine();

            title += inputLine;
            tv.setText(title);

            urlConn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Go back to Settings page
     * @param view view
     */
    public void onGoBack(View view){
        Intent intent = new Intent(this, UI_SettingActivity.class);

        User curUser = (User) getIntent().getSerializableExtra("curUser");
        intent.putExtra("curUser", curUser);

        startActivity(intent);
    }

}
