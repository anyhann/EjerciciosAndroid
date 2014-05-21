package com.anabellolguin.earthquakes;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



public class EarthQuakeDB {

	private static EarthQuakeDB INSTANCE = null;

	public static final String[] KEYS_ALL = {
			EarthquakeDatabaseHelper.Columns._ID,
			EarthquakeDatabaseHelper.Columns.KEY_PLACE,
			EarthquakeDatabaseHelper.Columns.KEY_TIME,
			EarthquakeDatabaseHelper.Columns.KEY_LOCATION_LAT,
			EarthquakeDatabaseHelper.Columns.KEY_LOCATION_LNG,
			EarthquakeDatabaseHelper.Columns.KEY_MAGNITUDE,
			EarthquakeDatabaseHelper.Columns.KEY_URL };

	private Context mContext;
	private SQLiteDatabase mDatabase;
	private EarthquakeDatabaseHelper dbHelper;

	private EarthQuakeDB(Context context) {
		mContext = context;
	}

	public static EarthQuakeDB getDB(Context context) {
		if (INSTANCE == null) {
			INSTANCE = new EarthQuakeDB(context);
			INSTANCE.open();
		}

		return INSTANCE;
	}

	public void open() throws SQLException {//este metodo comprueba si existe el fichero y si no existe lo crea
		dbHelper = new EarthquakeDatabaseHelper(mContext,
				EarthquakeDatabaseHelper.DATABASE_NAME, null,
				EarthquakeDatabaseHelper.DATABASE_VERSION);

		mDatabase = dbHelper.getWritableDatabase();//abre la base de datos la obtiene en modo escritura
	}

	public void close() {
		dbHelper.close();
		dbHelper = null;
		mDatabase = null;
	}

	public long insertEarthQuake(EarthQuake q) {//lo que se pasa en un terremoto y tengo que incertarlo a la BD
		ContentValues newValues = new ContentValues();//se cre un contenedor de valores, es como un bandle en donde se mete nombre de la columna-valor

		newValues.put(EarthquakeDatabaseHelper.Columns.KEY_TIME, q.getTime()//nombre columna-valor
				.getTime());
		newValues
				.put(EarthquakeDatabaseHelper.Columns.KEY_ID_STR, q.getIdStr());
		newValues.put(EarthquakeDatabaseHelper.Columns.KEY_PLACE, q.getPlace());
		newValues.put(EarthquakeDatabaseHelper.Columns.KEY_LOCATION_LAT, q
				.getLocation().getLatitude());
		newValues.put(EarthquakeDatabaseHelper.Columns.KEY_LOCATION_LNG, q
				.getLocation().getLongitude());
		newValues.put(EarthquakeDatabaseHelper.Columns.KEY_MAGNITUDE,
				q.getMagnitude());
		newValues.put(EarthquakeDatabaseHelper.Columns.KEY_URL, q.getUrl());

		try {
			return this.createRow(newValues);
		} catch (SQLiteConstraintException ex) {
			Log.e("EARTHQUAKE", ex.getMessage());
			return -1;
		}
	}

	private long createRow(ContentValues values)//se dice a la DB que cree una nueva fila
			throws SQLiteConstraintException {
		long now = System.currentTimeMillis();

		values.put(EarthquakeDatabaseHelper.Columns.KEY_CREATED_AT, now);
		values.put(EarthquakeDatabaseHelper.Columns.KEY_UPDATED_AT, now);

		return mDatabase.insertOrThrow(
				EarthquakeDatabaseHelper.EARTHQUAKE_TABLE, null, values);//para incertar algo, pasar nombre de la tabla y los valores
	}

	public ArrayList<EarthQuake> getEarthquakesByMagnitude(double magnitude) {//metodo para obtener terremotos y que lo filtre por magnitud 
		ArrayList<EarthQuake> list = new ArrayList<EarthQuake>();

		String whereArgs[] = { String.valueOf(magnitude) };
		Cursor c = this.query(KEYS_ALL,
				EarthquakeDatabaseHelper.Columns.KEY_MAGNITUDE + " > ?",
				whereArgs, null, null,
				EarthquakeDatabaseHelper.Columns.KEY_TIME + " DESC");//se ordena de manera descendente y se obtiene el cursor

		int idIdx = c.getColumnIndex(EarthquakeDatabaseHelper.Columns._ID);//al cursor de digo que me devuelva el indice de la columna cuyo nombre es igual al que le paso
		int idStrIdx = c
				.getColumnIndex(EarthquakeDatabaseHelper.Columns.KEY_ID_STR);
		int placeIdx = c
				.getColumnIndex(EarthquakeDatabaseHelper.Columns.KEY_PLACE);
		int timeIdx = c
				.getColumnIndex(EarthquakeDatabaseHelper.Columns.KEY_TIME);
		int magIdx = c
				.getColumnIndex(EarthquakeDatabaseHelper.Columns.KEY_MAGNITUDE);
		int urlIdx = c.getColumnIndex(EarthquakeDatabaseHelper.Columns.KEY_URL);
		int latIdx = c
				.getColumnIndex(EarthquakeDatabaseHelper.Columns.KEY_LOCATION_LAT);
		int lngIdx = c
				.getColumnIndex(EarthquakeDatabaseHelper.Columns.KEY_LOCATION_LNG);

		while (c.moveToNext()) {//el el cursor se va moviendo
			EarthQuake q = new EarthQuake();//se crea una instacia de la clase terremto
			q.setId(c.getLong(idIdx));
			q.setIdStr(c.getString(idStrIdx));
			q.setPlace(c.getString(placeIdx));
			q.setTime(new Date(c.getLong(timeIdx)));
			q.setMagnitude(c.getDouble(magIdx));
			q.setUrl(c.getString(urlIdx));
			q.setLatitude(c.getDouble(latIdx));
			q.setLongitude(c.getDouble(lngIdx));

			list.add(q);//se agrega en la lista los datos
		}

		c.close();//se cierra el cursor

		return list;//se devuelve la lista
	}

	private Cursor queryAll() {
		return mDatabase.query(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE,
				KEYS_ALL, null, null, null, null,
				EarthquakeDatabaseHelper.Columns.KEY_TIME + " DESC");
	}

	private Cursor query(String[] columns, String where, String[] whereArgs,
			String groupBy, String having, String orderBy) {
		return mDatabase.query(EarthquakeDatabaseHelper.EARTHQUAKE_TABLE,
				columns, where, whereArgs, groupBy, having, orderBy);
	}

}
