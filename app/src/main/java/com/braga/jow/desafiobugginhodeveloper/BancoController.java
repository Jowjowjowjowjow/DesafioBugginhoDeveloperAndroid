package com.braga.jow.desafiobugginhodeveloper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Jow on 29/12/2016.
 */

public class BancoController {
    private SQLiteDatabase db;
    private CriaBanco banco;

    public BancoController(Context context){
        banco = new CriaBanco(context);
    }

    public String insereFuncionario(String nome, String cpf, String cargo, double salario){
        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(CriaBanco.NOME, nome);
        valores.put(CriaBanco.CPF, cpf);
        valores.put(CriaBanco.CARGO, cargo);
        valores.put(CriaBanco.SALARIO, salario);

        resultado = db.insert(CriaBanco.TABELA, null, valores);
        db.close();
        if(resultado == -1)
            return "Erro ao cadastrar funcionário, talvez o cpf já tenha sido cadastrado";
        else
            return "Funcionário cadastrado com sucesso";
    }
    public String cadastraFaturamentoAnual(double faturamento){
        ContentValues valores;
        long resultado;
        db = banco.getWritableDatabase();
        valores = new ContentValues();
        valores.put(CriaBanco.FATURAMENTO,faturamento);
        valores.put(CriaBanco.ID2, 1);
        resultado = db.insert(CriaBanco.TABELA2,null,valores);
        db.close();
        if(resultado == -1)
            return "Erro ao cadastrar faturamento";
        else
            return "Faturamento cadastrado com sucesso";
    }

    public Cursor listaFuncionarios(){
        Cursor cursor;
        String[] campos = {CriaBanco.ID, CriaBanco.NOME, CriaBanco.CPF, CriaBanco.CARGO, CriaBanco.SALARIO};
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.TABELA, campos, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Cursor consultaFuncionarioPorID(int id){
        Cursor cursor;
        String[] campos = {CriaBanco.ID, CriaBanco.NOME, CriaBanco.CPF, CriaBanco.CARGO, CriaBanco.SALARIO};
        String where = CriaBanco.ID + "=" +id;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.TABELA,campos,where,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public Double consultaBonus(String cargo){
        Cursor cursor;
        int quantidadeFuncionarios = 0;
        double faturamento;
        double bonus = 0;
        double porcentagem = 0;

        String[] campos = {CriaBanco.ID, CriaBanco.SALARIO};
        String where = CriaBanco.CARGO + "='" +cargo +"'";
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.TABELA,campos,where,null,null,null,null);
        if(cursor!=null){
            quantidadeFuncionarios = cursor.getCount();
        }
        db.close();
        cursor = consultaFaturamento();
        if(cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            faturamento = cursor.getDouble(cursor.getColumnIndexOrThrow(CriaBanco.FATURAMENTO));

            switch (cargo){
                case("Programador"):
                    porcentagem = 1.5;
                    break;
                case("Designer"):
                    porcentagem = 1.5;
                    break;
                case("Gerente"):
                    porcentagem = 3.0;
                    break;
                case("Atendimento"):
                    porcentagem = 1.0;
                    break;
            }
            bonus = ((faturamento*porcentagem)/100)/quantidadeFuncionarios;
        }else{
            bonus = 0;
        }
        return bonus;
    }
    public Cursor consultaFaturamento(){
        Cursor cursor;
        String[] campos = {CriaBanco.ID2, CriaBanco.FATURAMENTO};
        String where = CriaBanco.ID2 + "= 1";
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.TABELA2,campos,where,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }
    public Cursor consultaFuncionarioPorCPF(String cpf){
        Cursor cursor;
        String[] campos = {CriaBanco.ID, CriaBanco.NOME, CriaBanco.CPF, CriaBanco.CARGO, CriaBanco.SALARIO};
        String where = CriaBanco.CPF + "=" + cpf;
        db = banco.getReadableDatabase();
        cursor = db.query(CriaBanco.TABELA,campos,where,null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        db.close();
        return cursor;
    }

    public void alterarFuncionario(int id, String nome, String cpf, String cargo, String salario){
        ContentValues valores;
        String where;


        db = banco.getWritableDatabase();
        where = CriaBanco.ID + "=" +id;

        valores = new ContentValues();
        valores.put(CriaBanco.NOME,nome);
        valores.put(CriaBanco.CPF,cpf);
        valores.put(CriaBanco.CARGO,cargo);
        valores.put(CriaBanco.SALARIO,salario);

        db.update(CriaBanco.TABELA,valores,where,null);
        db.close();
    }

    public void alterarFaturamento(double faturamento){
        ContentValues valores;
        String where;

        db = banco.getWritableDatabase();
        where = CriaBanco.ID2 + "= 1";

        valores = new ContentValues();
        valores.put(CriaBanco.FATURAMENTO,faturamento);
        db.update(CriaBanco.TABELA2,valores,where,null);
        db.close();
    }
    public void deletarFuncionario(int id){
        String where = CriaBanco.ID + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CriaBanco.TABELA,where,null);
        db.close();
    }

}
