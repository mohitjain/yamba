package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener {
	static final String TAG = "Status Activity";
	EditText editStatus;
	Button updateButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "On Create with Bundle" + savedInstanceState);
		Debug.startMethodTracing("Yamba.trace");
		setContentView(R.layout.status);
		updateButton = (Button) findViewById(R.id.button_update);
		updateButton.setOnClickListener(this);

		editStatus = (EditText) findViewById(R.id.edit_status);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(StatusActivity.this, UpdaterService.class);
		switch (item.getItemId()) {
		case R.id.item_start_service:
			startService(intent);
			return true;
		case R.id.item_stop_service:
			stopService(intent);
			return true;
		default:
			return false;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		Debug.stopMethodTracing();
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
				Twitter twitter = new Twitter("student", "password");
				twitter.setAPIRootUrl("http://yamba.marakana.com/api");
				twitter.setStatus(params[0]);
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

}
