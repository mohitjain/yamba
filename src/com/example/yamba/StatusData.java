package com.example.yamba;

import winterwell.jtwitter.Twitter.Status;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StatusData {
	static final String TAG = "DbHelper";
	static final int DB_VERSION = 2;
	static final String DB_NAME = "timeline.db";
	static final String TABLE = "status";
	public static final String C_ID = "_id";
	public static final String C_CREATED_AT = "created_at";
	public static final String C_TEXT = "status_text";
	public static final String C_USER = "user_name";
	Context context;
	DbHelper dbHelper;
	SQLiteDatabase db;
	StatusData statusData;

	public StatusData(Context context) {
		this.context = context;
		dbHelper = new DbHelper();
	}

	public void insert(Status status) {
		Log.d(TAG, "inserting row in database: user: " + status.text);
		db = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(C_ID, status.id);
		values.put(C_CREATED_AT, status.createdAt.getTime());
		values.put(C_TEXT, status.text);
		values.put(C_USER, status.user.name);

		// db.insert(TABLE, null, values); // THIS LINE here is commeted cause
		// _id should be unique and that will be cause issue with
		// database and will start throwing errors so we need to handle it and
		// the method for that is insertWithOnConflict

		db.insertWithOnConflict(TABLE, null, values,
				SQLiteDatabase.CONFLICT_IGNORE);
	}

	public Cursor query() {
		db = dbHelper.getReadableDatabase();
		Cursor cursor = db.query(TABLE, null, null, null, null, null,
				C_CREATED_AT + " DESC"); // select * from status order by
											// created_at DESC
		return cursor;
	}

	public class DbHelper extends SQLiteOpenHelper {

		public DbHelper() {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d(TAG, "Creating database: " + DB_NAME);
			db.execSQL("create table " + TABLE + " (" + C_ID
					+ " int primary key, " + C_CREATED_AT + " int, " + C_USER
					+ " text, " + C_TEXT + " text)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Typically do ALTER TABLE statements, but...we're just in
			// development,
			// so:

			db.execSQL("drop table if exists " + TABLE); // blow the old
															// database
			// away
			Log.d(TAG, "onUpdated");
			onCreate(db); // run onCreate to get new database
		}

	}
}
