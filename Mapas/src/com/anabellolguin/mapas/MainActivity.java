package com.anabellolguin.mapas;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private LocationManager locationManager;
	
	private TextView longitude;
	private TextView latitude;
	private TextView altitude;

	private Button btnRefresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		longitude = (TextView) findViewById(R.id.Longitud);
		latitude = (TextView) findViewById(R.id.Latitud);
		altitude = (TextView) findViewById(R.id.Altitud);

		btnRefresh = (Button) findViewById(R.id.Refresh);
		btnRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getLastKnownLocation();
			}
		});

		// setUpMapIfNeeded();
		
		
		String serviceString = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) getSystemService(serviceString);

		getLastKnownLocation();
		trackPosition();
	}



	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	private String getBestProvider() {
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		// criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setSpeedRequired(false);

		// return locationManager.getBestProvider(criteria, true);
		return LocationManager.GPS_PROVIDER;
	}

	private void getLastKnownLocation() {

		String bestProvider = getBestProvider();

		Location location = locationManager.getLastKnownLocation(bestProvider);
        //obtener posicion actual, si hay la pinta
		if (location != null) {
			Log.i("GPS", location.toString());
			updateViews(location);
		} else {
			Log.i("GPS", "No position");
			Toast t = Toast.makeText(this, "No last known location",
					Toast.LENGTH_LONG);
			t.show();
		}
	}

	private void trackPosition() {//escuchar posicion, segun criterios
		String bestProvider = getBestProvider();

		int t = 5000; // milliseconds
		int distance = 5; // meters

		LocationListener locListener = new LocationListener() {
			public void onLocationChanged(Location location) {//cuando ha cambiado, actualiza localizacion y vista
				Log.i("GPS", location.toString());
				updateViews(location);
			}

			public void onProviderDisabled(String provider) {
				// Update application if provider disabled.
			}

			public void onProviderEnabled(String provider) {
				// Update application if provider enabled.
			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// Update application if provider hardware status changed.
			}
		};
		locationManager.requestLocationUpdates(bestProvider, t, distance,
				locListener);//avisa al manager que cuando haya cambios me avises, (proveder, distancia, metros, quien es el ejecutador(quien ejecuta los callbacks)
	}

	private void updateViews(Location location) {
		latitude.setText(String.valueOf(location.getLatitude()));
		longitude.setText(String.valueOf(location.getLongitude()));
		altitude.setText(String.valueOf(location.getAltitude()));
	}

}
