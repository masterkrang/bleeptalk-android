package com.kurt.bleeptalk.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kurt.bleeptalk.R;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import libs.android-utils.com.kurt.utils.FoursquareVO;

public class PlaceSearch extends ListActivity {

	String url = "https://api.foursquare.com/v2/venues/suggestCompletion?";  
    HttpResponse response;  
    String deviceId = "xxxxx";  
    final String tag = "PlaceSearch: "; 
    List<NameValuePair> params = new LinkedList<NameValuePair>();
    
    final String CLIENT_ID = "2RIEFGJ11QYCMK1KW2RECDINMBWEL2IMUWYUC4MGGYGQQBDL";
    final String CLIENT_SECRET = "02SA1F4ZLE3SRFJUM0RKLGJRVWS43M1EAG4DYZEFP1SFISU3";
    
    //list
    ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
    
    final ListView lv = getListView();
    
    ArrayAdapter adapter;
    
    FoursquareVO fs_data[];
    

	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        
        Log.i(tag, "onCreate called");
        
        final EditText edittext = (EditText) findViewById(R.id.edittext);
        TextWatcher watcher = new TextWatcher() {
    		
    	    @Override
    	    public void onTextChanged(CharSequence charsequence, int i, int j, int k) {
    	        // TODO Auto-generated method stub
    	    	Toast.makeText(PlaceSearch.this, edittext.getText(), Toast.LENGTH_SHORT).show();
    	    	Log.i(tag, "onTextChanged = " + edittext.getText());
    	    	if (edittext.getText().length() > 2) {
    	    		Log.i(tag, "text.length > 2");
    	    		callWebService(edittext.getText().toString());
    	    	}
    	    }
    	    
    	    @Override
    	    public void beforeTextChanged(CharSequence charsequence, int i, int j,  int k) {
    	        // TODO Auto-generated method stub
    	    }
    	    
    	    @Override
    	    public void afterTextChanged(Editable editable) {
    	        // TODO Auto-generated method stub
    	    }    
    	};
        
        //setupInput();
        
        edittext.addTextChangedListener(watcher);
        
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		@SuppressWarnings("unchecked")
        		HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(position);
        		Toast.makeText(PlaceSearch.this, "ID '" + o.get("id") + "' was clicked.", Toast.LENGTH_SHORT).show(); 

        	}
        });
        
        adapter = new ArrayAdapter(this, mylist , R.layout.search,
                new String[] { "venue_id", "name" },
                new int[] { R.id.venue_id, R.id.venue_name });

        setListAdapter(adapter);

        
        
        /*
        edittext.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				Toast.makeText(PlaceSearch.this, edittext.getText(), Toast.LENGTH_SHORT).show();
		        //if ((event.getAction() == KeyEvent.ACTION_DOWN)) { // &&
		            //(keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		          //Toast.makeText(PlaceSearch.this, edittext.getText(), Toast.LENGTH_SHORT).show();
		          //return true;
		        //}
		        //return false;
		        return true;
			}
		});
		*/
        
        
	} // end onCreate
	
	private String inputStreamToString(InputStream is) {
		BufferedReader r = new BufferedReader(new InputStreamReader(is));
		StringBuilder total = new StringBuilder();
		String line;
		try {
			while ((line = r.readLine()) != null) {
			    total.append(line);
			}
		} catch (IOException e) {
			Log.i(tag, "problem with readline in inputStreamToString function");
		}
		
		return total.toString();
	}
	
	public JSONObject getJSONfromURL(String url){

		//initialize
		InputStream is = null;
		String result = "";
		JSONObject jArray = null;

		//http post
		try{
			HttpClient httpclient = new DefaultHttpClient();
			//HttpPost httppost = new HttpPost(url);
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e(tag, "Error in http connection "+e.toString());
		}

		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		} catch (Exception e) {
			Log.e(tag, "Error converting result "+e.toString());
		}

		//try parse the string to a JSON object
		try {
	        	jArray = new JSONObject(result);
		} catch (JSONException e) {
			Log.e(tag, "Error parsing data " + e.toString());
		}

		return jArray;
	}
	
	public void callWebService(String q){  
		Log.i(tag, "callWebService");
        HttpClient httpclient = new DefaultHttpClient(); 
        
        //clear the list array
        mylist.clear();
        
        params.add(new BasicNameValuePair("ll", "37.787920,-122.407458"));
        params.add(new BasicNameValuePair("limit", "10"));
        params.add(new BasicNameValuePair("v", "20120603"));
        params.add(new BasicNameValuePair("client_id", CLIENT_ID));
        params.add(new BasicNameValuePair("client_secret", CLIENT_SECRET));
        params.add(new BasicNameValuePair("query", q));
        
        String paramString = URLEncodedUtils.format(params, "utf-8");
        
        url = url + paramString;
        
        Log.i(tag, url);
        
        JSONObject job = getJSONfromURL(url);
        Log.i(tag, "json object " + job.toString());
        
        try {
        	JSONObject j = job.getJSONObject("response");
        	JSONArray minivenues = j.getJSONArray("minivenues");
        	
        	Log.i(tag, "minivenues length " + minivenues.length());
        	
        	
        	//iterate through minivenues
        	for(int i = 0; i < minivenues.length(); i++) {
        		JSONObject mv = minivenues.getJSONObject(i);
        		HashMap<String, String> map = new HashMap<String, String>();
        		map.put("id", Integer.toString(i));
        		map.put("venue_id", mv.getString("id"));
        		map.put("name", mv.getString("name"));
        		mylist.add(map);
        	}
        	
        	//create ListView and adapter
        	//http://p-xr.com/android-tutorial-how-to-parse-read-json-data-into-a-android-listview/
        	
        } catch (JSONException e) {
        	Log.e(tag, "problem getting jsonarray from job.getJSONArray");
        }
        
        //lv.getAdapter().notifyDataSetChanged();        
        /* my hand written code 
        HttpGet request = new HttpGet(url);
        request.addHeader("accept", "application/json"); 
        
        
         ll : '37.787920,-122.407458', //default to SF since it's well known
			v : '20120515', //the date of the foursquare api version
			limit : 10, //perhaps an option to ignore limits
			client_id : "YOUR_FS_CLIENT_ID", //get this from foursquare.com
			client_secret : "YOUR_FS_CLIENT_SECRET", //same
			style_results: true //set to false if the way i control the position of results, you can do it yourse
								//the default is to be right under the input and match the width of the input
								//and hopefully to adjust in a responsive way
         
        
        
        //ResponseHandler<String> handler = new BasicResponseHandler();  
        try {  
            response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
            	//Log.i(tag, Integer.toString(response.getStatusLine().getStatusCode()));
            	Log.i(tag, "response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
    			throw new RuntimeException("Failed : HTTP error code : "
    			   + response.getStatusLine().getStatusCode());
    		}
            
            Log.i(tag, "response.getStatusLine().getStatusCode() = " + response.getStatusLine().getStatusCode());
            
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release

            if (entity != null) {
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                //String res = new Scanner(instream).useDelimiter("\\\\A").next();
                try {
                	JSONObject jsonResponse = new JSONObject(inputStreamToString(instream));
                	
                	Log.i(tag, "json response to string: " + jsonResponse.toString());
                	
                } catch (JSONException jsonE) {
                	Log.i(tag, "problem creating JSON object callWebService");
                }
                
            }    
            
        } catch (ClientProtocolException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        httpclient.getConnectionManager().shutdown();  
        */
        
        
          
    } // end callWebService() 
}
