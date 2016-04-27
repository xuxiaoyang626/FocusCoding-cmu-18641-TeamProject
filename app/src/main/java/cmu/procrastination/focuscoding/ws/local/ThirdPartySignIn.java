package cmu.procrastination.focuscoding.ws.local;

import cmu.procrastination.focuscoding.entities.User;

/**
 * Created by ximengw on 4/13/2016.
 *
 * Responsible for managing sign in/ sign up with third party APIs
 */

public interface ThirdPartySignIn {

    /**
     *
     * @param username
     * @param pwd
     * @return
     */
    public User signIn(String username, String pwd);

}
