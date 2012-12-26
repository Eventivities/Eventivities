package com.eventivities.android;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.eventivities.android.adapters.LocalesAdapter;
import com.eventivities.android.domain.Producto;
import com.eventivities.android.handlers.LocalHandler;

public class LocalesActivity extends SherlockActivity {
	
	private List<Producto> locales;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_locales);
        
        LocalHandler localHandler = new LocalHandler(this);
        locales = localHandler.obtenerTodos();
		
		GridView gridView = (GridView) findViewById(R.id.GridViewLocales);
		LocalesAdapter adapter = new LocalesAdapter(this, R.layout.item_local, locales);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(itemClickListener);
	}
    
    private OnItemClickListener itemClickListener = new OnItemClickListener() {

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			int localId = locales.get(arg2).getId();

			Intent i = new Intent(LocalesActivity.this, EventosActivity.class);
			Bundle b = new Bundle();
			b.putInt(Param.LOCAL_ID.toString(), localId);
			i.putExtras(b);

			startActivity(i);
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.activity_locales, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_login:
			startActivity(new Intent(LocalesActivity.this, MiPerfilActivity.class));
			break;
		case R.id.menu_tmp_main:
			startActivity(new Intent(LocalesActivity.this, MainActivity.class));
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// ANIMACION DE SALIDA
    	overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
	}
}
