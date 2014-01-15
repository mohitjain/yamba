package com.example.yamba;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

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
		list.setAdapter(adapter);
		
	}
	


	  
}
 