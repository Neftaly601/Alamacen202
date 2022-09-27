package com.almacen.alamacen202.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.Adapter.AdapterInventario;
import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.Folios;
import com.almacen.alamacen202.SetterandGetters.Inventario;
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

import dmax.dialog.SpotsDialog;

public class ActivityInventario extends AppCompatActivity {
    private SharedPreferences preference;
    private String strusr,strpass,strServer,ProductoAct="",cerrar="0",folio="",suc="",mensaje;
    private ArrayList<Inventario> listaInv = new ArrayList<>();
    private EditText txtFolioInv,txtFechaI,txtHoraI,txtProducto,txtCant;
    private ArrayList<Folios>listaFol;
    private Button btnGuardar;
    private CheckBox chbMan;
    private RecyclerView rvInventario;
    private AdapterInventario adapter;
    private AlertDialog mDialog;
    private InputMethodManager keyboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        MyToolbar.show(this, "Inventario", true);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        strusr = preference.getString("user", "null");
        strpass = preference.getString("pass", "null");
        strServer = preference.getString("Server", "null");
        mDialog = new SpotsDialog.Builder().setContext(ActivityInventario.this).
                setMessage("Espere un momento...").build();

        txtFolioInv     = findViewById(R.id.txtFolioInv);
        txtFechaI       = findViewById(R.id.txtFechaI);
        txtHoraI        = findViewById(R.id.txtHoraI);
        txtProducto     = findViewById(R.id.txtProducto);
        txtCant         = findViewById(R.id.txtCant);
        btnGuardar      = findViewById(R.id.btnGuardar);
        chbMan          = findViewById(R.id.chbMan);
        rvInventario    = findViewById(R.id.rvInventario);

        rvInventario.setLayoutManager(new LinearLayoutManager(ActivityInventario.this));
        keyboard = (InputMethodManager) getSystemService(ActivityInventario.INPUT_METHOD_SERVICE);

        chbMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                txtProducto.setText("");
                txtProducto.requestFocus();
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
                    //txtProducto.setText("");
                    if (!chbMan.isChecked()) {
                        new AsyncActualizaInv().execute();
                    }else{
                        txtCant.requestFocus();
                        keyboard.showSoftInput(txtCant, InputMethodManager.SHOW_IMPLICIT);
                    }
                }//if !editable
            }//after
        });//txtProducto.addTextChanged


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1=ProductoAct;
                String v2=txtCant.getText().toString();
                if(!v1.equals("") && !v2.equals("") && Integer.parseInt(v2)>0){
                    new AsyncActualizaInv().execute();
                    keyboard.hideSoftInputFromWindow(txtCant.getWindowToken(), 0);
                    txtProducto.setText("");
                    txtProducto.requestFocus();
                }//if
            }//onclick
        });//btnGuardar setonclick

        new AsyncFolios().execute();
    }//onCreate


    public void listaFolio(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventario.this);
        builder.setCancelable(false);

        folio="";suc="";
        String[] opciones = new String[listaFol.size()];
        for (int i = 0; i < listaFol.size(); i++) {
            opciones[i] = "FOLIO:"+listaFol.get(i).getFolio()+"\nSUCURSAL:"+listaFol.get(i).getSuc()+"\nFECHA:"+listaFol.get(i).getFecha()+" HORA:"+listaFol.get(i).getHora();
        }//for
        builder.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                folio=listaFol.get(which).getFolio();
                suc=listaFol.get(which).getSuc();
                txtFolioInv.setText(folio);
                txtFechaI.setText(listaFol.get(which).getFecha());
                txtHoraI.setText(listaFol.get(which).getHora());
                new AsyncListInv().execute();
            }//onClick
        });//setItems
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) { finish();}
        });//negative botton
        AlertDialog dialog = builder.create();
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
            if (listaFol.size()>0) {
                listaFolio();
            }else{
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventario.this);
                builder.setMessage("No se encontro folios");
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
    }//AsyncFolios


    private void conectaFolios() {
        String SOAP_ACTION = "Folios";
        String METHOD_NAME = "Folios";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLFolios soapEnvelope = new XMLFolios(SoapEnvelope.VER11);
            soapEnvelope.XMLFol(strusr, strpass,cerrar, folio, suc);
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
                        (response0.getPropertyAsString("k_suc").equals("anyType{}")?"" : response0.getPropertyAsString("k_suc")),
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
                rvInventario.setAdapter(null);
                adapter= new AdapterInventario(listaInv);
                rvInventario.setAdapter(adapter);
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
            soapEnvelope.XMLLI(strusr, strpass, folio,suc);
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
        @Override
        protected void onPreExecute() {mDialog.show();}

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            pro=ProductoAct;
            cc=Integer.parseInt(txtCant.getText().toString())+"";
            conectaActualiza(pro,cc);
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            if (mensaje.equals("Actualizado") || mensaje.equals("Guardado")) {
                new AsyncListInv().execute();
                for(int i=0;i<listaInv.size();i++){
                    if(listaInv.get(i).getProducto().equals(pro)){
                        rvInventario.findViewHolderForAdapterPosition(i);
                        break;
                    }//if
                }//for
            }else{
                Toast.makeText(ActivityInventario.this, "Error al actualizar dato", Toast.LENGTH_SHORT).show();
            }//else
            txtProducto.setText("");
            if(chbMan.isChecked()){
                txtCant.setText("");
            }else{
                txtCant.setText("1");
            }
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
            soapEnvelope.XMLActInv(strusr, strpass, folio, suc, producto,cant);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("PRODUCTO");

            mensaje=(response.getPropertyAsString("MENSAJE").equals("anyType{}") ? null : response.getPropertyAsString("MENSAJE"));
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

}//ActivityInventario
