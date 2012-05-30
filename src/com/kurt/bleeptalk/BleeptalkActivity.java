package com.kurt.bleeptalk;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
public class BleeptalkActivity extends MapActivity {
	
	TextView tv; 
	
	LocationManager locMgr;// = (LocationManager) getSystemService(LOCATION_SERVICE);
	Location recentLoc;// = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	
	LocationListener locationListener; // = new LocationListener() {
        
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        tv = new TextView(this);
        tv.setMovementMethod(new ScrollingMovementMethod());
        
        
        initLocation();
        listeners();
        
        locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        
        MapView mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
    }
    
    private void listeners() {
    	//set up listeners
    	// Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                tv.setText("on location changed " + getRecentLocation().getLatitude() + " " + getRecentLocation().getLongitude() + "\n" + tv.getText());
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                tv.setText("status changed " + getRecentLocation().getLatitude() + " " + getRecentLocation().getLongitude() + "\n" + tv.getText());
            }

            public void onProviderEnabled(String provider) {
                tv.setText(tv.getText() + "\nonProviderEnabled");
            }

            public void onProviderDisabled(String provider) {
                tv.setText(tv.getText() + "\nonProviderDisabled");
            }
        };
    }
    
    private Location getRecentLocation() {
    	Location loc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    	return loc;
    }
    
    private void initLocation() {
    	try {
    		locMgr = (LocationManager) getSystemService(LOCATION_SERVICE);
    		recentLoc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    		//Log.i(DEBUG_TAG, "loc: " + recentLoc.toString());
    		//Log.i()
    		showLocation(recentLoc.getLatitude(),recentLoc.getLongitude());
    	} catch(Exception e) {
    			
    	}
    	
    	
    }
    
    private void showLocation(double lat, double lng) {
    	
        tv.setText("This is gay " + lat + " " + lng);
        setContentView(tv);
    }

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}