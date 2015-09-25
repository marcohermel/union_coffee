package com.example.union_coffee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.*;
import android.view.*;

public class HistoricoActivity extends Activity {

	private	Button btnVoltar;
	private TextView tvNome;
	private TextView tvData;
	private TextView tvValorDoado;
	private Button btLimpar;
	private TextView tvValorTotal;

	private Button btConsultar;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_historico);

		btnVoltar = (Button) findViewById(R.id.btnVoltar);
		tvNome = (TextView) findViewById(R.id.tvNome);
		tvData = (TextView) findViewById(R.id.tvData);
		tvValorDoado = (TextView) findViewById(R.id.tvValorDoado);
		
		tvValorTotal = (TextView) findViewById(R.id.tvValorTotal);
	
		btConsultar = (Button) findViewById(R.id.btConsultar);
		btLimpar = (Button) findViewById(R.id.btLimpar);
		btnVoltar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent troca = new Intent(HistoricoActivity.this, MainActivity.class);
				HistoricoActivity.this.startActivity(troca);
				HistoricoActivity.this.finish();
			}
		});
		
		btConsultar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				btConsultarOnClick();

			}
		});
		
		btLimpar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				btLimparOnClick();

			}
		});

		Consultar();
	}
	public void btLimparOnClick() {
		new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Alerta!")
				.setMessage("Tem certeza que deseja excluir todos os dados?")
				.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						LimparDados();
					}

				}).setNegativeButton("Não", null).show();
	}
	private void LimparDados() {

		try {

			SQLiteDatabase db = openOrCreateDatabase("union_coffee_db", Context.MODE_PRIVATE, null);
			StringBuilder SQL = new StringBuilder();

			SQL.append(" DELETE FROM doacoes");
			Toast.makeText(this, "Dados Excluidos com sucesso!", 2000).show();
			db.execSQL(SQL.toString());
			Toast.makeText(this, "Operação Efetuada Com Sucesso!", 2000).show();
		} catch (Exception ex) {

			Toast.makeText(this, ex.getMessage(), 2000).show();
		}

	}
	private void Consultar() {
		try {

			SQLiteDatabase db = openOrCreateDatabase("union_coffee_db", Context.MODE_PRIVATE, null);
			StringBuilder SQL = new StringBuilder();

			// String Mes[] = new String[] { ("" + (dpData.getMonth() + 1)) };

			Cursor c = db.query("doacoes", new String[] { "USUARIO", "DATA", "VALOR" }, "", null, null, null, null);

			String nome = "";
			String data = "";
			String valores = "";
			if (c.getCount() > 0) {

				while (c.moveToNext()) {

					//Toast.makeText(this, "	DATA: " + c.getString(1), 2000).show();
					nome += "\n " + c.getString(0);
					data += "\n " + c.getString(1);
					valores += "\n " + c.getString(2);
				}

			} else {
				Toast.makeText(this, "Não encontrou registros!", 2000).show();

			}
			tvNome.setText(nome);
			tvData.setText(data);
			tvValorDoado.setText(valores);
			
		} catch (Exception ex) {

			Toast.makeText(this, ex.getMessage(), 20000).show();
		}
	}
	private void btConsultarOnClick() {
		try {

			SQLiteDatabase db = openOrCreateDatabase("union_coffee_db", Context.MODE_PRIVATE, null);
			StringBuilder SQL = new StringBuilder();

			// String Mes[] = new String[] { ("" + (dpData.getMonth() + 1)) };

			Cursor c = db.query("doacoes", new String[] { "USUARIO", "DATA", "VALOR" }, "", null, null, null, null);

			Double total = 0.00;

			if (c.getCount() > 0) {

				while (c.moveToNext()) {

					//Toast.makeText(this, "	DATA: " + c.getString(1), 2000).show();

					total += Double.parseDouble(c.getString(2));
				}

			} else {
				Toast.makeText(this, "Não encontrou registros!", 2000).show();

			}
			
			tvValorTotal.setText("Valor Total: " + total);
			
		} catch (Exception ex) {

			Toast.makeText(this, ex.getMessage(), 20000).show();
		}
	}

}