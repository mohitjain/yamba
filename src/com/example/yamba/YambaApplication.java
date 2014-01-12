package com.example.yamba;

import winterwell.jtwitter.Twitter;
import android.app.Application;
import android.util.Log;

public class YambaApplication extends Application {
	static final String TAG = "YambaApplication";
	Twitter twitter;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		twitter = new Twitter("student", "password");
		twitter.setAPIRootUrl("http://yamba.marakana.com/api");
		Log.d(TAG, "onCreate"); 
	}

	
}
