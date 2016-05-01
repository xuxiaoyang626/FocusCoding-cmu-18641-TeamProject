package cmu.procrastination.focuscoding.ws.local;

import java.io.DataOutputStream;

/**
 * Created by ximengw on 4/27/2016.
 *
 * Implements blocking web access to Facebook
 */
public class BlockFacebookAccess implements BlockWebAccess {

    @Override
    /**
     * Input the iptables command, escalate to root first.
     */
    public void castLimitation() {

        String command = "iptables -A INPUT -p tcp --sport 443 -j DROP\n";

        try {
            Runtime runtime = Runtime.getRuntime();

            // Requires root access and controls the exec terminal with Process
            Process su = runtime.exec("su");

            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes(command);
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();

            su.waitFor();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    /**
     * Remove the rule by -D the same command
     */
    public void removeLimitation() {

        // -D the same command to remove
        String command = "iptables -D INPUT -p tcp --sport 443 -j DROP\n";

        try {
            Runtime runtime = Runtime.getRuntime();

            // Requires root access and controls the exec terminal with Process
            Process su = runtime.exec("su");

            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes(command);
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();

            su.waitFor();
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
}
