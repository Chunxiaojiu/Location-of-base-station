package com.example.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbJieguo extends SQLiteOpenHelper {

	public DbJieguo(Context context) {
		super(context, "dbjieguo", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE jieguo("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "GETLAT TEXT DEFAULT \"\"," + "GETLON TEXT DEFAULT \"\","
				+ "TRUECID TEXT DEFAULT \"\"," + "TRUELAC TEXT DEFAULT \"\")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
