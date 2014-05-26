package com.anabellolguin.earthquakes;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
	private EarthquakeDatabaseHelper myOpenHelper;

	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	private static final UriMatcher uriMatcher;

	public static final Uri CONTENT_URI = Uri
			.parse("content://com.anabellolguin.provider.earthquakes/earthquakes");

	private SQLiteDatabase db;

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
		uriMatcher.addURI("com.anabellolguin.provider.earthquakes",
				"earthquakes/#", SINGLE_ROW);
	}

	@Override
	public boolean onCreate() {
		myOpenHelper = new EarthquakeDatabaseHelper(getContext(),
				EarthquakeDatabaseHelper.DATABASE_NAME, null,
				EarthquakeDatabaseHelper.DATABASE_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] columnas, String where,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db;

		try {
			db = myOpenHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = myOpenHelper.getReadableDatabase();
		}
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(Columns._ID + "=" + rowID);
		default:
			break;
		}

		queryBuilder.setTables(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE);

		Cursor cursor = queryBuilder.query(db, columnas, where, selectionArgs,
				null, null, sortOrder);

		return cursor;
	}

	@Override
	public String getType(Uri uri) {

		switch (uriMatcher.match(uri)) {
		case ALLROWS:
			return "vnd.android.cursor.dir/vnd.com.anabellolguin.provider.earthquakes";
		case SINGLE_ROW:
			return "vnd.android.cursor.item/vnd.com.anabellolguin.provider.earthquakes";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		db = myOpenHelper.getWritableDatabase();
		long id = db.insert(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE, null, values);
		
		if (id > -1) {
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
			getContext().getContentResolver().notifyChange(insertedId, null);
			return insertedId;
		} else

			return null;
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
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// metodo para actualizar base de datos, primero hay que cambiar la
			// version

			Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
					+ newVersion + ", which will destroy all old data");

			db.execSQL("DROP TABLE IF EXISTS " + EARTHQUAKE_TABLE);
			onCreate(db);
		}

	}

}
