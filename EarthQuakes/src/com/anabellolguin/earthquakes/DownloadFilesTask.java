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

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

public class DownloadFilesTask extends AsyncTask<String, Integer, ArrayList<EarthQuake>> {
	
	private EarthQuakeDB db;
	private EarthquakesList listfrag;
	private ArrayAdapter<EarthQuake> aa;
	private ArrayList<EarthQuake> earthQuakeList, earthQuakeList2;
	

	public DownloadFilesTask(Context ctx) {
		db = EarthQuakeDB.getDB(ctx);
	}

	protected ArrayList<EarthQuake> doInBackground(String... urls) {
		
		// TODO: crear una lista de terremotos
		downloadJSON(urls[0]);
		earthQuakeList2 = new ArrayList<EarthQuake>();
		
		// TODO: devolver la lista
		return earthQuakeList2;

	}
	
	protected void postExecute(ArrayList<EarthQuake> result) {
		// TODO: actualizar la vista del fragmento y pintarla
		
		earthQuakeList2.addAll(0,result);
		aa.notifyDataSetChanged();

	
		
	}
	
	public interface IEarthQuakes {
		
		public void creaLista(ArrayList<EarthQuake> terremotos);
			
		
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
			q.setTime(new Date(terremoto.getJSONObject("properties").getLong("time")));
			q.setMagnitude(terremoto.getJSONObject("properties").getDouble("mag"));
			q.setLongitude(terremoto.getJSONObject("geometry").getJSONArray("coordinates").getDouble(0));
			q.setLatitude(terremoto.getJSONObject("geometry").getJSONArray("coordinates").getDouble(1));
			q.setUrl(terremoto.getJSONObject("properties").getString("url"));
			
			long id = db.insertEarthQuake(q);
			
			//meter datos en la lista
			
			earthQuakeList = new ArrayList<EarthQuake>();

			earthQuakeList.addAll(db.getEarthquakesByMagnitude(0));//coge los campos de la BD y los egrega a la lista filtrados por magnitud
			aa.notifyDataSetChanged();
			
			
		}

	}

}
