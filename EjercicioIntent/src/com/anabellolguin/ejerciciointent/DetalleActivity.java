package com.anabellolguin.ejerciciointent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class DetalleActivity extends Activity {
	public EditText et2;
	public static final String TEXTO = "TEXTO";
	private final int FORM_ACTION = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);
		et2 = (EditText) findViewById(R.id.editTextDetalle);

		TextView text = (TextView) findViewById(R.id.textViewDetalle);

		Intent intent = getIntent();
		String cadena = intent.getStringExtra(MainActivity.TEXTO);

		// pintar en el TexView, lo que me envia la actividad Main
		text.setText(cadena);

		Button formbuton = (Button) findViewById(R.id.buttonOk);
		formbuton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.putExtra(TEXTO, et2.getText().toString());
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		Button buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonBack.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}

}
