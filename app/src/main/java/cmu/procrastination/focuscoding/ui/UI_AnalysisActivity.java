package cmu.procrastination.focuscoding.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cmu.procrastination.focuscoding.R;

public class UI_AnalysisActivity extends AppCompatActivity {
    public ImageView ivLocation;
    public EditText etMPSDate;
    public EditText etEFT;
    public Button bBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        //initialize the components instance variables

        ivLocation = (ImageView)findViewById(R.id.ivLocation);
        etMPSDate = (EditText)findViewById(R.id.etMPSdate);
        etEFT = ( EditText)findViewById(R.id.etETF);
        bBack = (Button)findViewById(R.id.bBack);

        //read contents from the input as required

        String date = etMPSDate.getText().toString();
        String eft = etEFT.getText().toString();
    }

    /**
     * Go back to Settings page
     * @param view v
     */
    public void onBack(View view){
        Intent intent = new Intent(this, UI_SettingActivity.class);
        startActivity(intent);
    }

}
