package com.anabellolguin.earthquakes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class EarthquakesBroadcastReceiver extends BroadcastReceiver {

	public static final String ACTION_REFRESH_EARTHQUAKE_ALARM = "com.anabellolguin.earthquakes.ACTION_REFRESH_EARTHQUAKE_ALARM";
	private static final String TAG = null;


	@Override
	public void onReceive( Context context, Intent intent) {

        /*
         * /para un servicio explicito en este caso MyService
         */
		Intent startIntent = new Intent(context, MyService.class);
		context.startService(startIntent);

		
	}
}
