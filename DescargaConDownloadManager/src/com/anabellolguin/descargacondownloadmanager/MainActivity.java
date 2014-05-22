package com.anabellolguin.descargacondownloadmanager;

import org.json.JSONObject;

import com.anabellolguin.descargacondownloadmanager.*;

import android.app.Activity;
import android.app.ActionBar;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends Activity {
	public static long myreferencia;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				String serviceString = Context.DOWNLOAD_SERVICE;
				DownloadManager downloadManager;
				downloadManager = (DownloadManager) getSystemService(serviceString);

				Uri uri = Uri
						.parse("http://developer.android.com/shareables/icon_templates-v4.0.zip");
				DownloadManager.Request request = new Request(uri);

				request.setAllowedNetworkTypes(Request.NETWORK_WIFI);
				request.setDestinationInExternalFilesDir(MainActivity.this, Environment.DIRECTORY_DOWNLOADS,"icon_templates-v4.0.zip");
				request.setNotificationVisibility(request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				myreferencia  = downloadManager.enqueue(request);

			}
		});
	
	
	Button btn2 = (Button) findViewById(R.id.button1);
	btn2.setOnClickListener(new View.OnClickListener(){
		
	
	
	BroadcastReceiver receiver	= new BroadcastReceiver()	{	
			@Override	
			public	void onReceive(Context	context, Intent	intent)	{
				Log.d(tag, msg);
			
					long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,	-1);	
					if	(myDownloadReference	==	reference)	{									
									
					}	
			}
	};	
	
	registerReceiver(receiver, filter);
	
	}
	
	private void downloadJSON(){
		private void processJSONObject(JSONObject json) {
			private void getDownloadInfo(long reference){
				Query pausedDownloadQuery = new Query(); pausedDownloadQuery.setFilterByStatus(DownloadManager.STATUS_PAUSED); 
				
				DownloadManager downloadManager;
				Cursor pausedDownloads = downloadManager.query(pausedDownloadQuery); 
				int filenameIdx = pausedDownloads.getColumnIndex(DownloadManager.COLUMN_STATUS);
				int StatusIdx = pausedDownloads.getColumnIndex(DownloadManager.COLUMN_STATUS);
				int sizeIdx = pausedDownloads.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
				
				while (pausedDownloads.moveToNext()) {
					 String filename = pausedDownloads.getString(filenameIdx);
					 long size = pausedDownloads.getLong(sizeIdx);
					 
				    int status = pausedDownloads.getInt(StatusIdx);
				   
				    switch (status) {
				        case DownloadManager.STATUS_SUCCESSFUL:
				        //Text
				        default : break;
				    }
				}
				
				pausedDownloads.close();
			}
			
			
			
		
	}

	







