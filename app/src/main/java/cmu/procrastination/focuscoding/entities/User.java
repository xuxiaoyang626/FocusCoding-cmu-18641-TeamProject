package cmu.procrastination.focuscoding.entities;

import java.io.Serializable;

/**
 * Created by ximengw on 4/13/2016.
 *
 * Account-related class. Manages users and their activities.
 * Interacts with Tasks.
 */
public class User implements Serializable{

    private String myAccount;

    private Task myTask;
    private int myTotal;
    private int myProgress;

    private String myLCname;
    private String myLCpwd;

    public User(){
        myTask = new Task(this, 0);
    }

    public Task getMyTask() {
        return myTask;
    }

    public void setMyTask(Task myTask) {
        this.myTask = myTask;
    }

    public int getMyTotal() {
        return myTotal;
    }

    public void setMyTotal(int myTotal) {
        this.myTotal = myTotal;
    }

    public int getMyProgress() {
        return myProgress;
    }

    public void setMyProgress(int myProgress) {
        this.myProgress = myProgress;
    }

    public String getMyLCname() {
        return myLCname;
    }

    public void setMyLCname(String myLCname) {
        this.myLCname = myLCname;
    }

    public String getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(String myAccount) {
        this.myAccount = myAccount;
    }

    public String getMyLCpwd() {
        return myLCpwd;
    }

    public void setMyLCpwd(String myLCpwd) {
        this.myLCpwd = myLCpwd;
    }

}
