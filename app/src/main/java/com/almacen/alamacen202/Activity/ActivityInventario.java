package com.almacen.alamacen202.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.Adapter.AdaptadorListProductos;
import com.almacen.alamacen202.Adapter.AdaptadorListaFolios;
import com.almacen.alamacen202.Adapter.AdapterInventario;
import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.Folios;
import com.almacen.alamacen202.SetterandGetters.Inventario;
import com.almacen.alamacen202.Sqlite.ConexionSQLiteHelper;
import com.almacen.alamacen202.XML.XMLActualizaInv;
import com.almacen.alamacen202.XML.XMLFolios;
import com.almacen.alamacen202.XML.XMLlistInv;
import com.almacen.alamacen202.includes.MyToolbar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import dmax.dialog.SpotsDialog;

public class ActivityInventario extends AppCompatActivity {
    private SharedPreferences preference,preferenceF;
    private SharedPreferences.Editor editor;
    private boolean comprobar=false;
    private int posicion=0;
    private String strusr,strpass,strServer,strbran,codeBar,ProductoAct="",folio="",fecha="",hora="",mensaje;
    private ArrayList<Inventario> listaInv = new ArrayList<>();
    private EditText txtFolioInv,txtProductoVi,txtFechaI,txtHoraI,txtProducto,txtCant;
    private ArrayList<Folios>listaFol;
    private Button btnGuardar,btnSincronizar;
    private CheckBox chbMan;
    private RecyclerView rvInventario;
    private AdapterInventario adapter;
    private AlertDialog mDialog;
    private InputMethodManager keyboard;
    private ConexionSQLiteHelper conn;
    private SQLiteDatabase db;
    private RecyclerView rvFolios;//para alertdialog
    private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        MyToolbar.show(this, "Inventario", true);
        preferenceF = getSharedPreferences("Folio", Context.MODE_PRIVATE);//para guardar folio
        editor = preferenceF.edit();

        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);

        folio=preferenceF.getString("folio", "");
        fecha=preferenceF.getString("fechaI", "");
        hora=preferenceF.getString("horaI", "");

        strusr = preference.getString("user", "null");
        strpass = preference.getString("pass", "null");
        strServer = preference.getString("Server", "null");
        strbran = preference.getString("codBra", "null");
        codeBar = preference.getString("codeBar", "null");
        mDialog = new SpotsDialog.Builder().setContext(ActivityInventario.this).
                setMessage("Espere un momento...").build();

        txtFolioInv     = findViewById(R.id.txtFolioInv);
        txtFechaI       = findViewById(R.id.txtFechaI);
        txtHoraI        = findViewById(R.id.txtHoraI);
        txtProducto     = findViewById(R.id.txtProducto);
        txtProductoVi   = findViewById(R.id.txtProductoVi);
        txtCant         = findViewById(R.id.txtCant);
        btnGuardar      = findViewById(R.id.btnGuardar);
        btnSincronizar  = findViewById(R.id.btnSincronizar);
        chbMan          = findViewById(R.id.chbMan);
        rvInventario    = findViewById(R.id.rvInventario);

        conn = new ConexionSQLiteHelper(ActivityInventario.this, "bd_INVENTARIO", null, 1);
        db = conn.getReadableDatabase();
        rvInventario.setLayoutManager(new LinearLayoutManager(ActivityInventario.this));
        keyboard = (InputMethodManager) getSystemService(ActivityInventario.INPUT_METHOD_SERVICE);

        txtProducto.requestFocus();
        txtProducto.setInputType(InputType.TYPE_NULL);

        chbMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                txtProducto.setText("");
                txtProducto.requestFocus();
                txtProductoVi.setText("");
                if (b){
                    //keyboard.showSoftInput(txtProducto, InputMethodManager.SHOW_IMPLICIT);
                    txtCant.setEnabled(true);
                    txtCant.setText("");
                    //keyboard.showSoftInput(Cantidad, InputMethodManager.SHOW_IMPLICIT);
                    btnGuardar.setEnabled(true);
                    btnGuardar.setBackgroundTintList(ColorStateList.
                            valueOf(getResources().getColor(R.color.AzulBack)));
                }else {
                    txtCant.setText("1");
                    txtCant.setEnabled(false);
                    keyboard.hideSoftInputFromWindow(txtCant.getWindowToken(), 0);
                    btnGuardar.setEnabled(false);
                    btnGuardar.setBackgroundTintList(ColorStateList.
                            valueOf(getResources().getColor(R.color.ColorGris)));
                }//else
            }//oncheckedchange
        });//chbMan.setoncheckedchange

        //EVENTOS txtProducto
        txtProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                ProductoAct=editable.toString();
                if (!editable.toString().equals("")) {
                    txtProductoVi.setText(ProductoAct);
                    if (codeBar.equals("Zebra")) {//codebar
                        if (!chbMan.isChecked()) {//manual no
                            buscarEnSql(ProductoAct,compararCantidad(ProductoAct)+"");
                            txtProducto.setText("");
                        }else{//manual si
                            txtCant.requestFocus();
                            keyboard.showSoftInput(txtCant, InputMethodManager.SHOW_IMPLICIT);
                        }//else
                    } else{
                        for (int i = 0; i < editable.length(); i++) {
                            char ban;
                            ban = editable.charAt(i);
                            if (ban == '\n') {
                                if (!chbMan.isChecked()) {//manual no
                                    buscarEnSql(ProductoAct,compararCantidad(ProductoAct)+"");
                                }else{//manual si
                                    txtCant.requestFocus();
                                    keyboard.showSoftInput(txtCant, InputMethodManager.SHOW_IMPLICIT);
                                }//else
                                break;
                            }//if
                        }//for
                    }//else
                }//if !editable
            }//after
        });//txtProducto.addTextChanged


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1=ProductoAct;
                String v2=txtCant.getText().toString();
                if(!v1.equals("") && !v2.equals("")){
                    buscarEnSql(v1,v2);
                    keyboard.hideSoftInputFromWindow(txtCant.getWindowToken(), 0);
                    txtProducto.setText("");
                    txtProducto.requestFocus();
                    keyboard.hideSoftInputFromWindow(txtProducto.getWindowToken(), 0);
                    if (chbMan.isChecked()) {
                        txtCant.setText("");
                    }else{
                        txtCant.setText("1");
                    }//else
                }else{
                    Toast.makeText(ActivityInventario.this, "Campos vacios", Toast.LENGTH_SHORT).show();
                }
            }//onclick
        });//btnGuardar setonclick
        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventario.this);
                builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AsyncActualizaInv().execute();
                    }//onclick
                });//positive button
                builder.setNegativeButton("CANCELAR",null);
                builder.setCancelable(false);
                builder.setTitle("AVISO").setMessage("¿Desea sincronizar?").create().show();
            }//onclcik
        });//btnSincronizar onclick


        //FOLIO
        if(folio.equals("")){//si no hay folio guardado
            new AsyncFolios().execute();
        }else{
            comprobar=true;
            new AsyncFolios().execute();
        }//else
    }//onCreate

    public void buscaFolios(View v){
        if(!folio.equals("")){//si ya hay folio guardado
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventario.this);
            builder.setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }//onclick
            });//positive button
            builder.setNegativeButton("SELECCIONAR OTRO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    editor.clear().commit();
                    eliminarSql("");
                    new AsyncFolios().execute();
                }
            });//negative
            builder.setCancelable(false);
            builder.setTitle("AVISO").setMessage("Estas trabajando con un folio"+
                    "¿Desea seleccionar uno nuevo?(Se perderan los cambios no guardados)").create().show();
        }else{//si no hay folio guardado
            new AsyncFolios().execute();
        }//else
    }//buscarFolios

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
    public int compararCantidad(String prod){//en caso de que no sea manual, se toma la cantidad que se tenia y se suma 1
        int cant=1;
        for(int i=0;i<listaInv.size();i++){
            if(listaInv.get(i).getProducto().equals(prod)){
                cant=Integer.parseInt(listaInv.get(i).getCantidad())+1;
                break;
            }//if
        }//for
        return cant;
    }//compararCant

    public void seleccionEnAlertFolios(View v){
        int pos = rvFolios.getChildAdapterPosition(rvFolios.findContainingItemView(v));
        folio=listaFol.get(pos).getFolio();
        fecha=listaFol.get(pos).getFecha();
        hora=listaFol.get(pos).getHora();
        txtFolioInv.setText(folio);
        txtFechaI.setText(fecha);
        txtHoraI.setText(hora);
        editor.putString("folio", folio);
        editor.putString("fechaI", fecha);
        editor.putString("horaI", hora);
        editor.commit();
        rvInventario.setAdapter(null);
        new AsyncListInv().execute();
        dialog.dismiss();
    }//seleccionEnAlertFolios

    @SuppressLint("MissingInflatedId")
    public void listaFolio(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_info_folios, null);
        builder.setView(dialogView);

        rvFolios =dialogView.findViewById(R.id.rvFolios);
        GridLayoutManager gl = new GridLayoutManager(this, 1);
        rvFolios.setLayoutManager(gl);

        AdaptadorListaFolios adapter = new AdaptadorListaFolios(listaFol);
        rvFolios.setAdapter(null);
        rvFolios.setAdapter(adapter);

        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog.show();
    }//listaFolio


    private class AsyncFolios extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {mDialog.show();}

        @Override
        protected Void doInBackground(Void... params) {
            listaFol = new ArrayList<>();
            conectaFolios();
            return null;
        }//doInBackground

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if(comprobar==true){//comprobar si folio esta abierto
                comprobar=false;
                if (listaFol.size()>0) {//ahora busca en la lista de folios disponibles
                    boolean var=false;
                    for(int i=0;i<listaFol.size();i++){//buscar si el folio esta entre los folios que estan abiertos
                        if(listaFol.get(i).getFolio().equals(folio)){
                            var=true;
                            break;
                        }//if
                    }//for
                    if(var==true){//si esta abierto
                        txtFolioInv.setText(folio);
                        txtFechaI.setText(fecha);
                        txtHoraI.setText(hora);
                        consultaSql();
                    }else {//si no esta abierto
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventario.this);
                        builder.setMessage("Folio cerrado");
                        builder.setCancelable(false);
                        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new AsyncFolios().execute();
                            }
                        });//negative botton
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }//else
                }else{//como no hay folios disponibles el folio no esta abierto
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventario.this);
                    builder.setMessage("Folio cerrado");
                    builder.setCancelable(false);
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new AsyncFolios().execute();
                        }
                    });//negative botton
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }//else
            }else{//cuando no se quiere comprobar el folio
                if (listaFol.size()>0) {
                    listaFolio();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventario.this);
                    builder.setMessage("No se encontró folios");
                    builder.setCancelable(false);
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });//negative botton
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }//else
            }//else
        }//onPostExecute
    }//AsyncFolios


    private void conectaFolios() {
        String SOAP_ACTION = "Folios";
        String METHOD_NAME = "Folios";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLFolios soapEnvelope = new XMLFolios(SoapEnvelope.VER11);
            soapEnvelope.XMLFol(strusr, strpass,strbran);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
                response0 = (SoapObject) response0.getProperty(i);
                listaFol.add(new Folios((response0.getPropertyAsString("k_folio").equals("anyType{}")?"" : response0.getPropertyAsString("k_folio")),
                        (response0.getPropertyAsString("k_fecha").equals("anyType{}")?"" : response0.getPropertyAsString("k_fecha")),
                        (response0.getPropertyAsString("k_hora").equals("anyType{}")? "" : response0.getPropertyAsString("k_hora"))));
            }//for
        } catch (Exception ex) {}//catch
    }//conectaFolios


    private class AsyncListInv extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {mDialog.show();}

        @Override
        protected Void doInBackground(Void... params) {
            listaInv.clear();
            conectaListInv();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if (listaInv.size()>0) {
                for(int i=0;i<listaInv.size();i++){
                    insertarSql(listaInv.get(i).getProducto(),listaInv.get(i).getCantidad());
                }//for
                consultaSql();
            }else{
                Toast.makeText(ActivityInventario.this, "Ningun dato", Toast.LENGTH_SHORT).show();
            }
            txtProducto.setText("");
        }//onPostExecute
    }//AsynInsertInv


    private void conectaListInv() {
        String SOAP_ACTION = "ListInv";
        String METHOD_NAME = "ListInv";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLlistInv soapEnvelope = new XMLlistInv(SoapEnvelope.VER11);
            soapEnvelope.XMLLI(strusr, strpass, folio,strbran);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            for (int i = 0; i < response.getPropertyCount(); i++) {
                SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;
                response0 = (SoapObject) response0.getProperty(i);
                listaInv.add(new Inventario(
                        (i+1)+"",
                        (response0.getPropertyAsString("k_prod").equals("anyType{}") ? " " : response0.getPropertyAsString("k_prod")),
                        (response0.getPropertyAsString("k_cant").equals("anyType{}") ? " " : response0.getPropertyAsString("k_cant"))));
            }//for
        } catch (Exception ex) {}//catch
    }//conectaListInv

    private class AsyncActualizaInv extends AsyncTask<Void, Void, Void> {
        private String pro,cc;
        private int contador=0;
        @Override
        protected void onPreExecute() {mDialog.show();}

        @Override
        protected Void doInBackground(Void... params) {
            for(int j=0;j<listaInv.size();j++){//for para los registros de cada servidor
                mensaje="";
                pro=listaInv.get(j).getProducto();
                cc=listaInv.get(j).getCantidad();
                conectaActualiza(pro,cc);
                if(mensaje.equals("1")){
                    eliminarSql(" PRODUCTO='"+pro+"'");
                    contador++;
                }//if
            }//for
            return null;
        }//doinbackground

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if (contador==listaInv.size()) {
                listaInv.clear();
                rvInventario.setAdapter(null);
                editor.clear().commit();
                eliminarSql("");
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventario.this);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AsyncFolios().execute();
                    }//onclick
                });//positivebutton
                builder.setCancelable(false);
                builder.setTitle("Resultado Sincronización").setMessage(contador+" Datos sincronizados").create().show();

            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventario.this);
                builder.setMessage("El folio esta cerrado");
                builder.setCancelable(false);
                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });//negative botton
                AlertDialog dialog = builder.create();
                dialog.show();
            }//else
        }//onPostExecute
    }//AsynInsertInv


    private void conectaActualiza (String producto, String cant) {
        String SOAP_ACTION = "ActualizaInv";
        String METHOD_NAME = "ActualizaInv";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLActualizaInv soapEnvelope = new XMLActualizaInv(SoapEnvelope.VER11);
            soapEnvelope.XMLActInv(strusr, strpass, folio, strbran, producto,cant);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            mensaje=response.getPropertyAsString("k_estatus");
            //mensaje=(response.getPropertyAsString("k_estatus").equals("anyType{}") ? null : response.getPropertyAsString("k_estatus"));
        } catch (SoapFault soapFault) {
            mensaje=soapFault.getMessage();
        } catch (XmlPullParserException e) {
            mensaje=e.getMessage();
        } catch (IOException e) {
            mensaje=e.getMessage();
        } catch (Exception ex) {
            mensaje=ex.getMessage();
        }//catch
    }//conectaActualiza

    public void buscarEnSql(String prod,String cant){
        try{
            @SuppressLint("Recycle") Cursor fila = db.rawQuery(
                    "SELECT PRODUCTO,CANTIDAD from INVENTARIOALM WHERE PRODUCTO='"+prod+"'", null);
            if (fila != null && fila.moveToFirst()) {
                actualizarSql(prod,Integer.parseInt(cant)+"");
            }else{
                insertarSql(prod,cant);
            }
            fila.close();
        }catch(Exception e){
            Toast.makeText(ActivityInventario.this,e+"", Toast.LENGTH_SHORT).show();
        }//catch
        consultaSql();
    }//consultaSql



    public void consultaSql(){
        try{
            listaInv.clear();
            rvInventario.setAdapter(null);
            int j=0;
            @SuppressLint("Recycle") Cursor fila = db.rawQuery("SELECT PRODUCTO,CANTIDAD FROM INVENTARIOALM ORDER BY PRODUCTO ", null);
            if (fila != null && fila.moveToFirst()) {
                do {
                    j++;
                    if(ProductoAct.equals(fila.getString(0))){
                        posicion=j-1;
                        txtCant.setText(fila.getString(1));
                    }
                    listaInv.add(new Inventario(j+"",fila.getString(0),fila.getString(1)));
                } while (fila.moveToNext());

                rvInventario.setAdapter(null);
                adapter= new AdapterInventario(listaInv);
                rvInventario.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                rvInventario.scrollToPosition(posicion);
            }//if
            fila.close();
        }catch(Exception e){
            Toast.makeText(ActivityInventario.this,
                    "Error al consultar datos de la base de datos interna", Toast.LENGTH_SHORT).show();
        }//catch
    }//consultaSql

    public void insertarSql(String prod,String cant){
        try{
            if(db != null){
                ContentValues valores = new ContentValues();
                valores.put("PRODUCTO", prod);
                valores.put("CANTIDAD", cant);
                db.insert("INVENTARIOALM", null, valores);
            }
        }catch(Exception e){
            Toast.makeText(this, "Problema al guardar producto", Toast.LENGTH_SHORT).show();
        }
    }//insertarSql

    public void actualizarSql(String prod,String cant){
        try{
            ContentValues valores = new ContentValues();
            valores.put("CANTIDAD", Integer.parseInt(cant));
            db.update("INVENTARIOALM", valores, "PRODUCTO='"+prod+"'", null);
        }catch(Exception e){
            Toast.makeText(this, "Problema al actualizar la cantidad del producto", Toast.LENGTH_SHORT).show();
        }
    }//actualizarSql

    public void eliminarSql(String sentProd) {//parte de sentencia que es para eliminar prod o todos los productos
        try{
            SQLiteDatabase db = conn.getWritableDatabase();
            db.delete("INVENTARIOALM",sentProd,null);
        }catch(Exception e){}
    }//eliminarSql

}//ActivityInventario
