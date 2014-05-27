package com.anabellolguin.earthquakes;

import java.sql.RowId;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;
import android.preference.PreferenceManager;

public class Detalle extends Activity  implements LoaderCallbacks<Cursor>{

	private static final String TAG = "EARTHQUAKE";
	public static final Uri CONTENT_URI = Uri
			.parse("content://com.anabellolguin.provider.earthquakes/earthquakes");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);
		
		long id = getIntent().getLongExtra(EarthquakesList.ID , 0);
		Log.d(TAG, String.valueOf(id));
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detalle, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		String where = MyContentProvider.Columns.KEY_ID_STR + " = ?";
		String whereArgs[] = { String.valueOf(id) };
		
	
		CursorLoader loader = new CursorLoader(getActivity(), MyContentProvider.CONTENT_URI, MyContentProvider.Columns.KEY_MAGNITUDE,MyContentProvider.Columns.KEY_PLACE , where, whereArgs, null);
		
	
		Uri rowAddress = ContentUris.withAppendedId(MyContentProvider.CONTENT_URI, id);
		
		
		return null;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}



	

}
