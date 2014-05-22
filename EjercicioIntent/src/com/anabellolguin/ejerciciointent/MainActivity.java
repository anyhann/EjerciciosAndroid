package com.anabellolguin.ejerciciointent;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

import android.os.Environment;
import android.provider.MediaStore;
import android.net.Uri;

public class MainActivity extends Activity {

	public static final String TEXTO = "TEXTO";
	private final int FORM_ACTION = 1;
	private final int CAMERA_ACTION = 2;

	public EditText et1;
	private Button bt_hacerfoto;
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et1 = (EditText) findViewById(R.id.editText1);
		img = (ImageView) this.findViewById(R.id.imageView1);
		bt_hacerfoto = (Button) this.findViewById(R.id.buttonCamara);
		// A–adimos el Listener Boton
		bt_hacerfoto.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Creamos el Intent para llamar a la Camara
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				// Creamos una carpeta en la memeria del terminal
				File imagesFolder = new File(Environment
						.getExternalStorageDirectory(), "ImagenesAndroid");
				imagesFolder.mkdirs();
				// a–adimos el nombre de la imagen
				File image = new File(imagesFolder, "foto.jpg");
				Uri uriSavedImage = Uri.fromFile(image);
				// Le decimos al Intent que queremos grabar la imagen
				cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
				// Lanzamos la aplicacion de la camara con retorno (forResult)
				startActivityForResult(cameraIntent, CAMERA_ACTION);
			}
		});

		Button formbuton = (Button) findViewById(R.id.buttonForm);
		formbuton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MainActivity.this,
						DetalleActivity.class);
				intent.putExtra(TEXTO, et1.getText().toString());
				startActivityForResult(intent, FORM_ACTION);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {
			if (requestCode == FORM_ACTION) {
				String text = data.getStringExtra(TEXTO);
				TextView text2 = (TextView) findViewById(R.id.textView2);
				text2.setText(text);
			} else if (requestCode == CAMERA_ACTION) {
				// Creamos un bitmap con la imagen recientemente
				// almacenada en la memoria
				String path = Environment
						.getExternalStorageDirectory()
						+ "/ImagenesAndroid/"
						+ "foto.jpg";
				Bitmap bMap = BitmapFactory.decodeFile(path);
				// A–adimos el bitmap al imageView para
				// mostrarlo por pantalla
				img.setImageBitmap(bMap);

				// **PARA TOMAR FOTO CON BAJA CALIDAD**//
				// Bundle extras = data.getExtras();
				// Bitmap imageBitmap = (Bitmap) extras.get("data");
				// img = (ImageView)this.findViewById(R.id.imageView1);
				// img.setImageBitmap(imageBitmap);
			}

		}
	}
	
	//@Override
	/*protected void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	   
	
		if ( uriSavedImage!= null)
	        outState.putString("Uri", uriSavedImage.toSting());
	}
	 
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    super.onRestoreInstanceState(savedInstanceState);
	    if (savedInstanceState.containsKey("Uri")) {
	        Uri mImageUri = Uri.parse(savedInstanceState.getString("Uri"));
	    }
	}*/

}
