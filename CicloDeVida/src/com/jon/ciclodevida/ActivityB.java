package com.jon.ciclodevida;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ActivityB extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		Log.d("ActivityB", "SE HA EJECUTADO ONCREATE DE B");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_b, menu);
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
	protected void onStart() {
		super.onStart();
		Log.d("ActivityB", "SE HA EJECUTADO ONSTART DE B");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("ActivityB", "SE HA EJECUTADO ONRESUME DE B");
	}
	
	@Override
	protected void onPause() {
		Log.d("ActivityB", "SE HA EJECUTADO ONPAUSE DE B");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		Log.d("ActivityB", "SE HA EJECUTADO ONSTOP DE B");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.d("ActivityB", "SE HA EJECUTADO ONDESTROY DE B");
		super.onDestroy();
	}
	
	public void abrirA(View v) {
		Intent intent = new Intent(this, ActivityA.class);
		startActivity(intent);
	}
	
	public void abrirC(View v) {
		Intent intent = new Intent(this, ActivityC.class);
		startActivity(intent);
	}
	
	public void cerrar() {
		finish();
	}

}
