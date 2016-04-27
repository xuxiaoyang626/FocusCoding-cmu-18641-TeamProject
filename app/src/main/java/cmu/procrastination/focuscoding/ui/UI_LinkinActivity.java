package cmu.procrastination.focuscoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cmu.procrastination.focuscoding.exception.ExceptionHandler;
import cmu.procrastination.focuscoding.R;

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
        leetUser = etLeetUsr.getText().toString();
        leetPsw = etLeetPsw.getText().toString();
    }

    public void onLogIn(View view){
        if (leetUser == null || leetPsw == null) {
            new ExceptionHandler().messageBox(this,"INPUT ERROR", "ERROR: USERNAME OR PASSWORD CANNOT BE BLANK!");
            return;
        }

        Intent intent = new Intent(this, UI_MainActivity.class);
        startActivity(intent);
    }

}
