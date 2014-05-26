package com.anabellolguin.earthquakes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class DownloadFilesTask extends
		AsyncTask<String, Void, ArrayList<EarthQuake>> {

	public interface IEarthQuakes {
		public void creaLista(ArrayList<EarthQuake> terremotos);
	}

	private Context ctx;
	private EarthQuakeDB db;
	private ArrayList<EarthQuake> earthQuakeList;
	private IEarthQuakes fragment;

	public DownloadFilesTask(Context ctx) {
		this.ctx = ctx;
		db = EarthQuakeDB.getDB(ctx);

		earthQuakeList = new ArrayList<EarthQuake>();
	}

	protected ArrayList<EarthQuake> doInBackground(String... urls) {

		// TODO: crear una lista de terremotos
		downloadJSON(urls[0]);// envia al metodo downloanJSON la url donde esta
								// elJSON, para que procese los datos

		// TODO: devolver la lista
		return earthQuakeList;// retorna la lista

	}

	protected void postExecute(ArrayList<EarthQuake> result) {
		// TODO: actualizar la vista del fragmento y pintarla
//		fragment.creaLista(result);// se le envia el earraylist al fragmento
									// para que lo pinte
	}

	private void downloadJSON(String u) {
		JSONObject json = null;
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
			 * long id = db.insertEarthQuake(q);
			 * 
			 * if(id != -1) {//si existe el id lo agrega a la lista
			 * 
			 * earthQuakeList.add(q);//agregar datos a la lista }
			 */

			ContentValues newValues = new ContentValues();// se cre un
															// contenedor de
															// valores, es como
															// un bandle en
															// donde se mete
															// nombre de la
															// columna-valor

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

			ContentResolver cr = this.ctx.getContentResolver();
			Uri uri = cr.insert(MyContentProvider.CONTENT_URI, newValues);
			if(uri == null) {
				Log.d("EARTHQUAKE", "ERROR: " + q.getPlace());
			}
		}

	}

}
