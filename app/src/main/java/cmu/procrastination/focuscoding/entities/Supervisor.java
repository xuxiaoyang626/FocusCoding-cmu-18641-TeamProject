package cmu.procrastination.focuscoding.entities;

import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import cmu.procrastination.focuscoding.ws.remote.AccountServices;

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

    /**
     * Perform a test of the User's progress. If the User's goal is reached, return true.
     * @return result
     */
    public boolean doExamine(){

        try {
            //connect to the local server via HTTP
            String queryUrl = AccountServices.doLeetcodeAddr + "?username=" + task.getUser().getMyLCname() + "&pwd=" + task.getUser().getMyLCpwd();

            URL url = new URL(queryUrl);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setConnectTimeout(10*1000);

            if (urlConn.getResponseCode() != 200)
                return false;

            InputStreamReader in = new InputStreamReader(urlConn.getInputStream());
            BufferedReader buffer = new BufferedReader(in);
            String inputLine =  buffer.readLine();

            int progress = Integer.parseInt(inputLine);

            in.close();
            urlConn.disconnect();

            //if the goal is reached:
            if(progress>=task.getGoal()){
                return true;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }

}
