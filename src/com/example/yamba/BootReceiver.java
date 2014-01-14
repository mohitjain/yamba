package com.example.yamba;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	static final String TAG = "RefreshService";

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent intentUpdater = new Intent(context, UpdaterService.class);
		context.startService(intentUpdater);
		Log.d(TAG, "onReceive called...");
		
	}

}
