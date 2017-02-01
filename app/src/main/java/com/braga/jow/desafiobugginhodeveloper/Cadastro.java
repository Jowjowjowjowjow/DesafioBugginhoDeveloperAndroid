package com.braga.jow.desafiobugginhodeveloper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cadastro extends AppCompatActivity {
    Funcionario funcionario = new Funcionario();
    Spinner cargo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        Button cadastrar = (Button)findViewById(R.id.button);
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cadastrar();
            }
        });

        Button voltar = (Button)findViewById(R.id.button2);
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cadastro.super.finish();
            }
        });


        cargo = (Spinner)findViewById(R.id.spinner2);

        List<String> lista = new ArrayList<String>();
        lista.add("Programador");
        lista.add("Designer");
        lista.add("Gerente");
        lista.add("Atendimento");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lista);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cargo.setAdapter(dataAdapter);

            }

    public void Cadastrar(){
        String resultado;
        BancoController crud = new BancoController(getBaseContext());
        EditText campoNome, campoCPF, campoSalario;
        campoNome = (EditText) findViewById(R.id.editText);
        campoCPF = (EditText) findViewById(R.id.editText2);
        campoSalario = (EditText)findViewById(R.id.editText3);

        if(campoNome.getText().toString().trim().length() != 0) {
            if (campoCPF.getText().toString().length() == 11) {
                if(campoSalario.getText().toString().length() != 0) {
                    funcionario.setNome(campoNome.getText().toString());
                    funcionario.setCpf(campoCPF.getText().toString().trim());
                    funcionario.setCargo(String.valueOf(cargo.getSelectedItem().toString()));
                    funcionario.setSalario(Double.parseDouble(campoSalario.getText().toString()));

                    campoNome.setText("");
                    campoCPF.setText("");
                    cargo.setSelection(0);
                    campoSalario.setText("");
                    resultado = crud.insereFuncionario(funcionario.getNome(), funcionario.getCpf(), funcionario.getCargo(), funcionario.getSalario());

                    Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Digite um salário válido", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Digite um cpf válido!", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Digite um nome válido!", Toast.LENGTH_SHORT).show();
        }


    }




}
