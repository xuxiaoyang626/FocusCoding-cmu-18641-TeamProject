package cmu.procrastination.focuscoding.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by icywang on 16/4/13.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper{
    // public constructor
    public DatabaseOpenHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    } // end DatabaseOpenHelper constructor

    // creates the contacts table when the database is created
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // query to create a new table named contacts
        String createQuery = "CREATE TABLE personInfo" +
                "(_id integer primary key AUTOINCREMENT, username TEXT" +
                "password TEXT, leetUser TEXT, leetPass TEXT);";

        db.execSQL(createQuery); // execute the query
        createQuery = "CREATE TABLE record" +
                "(_id integer primary key AUTOINCREMENT, userId integer NOT NULL," +
                " goal integer, actual integer, startTime TEXT, endTime TEXT, date TEXT, location TEXT)";
        db.execSQL(createQuery);
    } // end method onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion)
    {
    } // end method onUpgrade
} // end class DatabaseOpenHelper

