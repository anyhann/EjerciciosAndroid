package com.anabellolguin.listaconfragmento;

import java.util.ArrayList;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TodoListFragment extends ListFragment {

	public final static String ITEMS_ARRAY = "ITEMS_ARRAY";

	// // Create the Array List of to do items
	private ArrayList<String> items;
	// // Create the Array Adapter to bind the array to the List View
	private ArrayAdapter<String> aa;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		items = new ArrayList<String>();
		aa = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, items);

		// Bind the Array Adapter to the List View
		setListAdapter(aa);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	public void addItem(String txt) {
		items.add(0, txt);
		aa.notifyDataSetChanged();
	}

	public void onActivityCreate(Bundle inState) {
super.onActivityCreated(inState);
        
        if (inState != null) {    		
			// AÐadir a la lista
			items.addAll(inState.getStringArrayList(ITEMS_ARRAY));
			aa.notifyDataSetChanged();
        }
		
		
	}

	public void onSaveInstance(Bundle autoState) {
		outState.putStringArrayList(ITEMS_ARRAY, items);

		super.onSaveInstanceState(outState);
	}
}
