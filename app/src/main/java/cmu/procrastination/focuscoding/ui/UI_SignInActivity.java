package cmu.procrastination.focuscoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cmu.procrastination.focuscoding.R;
import cmu.procrastination.focuscoding.entities.User;
import cmu.procrastination.focuscoding.ws.remote.AccountServices;

public class UI_SignInActivity extends AppCompatActivity{
    public EditText etUserName;
    public EditText etPassword;
    public EditText etEmail;
    public Button bSignIn;
    public Button bLinkIn;

    /**
     * User is created once log in is successful.
     * Get LC information / user progress from the server.
     */
    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //TODO for now to connect
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        //initialize the components instance variables

        etUserName = (EditText)findViewById(R.id.etUsr);
        etPassword = (EditText)findViewById(R.id.etPsw);

        bSignIn = (Button)findViewById(R.id.bSignIn);
        bLinkIn = (Button)findViewById(R.id.bLinkLeet);

        curUser = new User();
    }
    /**
     * On sign in:  verify with the server
     *
     * If valid, go to main page
     * @param view v
     */
    public void onSignIn(View view){

        String username = "";
        String pwd = "";

        EditText userText = (EditText) findViewById(R.id.etUsr);
        EditText pwdText = (EditText) findViewById(R.id.etPsw);

        if(userText!=null)
            username = userText.getText().toString();
        if(pwdText!=null)
            pwd = pwdText.getText().toString();

        //call verification function
        boolean result = doAuthentication(username, pwd);

        //if invalid
        if(!result){
            Toast.makeText(this, "Invalid credentials!",  Toast.LENGTH_LONG).show();
            return;
        }

        //Transfer the User information, and then start Main
        Intent intent = new Intent(this, UI_MainActivity.class);

        /**
         * Put serializable User object
         */
        intent.putExtra("curUser", curUser);
        startActivity(intent);
    }

    /**
     * Authenticate the user credentials with the server.
     * Also, retrieve user information / LC info / progress and store in User.
     *
     * @param name user
     * @param pwd  pwd
     * @return log in successful?
     */
    public boolean doAuthentication(String name, String pwd) {

        try {
            //connect to the local server via HTTP
            String queryUrl = AccountServices.loginAddr + "?username=" + name + "&pwd=" + pwd;

            URL url = new URL(queryUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
         //   urlConn.setConnectTimeout(6*1000);

            if (urlConn.getResponseCode() != 200)
                return false;

            InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buffer = new BufferedReader(in);
            String inputLine =  buffer.readLine();

            //check the returned result
            if(!inputLine.startsWith("Success")){
                return false;
            }

            //if success, also set the User with LC information / progress
            String line = buffer.readLine();
            String [] words = line.split(",");
            curUser.setMyAccount(name);
            curUser.setMyLCname(words[0]);
            curUser.setMyLCpwd(words[1]);
            curUser.setMyProgress(Integer.parseInt(words[2]));

            in.close();
            urlConn.disconnect();

        } catch (Exception e) {
            //TODO connection exception here

            e.printStackTrace();
        }

        return true;
    }


    public void onSignUp(View view){

        Intent intent = new Intent(this, UI_SignUpActivity.class);
        startActivity(intent);
    }


    public void onLinkLeet(View view) {
        Intent intent = new Intent(this, UI_LinkinActivity.class);
        startActivity(intent);
    }

}
