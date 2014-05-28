package com.anabellolguin.earthquakes;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Detalle extends Activity implements LoaderCallbacks<Cursor> {

	private long earthquake_id = 0;
	private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
	private static final int LOADER_ID = 2;

	private static final String TAG = "EARTHQUAKE";
	public static final Uri CONTENT_URI = Uri

	.parse("content://com.anabellolguin.provider.earthquakes/earthquakes");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);

		earthquake_id = getIntent().getLongExtra(EarthquakesList.ID, 0);
		Log.d(TAG, String.valueOf(earthquake_id));
		mCallbacks = this;
       //crear un loader nuevo
		LoaderManager lm = getLoaderManager();
		lm.initLoader(LOADER_ID, null, mCallbacks);

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
	protected void onResume() {
		super.onResume();

		getLoaderManager().restartLoader(LOADER_ID, null, mCallbacks);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri rowAddress = ContentUris.withAppendedId(
				MyContentProvider.CONTENT_URI, earthquake_id);//se construye la nueva uri y se le grega el ID al final

		String where = MyContentProvider.Columns.KEY_ID_STR + " = ?";
		String whereArgs[] = { String.valueOf(earthquake_id) };

		CursorLoader loader = new CursorLoader(Detalle.this,
				rowAddress, MyContentProvider.KEYS_ALL,null, null, null);

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		
		int place_idx = cursor.getColumnIndex(MyContentProvider.Columns.KEY_PLACE);
		int mag_idx = cursor.getColumnIndex(MyContentProvider.Columns.KEY_MAGNITUDE);


		if(cursor.moveToFirst()) {//para obtener datos hay que moverse al primero
			String place = cursor.getString(place_idx);
			double magnitud = cursor.getDouble(mag_idx);
			
			((TextView) findViewById(R.id.detalle_Mag)).setText(String.valueOf(magnitud));
			((TextView) findViewById(R.id.detalle_Place)).setText(place);
			
			
		}

	}

	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub

	}

}
