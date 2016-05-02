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

    //TODO  change server address here!!!
    public static final String serverIP = "128.237.182.19";


    public static final String signUpAddr = "http://"+serverIP+":8080/FocusCodingServer/RegisterServlet";
    public static final String loginAddr= "http://"+serverIP+":8080/FocusCodingServer/LoginServlet";
    public static final String doLeetcodeAddr = "http://"+serverIP+":8080/FocusCodingServer/LCServlet";
    public static final String doRecordAddr = "http://"+serverIP+":8080/FocusCodingServer/RecordServlet";
    public static final String getStatsAddr = "http://"+serverIP+":8080/FocusCodingServer/StatsServlet";

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
    public boolean doAuthentication(String name, String pwd);


}
