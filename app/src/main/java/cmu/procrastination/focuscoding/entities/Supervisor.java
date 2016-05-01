package cmu.procrastination.focuscoding.entities;

import java.io.Serializable;

/**
 * Created by ximengw on 4/13/2016.
 *
 * Supervisor: responsible for accepting task verification requests,
 * process the verification and send out results
 */
public class Supervisor implements Serializable{

    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Supervisor(Task t){
        task = t;
    }

}
