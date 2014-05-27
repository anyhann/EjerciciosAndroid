package com.anabellolguin.earthquakes;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
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

public class EarthquakesList extends ListFragment implements LoaderCallbacks<Cursor>{
	
	public final static String ID = "_id";

	private static final String TAG = "EARTHQUAKE";
	private final int LOADER_ID = 1;
	

	private String[] result_columns = new String[] { Columns.KEY_MAGNITUDE,
			Columns.KEY_PLACE, Columns.KEY_TIME , Columns._ID };

	
	private int[] to = { R.id.textViewMag, R.id.textViewPlace,
			R.id.textViewTime };

	private SimpleCursorAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		adapter = new SimpleCursorAdapter(getActivity(),
				R.layout.activity_fila, null, result_columns,
				to, 0);

		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle inState) {
		super.onActivityCreated(inState);
		
		getLoaderManager().initLoader(1, null, this);

		DownloadFilesTask d = new DownloadFilesTask(getActivity());
		//d.execute("http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_hour.geojson");

	}

	public Loader<Cursor>onCreateLoader(int id, Bundle args){
		
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		double minMag = Double.valueOf(prefs.getString(getResources()
				.getString(R.string.PREF_MIN_MAG), "0"));
		
		String where = MyContentProvider.Columns.KEY_MAGNITUDE + " >= ?";
		String whereArgs[] = { String.valueOf(minMag) };
		String order = MyContentProvider.Columns.KEY_TIME + " DESC";
	
		CursorLoader loader = new CursorLoader(getActivity(), MyContentProvider.CONTENT_URI, result_columns, where, whereArgs, order);
		
		//adapter.swapCursor(c);//se le dice al adaptador que cambie su origen de datos
		//setListAdapter(adapter);//aqui se le indic a la lista cual es su adaptador para que lo pinte

		return loader;
	
	}
	
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor){
		adapter.swapCursor(cursor);
		setListAdapter(adapter);
	}
	
	public void onLoaderReset(Loader<Cursor>loader){
		adapter.swapCursor(null);
	}
	

	@Override
	public void onResume() {
		super.onResume();

		
	}
	
	@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			
			//hacer consulta a la base de datos, que me devuelve un objeto con los datos del id
			//pintar los datos en el layout de detalle
			 Intent intent = new Intent(getActivity(), Detalle.class);

			 intent.putExtra("id", id);

			 startActivity(intent);
			
		}
	
	
	

	/*public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.list, menu);
		return true;

	}
	
public boolean onOptionsItemSelected(MenuItem item) {

		
	int id = item.getItemId();
	if (id == R.id.action_settings) {
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
		return true;
	} else if (id == R.id.action_refresh) {
		((EarthQuakeList) getFragmentManager().findFragmentByTag("lista"))
				.refreshEarthquakes();
	}

	return super.onOptionsItemSelected(item);
}*/




	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
