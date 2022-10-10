package com.almacen.alamacen202.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    final String CREAR_TABLA_INVENTARIOALM = "CREATE TABLE INVENTARIOALM (" +
            "PRODUCTO VARCHAR (45)," +
            "CANTIDAD INTEGER (11),PRIMARY KEY(PRODUCTO))";

    final String CREAR_TABLA_INVENTARIO = "CREATE TABLE INVENTARIO (" +
            "SUCURSAL VARCHAR (2)," +
            "PRODUCTO VARCHAR (45)," +
            "CANTIDAD INTEGER (11)," +
            "SURTIDO INTEGER (11),PRIMARY KEY(SUCURSAL,PRODUCTO))";



    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_INVENTARIOALM);
        db.execSQL(CREAR_TABLA_INVENTARIO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS CREAR_TABLA_INVENTARIOALM");
        db.execSQL("DROP TABLE IF EXISTS CREAR_TABLA_INVENTARIO");
        onCreate(db);
    }
}
