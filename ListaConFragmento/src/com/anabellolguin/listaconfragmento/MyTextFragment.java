package com.anabellolguin.listaconfragmento;



import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MyTextFragment extends Fragment {
	
	public interface IInput {
		public void addItem(String txt);
	}
	
	EditText myEditText;
	IInput activity;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			this.activity = (IInput)activity;
		} catch(ClassCastException e) {
			throw new ClassCastException(activity.toString() + " no implementa la interfaz IInput");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_text, container, false);
		
		myEditText = (EditText)view.findViewById(R.id.editText1);
		Button btn = (Button)view.findViewById(R.id.buttonAdd);
		
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				activity.addItem(myEditText.getText().toString());
			}
		});
		
		return view;
	}
	
	
		
	}
	

