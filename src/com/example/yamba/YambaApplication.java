package com.example.yamba;

import java.util.List;

import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import winterwell.jtwitter.Twitter.Status;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApplication extends Application implements
		OnSharedPreferenceChangeListener {
	static final String TAG = "YambaApplication";
	public static final String ACTION_NEW_STATUS = "com.example.yamba.NEW_STATUS";
	public static final String ACTION_REFRESH = "com.example.yamba.RefreshService";
	public static final String ACTION_REFRESH_ALARM = "com.example.yamba.RefreshAlarm";
	private Twitter twitter;
	SharedPreferences prefs;
	StatusData statusData;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		statusData = new StatusData(this);
		Log.d(TAG, "onCreate");
	}

	public Twitter getTwitter() {
		// We are setting it to null when preferences are changed.
		// the reason is when this will reset to null. Getter will read from the
		// shared prefrences again. else it will keep passing the same
		// variable whenever this getter will be called.
		if (twitter == null) {
			String username = prefs.getString("username", "");
			String password = prefs.getString("password", "");
			String server = prefs.getString("server", "");
			twitter = new Twitter(username, password);
			twitter.setAPIRootUrl(server);
		}

		return twitter;
	}

	public static final Intent refreshIntent = new Intent(ACTION_REFRESH_ALARM);	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// We are setting it to null when preferences are changed.
		// the reason is when this will reset to null. Getter will read from the
		// shared prefrences again. else it will keep passing the same
		// variable whenever this getter will be called.
		
		twitter = null;
		sendBroadcast(refreshIntent);
		Log.e(TAG, "in the preferance changes");
	}

	long lastTimeStampSeen = -1;

	public int pullAndInsert() {
		int count = 0;
		try {
			List<Status> timeline = getTwitter().getPublicTimeline();
			for (Status status : timeline) {
				Log.d(TAG,
						String.format("%s: %s", status.user.name, status.text));
				statusData.insert(status);
				if (status.createdAt.getTime() > lastTimeStampSeen) {
					lastTimeStampSeen = status.createdAt.getTime();
					count++;

				}
			}
		} catch (TwitterException e) {
			Log.e(TAG, "Died in Yamba Application:", e);
			e.printStackTrace();
		}
		if (count > 0) {
			sendBroadcast(new Intent(ACTION_NEW_STATUS)
					.putExtra("count", count));
		}
		return count;
	}

}
