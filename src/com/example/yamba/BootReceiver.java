package com.example.yamba;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
	static final String TAG = "BootReceiver";
	static PendingIntent lastOperation;

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(TAG, "onReceiveStart");
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

		long interval = Long.parseLong(prefs.getString("DELAY", "900000"));

		PendingIntent operation = PendingIntent.getService(context, -1, new Intent(YambaApplication.ACTION_REFRESH), PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

		alarmManager.cancel(lastOperation);

		if (interval > 0) {
			Log.d(TAG, "in Process of calling the refresh service");

			alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), interval, operation);
			// Intent intentUpdater = new Intent(context, UpdaterService.class);
			// context.startService(intentUpdater);
		}
		else{
			Log.d(TAG, "Interval value is less than 0 or zero");
		}
			

		lastOperation = operation;
		Log.d(TAG, "onReceive");

	}

}
