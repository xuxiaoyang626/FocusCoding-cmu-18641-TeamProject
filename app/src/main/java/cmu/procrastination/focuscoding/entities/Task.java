package cmu.procrastination.focuscoding.entities;

/**
 * Created by ximengw on 4/13/2016.
 *
 * The running task, interacts with its User and creates a Supervisor for itself.
 */
public class Task {

    private User user;
    private Supervisor supervisor;

    private int totalNum;
    private int curProgress;

    public Task(User u, int total){
        user = u;
        totalNum = total;

        supervisor = new Supervisor(this);
    }


    public int getCurProgress() {
        return curProgress;
    }

    public void setCurProgress(int curProgress) {
        this.curProgress = curProgress;
    }
}
