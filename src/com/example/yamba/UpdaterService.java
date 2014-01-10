package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.Twitter.Status;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {
	static final String TAG = "Updater Service";
	Twitter twitter;
	@Override
	public void onCreate() {
		super.onCreate();
		twitter = new Twitter("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		Log.d(TAG, "onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(TAG, "onStartCommand");
		new Thread() {
			public void run() {
				try {
					Log.d(TAG, "onStartCommandThread");
					List<Status> timeline = twitter.getPublicTimeline();	
					for (Status status : timeline) {
						
						Log.d(TAG, String.format("%s: %s", status.user.name,
								status.text));
					}
				} catch (Exception e) {
					Log.e(TAG, "Died:", e);
					e.printStackTrace();
				}
			}
		}.start();
		return super.onStartCommand(intent, flags, startId);
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}


	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
