package com.braga.jow.desafiobugginhodeveloper;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DigitaCPF extends AppCompatActivity {
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digita_cpf);
        final EditText CampoCPF = (EditText)(findViewById(R.id.consultaCPF));
        Button consultaCPF = (Button) (findViewById(R.id.button7));
        Button voltar = (Button) (findViewById(R.id.button8));
        final BancoController crud = new BancoController(getBaseContext());
        consultaCPF.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                final String cpf;
                cpf = CampoCPF.getText().toString();
                if(cpf.length() == 11) {
                    Intent intent = new Intent(DigitaCPF.this, Listagem.class);
                    intent.putExtra("CPF", cpf);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Digite um cpf válido", Toast.LENGTH_SHORT).show();
                }
                }
        });

        voltar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}
