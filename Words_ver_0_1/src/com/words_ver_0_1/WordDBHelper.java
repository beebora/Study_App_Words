package com.words_ver_0_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class WordDBHelper extends SQLiteOpenHelper {
	public WordDBHelper(Context context) {
		super(context, "Words.db", null, 1);
	}

	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE Words "
				+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, past TEXT, pronun TEXT, mean TEXT, level INTEGER);");
		
		db.execSQL("CREATE TABLE All_Words "
				+ "( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, past TEXT, pronun TEXT, mean TEXT, taken INTEGER);");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS dic");
		onCreate(db);
	}

}

