package cmu.procrastination.focuscoding.ws.local;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Location service runs as an Android service since it needs to record an up-to-date location
 * whenever the user's location changes, in order to keep track of the user's progress
 */
public class LocationServices extends Service implements LocationListener {
    public LocationServices() {


    }

    public void onStart(Intent in, int startID){
        Intent intent=new Intent();

        intent.putExtra("curLocation", getLocation());
        intent.setAction("cmu.procrastination.focuscoding.ws.local.LocationServices");
        sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    /**
     * Provide a location string. Lati,Logi
     * @return string
     */
    private String getLocation(){

        String message = "";

        try {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

            //message = "My current location: Latitude ";
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            message += location.getLatitude();
            message += ",";

            message += location.getLongitude();


        } catch (SecurityException e){

            e.printStackTrace();
        }

        return message;
    }

    /**
     * Called when the location has changed.
     *
     * Broadcast a new location message to the Task activity. Task will record and send to server DB.
     *
     * @param location The new location, as a Location object.
     */
    @Override
    public void onLocationChanged(Location location) {

        Intent intent=new Intent();

        intent.putExtra("curLocation", getLocation());
        intent.setAction("cmu.procrastination.focuscoding.ws.local.LocationServices");
        sendBroadcast(intent);

    }

    /**
     * Called when the provider status changes. This method is called when
     * a provider is unable to fetch a location or if the provider has recently
     * become available after a period of unavailability.
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {

    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {

    }
}
