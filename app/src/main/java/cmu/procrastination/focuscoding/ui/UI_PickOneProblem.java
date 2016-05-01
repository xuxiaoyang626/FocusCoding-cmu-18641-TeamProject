package cmu.procrastination.focuscoding.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cmu.procrastination.focuscoding.R;
import cmu.procrastination.focuscoding.ws.local.ShakeDetector;

/**
 * Display a page that allows user to shake the phone a pick up a random problem from Leetcode
 */
public class UI_PickOneProblem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_pick_one_problem);

        //Start a shaking detection:
        ShakeDetector sd = new ShakeDetector(this);
        sd.registerOnShakeListener(
                new ShakeDetector.OnShakeListener(){
                    public void onShake()
                    {
                        //on shake: call the pickOne() and display a random question
                        pickOne();
                    }
        });

    }

    /**
     * TODO When called, grab a random Leetcode problem content from the website.
     */
    private void pickOne(){

    }

}
