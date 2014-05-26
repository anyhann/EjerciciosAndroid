package com.anabellolguin.earthquakes;

import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.anabellolguin.earthquakes.MyContentProvider.Columns;

public class EarthquakesList extends ListFragment {

//	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

	private String[] result_columns = new String[] { Columns.KEY_MAGNITUDE,
			Columns.KEY_PLACE, Columns.KEY_TIME , Columns._ID };

	
	private int[] to = { R.id.textViewMag, R.id.textViewPlace,
			R.id.textViewTime };

	private SimpleCursorAdapter adapter;

	// private EarthQuakeDB db;
	// private ArrayAdapter<EarthQuake> aa;
	// private ArrayList<EarthQuake> earthQuakeList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// Create the array list of to do items
		// earthQuakeList = new ArrayList<EarthQuake>();

		// Create the array adapter to bind the array to the listview
		// aa = new ArrayAdapter<EarthQuake>(inflater.getContext(),
		// android.R.layout.simple_list_item_1, earthQuakeList);

		// setListAdapter(aa);

		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.activity_fila, null, result_columns,
				to, 0);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle inState) {
		super.onActivityCreated(inState);

		DownloadFilesTask d = new DownloadFilesTask(getActivity());
		d.execute("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");

		

		// db = EarthQuakeDB.getDB(getActivity());
		// earthQuakeList.addAll(db.getEarthquakesByMagnitude(0));// coge los
		// // campos de la
		// // BD y los
		// // egrega a la
		// // lista
		// // filtrados por
		// // magnitud
		// aa.notifyDataSetChanged();

	}

	// @Override
	// public void creaLista(ArrayList<EarthQuake> terremotos) {
	// for (EarthQuake q : terremotos) {
	// earthQuakeList.add(0, q);
	// }
	//
	// aa.notifyDataSetChanged();
	// }

	@Override
	public void onResume() {
		super.onResume();

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		double minMag = Double.valueOf(prefs.getString(getResources()
				.getString(R.string.PREF_MIN_MAG), "0"));

		Log.d("EARTHQUAKE", String.valueOf(minMag));
		
		ContentResolver cr = getActivity().getContentResolver();

		String where = MyContentProvider.Columns.KEY_MAGNITUDE + " >= ?";
		String whereArgs[] = { String.valueOf(minMag) };
		String order = MyContentProvider.Columns.KEY_TIME + " DESC";

		Cursor c = cr.query(MyContentProvider.CONTENT_URI, result_columns,
				where, whereArgs, order);
		
		adapter.swapCursor(c);
		setListAdapter(adapter);

		// db = EarthQuakeDB.getDB(getActivity());
		// earthQuakeList.clear();
		// earthQuakeList.addAll(db.getEarthquakesByMagnitude(minMag));
		// aa.notifyDataSetChanged();

	}
	
	@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			
			Intent i = new Intent(EarthquakesList.this, Detalle.class);
			//hacer consulta a la base de datos, que me devuelve un objeto con los datos del id
			//pintar los datos en el layout de detalle
			i.putExtra("_id", id);
			startActivity(i);
		}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
