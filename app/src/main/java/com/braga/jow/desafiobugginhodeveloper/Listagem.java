package com.braga.jow.desafiobugginhodeveloper;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Listagem extends AppCompatActivity {
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listagem);

        BancoController crud = new BancoController(getBaseContext());
        cursor = crud.listaFuncionarios();
        String CPF = this.getIntent().getStringExtra("CPF");
        if(CPF == null) {
            if (cursor.getCount() != 0) {
                String[] nomeCampos = new String[]{CriaBanco.NOME, CriaBanco.CPF, CriaBanco.ID};
                int[] idViews = new int[]{R.id.nomeDoFuncionario, R.id.cpfDoFuncionario, R.id.idDoFuncionario};
                SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(), R.layout.lista_layout, cursor, nomeCampos, idViews, 0);
                ListView lista = (ListView) findViewById(R.id.listView);
                lista.setAdapter(adaptador);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String codigo;
                        cursor.moveToPosition(position);
                        codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ID));
                        Intent intent = new Intent(Listagem.this, Alterar.class);
                        intent.putExtra("codigo", codigo);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(), "Nenhum funcionário encontrado.", Toast.LENGTH_SHORT).show();
            }
        }else{
            cursor = crud.consultaFuncionarioPorCPF(CPF);
            if (cursor.getCount() != 0) {
                String[] nomeCampos = new String[]{CriaBanco.NOME, CriaBanco.CPF, CriaBanco.ID};
                int[] idViews = new int[]{R.id.nomeDoFuncionario, R.id.cpfDoFuncionario, R.id.idDoFuncionario};
                SimpleCursorAdapter adaptador = new SimpleCursorAdapter(getBaseContext(), R.layout.lista_layout, cursor, nomeCampos, idViews, 0);
                ListView lista = (ListView) findViewById(R.id.listView);
                lista.setAdapter(adaptador);
                lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String codigo;
                        cursor.moveToPosition(position);
                        codigo = cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.ID));
                        Intent intent = new Intent(Listagem.this, Alterar.class);
                        intent.putExtra("codigo", codigo);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "Nenhum funcionário encontrado.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
