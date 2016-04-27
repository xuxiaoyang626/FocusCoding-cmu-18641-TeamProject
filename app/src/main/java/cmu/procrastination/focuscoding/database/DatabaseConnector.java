package cmu.procrastination.focuscoding.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UnknownFormatConversionException;

/**
 * Created by icywang on 16/4/13.
 */
public class DatabaseConnector {
    private static final String DATABASE_NAME = "FocusCodingDB";
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseOpenHelper;

    public DatabaseConnector(Context context) {
        databaseOpenHelper = new DatabaseOpenHelper(context, DATABASE_NAME,null,1);
    }

    public void open() throws SQLException {
        database = databaseOpenHelper.getWritableDatabase();
    }

    public void close() {
        if(database != null)
            database.close();
    }

    /**
     * INSERT PERSONAL INFORMATION INTO personInfo table
     * @param username
     * @param password
     * @param leetuser
     * @param leetpass
     */
    public void insertPersonInfo (String username, String password, String leetuser, String leetpass) {
        ContentValues record = new ContentValues();
        record.put("username",username);
        record.put("password",password);
        record.put("leetUser",leetuser);
        record.put("leetPass",leetpass);

        try {
            open();
        } catch (Exception E) {
            E.printStackTrace();
        }

        database.insert("personInfo",null,record);
        close();

    }

    /**
     *
     * @param userId
     * @param goal
     * @param actual
     * @param startTime
     * @param endTime
     * @param location
     */
    public void insertRecord (String userId, int goal, int actual, String startTime, String endTime, String date, String location) {
        ContentValues record = new ContentValues();
        record.put("userId",userId);
        record.put("goal",goal);
        record.put("actual",actual);
        record.put("startTime",startTime);
        record.put("endTime", endTime);
        record.put("date",date);
        record.put("location", location);

        try {
            open();
        } catch (Exception E) {
            E.printStackTrace();
        }

        database.insert("record",null,record);
        close();

    }

    /**
     *
     * @param id
     * @param endTime
     */
    public void updateRecordEndTime (int id, String endTime) {
        try {
            open();
        } catch (Exception E) {
            E.printStackTrace();
        }
        String updateQuery = "update record set endTime = " + endTime + "where _id = " + id + ";";
        database.execSQL(updateQuery);
        close();
    }

    /**
     *
     * @param userId
     * @param date
     * @return
     */
    public ArrayList<Integer> selectDateComplete (String userId, String date) {
        try {
            open();
        } catch (Exception E) {
            E.printStackTrace();
        }
        String selectQuery = "select autual from record where userId = " + userId + "and date = " + date + ";";
        Cursor c = database.rawQuery(selectQuery,null);
        ArrayList<Integer> amount = new ArrayList<Integer>();
        close();
        int i = 0;
        while(c.moveToNext()) {
            amount.add(c.getInt(i));
            i++;
        }
        c.close();
        return amount;
    }

    /**
     *
     * @param userId
     * @param location
     * @return
     */
    public int selectLocationComplete (String userId, String location) {
        try {
            open();
        } catch (Exception E) {
            E.printStackTrace();
        }
        String selectQuery = "select sum(autual) from record where userId = " + userId + ";";
        Cursor c = database.rawQuery(selectQuery, null);
        close();
        int amount = c.getInt(0);
        c.close();
        return amount;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param userId
     * @return
     */
    public int selectRecordsTime (String startDate, String endDate, String userId) {
        try {
            open();
        } catch (Exception E) {
            E.printStackTrace();
        }
        String selectQuery = "select sum(autual) from record where userId = " + userId +
                "and date between "+startDate+" and" + endDate +";";
        Cursor c = database.rawQuery(selectQuery, null);
        close();
        int amount = c.getInt(0);
        c.close();
        return amount;
    }

    /**
     *
     * @param userId
     * @return
     */
    public String selectEarliestDate(String userId) {
        try {
            open();
        } catch (Exception E) {
            E.printStackTrace();
        }
        String selectQuery = "select min(endTime),date from record where userId = " + userId + ";";
        Cursor c = database.rawQuery(selectQuery, null);
        close();
        String time = null;
        while (c.moveToNext()) {
            time += c.getString(0);
            time += c.getString(1);
        }
        return time;
    }



}
