package com.example.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbbiaozhun extends SQLiteOpenHelper {

	public Dbbiaozhun(Context context) {
		super(context, "dbbiaozhun", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE biaozhun("
				+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "TRUELATLON TEXT DEFAULT \"\")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
