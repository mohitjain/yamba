package com.example.yamba;
import java.util.List;

import winterwell.jtwitter.Twitter.Status;
import winterwell.jtwitter.TwitterException;
import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class RefreshService extends IntentService {
	static final String TAG = "RefreshService";

	public RefreshService() {
		super(TAG);
		Log.d(TAG, "Construtor called...");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(TAG, "onHandleIntent");
		try {
			List<Status> timeline = ((YambaApplication) getApplication()).twitter.getPublicTimeline();
			for (Status status : timeline) {
				Log.d(TAG,
						String.format("%s: %s", status.user.name, status.text));
			}
		} catch (TwitterException e) {
			Log.e(TAG, "Died:", e);
			e.printStackTrace();
		}

	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "onCreate");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy");
	}

}
