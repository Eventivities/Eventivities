package com.eventivities.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.eventivities.android.domain.Evento;
import com.eventivities.android.domain.Local;
import com.eventivities.android.util.TnUtil;
import com.eventivities.android.util.ViewUtil;

public class DetalleEventoActivity extends SherlockActivity {
	
	private Evento evento;
	private String nombreLocal;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_evento);
		getSupportActionBar().setHomeButtonEnabled(true);
        
        Bundle extras = getIntent().getExtras();
		if(extras != null)
		{
			evento = (Evento) extras.getSerializable(Param.EVENTO.toString());
			nombreLocal=extras.getString(Param.LOCAL_NOMBRE.toString());
	
			if (evento != null) {
				TextView textViewNombre = (TextView) findViewById(R.id.textViewNombreEvento);
				if (textViewNombre != null)
					textViewNombre.setText(evento.getNombre());
				
				TextView textViewFecha = (TextView)findViewById(R.id.textViewFechaEvento);
				if (textViewFecha != null) {
					String formatoRango = getString(R.string.formato_rango_fecha);
					String rangoFecha = ViewUtil.rangoFecha(formatoRango, evento.getFechaInicio(), evento.getFechaFin());
					textViewFecha.setText(rangoFecha);
				}
				
				TextView textViewPrecio = (TextView)findViewById(R.id.textViewPrecioEvento);
				if (textViewFecha != null) {
					String format = getString(R.string.formato_precio);
					textViewPrecio.setText(String.format(format, evento.getPrecio()));
				}

				Button botonVerComentarios=(Button) findViewById(R.id.detalleEvento_BotonComentario);
				//TextView textViewPuntEvento = (TextView)findViewById(R.id.textViewPuntEvento);
				if (botonVerComentarios != null) {
					//textViewPuntEvento.setText(ViewUtil.obtenerEstrellas(evento.getMedia()));
					botonVerComentarios.setText( getString(R.string.detalle_eventoBotonComentarios));
				}
				
				Button botonVerPuntos=(Button) findViewById(R.id.detalleEvento_botonVotar);
				//TextView textViewPuntEvento = (TextView)findViewById(R.id.textViewPuntEvento);
				if (botonVerPuntos != null) {
					//textViewPuntEvento.setText(ViewUtil.obtenerEstrellas(evento.getMedia()));
					botonVerPuntos.setText(ViewUtil.obtenerEstrellas(evento.getMedia()));
				}
				
				TextView textViewDirector = (TextView)findViewById(R.id.textViewDirector);
				if (textViewDirector != null) {
					String format = getString(R.string.formato_director);
					textViewDirector.setText(String.format(format, evento.getDirector()));
				}
				
				TextView textViewInterpretes = (TextView)findViewById(R.id.textViewInterpretes);
				if (textViewInterpretes != null) {
					String format = getString(R.string.formato_interpretes);
					textViewInterpretes.setText(String.format(format, evento.getInterpretes()));
				}
				
				
				TextView textViewDescripcion = (TextView)findViewById(R.id.textViewDescripcion);
				if (textViewDescripcion != null) {
					textViewDescripcion.setText(evento.getDescripcion());
				}
				
				setTitle(evento.getNombre());
			}
		}
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.general, menu);
		return true;
	}
	
	public void verComentarios(View v){

		TnUtil.vibrar(this);
		Intent i = new Intent(DetalleEventoActivity.this, VerComentariosActivity.class);
		Bundle b = new Bundle();
		b.putSerializable(Param.EVENTO.toString(), evento);
		b.putString(Param.LOCAL_NOMBRE.toString(), nombreLocal);
		try{
		  i.putExtras(b);
		  startActivity(i);
		  overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		}catch(Exception e){
			TnUtil.escribeLog("¿ Una Excepcionnn ?!! \n"+e.getCause());
			Toast.makeText(this , "\n No se ha podido presentar los Comentarios \n\n",Toast.LENGTH_SHORT).show();
		}
    	//
    		
	}

	public void aVotar(View v){


		Intent i = new Intent(DetalleEventoActivity.this, VotarActivity.class);
		Bundle b = new Bundle();
		b.putSerializable(Param.EVENTO.toString(), evento); 
		b.putString(Param.LOCAL_NOMBRE.toString(), nombreLocal);
		i.putExtras(b);
		startActivity(i);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(DetalleEventoActivity.this, LocalesActivity.class)
			.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			break;
		case R.id.menu_login:
			startActivity(new Intent(DetalleEventoActivity.this, MiPerfilActivity.class)
			.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			break;
		case R.id.menu_location:
			startActivity(new Intent(DetalleEventoActivity.this, UbicacionActivity.class)
			.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
