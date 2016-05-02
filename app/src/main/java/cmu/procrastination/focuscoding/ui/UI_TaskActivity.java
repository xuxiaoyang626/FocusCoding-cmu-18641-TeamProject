package cmu.procrastination.focuscoding.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cmu.procrastination.focuscoding.R;
import cmu.procrastination.focuscoding.entities.Supervisor;
import cmu.procrastination.focuscoding.entities.Task;
import cmu.procrastination.focuscoding.entities.User;
import cmu.procrastination.focuscoding.ws.local.BlockFacebookAccess;
import cmu.procrastination.focuscoding.ws.local.LocationServices;
import cmu.procrastination.focuscoding.ws.remote.AccountServices;

/**
 * @author Team Procrastination
 *
 * Displays the Task Page and interacts with User and Entities
 */
public class UI_TaskActivity extends AppCompatActivity {
    public Button testButton;
    public TextView tvCount;
    public TextView tvTotal;
    public Chronometer chronometer;

    //The current User
    private User curUser;
    //Current Task
    private Task task;
    //A Supervisor is created
    private Supervisor supervisor;

    long elapsedTime=0;
    String currentTime="";
    long startTime=SystemClock.elapsedRealtime();
    Boolean resume=false;

    String curLocation;

    @Override
    /**
     * On create: Initialize necessary components and a new Supervisor
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        /**
         * Floating button: enables the user to randomly pick up a Leetcode problem
         * Redirect to Pick-One page
         */
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if(fab!=null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(fab.getContext(), UI_PickOneProblem.class);
                    startActivity(intent);
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

        //get the current User from Main page:
        curUser = (User) getIntent().getSerializableExtra("curUser");
        task = curUser.getMyTask();
        supervisor = new Supervisor(task);

        //Start the location services to get updated locations every 30 min
        startService(new Intent(UI_TaskActivity.this, LocationServices.class));

        //Register a receiver to receive the location broadcasts
        MyReceiver receiver=new MyReceiver();

        IntentFilter filter=new IntentFilter();
        filter.addAction("cmu.procrastination.focuscoding.ws.local.LocationServices");
        UI_TaskActivity.this.registerReceiver(receiver, filter);

        //provide a call back
        receiver.setCurTask(this);

        //Display the progress info:
        String ts = "" + (curUser.getMyTask().getGoal()-curUser.getMyTotal());
        tvTotal.setText(ts);
        curLocation = "CMU";
    }

    /**
     * @author ximengw
     *
     * Upon receiving an updated location:
     * send a notification record to server and update the current location
     */
    private class MyReceiver extends BroadcastReceiver {

        /**
         * Needs the current context to update locations
         */
        private UI_TaskActivity curTask;

        @Override
        /**
         * On receive: update the Task's location and call the record function
         */
        public void onReceive(Context context, Intent intent) {
            Bundle bundle=intent.getExtras();

            //System.out.println(locationMsg);
            curTask.curLocation =  bundle.getString("curLocation");

        }

        public void setCurTask(UI_TaskActivity task){
            curTask = task;
        }

    }


    /**
     * On test: call the Supervisor to examine the User's progress
     * If success, unblock the web access limitations.
     *
     * @param view view
     */
    public void onTest(View view){


        boolean result = supervisor.doExamine();

        //Verify the result and remove the Facebook restriction
        if(!result) {
            Toast.makeText(this, "You have not accomplished your task!", Toast.LENGTH_LONG).show();
            return;
        }

        BlockFacebookAccess bfa = new BlockFacebookAccess();
        bfa.removeLimitation();

        //Also, record the current achievements:
        recordAchievement(curLocation, ""+curUser.getMyTask().getGoal());

        //Then switch to the Home Page and pass updated User:
        Intent intent = new Intent(this, UI_MainActivity.class);

        curUser.setMyProgress(curUser.getMyTask().getGoal()-curUser.getMyTotal());
        intent.putExtra("curUser", curUser);

        startActivity(intent);
    }


    /**
     * Record the current progress to the server and DB
     * report: username, datetime, current location, progress
     *
     */
    protected void recordAchievement(String curLocation, String progress){

        //transform location string to send via URL:
        curLocation = curLocation.replaceAll("\\.", "x");

        try {
            //connect to the local server via HTTP
            String queryUrl = AccountServices.doRecordAddr + "?username=" + curUser.getMyAccount()
                    +"&progress=" + progress + "&location=" + curLocation;

            URL url = new URL(queryUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(10 * 1000);

            if (urlConn.getResponseCode() != 200) {
                //TODO exception
                throw new Exception("");
            }

            urlConn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
