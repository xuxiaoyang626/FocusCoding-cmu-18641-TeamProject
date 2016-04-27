package cmu.procrastination.focuscoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cmu.procrastination.focuscoding.R;
import cmu.procrastination.focuscoding.entities.User;
import cmu.procrastination.focuscoding.ws.remote.AccountServices;

public class UI_SignInSignUpActivity extends AppCompatActivity implements AccountServices{
    public EditText etUserName;
    public EditText etPassword;
    public EditText etEmail;
    public Button bSignIn;
    public Button bLinkIn;
    String username;
    String password;
    String email;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_sign_up);

        //TODO for now
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        //initialize the components instance variables

        etUserName = (EditText)findViewById(R.id.etUsr);
        etPassword = (EditText)findViewById(R.id.etPsw);
        etEmail = (EditText)findViewById(R.id.etEmail);
        bSignIn = (Button)findViewById(R.id.bSignIn);
        bLinkIn = (Button)findViewById(R.id.bLinkLeet);


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
        boolean result = doLogIn(username, pwd);

        //if invalid
//        if(!result){
//            Toast.makeText(this, "Invalid credentials!",  Toast.LENGTH_LONG).show();
//            return;
//        }

        //else
        Intent intent = new Intent(this, UI_MainActivity.class);
        startActivity(intent);
    }

    /**
     * @param username name
     * @param pwd      password
     * @return sign-up result
     */
    @Override
    public boolean doSignUp(String username, String pwd) {
        return false;
    }

    /**
     * @param name user
     * @param pwd  pwd
     * @return log in successful?
     */
    @Override
    public boolean doLogIn(String name, String pwd) {

        try {
            EditText nameText = (EditText) findViewById(R.id.etUsr);
            EditText pwdTest = (EditText) findViewById(R.id.etPsw);

            //connect to the local server via HTTP
            String queryUrl = AccountServices.serverAddr + "?username="
                    + nameText.getText().toString() + "&pwd=" + pwdTest.getText().toString();

            URL url = new URL(queryUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(6*1000);

            if (urlConn.getResponseCode() != 200)
                return false;

            InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buffer = new BufferedReader(in);

            String inputLine =  buffer.readLine();

            in.close();
            urlConn.disconnect();


            //check the returned result
            if(inputLine.startsWith("Success")){
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }


    /**
     * @param leetCodeName lc username
     * @param pwd          lc pwd
     * @return link result
     */
    @Override
    public boolean linkLeetCode(String leetCodeName, String pwd) {
        return false;
    }
    public void onLinkLeet(View view) {
        Intent intent = new Intent(this, UI_LinkinActivity.class);
        startActivity(intent);
    }

}
