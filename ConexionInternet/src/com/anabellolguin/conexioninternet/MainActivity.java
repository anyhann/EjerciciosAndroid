package com.anabellolguin.conexioninternet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final String DATOS = "DATOS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = (Button) findViewById(R.id.buttonDatos);
		btn.setOnClickListener(new OnClickListener()

		{

			@Override
			public void onClick(View v) {
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
		});

	}

	private void downloadJSON() {
		JSONObject json = null;
		try {
			URL url = new URL("http://www.arkaitzgarro.com/android/photos.json");

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
			array = json.getJSONArray("photos");// se obtiene la tabla que nos
												// interesa del JSON utilizando
												// el objeto que nos devolvio al
												// crear el JSON.
		} catch (JSONException e) {

			e.printStackTrace();
		}
		// aqui se trabaja con el array que obtenemos del JSON
		for (int i = 0; i < array.length(); i++) {// se recorre el array para
													// obtener s—lo las claves y
													// los datos que nos
													// interesan
			JSONObject photo = array.getJSONObject(i);
			Log.d(DATOS,
					"\n id=" + photo.getString("id") + "\n name="
							+ photo.getString("name") + " \n image_url="
							+ photo.getString("image_url"));

		}
	}

}
