package com.anabellolguin.persistencia;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class PreferencesActivity extends Activity implements OnSharedPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		prefs.registerOnSharedPreferenceChangeListener(this);

		// Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();

	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		if(key == getResources().getString(R.string.PREF_AUTO_UPDATE)) {
			boolean autoRefresh = prefs.getBoolean(key, true);
			Log.d("PREFERENCES", String.valueOf(autoRefresh));

			if(autoRefresh) {
				// Start
			} else {
				// Pause
			}
		} else {
			Log.d("PREFERENCES", prefs.getString(key, "60"));
		}
	}

	
}
