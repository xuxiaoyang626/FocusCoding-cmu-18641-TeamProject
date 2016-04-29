package cmu.procrastination.focuscoding.ui;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import cmu.procrastination.focuscoding.R;
import cmu.procrastination.focuscoding.entities.Supervisor;
import cmu.procrastination.focuscoding.entities.Task;
import cmu.procrastination.focuscoding.entities.User;
import cmu.procrastination.focuscoding.ws.remote.GetACNum;

public class UI_TaskActivity extends AppCompatActivity {
    public Button testButton;
    public TextView tvCount;
    public TextView tvTotal;
    public Chronometer chronometer;

    private User user;
    private Task task;
    private Supervisor supervisor;
    long elapsedTime=0;
    String currentTime="";
    long startTime=SystemClock.elapsedRealtime();
    Boolean resume=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab!=null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        //initialize the components instance variables

        testButton = (Button)findViewById(R.id.bTest);
        tvCount = (TextView)findViewById(R.id.tvCount);
        tvTotal = (TextView)findViewById(R.id.tvTotal);
        chronometer = (Chronometer)findViewById(R.id.chronometer1);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long minutes = ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000) / 60 % 60;
                long seconds = ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000) % 60;
                long hours = ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000) / 60 / 60;
                currentTime =hours + ":" + minutes + ":" + seconds;
                chronometer.setText(currentTime);
                elapsedTime = SystemClock.elapsedRealtime();
            }
        });

        //read contents from the input as required

        String count = tvCount.getText().toString();
        String total = tvTotal.getText().toString();

    }


    /**
     * TODO:
     *
     * For now go back to Main page
     * @param view v
     */
    public void onTest(View view){
        GetACNum acNum_thread = new GetACNum(UI_TaskActivity.this, view);
        new Thread(acNum_thread).start();
    }
}
