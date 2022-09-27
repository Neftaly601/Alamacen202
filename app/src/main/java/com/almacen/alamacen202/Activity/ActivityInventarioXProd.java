package com.almacen.alamacen202.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.almacen.alamacen202.Adapter.AdaptadorComprometidas;
import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ComprometidasSandG;
import com.almacen.alamacen202.SetterandGetters.ListaUbicasSandG;
import com.almacen.alamacen202.SetterandGetters.UbicacionSandG;
import com.almacen.alamacen202.XML.XMLActualizaOrdenCompra;
import com.almacen.alamacen202.XML.XMLCLArticulo;
import com.almacen.alamacen202.XML.XMLCompromeAlma;
import com.almacen.alamacen202.XML.XMLConPrincipal;
import com.almacen.alamacen202.XML.XMLConsultaXprod;
import com.almacen.alamacen202.XML.XMLListaUbica;
import com.almacen.alamacen202.XML.XMLUbicacionAlma;
import com.almacen.alamacen202.includes.MyToolbar;
import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import dmax.dialog.SpotsDialog;

public class ActivityInventarioXProd extends AppCompatActivity {
    private EditText txtFolioProd,txtUbi,txtCantProd,txtTotUbi,txtExistAlmProd,txtCantAcum;
    private Button btnBuscarProd,btnGuarda,btnTrasl;
    private TextView tvClvProd,tvDescProd;
    private ImageView ivProdIm;
    private SharedPreferences preference;
    private String strusr,strpass,strbran,strServer,codeBar,clvProducto,Descripcion,mensaje="",Ubicacion,Cantidad,SumUbic,SumAlm,cantAlm;
    private AlertDialog mDialog;
    private ArrayList<UbicacionSandG> listaUbicaciones = new ArrayList<>();
    private ArrayList<ComprometidasSandG> listaComprometidas = new ArrayList<>();
    boolean v=false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invent_por_prod);

        MyToolbar.show(this, "Inventario por producto", true);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        strusr = preference.getString("user", "null");
        strpass = preference.getString("pass", "null");
        strbran = preference.getString("codBra", "null");
        strServer = preference.getString("Server", "null");
        codeBar = preference.getString("codeBar", "null");

        mDialog = new SpotsDialog.Builder().setContext(ActivityInventarioXProd.this).
                setMessage("Espere un momento...").build();

        txtFolioProd   = findViewById(R.id.txtFolioProd);
        btnBuscarProd = findViewById(R.id.btnBuscarProd);
        btnGuarda      = findViewById(R.id.btnGuarda);
        btnTrasl       = findViewById(R.id.btnTrasl);
        tvClvProd      = findViewById(R.id.tvClvProd);
        tvDescProd     = findViewById(R.id.tvDescProd);
        txtUbi         = findViewById(R.id.txtUbicXprod);
        txtCantProd    = findViewById(R.id.txtCantXprod);
        txtTotUbi      = findViewById(R.id.txtSumCantXprod);
        txtExistAlmProd= findViewById(R.id.txtExistAlmXprod);
        ivProdIm       = findViewById(R.id.ivProd);
        txtCantAcum    = findViewById(R.id.txtCantAcum);

        /*txtFolioProd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b==false){
                    Toast.makeText(ActivityInventarioXProd.this, "Escaner", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        txtFolioProd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    if (codeBar.equals("Zebra")) {
                        firtMet();
                    } /*else {
                        for (int i = 0; i < editable.length(); i++) {
                            char ban;
                            ban = editable.charAt(i);
                            if (ban == '\n') {
                                txtEscaneo.setText("");
                                txtEscaneo.setText(editable);
                                Producto = editable.toString();
                                Producto = Producto.replace("\n","");
                                listaExistencia = new ArrayList<>();
                                GridLayoutManager gl = new GridLayoutManager(ActivityConsultaPA.this, 1);
                                recyclerListas.setLayoutManager(gl);
                                ActivityConsultaPA.DatosPrincipales task = new ActivityConsultaPA.DatosPrincipales();
                                task.execute();
                            }//if
                        }//for
                    }//else
                    txtFolioProd.setText(null);*/
                }//if

            }//afterTextChange
        });
        btnBuscarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firtMet();
            }//onClick
        });//btnBuscarProd.setonclick

        btnGuarda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNumeric(txtCantProd.getText().toString())==false){
                    Toast.makeText(ActivityInventarioXProd.this, "Cantidad no válida o vacia", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityInventarioXProd.this);
                    alerta.setMessage("¿Guardar?").setCancelable(false).
                            setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    new AsynCallActualiza().execute();
                                    dialogInterface.cancel();
                                }//onClick
                            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }//onClick
                    });//Alert
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Confirmar");
                    titulo.show();
                }//else
            }//onclick
        });//btnGuarda

        btnTrasl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clvProducto=tvClvProd.getText().toString();
                new AsyncallUbicaciones(3).execute();
            }//onClick
        });//btnTrasl

    }//onCreate
    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        Toast.makeText(this, "Input", Toast.LENGTH_SHORT).show();
        return false; // prevent default behaviour
    }

    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }//isNumeric

    public void firtMet() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtFolioProd.getWindowToken(),0);//cerrar teclado si esta abierto
        txtFolioProd.clearFocus();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if(!txtFolioProd.getText().toString().equals("")){
                clvProducto=txtFolioProd.getText().toString();
                new AsyncallUbicaciones(1).execute();
            }else if(!tvClvProd.getText().toString().equals("")) {
                clvProducto=tvClvProd.getText().toString();
                new AsyncallUbicaciones(1).execute();
            }else{
                Toast.makeText(this, "Campo clave producto está vacio", Toast.LENGTH_SHORT).show();
            }//else
        }else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityInventarioXProd.this);
            alerta.setMessage("NO HAY CONEXION A INTERNET").setCancelable(false)
                    .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }//onClick
            });//alertdialog
            AlertDialog titulo = alerta.create();
            titulo.setTitle("¡ERROR DE CONEXION!");
            titulo.show();
        }//else
    }//firtMet

    public void alertTraspasoUbi(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_transf_ubic, null);
        builder.setView(dialogView);

        final TextView tvClvProdDial = dialogView.findViewById(R.id.tvClvProdDial);
        final TextView tvDescProdDial = dialogView.findViewById(R.id.tvDescProdDial);
        final TextView tvOrigen = dialogView.findViewById(R.id.tvOrigen);
        final TextView tvDestino = dialogView.findViewById(R.id.tvDestino);
        final EditText txtDestino = dialogView.findViewById(R.id.txtDestino);
        final Spinner spOrigen = dialogView.findViewById(R.id.spOrigen);
        final Spinner spDestino = dialogView.findViewById(R.id.spDestino);
        final Button btnSelecciona = dialogView.findViewById(R.id.btnSelecciona);

        tvClvProdDial.setText(tvClvProd.getText().toString());
        tvDescProdDial.setText(tvDescProd.getText().toString());

        ArrayList<String>listaUbicString= new ArrayList<>();//Listas String para los spinner
        ArrayList<String>listaCantString= new ArrayList<>();//una lista para ubicaciones y otra para guardar la cantidad
        for(int i=0;i<listaUbicaciones.size();i++){
            listaUbicString.add(listaUbicaciones.get(i).getUbicacion());
            listaCantString.add(listaUbicaciones.get(i).getCantidad());
        }//acomodar en lista string las ubicaciones

        //spinnerOrigen--------------------------------------------------------
        spOrigen.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,listaUbicString));
        spOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                tvOrigen.setText(listaCantString.get(pos));
            }//onItemselected
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                tvOrigen.setText(listaCantString.get(0));
            }//onNothingselected
        });//spOrigentsetonitemselected

        //btnSeleccione-----------------------------------------
        btnSelecciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spOrigen.setEnabled(false);
                spOrigen.setBackgroundTintList(ColorStateList.
                        valueOf(getResources().getColor(R.color.ColorGris)));
                btnSelecciona.setBackgroundTintList(ColorStateList.
                        valueOf(getResources().getColor(R.color.ColorGris)));

                ArrayList<String>listUbiCopia= new ArrayList<>(listaUbicString);//copia de las listas copia para el spinner destino
                ArrayList<String>listCantCopia= new ArrayList<>(listaCantString);
                listUbiCopia.remove(spOrigen.getSelectedItemPosition());
                listCantCopia.remove(spOrigen.getSelectedItemPosition());//se elimina la ubicacion y cantidad que ya se selecciono en spinner origen
                spDestino.setAdapter(new ArrayAdapter<String>(ActivityInventarioXProd.this,
                        android.R.layout.simple_spinner_item,listUbiCopia));
                spDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        tvDestino.setText(listCantCopia.get(pos));
                        txtDestino.setText("");
                    }//onItemselected
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) { }//onNothingselected
                });//spDestinotsetonitemselected
            }//onclick
        });//btnSelecciona.setonclick

        //builder--------------------------------------------------------------------
        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar",null);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {}
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(txtDestino.getText().toString().equals("") || Integer.parseInt(txtDestino.getText().toString())<=0 || spDestino.getSelectedItem()==null){
                            Toast.makeText(ActivityInventarioXProd.this, "Campos vacios o en cero", Toast.LENGTH_SHORT).show();
                        }else {
                            if(Integer.parseInt(tvOrigen.getText().toString())<Integer.parseInt(txtDestino.getText().toString())){
                                Toast.makeText(ActivityInventarioXProd.this, "No existe esa cantidad disponible",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                new AsyncModificarUbicDestino(spOrigen.getSelectedItem().toString(),spDestino.getSelectedItem().toString(),
                                        tvClvProdDial.getText().toString(),txtDestino.getText().toString(),dialogInterface).execute();
                            }//else if
                        }//else
                    }//ONCLICK
                });//SET ON CLICK
            }//onShow
        });//setonshowlistener
        dialog.show();
    }//alertTraspasoUbi

    public void habilitaOinhabilita(){//habilitar o no los botones de traspaso entre ubicaciones y guardar | tambien para habilitar el campo de cantidad
        if(tvClvProd.getText().toString().equals("") || txtUbi.getText().toString().equals("")){
            btnGuarda.setEnabled(false);
            btnGuarda.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.ColorGris)));
            btnTrasl.setEnabled(false);
            btnTrasl.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.ColorGris)));
            txtCantProd.setEnabled(false);
        }else{
            btnGuarda.setEnabled(true);
            btnGuarda.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.Amarillo)));
            btnTrasl.setEnabled(true);
            btnTrasl.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.AzulBack)));
            if(Integer.parseInt(txtTotUbi.getText().toString())==Integer.parseInt(txtExistAlmProd.getText().toString())){
                txtCantProd.setEnabled(false);
                btnGuarda.setEnabled(false);
                btnGuarda.setBackgroundTintList(ColorStateList.
                        valueOf(getResources().getColor(R.color.ColorGris)));
            }else{
                txtCantProd.setEnabled(true);
                btnGuarda.setEnabled(true);
                btnGuarda.setBackgroundTintList(ColorStateList.
                        valueOf(getResources().getColor(R.color.Amarillo)));
            }
        }//else
    }//habilitaOinhabilita

    //WebService ConsultaxProducto
    private class AsynCallConsulXprod extends AsyncTask<Void, Void, Void> {
        DialogInterface dialog;

        public AsynCallConsulXprod(DialogInterface dialog) {
            this.dialog = dialog;
        }
        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreejecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            conectaConsulXprod();
            return null;
        }//doInBackground

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if (mensaje.equals("")) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtCantProd.getWindowToken(),0);//cerrar teclado si esta abierto
                txtCantProd.clearFocus();
                tvClvProd.setText(clvProducto);
                tvDescProd.setText(Descripcion);
                txtUbi.setText(Ubicacion);
                txtCantProd.setText(Cantidad);
                txtTotUbi.setText(SumUbic);
                txtExistAlmProd.setText(SumAlm);
                new AsynCallCompromeAlma().execute();

                Picasso.with(getApplicationContext()).
                        load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" +clvProducto+".jpg")
                        .error(R.drawable.aboutlogo)
                        .fit()
                        .centerInside()
                        .into(ivProdIm);
                if(dialog!=null){
                    dialog.dismiss();
                }
            }else{
                Toast.makeText(ActivityInventarioXProd.this,
                        "Hubó un problema al actualizar datos del producto", Toast.LENGTH_SHORT).show();
            }//else
            habilitaOinhabilita();
        }//OnpostEjecute
    }//class AsynCall

    private void conectaConsulXprod() {
        String SOAP_ACTION = "ConsulXprod";
        String METHOD_NAME = "ConsulXprod";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLConsultaXprod soapEnvelope = new XMLConsultaXprod(SoapEnvelope.VER11);
            soapEnvelope.XMLConsultaPorProd(strusr,strpass,strbran,clvProducto,Ubicacion);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("DatProd");

            clvProducto=(response.getPropertyAsString("k_clvArt").equals("anyType{}") ? null : response.getPropertyAsString("k_clvArt"));
            Descripcion=(response.getPropertyAsString("k_descArt").equals("anyType{}") ? null : response.getPropertyAsString("k_descArt"));
            Ubicacion=(response.getPropertyAsString("k_ubicacion").equals("anyType{}") ? null : response.getPropertyAsString("k_ubicacion"));
            Cantidad=(response.getPropertyAsString("k_cant").equals("anyType{}") ? null : response.getPropertyAsString("k_cant"));
            SumUbic=(response.getPropertyAsString("k_sumUbi").equals("anyType{}") ? null : response.getPropertyAsString("k_sumUbi"));
            SumAlm=(response.getPropertyAsString("k_existAlm").equals("anyType{}") ? null : response.getPropertyAsString("k_existAlm"));
            cantAlm=(response.getPropertyAsString("k_existProc").equals("anyType{}") ? null : response.getPropertyAsString("k_existProc"));
        } catch (SoapFault soapFault) {
            mensaje = "Error: " + soapFault.getMessage();
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            mensaje = "Error: " + e.getMessage();
        } catch (IOException e) {
            mensaje = "No se encontró servidor";
        } catch (Exception ex) {
            mensaje = "Error: " + ex.getMessage();
        }//catch
    }//conecta

    private class AsyncallUbicaciones extends AsyncTask<Void, Void, Void> {
        private int var;//1 si se va a mostrar la lista de ubicaciones ,2 es para que no se muestre y solo se actualice la lista y 3 para el alert de traspasos entre ubic
        public AsyncallUbicaciones(int var) {
            this.var = var;
        }//constructor
        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreejecute
        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            listaUbicaciones.clear();
            conectaUbicaciones();
            return null;
        }//doInBackground
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            txtFolioProd.setText("");
            if(listaUbicaciones.size()>0){
                Ubicacion=txtUbi.getText().toString();
                if(var==1){
                    String[] opciones = new String[listaUbicaciones.size()];
                    for (int i = 0; i < listaUbicaciones.size(); i++) {
                        opciones[i] = listaUbicaciones.get(i).getUbicacion();
                    }//for
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventarioXProd.this);
                    builder.setTitle("SELECCIONE UNA UBICACION");
                    builder.setCancelable(false);
                    builder.setItems(opciones, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Ubicacion = listaUbicaciones.get(which).getUbicacion();
                            new AsynCallConsulXprod(dialog).execute();
                        }//onClick
                    });//setItems
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    });//negative botton
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    if(var==2){
                        new AsynCallConsulXprod(null).execute();
                    }else{
                        txtCantProd.setText(Cantidad);
                        txtCantProd.clearFocus();
                        alertTraspasoUbi();
                    }
                }//else var!=1
            }else{
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityInventarioXProd.this);
                alerta.setMessage("Hubó un problema al consultar ubicaciones del producto \n"+mensaje).setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                habilitaOinhabilita();
                                dialogInterface.cancel();
                            }
                        }).setCancelable(false);//alertdialog
                AlertDialog titulo = alerta.create();
                titulo.setTitle("¡ERROR DE CONEXION!");
                titulo.show();
            }//else
            mensaje="";
        }//onPostExecute
    }//AsyncallUbicaciones

    private void conectaUbicaciones() {
        String SOAP_ACTION = "UbicacionAlma";
        String METHOD_NAME = "UbicacionAlma";
        String NAMESPACE = "http://"+strServer+"/WSk75AlmacenesApp/";
        String URL = "http://"+strServer+"/WSk75AlmacenesApp";
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLUbicacionAlma soapEnvelope = new XMLUbicacionAlma(SoapEnvelope.VER11);
            soapEnvelope.XMLUbicacionAlma(strusr, strpass, clvProducto, strbran);
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
                listaUbicaciones.add(new UbicacionSandG(
                        (response0.getPropertyAsString("k_Ubicacion").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Ubicacion")),
                        (response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Cantidad")),
                        (response0.getPropertyAsString("k_Fecha").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Fecha")),
                        (response0.getPropertyAsString("k_Tipo").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Tipo"))));
            }//for
        }catch (SoapFault soapFault) {
            mensaje = "Error: " + soapFault.getMessage();
        }catch (XmlPullParserException e) {
            mensaje = "Error: " + e.getMessage();
        }catch (IOException e) {
            mensaje = "No se encontró servidor";
        }catch (Exception ex) {
            mensaje ="Puede que la clave del producto no exista";
        }
    }//AsynCall

    //WebService Actualizar Cantidad
    private class AsynCallActualiza extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreejecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            clvProducto=tvClvProd.getText().toString();
            Ubicacion=txtUbi.getText().toString();
            Cantidad=txtCantProd.getText().toString();
            conectaActualizar();
            return null;
        }//doInBackground



        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if(mensaje.equals("Cantidad Actualizada")){
                /*ivProdIm.setImageResource(R.drawable.aboutlogo);
                tvClvProd.setText("");
                tvDescProd.setText("");
                txtTotUbi.setText("");
                txtExistAlmProd.setText("");
                txtUbi.setText("");
                txtCantProd.setText("");*/
                txtFolioProd.setText("");
                new AsyncallUbicaciones(2).execute();
                habilitaOinhabilita();
            }//if
            Toast.makeText(ActivityInventarioXProd.this, mensaje, Toast.LENGTH_SHORT).show();
        }//OnpostEjecute
    }//class AsynCallActualiza


    private void conectaActualizar() {
        String SOAP_ACTION = "ActualizaCant";
        String METHOD_NAME = "ActualizaCant";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLActualizaOrdenCompra soapEnvelope = new XMLActualizaOrdenCompra(SoapEnvelope.VER11);
            soapEnvelope.XMLActOrd(strusr,strpass,clvProducto,strbran,Ubicacion,Cantidad);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            Vector response = (Vector) soapEnvelope.getResponse();
            mensaje = response.get(0).toString();

        } catch (SoapFault soapFault) {
            mensaje = "Error: " + soapFault.getMessage();
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            mensaje = "Error: " + e.getMessage();
        } catch (IOException e) {
            mensaje = "No se encontró servidor";
        } catch (Exception ex) {
            mensaje = "Error: " + ex.getMessage();
        }//catch
    }//conectaActualizar

    //Webservice modifica cantidad de ubicacion destino y origen
    private class AsyncModificarUbicDestino extends AsyncTask<Void, Void, Void> {
        String UbicacionOrigen,UbicacionDestino,Producto,Cantidad;
        DialogInterface dialogInterface;

        public AsyncModificarUbicDestino(String ubicacionOrigen, String ubicacionDestino, String producto, String cantidad,DialogInterface dialogInterface) {
            UbicacionOrigen = ubicacionOrigen;
            UbicacionDestino = ubicacionDestino;
            Producto = producto;
            Cantidad = cantidad;
            this.dialogInterface=dialogInterface;
        }

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreexecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            consultaUbicacionMod(UbicacionOrigen,UbicacionDestino,Producto,Cantidad);
            return null;
        }//doInBackground


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if(mensaje.equals("LA UBICACION A SIDO INSERTADO") || mensaje.equals("LA UBICACION A SIDO ACTUALIZADA")){
                dialogInterface.dismiss();
                Toast.makeText(ActivityInventarioXProd.this, mensaje, Toast.LENGTH_SHORT).show();
                new AsyncallUbicaciones(2).execute();
            }else{
                Toast.makeText(ActivityInventarioXProd.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }//onPosteExecute
    }

    private void consultaUbicacionMod(String UbicacionOrigen,String UbicacionDestino,String Producto,String Cantidad) {
        String SOAP_ACTION = "CLArticulo";
        String METHOD_NAME = "CLArticulo";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLCLArticulo soapEnvelope = new XMLCLArticulo(SoapEnvelope.VER11);
            soapEnvelope.XMLCLArticulo(strusr, strpass, UbicacionOrigen, UbicacionDestino, strbran, Producto, Cantidad);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("message");
            mensaje = (response.getPropertyAsString("k_menssage").equals("anyType{}") ? "" : response.getPropertyAsString("k_menssage"));


        } catch (SoapFault soapFault) {
            mensaje = "Error:" + soapFault.getMessage();
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            mensaje = "Error:" + e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            mensaje = "No se encontró servidor";
            e.printStackTrace();
        } catch (Exception ex) {
            mensaje = "Error:" + ex.getMessage();
        }//catch
    }//AsyncCallModificarUbic

    private class AsynCallCompromeAlma extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreejecute

        @Override
        protected Void doInBackground(Void... params) {
            listaComprometidas.clear();
            conectaCompromeAlma();
            return null;
        }//doInBackground

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            int contador=0;
            for(int i=0;i<listaComprometidas.size();i++){
                contador=contador+Integer.parseInt(listaComprometidas.get(i).getCantidad());
            }//for
            int r=Integer.parseInt(cantAlm)+contador;
            txtCantAcum.setText(r+"");
        }//onPostExecuted
    }//AsynCallCompromeAlma

    private void conectaCompromeAlma() {

        String SOAP_ACTION = "CompromeAlma";
        String METHOD_NAME = "CompromeAlma";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLCompromeAlma soapEnvelope = new XMLCompromeAlma(SoapEnvelope.VER11);
            soapEnvelope.XMLCompromeAlma(strusr, strpass, clvProducto, strbran);
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
                listaComprometidas.add(new ComprometidasSandG(
                        (response0.getPropertyAsString("k_TipoDocumento").equals("anyType{}") ? " " : response0.getPropertyAsString("k_TipoDocumento")),
                        (response0.getPropertyAsString("k_Folio").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("k_Cliente").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Cliente")),
                        (response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Cantidad")),
                        (response0.getPropertyAsString("k_Fecha").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Fecha"))));

            }//try
        } catch (SoapFault soapFault) {
            mDialog.dismiss();
            mensaje = "Error:" + soapFault.getMessage();
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            mDialog.dismiss();
            mensaje = "Error:" + e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            mDialog.dismiss();
            mensaje = "No se encontro servidor";
            e.printStackTrace();
        } catch (Exception ex) {
            mDialog.dismiss();
            mensaje = "Error:" + ex.getMessage();
        }//catch
    }//AsyncallCompreAlm


}//clase principal
