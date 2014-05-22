package com.anabellolguin.calculadoraconfragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class KeyboardFragment extends Fragment {
	
	public interface ICalculadora {
		public void pulsaDigito(int d);
		public void pulsaOperador(String op);
	}
	
	private ICalculadora activity;
	
	private int numeros[] = { R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9 }; //array que obtiene los botones numericos
	private int operadores[] = {R.id.btnMM, R.id.btnAC, R.id.btnAdd, R.id.btnCom, R.id.btnIgual, R.id.btnDiv, R.id.btnMul, R.id.btnPorcentaje, R.id.btnRes};//array que optiene los botones operador
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			this.activity = (ICalculadora)activity;
		} catch(ClassCastException e) {
			throw new ClassCastException(
					activity.toString() + " must implement ICaalculadora");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_keyboard, container, false);
		
		
		for (int i = 0; i < numeros.length; i++) {
			Button btn = (Button)view.findViewById(numeros[i]);
			btn.setOnClickListener(new OnClickListener() {//por cada elemento del array, se agrega un escuchador que escucha el evento click
				
				@Override
				public void onClick(View v) {
					Button btn = (Button)v;
					Integer d = Integer.valueOf(btn.getText().toString());
					
					activity.pulsaDigito(d);
				}
			});
			
			
		}
		

		
		for (int j = 0; j < operadores.length; j++) {
			Button btn = (Button)view.findViewById(operadores[j]);
			btn.setOnClickListener(new OnClickListener() {//por cada elemento del array, se agrega un escuchador que escucha el evento click
				
				@Override
				public void onClick(View v) {
					Button btn = (Button)v;
					String o = String.valueOf(btn.getText().toString());
					
					activity.pulsaOperador(o);
				}
			});
			
			
		}
		
		return view;
	}
	
	
	

}
