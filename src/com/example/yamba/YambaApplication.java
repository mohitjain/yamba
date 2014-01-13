package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApplication extends Application {
	static final String TAG = "YambaApplication";
	Twitter twitter;
	SharedPreferences prefs;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String username = prefs.getString("username", "");
		String password = prefs.getString("password", "");
		String server = prefs.getString("server", "");

		twitter = new Twitter(username, password);
		twitter.setAPIRootUrl(server);
		
		Log.d(TAG, "onCreate"); 
	}

	
}
