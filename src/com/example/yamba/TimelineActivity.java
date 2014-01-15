package com.example.yamba;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleCursorAdapter.ViewBinder;
import android.widget.TextView;

public class TimelineActivity extends Activity {
	static final String[] FROM  = {StatusData.C_USER, StatusData.C_TEXT, StatusData.C_CREATED_AT };
    static final int[] TO = { R.id.text_user, R.id.text_text,  R.id.text_created_at };
	ListView list;
	Cursor cursor;
	SimpleCursorAdapter adapter;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		list = (ListView) findViewById(R.id.list);
		cursor = ((YambaApplication)getApplication()).statusData.query();
		
//		while(cursor.moveToNext()){
//			String user_name = cursor.getString(cursor.getColumnIndex(StatusData.C_USER));
//			String status_text = cursor.getString(cursor.getColumnIndex(StatusData.C_TEXT)); 		
//			textOut.append(String.format("\n%s %s", user_name, status_text));
//		}
		
		adapter = new SimpleCursorAdapter(this, R.layout.row, cursor, FROM, TO); 
		adapter.setViewBinder(VIEW_BINDER);
		list.setAdapter(adapter);
		
	}
	
	static final ViewBinder VIEW_BINDER = new ViewBinder(){

		@Override
		public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
			if(view.getId() != R.id.text_created_at) return false;
			
			long time = cursor.getLong(cursor.getColumnIndex(StatusData.C_CREATED_AT));
			CharSequence relative_time = DateUtils.getRelativeTimeSpanString(time);
			((TextView) view).setText(relative_time); 
			return true;
		}
		
	}; 

	  
}
 