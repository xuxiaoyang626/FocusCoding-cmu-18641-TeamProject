package cmu.procrastination.focuscoding.ws.remote;

import cmu.procrastination.focuscoding.entities.Task;
import cmu.procrastination.focuscoding.entities.User;

/**
 * Created by ximengw on 4/14/2016.
 *
 * Verifies User's Leetcode progress
 */
public interface LeetCodeVerification {

    public boolean verifyProgress(User u, Task t);

}
