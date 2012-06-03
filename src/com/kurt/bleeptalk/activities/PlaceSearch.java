package com.kurt.bleeptalk.activities;

import com.kurt.bleeptalk.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.Toast;

public class PlaceSearch extends Activity {

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        
        //setupInput();
        final EditText edittext = (EditText) findViewById(R.id.edittext);
		edittext.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
		        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
		            (keyCode == KeyEvent.KEYCODE_ENTER)) {
		          // Perform action on key press
		          Toast.makeText(PlaceSearch.this, edittext.getText(), Toast.LENGTH_SHORT).show();
		          return true;
		        }
		        return false;
			}
		});
		
	}
}
