package com.example.yamba;

import java.util.List;
import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	static final String TAG = "Updater Service";
	// this is changed to string cause fucking java typecasting @
	// Integer.parseInt(((YambaApplication) getApplication()).prefs.getString("delay", DELAY));
	static final String DELAY = "30"; //in seconds 
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
						
						List<Status> timeline = ((YambaApplication) getApplication()).getTwitter().getPublicTimeline();
						StatusData statusData = ((YambaApplication)getApplication()).statusData;
						//There is a loop hole in the code that whenever delay is changed in prefs you need to restart the service
						final int delay = Integer.parseInt(((YambaApplication) getApplication()).prefs.getString("delay", DELAY));
						
						for (Status status : timeline) {

							Log.d(TAG, String.format("%s: %s",
									status.user.name, status.text));
							statusData.insert(status);
						}
						
						Thread.sleep(delay * 1000);
					}
				} catch (TwitterException e) {
					Log.e(TAG, "Died:", e);
					e.printStackTrace();
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
