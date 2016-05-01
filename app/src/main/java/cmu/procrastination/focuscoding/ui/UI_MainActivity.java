package cmu.procrastination.focuscoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cmu.procrastination.focuscoding.R;
import cmu.procrastination.focuscoding.entities.User;

public class UI_MainActivity extends AppCompatActivity {
    public Button bStart;
    public TextView tvCount;
    public TextView tvTotal;
    public TextView tvSolvedPr;
    public TextView tvTotalPr;

    private User curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab!=null)
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onSettings();
                }
            });

        //initialize the components instance variables

        bStart = (Button)findViewById(R.id.bStart);
        tvCount = (TextView)findViewById(R.id.tvCount);
        tvTotal = (TextView)findViewById(R.id.tvTotal);
        tvSolvedPr = (TextView)findViewById(R.id.tvSoledPr);
        tvTotalPr = (TextView)findViewById(R.id.tvTotalPr);

        //read contents from the input as required
        String count = tvCount.getText().toString();
        String total = tvTotal.getText().toString();
        String solvedPr = tvSolvedPr.getText().toString();
        String totalPr = tvTotalPr.getText().toString();

        //Retrieve User object from Login activity:
        curUser = (User) getIntent().getSerializableExtra("curUser");

        //Display current progress:
        String pro = curUser.getMyProgress()+"";
        tvCount.setText(pro);

    }


    /**
     * On clicking start button: to the task page
     */
    public void onStart (View view){

        Intent intent = new Intent(this, UI_TaskActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Helper class for the FAB to forward to Settings
     */
    public void onSettings(){
        //go to Settings
        Intent intent = new Intent(getApplicationContext(), UI_SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
