package com.kurt.bleeptalk.activities;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.kurt.bleeptalk.R;
import com.kurt.bleeptalk.gps.MyLocationListener;

public class MyMapActivity extends MapActivity {

	MapView mapView;// = (MapView) findViewById(R.id.mapview);
	MapController mapController;
	
	//location stuff
	LocationManager locationManager; 
	LocationListener locationListener; 
	Location lastKnownLocation; 
	Double latitude,longitude;
	
	static final int DEFAULT_ZOOM_LEVEL = 17;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_map);
        
		//get ref to MapView
		mapView = (MapView) findViewById(R.id.mapview);
		//give it some controls
		mapView.setBuiltInZoomControls(true);
		
		mapController = mapView.getController();
		
		
		mapController.setZoom(DEFAULT_ZOOM_LEVEL);
		
		setupLocation();
		
		setMapPosition(getGeoPointFromLastLocation());
	}
	

	@Override
	public void onStart() {
		super.onStart();
		//setupLocation();
	}
	
	private GeoPoint getGeoPointFromLastLocation() {
		Location lastLoc = getLastKnownLocation();
		GeoPoint gp = coordinatesToGeoPoint(lastLoc.getLatitude(), lastLoc.getLongitude());//new GeoPoint(lastLoc.getLatitude(), lastLoc.getLongitude());
		return gp;
	}
	
	public static GeoPoint coordinatesToGeoPoint(double lat, double lgn) {
	    return new GeoPoint((int) (lat * 1E6), (int) (lgn * 1E6));
	}
	
	private Location getLastKnownLocation() {
		return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
	}
	
	private void setupLocation() {
		// Acquire a reference to the system Location Manager
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		      setMapPosition(coordinatesToGeoPoint(location.getLatitude(), location.getLongitude()));
		    }

		    public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}
		  };

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		
		/* old shit
		locationListener = new MyLocationListener();
		
		locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0, locationListener); 
		//locationProvider = LocationManager.GPS_PROVIDER;
		
		// Access the last identified location
		try {
			lastKnownLocation = getLastKnownLocation();	
		} catch(Error e) {
			Log.i("MyMapActivity", "last location failed");
		}
		*/
	}
	
	private void setMapPosition(GeoPoint gp) {
		mapController.animateTo(gp);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
