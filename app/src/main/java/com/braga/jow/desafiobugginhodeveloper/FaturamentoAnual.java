package com.braga.jow.desafiobugginhodeveloper;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class FaturamentoAnual extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faturamento_anual);
        final EditText CampoFaturamento = (EditText) findViewById(R.id.editText5);
        final Cursor cursor;
        final BancoController crud;

        crud = new BancoController(getBaseContext());
        cursor = crud.consultaFaturamento();
        Button cadastrar = (Button)findViewById(R.id.button10);
        Button alterar = (Button)findViewById(R.id.button11);
        if (cursor != null && cursor.getCount() != 0) {
            double faturamento;
            cursor.moveToFirst();
            faturamento = cursor.getDouble(cursor.getColumnIndexOrThrow(CriaBanco.FATURAMENTO));
            CampoFaturamento.setText(String.valueOf(faturamento));
            cadastrar.setVisibility(View.INVISIBLE);
            alterar.setVisibility(View.VISIBLE);

        }

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double faturamento;

                if(!CampoFaturamento.getText().toString().trim().equals("") && Double.parseDouble(CampoFaturamento.getText().toString()) > 0) {
                    faturamento = Double.parseDouble(CampoFaturamento.getText().toString());
                    crud.cadastraFaturamentoAnual(faturamento);
                    Toast.makeText(getApplicationContext(), "Faturamento cadastrado com sucesso.", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Insira um valor válido no faturamento", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            double faturamento;

                if(!CampoFaturamento.getText().toString().trim().equals("") && Double.parseDouble(CampoFaturamento.getText().toString()) > 0) {
                    faturamento = Double.parseDouble(CampoFaturamento.getText().toString());
                    crud.alterarFaturamento(faturamento);
                    Toast.makeText(getApplicationContext(), "Faturamento alterado com sucesso.", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Insira um valor válido no faturamento", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
