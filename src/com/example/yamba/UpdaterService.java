package com.example.yamba;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log; 

public class UpdaterService extends Service {
	static final String TAG = "Updater Service";
	// this is changed to string cause fucking java typecasting @
	// Integer.parseInt(((YambaApplication)
	// getApplication()).prefs.getString("delay", DELAY));
	static final String DELAY = "30000"; // in milliseconds
	boolean running = false;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		running = true;
		Log.d(TAG, "onStartCommand");
		new Thread() {
			public void run() {
				try {
					while (running) {
						Log.d(TAG, "onStartCommandThread");

						((YambaApplication) getApplication()).pullAndInsert();

						// There is a loop hole in the code that whenever delay
						// is changed in prefs you need to restart the service
						final int delay = (Integer
								.parseInt(((YambaApplication) getApplication()).prefs
										.getString("delay", DELAY)))/1000;
						Thread.sleep(delay * 1000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		return super.onStartCommand(intent, flags, startId);

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		running = false;
		Log.d(TAG, "onDestroy");
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
