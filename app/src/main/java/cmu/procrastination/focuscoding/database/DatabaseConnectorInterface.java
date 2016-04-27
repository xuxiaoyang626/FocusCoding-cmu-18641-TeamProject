package cmu.procrastination.focuscoding.database;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by icywang on 16/4/13.
 */
public interface DatabaseConnectorInterface {
    //store personal information into personInfo when sign up
    public void insertPersonInfo (String username, String password, String leetuser, String leetpass);
    //store record into record when get start
    public void insertRecord (String userId, int goal, int actual, String startTime, String endTime, String date, String location);
    //update record after the user finished
    public void updateRecordEndTime (int id, String endTime);
    //find the number of problems user solve on a specific date
    public ArrayList<Integer> selectDateComplete (String userId, String date);
    //find the number of problems user solve in a specific location
    public int selectLocationComplete (String userId, String location);
    //find the number of problems user solve during a specific time
    public int selectRecordsTime (String startDate, String endDate, String userId);
    //find earliest date user solve the problems
    public String selectEarliestDate(String userId);
}
