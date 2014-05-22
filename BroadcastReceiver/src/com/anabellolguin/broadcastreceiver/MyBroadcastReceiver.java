package com.anabellolguin.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;

public class MyBroadcastReceiver extends BroadcastReceiver {
	public final static String EXTRA_STATE = "EXTRA_STATE";
	private final static String LOGTAG = "RecibirSMSEntrante";

	@Override
	public void onReceive(Context context, Intent intent) {
		
		Log.d("RECEIVER", data.getAction();;
		Bundle extras = intent.getExtras();
		String acion = data.getAction();
		if (extras != null) {
			if(action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
				String state = data.getStringExtra("state");
				Log.d("RECEIVER", "ACTION_AIRPLANE_MODE_CHANGED");
				Log.d("RECEIVER", );
			}
			String state = extras.getString(TelephonyManager.EXTRA_STATE);
			
			else if (action.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
				String numTel = extras
						.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
				Log.d("RECEIVER", numTel);
            
				//escucha si se ha recibido un mensaje SMS
				Log.d(LOGTAG, "SMS recibido");
				Object[] pdus = (Object[]) extras.get("pdus");
				final SmsMessage[] messages = new SmsMessage[pdus.length];
				for (int i = 0; i < pdus.length; i++) {
					messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

					String idMensaje = messages[i].getOriginatingAddress();
					String textoMensaje = messages[i].getMessageBody();

					Log.d(RECEIVER, "Mensaje recibido: id=" + idMensaje + " texto=" + textoMensaje);

				}
			}
		}

	}

	

}
