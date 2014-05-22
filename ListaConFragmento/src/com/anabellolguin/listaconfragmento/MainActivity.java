package com.anabellolguin.listaconfragmento;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity implements MyTextFragment.IInput {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			FragmentTransaction fragmentTransaction = getFragmentManager()
					.beginTransaction();// Obtener el FragmentManager
			// iniciar las transacciones
			// a–adir la instancia del fragmento al contenedor
			fragmentTransaction.add(R.id.lista, new TodoListFragment(), "list");
			fragmentTransaction.add(R.id.input, new MyTextFragment());
			fragmentTransaction.commit();
		}

	}

	public void addItem(String nexItem) {
		((TodoListFragment) getFragmentManager().findFragmentByTag("list"))
				.addItem(nexItem);

	}
}
