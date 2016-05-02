package cmu.procrastination.focuscoding.ui;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Calendar;
import cmu.procrastination.focuscoding.R;
import cmu.procrastination.focuscoding.entities.User;
import cmu.procrastination.focuscoding.notification.AlarmReceiver;
import cmu.procrastination.focuscoding.entities.Task;
import cmu.procrastination.focuscoding.exception.ExceptionHandler;

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

        //pass the User:
        User curUser = (User) getIntent().getSerializableExtra("curUser");
        intent.putExtra("curUser", curUser);

        startActivity(intent);
    }

    /**
     * Save state and go to the Main page
     * @param view v
     */
    public void onSave(View view){
        int hour, minute, second;
        if (edProblemNo == null) {
            new ExceptionHandler().messageBox(this,"INPUT ERROR", "ERROR: PROBLEM NUMBER CANNOT BE BLANK!");
            return;
        }


        String[] times = remindTime.split(":");
        hour = Integer.valueOf(times[0]);
        minute = Integer.valueOf(times[1]);
        second = Integer.valueOf(times[2]);
        setUpNotification(hour, minute, second);

        Intent intent = new Intent(this, UI_MainActivity.class);

        //set the task goal for the current User:
        User curUser = (User) getIntent().getSerializableExtra("curUser");

        EditText goalEdit = (EditText) findViewById(R.id.etProblemNo);
        int goal = 2;   //default
        if(goalEdit!=null && !goalEdit.getText().toString().equals("")){

            int tmp = Integer.parseInt(goalEdit.getText().toString());
            if(tmp<=0) {
                new ExceptionHandler().messageBox(this, "INPUT ERROR", "Goal must be more than 0!");
                return;
            }

            goal = tmp;
        } else {
            new ExceptionHandler().messageBox(this,"INPUT ERROR", "ERROR: PROBLEM NUMBER CANNOT BE BLANK!");
            return;
        }

        curUser.setMyTask(new Task(curUser, curUser.getMyTotal()+goal));
        intent.putExtra("curUser", curUser);
        startActivity(intent);
    }
    public void setUpNotification(int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        Intent intent1 = new Intent(UI_SettingActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(UI_SettingActivity.this, 100,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) UI_SettingActivity.this.getSystemService(UI_SettingActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        //new ExceptionHandler().messageBox(this, "TIME FOR NOTIFICATION" ,"HOUR: " + hour + "MINUTE: " + minute + "SECOND: " + second);
    }

}
