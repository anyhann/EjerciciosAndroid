package com.anabellolguin.earthquakes;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ListActivity extends Activity {

	private static final String DATOS = "DATOS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		double minMag = Double.valueOf(prefs.getString(getResources().getString(R.string.PREF_MIN_MAG), "0"));

		if (savedInstanceState == null) {
			// Get references to the Fragments
			android.app.FragmentTransaction fragmentTransaction = getFragmentManager()
					.beginTransaction();
			fragmentTransaction.add(R.id.lista, new EarthquakesList(),
					"list");
			fragmentTransaction.commit();
			
			//get the Content Resolver
			
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
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);

			return true;
		} else if(id == R.id.action_refresh) {
			//((EarthquakesList)getFragmentManager().findFragmentByTag("list")).refreshEarthQuakes();
			Intent intento = new Intent(this, MyService.class);
			intento.putExtra("abre","http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");
			startService(intento);
		}
		
		return super.onOptionsItemSelected(item);
	}

}
