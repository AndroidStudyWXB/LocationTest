package com.study.locationtest;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    private TextView positionTextView;
    private LocationManager locationManager;
    private String provider;
    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(true);
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria,true));

            if (location != null) {
                showLocation(location);
            }
        } else {
            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                }
                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }
                @Override
                public void onProviderEnabled(String s) {
                }
                @Override
                public void onProviderDisabled(String s) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                showLocation(location);
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if(locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    private void showLocation(Location location) {
        Toast.makeText(this, "GPS", Toast.LENGTH_LONG).show();
        String currentPosition = "latitude is " + location.getLatitude() + "\n"
                + "longitude is " + location.getLongitude();
        positionTextView.setText(currentPosition);
    }
}
