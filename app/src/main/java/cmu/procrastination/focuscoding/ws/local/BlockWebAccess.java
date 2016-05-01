package cmu.procrastination.focuscoding.ws.local;

/**
 * Created by ximengw on 4/14/2016.
 *
 * Blocks web access to target websites (currently FB)
 * by imposing iptables rules to the system
 */
public interface BlockWebAccess {

    public void castLimitation();

    public void removeLimitation();

}
