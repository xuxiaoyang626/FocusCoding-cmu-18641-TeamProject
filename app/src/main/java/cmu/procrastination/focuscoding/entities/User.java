package cmu.procrastination.focuscoding.entities;

/**
 * Created by ximengw on 4/13/2016.
 *
 * Account-related class. Manages users and their activities.
 * Interacts with Tasks.
 */
public class User {

    private String myAccount;

    private Task myTask;
    private int myTotal;
    private int myProgress;

    public User(String account){
        myAccount = account;
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


}
