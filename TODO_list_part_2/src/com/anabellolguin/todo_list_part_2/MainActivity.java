package com.anabellolguin.todo_list_part_2;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MainActivity extends Activity implements NewItemFragment.OnNewItemAddedListener {

		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// Inflate your view
			setContentView(R.layout.activity_main);

			if(savedInstanceState == null) {
				// Get references to the Fragments
				android.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
				fragmentTransaction.add(R.id.editTextLayout, new NewItemFragment());
				fragmentTransaction.add(R.id.listLayout, new ToDoListFragment(), "list");
				fragmentTransaction.commit();
			}
		}

		public void addItem(String newItem) {
			((ToDoListFragment)getFragmentManager().findFragmentByTag("list")).addItem(newItem);
		}

	
}
