package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StatusActivity extends Activity implements OnClickListener {
	static final String TAG = "Status Activity";
	EditText editStatus;
	Button updateButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "On Create with Bundle" + savedInstanceState);
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

	public void onClick(View view){
		final String statusText = editStatus.getText().toString();
		new Thread() {
			public void run() {
				try {
					Twitter twitter = new Twitter("student", "password");
					twitter.setAPIRootUrl("http://yamba.marakana.com/api");
					twitter.setStatus(statusText);
				} catch (Exception e) {
					Log.e(TAG, "Died:", e);
					e.printStackTrace();
					// TODO: handle exception
				}
			}
		}.start();
		Log.d(TAG, "onClick" + statusText);
		
	}


}
