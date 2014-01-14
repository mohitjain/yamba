package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;

public class YambaApplication extends Application implements OnSharedPreferenceChangeListener {
	static final String TAG = "YambaApplication";
	private Twitter twitter;
	SharedPreferences prefs;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();		
		prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);
		Log.d(TAG, "onCreate"); 
	}
	
	public Twitter getTwitter()
	{
		// We are setting it to null when preferences are changed. 
		// the reason is when this will reset to null. Getter will read from the shared prefrences again. else it will keep passing the same 
		// variable whenever this getter will be called.
		if(twitter == null)
		{
			String username = prefs.getString("username", "");
			String password = prefs.getString("password", "");
			String server = prefs.getString("server", "");
			twitter = new Twitter(username, password);
			twitter.setAPIRootUrl(server);
		}
		
		return twitter;
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// We are setting it to null when preferences are changed. 
		// the reason is when this will reset to null. Getter will read from the shared prefrences again. else it will keep passing the same 
		// variable whenever this getter will be called.
		twitter = null;		
	}

	
}
