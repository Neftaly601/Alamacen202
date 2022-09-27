package com.almacen.alamacen202;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityPerfil extends AppCompatActivity {


    private TextView usr;
    private TextView password;
    private TextView name;
    private TextView apellido;
    private TextView tipo;
    private TextView sucursal;
    private TextView mail;
    private TextView code;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    String strusr, strpass, strname, strlname, strtype, strtype2, strbran, strma, strco, strcodBra, StrServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        usr = (TextView) findViewById(R.id.txtusr);
        password = (TextView) findViewById(R.id.txtpass);
        name = (TextView) findViewById(R.id.txtname);
        apellido = (TextView) findViewById(R.id.txtlname);
        tipo = (TextView) findViewById(R.id.txttype);
        sucursal = (TextView) findViewById(R.id.txtbranch);
        mail = (TextView) findViewById(R.id.txtemail);

        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();

        strusr = preference.getString("usuario", null);
        strpass =preference.getString("pasword", null);
        strname = preference.getString("name", null);
        strlname = preference.getString("lname", null);
        strtype =preference.getString("type", null);
        strbran =preference.getString("branch", null);
        strma = preference.getString("email", null);
        strcodBra = preference.getString("codBra", null);
        strco =preference.getString("code", null);
        StrServer = preference.getString("Server", null);
        strtype2 =preference.getString("type2", null);

        usr.setText("                                          " + strusr);
        password.setText("                                          " + strpass);
        name.setText(strname);
        apellido.setText(strlname);
        tipo.setText((strtype2 != null )?strtype2:strtype);
        sucursal.setText(strbran);
        mail.setText(strma);


    }
}