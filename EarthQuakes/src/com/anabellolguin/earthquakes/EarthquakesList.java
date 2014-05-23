package com.anabellolguin.earthquakes;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.widget.ArrayAdapter;

public class EarthquakesList extends ListFragment implements
		DownloadFilesTask.IEarthQuakes {

	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

	private EarthQuakeDB db;
	private ArrayAdapter<EarthQuake> aa;
	private ArrayList<EarthQuake> earthQuakeList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Create the array list of to do items
		earthQuakeList = new ArrayList<EarthQuake>();

		// Create the array adapter to bind the array to the listview
		aa = new ArrayAdapter<EarthQuake>(inflater.getContext(),
				android.R.layout.simple_list_item_1, earthQuakeList);

		setListAdapter(aa);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle inState) {
		super.onActivityCreated(inState);

		DownloadFilesTask d = new DownloadFilesTask(getActivity(), this);
		d.execute("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");

		db = EarthQuakeDB.getDB(getActivity());
		earthQuakeList.addAll(db.getEarthquakesByMagnitude(0));// coge los
																// campos de la
																// BD y los
																// egrega a la
																// lista
																// filtrados por
																// magnitud
		aa.notifyDataSetChanged();

	}

	@Override
	public void creaLista(ArrayList<EarthQuake> terremotos) {
		for (EarthQuake q : terremotos) {
			earthQuakeList.add(0, q);
		}

		aa.notifyDataSetChanged();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		double minMag = Double.valueOf(prefs.getString(getResources().getString(R.string.PREF_MIN_MAG), "0"));
		
		Log.d("EARTHQUAKE", String.valueOf(minMag));

		db = EarthQuakeDB.getDB(getActivity());	
		earthQuakeList.clear();
		earthQuakeList.addAll(db.getEarthquakesByMagnitude(minMag));
		aa.notifyDataSetChanged();

	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
