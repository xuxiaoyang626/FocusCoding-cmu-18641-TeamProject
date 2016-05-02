package cmu.procrastination.focuscoding.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cmu.procrastination.focuscoding.R;
import cmu.procrastination.focuscoding.exception.ExceptionHandler;
import cmu.procrastination.focuscoding.ws.remote.LoginLeetCode;

public class UI_LinkinActivity extends AppCompatActivity {
    public EditText etLeetUsr;
    public EditText etLeetPsw;
    String leetUser;
    String leetPsw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linkin);
        etLeetUsr = (EditText)findViewById(R.id.etLeetUsr);
        etLeetPsw = (EditText)findViewById(R.id.etLeetPsw);
    }

    public void onLogIn(View view){
        leetUser = etLeetUsr.getText().toString();
        leetPsw = etLeetPsw.getText().toString();
        if (leetUser.isEmpty() || leetPsw.isEmpty()) {
            new ExceptionHandler().messageBox(this,"INPUT ERROR", "ERROR: USERNAME OR PASSWORD CANNOT BE BLANK!");
            return;
        }
        try {
            LoginLeetCode login_thread = new LoginLeetCode(UI_LinkinActivity.this, view, leetUser, leetPsw);
            new Thread(login_thread).start();
        } catch(RuntimeException e) {
            e.getStackTrace();
        }

    }

}
