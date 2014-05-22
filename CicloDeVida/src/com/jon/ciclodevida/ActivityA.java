package com.jon.ciclodevida;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ActivityA extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		Log.d("ActivityA", "SE HA EJECUTADO ONCREATE DE A");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
		Log.d("ActivityA", "SE HA EJECUTADO ONSTART DE A");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		Log.d("ActivityA", "SE HA EJECUTADO ONRESUME DE A");
	}
	
	@Override
	protected void onPause() {
		Log.d("ActivityA", "SE HA EJECUTADO ONPAUSE DE A");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		Log.d("ActivityA", "SE HA EJECUTADO ONSTOP DE A");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		Log.d("ActivityA", "SE HA EJECUTADO ONDESTROY DE A");
		super.onDestroy();
	}
	
	public void abrirB(View v) {
		Intent intent = new Intent(this, ActivityB.class);
		startActivity(intent);
	}
	
	public void abrirC(View v) {
		Intent intent = new Intent(this, ActivityC.class);
		startActivity(intent);
	}
	
	public void cerrar(View v) {
		finish();
	}

}
