package com.example.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Db extends SQLiteOpenHelper {

	public Db(Context context) {
		super(context, "db", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE jizhan("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "MCC TEXT DEFAULT \"\"," + "MNC TEXT DEFAULT \"\","
				+ "CID TEXT DEFAULT \"\"," + "LAC TEXT DEFAULT \"\","
				+ "RSSI TEXT DEFAULT \"\"," + "LAT TEXT DEFAULT \"\","
				+ "LON TEXT DEFAULT \"\")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
