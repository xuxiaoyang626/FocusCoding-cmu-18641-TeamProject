package cmu.procrastination.focuscoding.entities;

import java.io.Serializable;

/**
 * Created by ximengw on 4/13/2016.
 *
 * The running task, interacts with its User and creates a Supervisor for itself.
 */
public class Task implements Serializable{

    public User getUser() {
        return user;
    }

    private User user;

    private int goal;

    public Task(User u, int total){
        user = u;
        goal = total;
    }

    public int getGoal() {
        return goal;
    }

}
