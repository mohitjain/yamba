package com.example.yamba;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener, LocationListener {
	static final String TAG = "Status Activity";
	static final String PROVIDER = LocationManager.GPS_PROVIDER;
	EditText editStatus;
	Button updateButton;
	LocationManager locationManager;
	Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "On Create with Bundle" + savedInstanceState);
		//Debug.startMethodTracing("Yamba.trace");
		setContentView(R.layout.status);
		updateButton = (Button) findViewById(R.id.button_update);
		updateButton.setOnClickListener(this);

		editStatus = (EditText) findViewById(R.id.edit_status);
		locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
		location = locationManager.getLastKnownLocation(PROVIDER);
	}

	
	

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		locationManager.removeUpdates(this);
	}




	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		locationManager.requestLocationUpdates(PROVIDER, 30000, 10000, this);
	}




	@Override
	protected void onStop() {
		super.onStop();
		//Debug.stopMethodTracing();
	}


	public void onClick(View view) {
		String statusText = editStatus.getText().toString();
		new PostToTwitter().execute(statusText);
		Log.d(TAG, "onClick" + statusText);

	}

	class PostToTwitter extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				((YambaApplication) getApplication()).getTwitter().setStatus(params[0]);
				return "Successfully Posted";
			} catch (Exception e) {
				Log.e(TAG, "Died:", e);
				e.printStackTrace();
				return "Posting Failed";
				// TODO: handle exception
			}

		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG)
					.show();
		}

	}

	// -- Location Listener Callbacks..
	
	@Override
	public void onLocationChanged(Location location) {
		this.location = location;
		Log.d(TAG, "onLocationChanged: " + location.toString());
	}




	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
