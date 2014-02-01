package com.example.yamba;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class TimelineActivity extends ListActivity {

	static final String TAG = "TimelineActivity";
	static final String[] FROM = { StatusData.C_USER, StatusData.C_TEXT,
			StatusData.C_CREATED_AT };
	static final int[] TO = { R.id.text_user, R.id.text_text,
			R.id.text_created_at };
	Cursor cursor;
	TimelineReceiver receiver;
	SimpleCursorAdapter adapter;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		cursor = ((YambaApplication) getApplication()).statusData.query();

		// while(cursor.moveToNext()){
		// String user_name =
		// cursor.getString(cursor.getColumnIndex(StatusData.C_USER));
		// String status_text =
		// cursor.getString(cursor.getColumnIndex(StatusData.C_TEXT));
		// textOut.append(String.format("\n%s %s", user_name, status_text));
		// }
		adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM, TO);
		adapter.setViewBinder(VIEW_BINDER);
		setTitle(R.string.timeline);
		getListView().setAdapter(adapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.status, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intentUpdater = new Intent(this, UpdaterService.class);
		Intent intentRefresh = new Intent(this, RefreshService.class);
		switch (item.getItemId()) {
		case R.id.item_start_service:
			startService(intentUpdater);
			return true;
		case R.id.item_stop_service:
			stopService(intentUpdater);
			return true;
		case R.id.item_refresh_service:
			startService(intentRefresh);
			return true;
		case R.id.item_prefs_activity:
			startActivity(new Intent(this, PrefsActivity.class));
			return true;
		case R.id.item_new_status:
			startActivity(new Intent(this, StatusActivity.class));

		default:
			return false;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (receiver == null)
			receiver = new TimelineReceiver();
		registerReceiver(receiver, new IntentFilter(
				YambaApplication.ACTION_NEW_STATUS));
	}

	static final ViewBinder VIEW_BINDER = new ViewBinder() {

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if (view.getId() != R.id.text_created_at)
				return false;

			long time = cursor.getLong(cursor
					.getColumnIndex(StatusData.C_CREATED_AT));
			CharSequence relative_time = DateUtils
					.getRelativeTimeSpanString(time);
			((TextView) view).setText(relative_time);
			return true;
		}

	};

	class TimelineReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			cursor = ((YambaApplication) getApplication()).statusData.query();
			adapter.changeCursor(cursor);
			Log.d(TAG, "onReceive in TimelineReceiver with new tweets count "
					+ intent.getIntExtra("count", 0));

		}

	}

}
