package com.anabellolguin.earthquakes;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.preference.PreferenceManager;

public class SettingsActivity extends Activity implements
		OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();

	}

	private void setInexactRepeatingAlarm() {

		AlarmManager alarma = (AlarmManager) getSystemService(this.ALARM_SERVICE);
		int alarmType = AlarmManager.RTC;
		long timeOrLengthofWait = 1000;
		Intent intento = new Intent(this, MyService.class);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0,
				intento, 0);
		alarma.setInexactRepeating(alarmType, 0, timeOrLengthofWait,
				alarmIntent);

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		
		if (key == getResources().getString(R.string.PREF_AUTO_UPDATE)) {
			boolean autoRefresh = prefs.getBoolean(
					getResources().getString(R.string.PREF_AUTO_UPDATE), false);

			if (autoRefresh) {
				// Set a repeting alarm
				startAlarm(prefs);
			} else {
				// Cancel an alarm
				cancelAlarm();
			}
		} else if (key == getResources().getString(R.string.PREF_UPDATE_FREQ)) {
			startAlarm(prefs);
		}
	}

	private void startAlarm(SharedPreferences prefs) {
		int interval = Integer.valueOf(prefs.getString(getResources()
				.getString(R.string.PREF_UPDATE_FREQ), "0"));

		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent intentToFire = new Intent(
				EarthquakesBroadcastReceiver.ACTION_REFRESH_EARTHQUAKE_ALARM);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0,
				intentToFire, 0);

		int alarmType = AlarmManager.RTC;

		long timeOrLengthofWait = (long) (interval * 60 * 1000);
		/*volver a crear la alarma, para cambiar intervalo
		 * 
		 */

		alarmManager.setInexactRepeating(alarmType, timeOrLengthofWait,
				timeOrLengthofWait, alarmIntent);
	}

	private void cancelAlarm() {

		AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		Intent intentToFire = new Intent(
				EarthquakesBroadcastReceiver.ACTION_REFRESH_EARTHQUAKE_ALARM);
		PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0,
				intentToFire, 0);

		alarmManager.cancel(alarmIntent);
	}

}
