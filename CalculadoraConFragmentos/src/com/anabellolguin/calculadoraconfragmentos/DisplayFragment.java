package com.anabellolguin.calculadoraconfragmentos;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayFragment extends Fragment {
	
	TextView display;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_display, container, false);
		
		display = (TextView)view.findViewById(R.id.textView);
		
		return view;
	}
	
	public void muestraResultado(String valor){
		display.setText(valor);//despliega el valor en el cuadro de texto que contiene el fragmentoDisplay
		
	}
}
