package com.example.yamba;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class TimelineActivity extends Activity {
	TextView textOut;
	Cursor cursor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timeline);
		textOut = (TextView) findViewById(R.id.text_out);
		cursor = ((YambaApplication)getApplication()).statusData.query();
		
		while(cursor.moveToNext()){
			String user_name = cursor.getString(cursor.getColumnIndex(StatusData.C_USER));
			String status_text = cursor.getString(cursor.getColumnIndex(StatusData.C_TEXT)); 		
			textOut.append(String.format("\n%s %s", user_name, status_text));
		}
	}

	
}
 