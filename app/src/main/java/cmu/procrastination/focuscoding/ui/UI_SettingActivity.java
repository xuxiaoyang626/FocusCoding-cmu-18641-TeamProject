package cmu.procrastination.focuscoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cmu.procrastination.focuscoding.entities.Task;
import cmu.procrastination.focuscoding.exception.ExceptionHandler;
import cmu.procrastination.focuscoding.R;

public class UI_SettingActivity extends AppCompatActivity {
    public EditText edProblemNo;
    public EditText edRemindTime;
    public Button bShwAnalysis;
    public Button bShwSave;
    String problemNo;
    String remindTime;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //initialize the components instance variables

        edProblemNo = (EditText)findViewById(R.id.etProblemNo);
        edRemindTime = (EditText)findViewById(R.id.etMPSdate);
        bShwAnalysis = (Button)findViewById(R.id.bShowAnalysis);
        bShwSave = (Button)findViewById(R.id.bSave);

        //read contents from the input as required

        problemNo = edProblemNo.getText().toString();
        remindTime = edRemindTime.getText().toString();
    }

    /**
     * Forward to the Analysis page
     * @param view v
     */
    public void onShowAnalysis(View view){
        Intent intent = new Intent(this, UI_AnalysisActivity.class);
        startActivity(intent);
    }

    /**
     * Save state and go to the Main page
     * @param view v
     */
    public void onSave(View view){

        if (problemNo == null) {
            new ExceptionHandler().messageBox(this,"INPUT ERROR", "ERROR: PROBLEM NUMBER CANNOT BE BLANK!");
            return;
        }

        //TODO save

        Intent intent = new Intent(this, UI_MainActivity.class);
        startActivity(intent);
    }

}
