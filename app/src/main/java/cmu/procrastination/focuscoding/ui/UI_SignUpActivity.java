package cmu.procrastination.focuscoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cmu.procrastination.focuscoding.R;
import cmu.procrastination.focuscoding.ws.remote.AccountServices;

public class UI_SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

    }

    /**
     * Do the sign up:
     * Validate locally and transfer to the server DB
     *
     * @param view view
     */
    public void doSignUp(View view){

        EditText nameEdit = (EditText) findViewById(R.id.etUsername);
        EditText pwdEdit = (EditText) findViewById(R.id.etPassword);
        EditText LCnameEdit = (EditText) findViewById(R.id.etLCUsername);
        EditText LCpwdEdit = (EditText) findViewById(R.id.etLCPassword);
        EditText emailEdit = (EditText) findViewById(R.id.etLCEmail);

        String name = null, pwd = null, LCname = null, LCpwd = null, email = null;
        if(nameEdit!=null)
            name = nameEdit.getText().toString();
        if(pwdEdit!=null)
            pwd = pwdEdit.getText().toString();
        if(LCnameEdit!=null)
            LCname = LCnameEdit.getText().toString();
        if(LCpwdEdit!=null)
            LCpwd = LCpwdEdit.getText().toString();
        if(emailEdit!=null)
            email = emailEdit.getText().toString();

        try{

            //connect to the local server via HTTP
            String queryUrl = AccountServices.signUpAddr + "?username="
                    + name + "&pwd=" + pwd +"&lcname="+LCname + "&lcpwd=" + LCpwd + "&email="+email;

            URL url = new URL(queryUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(6*1000);

            InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buffer = new BufferedReader(in);

            String inputLine =  buffer.readLine();

            in.close();
            urlConn.disconnect();

            if(!inputLine.startsWith("Success")){
                Toast.makeText(this, "Username exists!", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(this, "Registration complete! Please sign in!", Toast.LENGTH_LONG).show();
                //redirect to the sign in page
                Intent intent = new Intent(this, UI_SignInActivity.class);
                startActivity(intent);
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }


}
