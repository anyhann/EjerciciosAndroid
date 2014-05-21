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

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ListActivity extends Activity {

	private EarthQuakeDB db;
	private static final String DATOS = "DATOS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		db = EarthQuakeDB.getDB(this);

		if (savedInstanceState == null) {
			// Get references to the Fragments
			android.app.FragmentTransaction fragmentTransaction = getFragmentManager()
					.beginTransaction();
			fragmentTransaction.add(R.id.lista, new EarthquakesList(),
					"list");
			fragmentTransaction.commit();
		}

		Thread t = new Thread(new Runnable() {// ejecutar un Thread en
					// paralelo y NO
					// ejecutar en el Thread
					// principal de android

					@Override
					public void run() {
						// TODO Auto-generated method stub
						downloadJSON();

					}
				});

		t.start();

	}

	private void downloadJSON() {
		JSONObject json = null;
		try {
			URL url = new URL(
					"http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");

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
						json = new JSONObject(sb.toString());// se crea un
																// onbjeto JSON
						procesarDatos(json); // devuelve objeto Json con el que
												// se trabaja
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
			array = json.getJSONArray("features");// se obtiene la tabla que nos
													// interesa del JSON
													// utilizando
													// el objeto que nos
													// devolvio al crear el
													// JSON.
		} catch (JSONException e) {

			e.printStackTrace();
		}
		// aqui se trabaja con el array que obtenemos del JSON
		for (int i = 0; i < array.length(); i++) {// se recorre el array para
													// obtener s—lo las claves y
													// los datos que nos
													// interesan
			EarthQuake q = new EarthQuake();
			JSONObject terremoto = array.getJSONObject(i);
			
			q.setIdStr(terremoto.getString("id"));
			q.setPlace(terremoto.getJSONObject("properties").getString("place"));
			q.setTime(new Date(terremoto.getJSONObject("properties").getLong("time")));
			q.setLongitude(terremoto.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0));
			q.setLatitude(terremoto.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1));
			q.setUrl(terremoto.getJSONObject("properties").getString("url"));
			
			long id = db.insertEarthQuake(q);
			
			/*
			 * Log.d(DATOS, "\n id=" + features.getString("id") + "\n place=" +
			 * features.getJSONObject("properties").getInt( "place") +
			 * " \n time=" + features.getJSONObject("properties").getLong(
			 * "time") + " \n latitude=" + features.getJSONObject("geometry")
			 * .getJSONArray("coordinates").getDouble(1) + " \n longitude=" +
			 * features.getJSONObject("geometry")
			 * .getJSONArray("coordinates").getDouble(0) + " \n magnitude=" +
			 * features.getJSONObject("properties").getDouble( "mag") +
			 * " \n url=" + features.getJSONObject("properties").getString(
			 * "url"));
			 */
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
