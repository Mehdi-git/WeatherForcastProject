package com.example.weatherforcastproject.utils;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

import com.example.weatherforcastproject.R;

public class GpsTracker extends Service implements LocationListener {

   private Context context;
   private LocationManager locationManager;
   private Location location;
   private double latitude;
   private double longitude;
   private boolean isGpsEnabled = false;
   private boolean isNetworkEnabled = false;
   private boolean canGetLocation = false;
   public static final int Time_BW_Update = 1000 * 60  * 1; //1 hour
   public static final int Min_Distance_For_Update = 1000 * 1; //1 kilometer



    public GpsTracker (Context context){
        this.context = context;
        getLocation();
    }


    public Location getLocation() throws SecurityException{
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if(!isNetworkEnabled && !isGpsEnabled){
            //Providers not enabled
        }
        else {
            canGetLocation = true;
            //get location using network
            if(isNetworkEnabled){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        Time_BW_Update,
                        Min_Distance_For_Update,
                        this);
                if(locationManager!=null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if(location!=null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();


                    }
                }
            }
            if(location==null&& isGpsEnabled){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        Time_BW_Update,
                        Min_Distance_For_Update,
                        this);
                if(locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if(location!=null){
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                    }
                }
            }
        }
        return location;
    }

    public double getLatitude() {
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude() {
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    public boolean canGetLocation() {
        return canGetLocation;
    }

    public void showGpsAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.GPS)
                .setMessage(R.string.GPS_is_not_enabled)
                .setPositiveButton(R.string.setting, (dialog, which) -> {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);


                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                    dialog.cancel();

                });
        builder.show();

    }
    public void stopUsingGps(){
        if(locationManager != null){
            locationManager.removeUpdates(this);
        }
    }





    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
