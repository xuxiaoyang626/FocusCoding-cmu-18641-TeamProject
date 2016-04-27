package cmu.procrastination.focuscoding.ws.remote;

/**
 * Created by ximengw on 4/13/2016.
 *
 * Manage account services provided by remote server, including:
 *
 * Sign up
 * Log in
 * Leetcode Info
 */
public interface AccountServices {

    //TODO  change server address here
    public static final String serverAddr = "http://192.168.1.152:8080/FocusCodeServer/AccountServlet";

    /**
     * @param username name
     * @param pwd password
     * @return sign-up result
     */
    public boolean doSignUp(String username, String pwd);

    /**
     *
     * @param name user
     * @param pwd pwd
     * @return log in successful?
     */
    public boolean doLogIn(String name, String pwd);

    /**
     *
     * @param leetCodeName lc username
     * @param pwd lc pwd
     * @return link result
     */
    public boolean linkLeetCode(String leetCodeName, String pwd);

}
