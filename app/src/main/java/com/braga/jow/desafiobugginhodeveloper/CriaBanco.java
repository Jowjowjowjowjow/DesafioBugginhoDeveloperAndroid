package com.braga.jow.desafiobugginhodeveloper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jow on 29/12/2016.
 */

public class CriaBanco extends SQLiteOpenHelper {
    public static final String NOME_BANCO = "BD.db";
    public static final String TABELA = "funcionarios";
    public static final String ID = "_id";
    public static final String NOME = "nome";
    public static final String CPF = "cpf";
    public static final String CARGO = "cargo";
    public static final String SALARIO = "salario";
    public static final int VERSAO = 5;
    public static final String TABELA2 ="faturamento";
    public static final String FATURAMENTO = "faturamentoAnual";
    public static final String ID2 = "_id";

    public CriaBanco(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String sql = "CREATE TABLE "+TABELA+"("+ID+" integer primary key autoincrement," + NOME +" text not null," + CPF +" integer(11) not null unique," + CARGO + " text not null, "+SALARIO +" double(8) not null)";
        db.execSQL(sql);
        String sql2 = "CREATE TABLE "+ TABELA2 + "("+ID+" integer primary key," +FATURAMENTO+" double(8) not null)";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS " + TABELA);
        db.execSQL("DROP TABLE IF EXISTS " +TABELA2);
        onCreate(db);
    }
}
