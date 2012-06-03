package com.kurt.bleeptalk;

import com.kurt.bleeptalk.activities.MyMapActivity;
import com.kurt.bleeptalk.activities.PlaceSearch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class BleeptalkActivity extends Activity {
	
	TextView tv; 
	
	//LocationManager locMgr;// = (LocationManager) getSystemService(LOCATION_SERVICE);
	//Location recentLoc;// = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	
	//LocationListener locationListener; // = new LocationListener() {
        
	/** ActionsBar */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i("BleeptalkActivity", "hello onCreateOptionsMenu");
		MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        // If the device doesn't support camera, remove the camera menu item
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
//            /menu.removeItem(R.id.menu_camera);
        }
        return true;
	}
	
	@Override 
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tv = new TextView(this);
        tv.setMovementMethod(new ScrollingMovementMethod());
        
        //initLocation();
        //listeners();
        
        //locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        
        //fire up map
        //Intent intent = new Intent(this, MyMapActivity.class);
        //startActivity(intent);
        
        //fire up search
        Intent intent = new Intent(this, PlaceSearch.class);
        startActivity(intent);
    }
    /*
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
	*/
}