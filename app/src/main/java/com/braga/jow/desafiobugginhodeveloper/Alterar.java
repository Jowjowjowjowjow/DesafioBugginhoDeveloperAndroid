package com.braga.jow.desafiobugginhodeveloper;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Alterar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final EditText nome;
        final EditText cpf;
        final EditText salario;
        final Button alterar;
        final Button deletar;
        final Cursor cursor;
        final BancoController crud;
        final String codigo;
        final Spinner cargoSpinner;
        final EditText bonus;
        final double valorBonus;
        final TextView bonusTexto;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar);

        codigo = this.getIntent().getStringExtra("codigo");
        crud = new BancoController(getBaseContext());
        bonus = (EditText)findViewById(R.id.editText4);
        nome = (EditText)findViewById(R.id.editText6);
        cpf = (EditText)findViewById(R.id.editText7);
        bonusTexto = (TextView)findViewById(R.id.textView13);
        cargoSpinner = (Spinner)findViewById(R.id.spinner);
        List<String> lista = new ArrayList<String>();
        lista.add("Programador");
        lista.add("Designer");
        lista.add("Gerente");
        lista.add("Atendimento");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lista);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cargoSpinner.setAdapter(dataAdapter);
        salario = (EditText)findViewById(R.id.editText9);
        alterar = (Button)findViewById(R.id.button3);
        deletar = (Button)findViewById(R.id.button5);
        cursor = crud.consultaFuncionarioPorID(Integer.parseInt(codigo));
        switch (cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.CARGO))){
            case("Programador"):
                cargoSpinner.setSelection(0);
                break;
            case("Designer"):
                cargoSpinner.setSelection(1);
                break;
            case("Gerente"):
                cargoSpinner.setSelection(2);
                break;
            case("Atendimento"):
                cargoSpinner.setSelection(3);
                break;
        }

        valorBonus = crud.consultaBonus(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.CARGO)));
        if(valorBonus == 0){
            bonus.setVisibility(View.INVISIBLE);
            bonusTexto.setText("Cadastre o faturamento anual para ver o bônus.");
        }else{
            bonus.setText(String.valueOf(valorBonus));
            bonus.setEnabled(false);
        }

        nome.setText(cursor.getString(cursor.getColumnIndexOrThrow((CriaBanco.NOME))));
        cpf.setText(cursor.getString(cursor.getColumnIndexOrThrow(CriaBanco.CPF)));
        salario.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndexOrThrow(CriaBanco.SALARIO))));
        alterar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(nome.getText().toString().trim().length() != 0) {
                    if (salario.getText().toString().length() != 0) {
                        if (cpf.getText().toString().length() == 11) {
                            crud.alterarFuncionario(Integer.parseInt(codigo), nome.getText().toString(), cpf.getText().toString(), cargoSpinner.getSelectedItem().toString(), salario.getText().toString());
                            Toast.makeText(getApplicationContext(), "Funcionário alterado com sucesso", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Digite um cpf válido", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Digite um salário válido", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Digite um nome válido", Toast.LENGTH_SHORT).show();
                }
            }
        });


        deletar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                crud.deletarFuncionario(Integer.parseInt(codigo));
                Toast.makeText(getApplicationContext(), "Funcionário deletado com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
