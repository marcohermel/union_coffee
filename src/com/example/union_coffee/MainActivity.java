package com.example.union_coffee;

import android.support.v7.app.ActionBarActivity;
import android.text.method.DateTimeKeyListener;

import java.sql.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Telephony.Sms.Conversations;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.DatePicker;

public class MainActivity extends ActionBarActivity {

	private EditText etUser;
	private EditText etValor;

	private Button btDoar;
	

	private Button btnHistorico;
	private DatePicker dpData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {
			etUser = (EditText) findViewById(R.id.etUser);
			etValor = (EditText) findViewById(R.id.etValor);
			dpData = (DatePicker) findViewById(R.id.dpData);

			btDoar = (Button) findViewById(R.id.btDoar);
			
			btnHistorico = (Button) findViewById(R.id.btnHistorico);

			
			btDoar.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					btDoarOnClick();

				}
			});

		
			btnHistorico.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent troca = new Intent(MainActivity.this, HistoricoActivity.class);
					MainActivity.this.startActivity(troca);
					MainActivity.this.finish();

				}
			});

			SQLiteDatabase db = openOrCreateDatabase("union_coffee_db", Context.MODE_PRIVATE, null);

			StringBuilder SQL = new StringBuilder();

			SQL.append("CREATE TABLE IF NOT EXISTS [doacoes] ( ");
			SQL.append("[Usuario] VARCHAR(50) NOT NULL, ");
			SQL.append("[Valor] DECIMAL(15,2) NOT NULL DEFAULT 0, ");
			SQL.append("[Data] DATETIME NOT NULL) ");
			db.execSQL(SQL.toString());
			// Monta a string SQL
		} catch (Exception ex) {
			Toast.makeText(this, ex.getMessage(), 20000).show();

		}
	}

	private void btDoarOnClick() {

		try {

			SQLiteDatabase db = openOrCreateDatabase("union_coffee_db", Context.MODE_PRIVATE, null);
			StringBuilder SQL = new StringBuilder();

			String data = dpData.getDayOfMonth() + "/" + (dpData.getMonth() + 1) + "/" + dpData.getYear();

			SQL.append("INSERT INTO doacoes");
			SQL.append(" (USUARIO, VALOR, DATA)");
			SQL.append(" values ('" + etUser.getText().toString() + "',");
			SQL.append("" + etValor.getText().toString() + ", ");
			SQL.append("'" + data + "')");
			db.execSQL(SQL.toString());
			Toast.makeText(this, "Operação Efetuada Com Sucesso!", 20000).show();
		} catch (Exception ex) {

			Toast.makeText(this, ex.getMessage(), 20000).show();
		}

	}



	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}