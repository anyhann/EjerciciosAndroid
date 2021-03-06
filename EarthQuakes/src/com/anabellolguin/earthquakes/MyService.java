package com.anabellolguin.earthquakes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	private String url;

	@Override
	public void onCreate() {

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		url = intent.getStringExtra("abre");

		/*
		 * Thread, para descargar datos de Internet
		 */

		Thread th1 = new Thread(new Runnable() {
			@Override
			public void run() {
				downloadJSON(url);
			}

		});
		th1.start();

		return Service.START_NOT_STICKY;
	}

	private void startBackgroundTask(Intent intent, int startId) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	private void downloadJSON(String u) {
		JSONObject json;
		
		try {
			URL url = new URL(u);

			// Create a new HTTP URL connection
			URLConnection connection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) connection;

			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					StringBuilder sb = new StringBuilder();

					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}

					in.close();

					try {
						/*
						 * Se crea un objeto JSON que devuelve objeto json que
						 * es con el que trabaja
						 */
						json = new JSONObject(sb.toString());

						procesarDatos(json);

					} catch (JSONException e) {
					}

				} catch (Exception e) {
					Log.d("ERROR", e.getMessage());
				}

			}
		} catch (MalformedURLException e) {
			Log.d("INTERNET", "Malformed	URL	Exception.", e);
		} catch (IOException e) {
			Log.d("INTERNET", "IO	Exception.", e);
		}
	}

	private void procesarDatos(JSONObject json) throws JSONException {

		JSONArray array = new JSONArray();// se crea un array de json

		try {
			/*
			 * Se obtiene la tabla que nos interesa del JSON utilizando el
			 * objeto que nos devolvio al crear el JSON
			 */
			array = json.getJSONArray("features");
		} catch (JSONException e) {

			e.printStackTrace();
		}
		/*
		 * Aqui se trabaja con el array que obtenemos del JSON se recorre el
		 * array para obtener s�lo las claves y los datos que nos interesa
		 */

		for (int i = 0; i < array.length(); i++) {//
			EarthQuake q = new EarthQuake();
			JSONObject terremoto = array.getJSONObject(i);

			q.setIdStr(terremoto.getString("id"));
			q.setPlace(terremoto.getJSONObject("properties").getString("place"));
			q.setTime(new Date(terremoto.getJSONObject("properties").getLong(
					"time")));
			q.setMagnitude(terremoto.getJSONObject("properties").getDouble(
					"mag"));
			q.setLongitude(terremoto.getJSONObject("geometry")
					.getJSONArray("coordinates").getDouble(0));
			q.setLatitude(terremoto.getJSONObject("geometry")
					.getJSONArray("coordinates").getDouble(1));
			q.setUrl(terremoto.getJSONObject("properties").getString("url"));


			/*
			 * se cre un contenedor de valores, es como un bandle en donde se
			 * mete nombre de la columna-valor
			 */
			ContentValues newValues = new ContentValues();//

			newValues.put(EarthquakeDatabaseHelper.Columns.KEY_TIME, q
					.getTime()// nombre columna-valor
					.getTime());
			newValues.put(EarthquakeDatabaseHelper.Columns.KEY_ID_STR,
					q.getIdStr());
			newValues.put(EarthquakeDatabaseHelper.Columns.KEY_PLACE,
					q.getPlace());
			newValues.put(EarthquakeDatabaseHelper.Columns.KEY_LOCATION_LAT, q
					.getLocation().getLatitude());
			newValues.put(EarthquakeDatabaseHelper.Columns.KEY_LOCATION_LNG, q
					.getLocation().getLongitude());
			newValues.put(EarthquakeDatabaseHelper.Columns.KEY_MAGNITUDE,
					q.getMagnitude());
			newValues.put(EarthquakeDatabaseHelper.Columns.KEY_URL, q.getUrl());

			ContentResolver cr = MyService.this.getContentResolver();
			Uri uri = cr.insert(MyContentProvider.CONTENT_URI, newValues);
			if (uri == null) {
				Log.d("EARTHQUAKE", "ERROR: " + q.getPlace());
			}
		}
		stopSelf();// termina el Servicio

	}

}
