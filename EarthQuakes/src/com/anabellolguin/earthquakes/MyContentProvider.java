package com.anabellolguin.earthquakes;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	private static final UriMatcher uriMatcher;
	
	public static final Uri CONTENT_URI = Uri
			.parse("content://com.anabellolguin.provider.earthquakes/earthquakes");

	// Clase interna para declarar las constantes de columna
	public static final class Columns implements BaseColumns {
		// Column Names
		public static final String KEY_ID_STR = "id_str";
		public static final String KEY_PLACE = "place";
		public static final String KEY_TIME = "time";
		public static final String KEY_LOCATION_LAT = "latitude";
		public static final String KEY_LOCATION_LNG = "longitude";
		public static final String KEY_MAGNITUDE = "magnitude";
		public static final String KEY_URL = "url";
		public static final String KEY_CREATED_AT = "created_at";
		public static final String KEY_UPDATED_AT = "updated_at";
	}
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.anabellolguin.provider.MyContentProvider",
                "elements", ALLROWS);
		uriMatcher.addURI("ccom.anabellolguin.provider.earthquakes","earthquakes/#", SINGLE_ROW);
	}
	
	

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		EarthquakeDatabaseHelper myOpenHelper = new EarthquakeDatabaseHelper(getContext(),
		EarthquakeDatabaseHelper.DATABASE_NAME, null, EarthquakeDatabaseHelper.DATABASE_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	private class EarthquakeDatabaseHelper extends SQLiteOpenHelper {

		private static final String TAG = "EarthquakeProvider";

		public static final String DATABASE_NAME = "earthquakes.db";
		public static final String EARTHQUAKE_TABLE = "earthquakes";
		public static final int DATABASE_VERSION = 1;

		private static final String DATABASE_CREATE = "create table "
				+ EARTHQUAKE_TABLE + " (" + Columns._ID
				+ " integer primary key autoincrement, " + Columns.KEY_ID_STR
				+ " TEXT UNIQUE, " + Columns.KEY_TIME + " INTEGER, "
				+ Columns.KEY_PLACE + " TEXT, " + Columns.KEY_LOCATION_LAT
				+ " FLOAT, " + Columns.KEY_LOCATION_LNG + " FLOAT, "
				+ Columns.KEY_MAGNITUDE + " FLOAT, " + Columns.KEY_URL
				+ " TEXT," + Columns.KEY_CREATED_AT + " INTEGER, "
				+ Columns.KEY_UPDATED_AT + " INTEGER" + ");";

		public EarthquakeDatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("EARTHQUAKE", DATABASE_CREATE);
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {// metodo
																					// para
																					// actualizar
																					// base
																					// de
																					// datos,
																					// primero
																					// hay
																					// que
																					// cambiar
																					// la
																					// version
			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");

			db.execSQL("DROP TABLE IF EXISTS " + EARTHQUAKE_TABLE);
			onCreate(db);
		}

	}

}
