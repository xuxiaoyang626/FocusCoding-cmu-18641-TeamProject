package cmu.procrastination.focuscoding.ui;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Calendar;

import cmu.procrastination.focuscoding.R;

/**
 * Display a page that allows user to shake the phone a pick up a random problem from Leetcode
 */
public class UI_PickOneProblem extends AppCompatActivity implements SensorEventListener {

    private int [] resources = {R.drawable.lc1, R.drawable.lc2, R.drawable.lc3, R.drawable.lc4};
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private int mX, mY, mZ;
    private long lasttimestamp = 0;
    Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_pick_one_problem);

        //Start a shaking detection:

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// TYPE_GRAVITY
        if (null == mSensorManager) {

            System.out.println("error!!");
        }

        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);// SENSOR_DELAY_GAME

    }


    /**
     *  When called, grab a random Leetcode problem content from the website.
     *
     */
    private void pickOne(){

        View myView = (View) findViewById(R.id.pickoneview);
        if (myView!=null){

            int rand = (int) (Math.random() * 4);

            myView.setBackgroundResource(resources[rand]);
        }

    }

    /**
     * Called when sensor values have changed.
     *
     * @param event the {@link SensorEvent SensorEvent}.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == null) {
            return;
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int x = (int) event.values[0];
            int y = (int) event.values[1];
            int z = (int) event.values[2];
            mCalendar = Calendar.getInstance();
            long stamp = mCalendar.getTimeInMillis() / 1000l;// 1393844912

            int second = mCalendar.get(Calendar.SECOND);// 53

            int px = Math.abs(mX - x);
            int py = Math.abs(mY - y);
            int pz = Math.abs(mZ - z);

            int maxvalue = getMaxValue(px, py, pz);
            if (maxvalue > 2 && (stamp - lasttimestamp) > 30) {
                lasttimestamp = stamp;

                //When moving is detected, change the problem.
                System.out.println("Moving detected!!!");
                pickOne();
            }

            mX = x;
            mY = y;
            mZ = z;

        }
    }

    public int getMaxValue(int px, int py, int pz) {
        int max = 0;
        if (px > py && px > pz) {
            max = px;
        } else if (py > px && py > pz) {
            max = py;
        } else if (pz > px && pz > py) {
            max = pz;
        }

        return max;
    }



    /**
     * Called when the accuracy of the registered sensor has changed.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
