package com.almacen.alamacen202.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ListLiberaSandG;
import com.almacen.alamacen202.SetterandGetters.ListaUbicasSandG;
import com.almacen.alamacen202.XML.XMLCLArticulo;
import com.almacen.alamacen202.XML.XMLConPrincipal;
import com.almacen.alamacen202.XML.XMLListFolioMIS;
import com.almacen.alamacen202.XML.XMLListaUbica;
import com.almacen.alamacen202.XML.XMLUbicacionModifi;
import com.almacen.alamacen202.includes.MyToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import dmax.dialog.SpotsDialog;

public class ActivityTrasladoUbi extends AppCompatActivity {

    EditText EDProducto;
    EditText EDUbicacionOrigen;
    EditText EDCantidad;
    EditText EDUbicacionDest;

    TextView MostrarProEd;
    TextView MostrarUbicOEd;
    TextView MostrarCantEd;
    TextView MostrarUbicDEd;
    ArrayList<ListaUbicasSandG> listaUbicaciones = new ArrayList<>();

    LinearLayout Ubicacionorigenlinear,ProducL;

    ImageView imgVi;


    TextView Instrucciones;
    String strusr, strpass, strname, strlname, strtype, strbran, strma, strcodBra, StrServer,codeBar;
    TextInputLayout TXTPRODVISI, TXTUBICAORIVISI, TXTCANTIDADVISI, TXTUBICADEST;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    String Validacion1;
    String CantidadOrigen;

    String messege,mensaje;
    AlertDialog mDialog;

    String Producto, UbicacionOrigen, Cantidad, UbicacionDestino;
    String Descripcion;
    String Linea;
    String Disponibilidad;
    String ProductoWeb="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traslado_ubi);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();
        mDialog = new SpotsDialog.Builder().setContext(ActivityTrasladoUbi.this).setMessage("Espere un momento...").build();
        MyToolbar.show(this, "", true);

        imgVi = findViewById(R.id.productoImag);
        TXTPRODVISI = findViewById(R.id.TXTPRODVISI);
        TXTUBICAORIVISI = findViewById(R.id.TXTUBICAORIVISI);
        TXTCANTIDADVISI = findViewById(R.id.TXTCANTIDADVISI);
        TXTUBICADEST = findViewById(R.id.TXTUBICADEST);
        EDProducto = findViewById(R.id.EDProducto);
        EDUbicacionOrigen = findViewById(R.id.EDUbicacionOr);
        EDCantidad = findViewById(R.id.EDCANTIDAD);
        EDUbicacionDest = findViewById(R.id.EDUbicacionDest);
        Instrucciones = findViewById(R.id.Instruccionestxt);

        MostrarProEd = findViewById(R.id.edProMostrar);
        MostrarUbicOEd = findViewById(R.id.edUbiOrMostrar);
        MostrarCantEd = findViewById(R.id.edCantMostrar);
        MostrarUbicDEd = findViewById(R.id.edUbiDestiMostrar);

        Ubicacionorigenlinear = findViewById(R.id.ubicacionedestino);
        ProducL= findViewById(R.id.ProducL);

        ProducL.setVisibility(View.VISIBLE);
        TXTPRODVISI.setVisibility(View.VISIBLE);
        TXTUBICAORIVISI.setVisibility(View.GONE);
        TXTCANTIDADVISI.setVisibility(View.GONE);
        TXTUBICADEST.setVisibility(View.GONE);

        strusr = preference.getString("user", "null");
        strpass = preference.getString("pass", "null");
        strname = preference.getString("name", "null");
        strlname = preference.getString("lname", "null");
        strtype = preference.getString("type", "null");
        strbran = preference.getString("branch", "null");
        strma = preference.getString("email", "null");
        strcodBra = preference.getString("codBra", "null");
        StrServer = preference.getString("Server", "null");
        codeBar =preference.getString("codeBar","null");
        EDProducto.requestFocus();
        EDProducto.setInputType(InputType.TYPE_NULL);
        EDUbicacionDest.setInputType(InputType.TYPE_NULL);
        EDProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    if (codeBar.equals("Zebra")){
                        Producto = editable.toString();
                        ProducL.setVisibility(View.GONE);
                        EDUbicacionOrigen.requestFocus();
                        TXTPRODVISI.setVisibility(View.GONE);
                        TXTUBICAORIVISI.setVisibility(View.VISIBLE);
                        Ubicacionorigenlinear.setVisibility(View.VISIBLE);
                        EDProducto.setText(null);
                        Picasso.with(getApplicationContext()).
                                load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + Producto + ".jpg")
                                .error(R.drawable.aboutlogo)
                                .fit()
                                .centerInside()
                                .into(imgVi);
                        MostrarProEd.setText(Producto);
                        Instrucciones.setText("Escanear Ubicacion Origen....");
                        EDUbicacionOrigen.setInputType(InputType.TYPE_NULL);

                    }else{

                        for(int i = 0; i<editable.length();i++){
                            char ban;
                            ban = editable.charAt(i);
                            if(ban == '\n'){
                                Producto = editable.toString();
                                Producto = Producto.replace("\n","");
                                ProducL.setVisibility(View.GONE);
                                EDUbicacionOrigen.requestFocus();
                                TXTPRODVISI.setVisibility(View.GONE);
                                TXTUBICAORIVISI.setVisibility(View.VISIBLE);
                                EDProducto.setText(null);
                                Picasso.with(getApplicationContext()).
                                        load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + Producto + ".jpg")
                                        .error(R.drawable.aboutlogo)
                                        .fit()
                                        .centerInside()
                                        .into(imgVi);
                                MostrarProEd.setText(Producto);
                                Instrucciones.setText("Escanear Ubicacion Origen....");
                                EDUbicacionOrigen.setInputType(InputType.TYPE_NULL);

                            }
                            }
                    }




                }
            }
        });

        EDUbicacionOrigen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {

                    if (codeBar.equals("Zebra")){
                        UbicacionOrigen = editable.toString();
                        ActivityTrasladoUbi.ModificarUbicacion task = new ActivityTrasladoUbi.ModificarUbicacion();
                        task.execute();
                    }else{

                        for(int i = 0; i<editable.length();i++){
                            char ban;
                            ban = editable.charAt(i);
                            if(ban == '\n'){
                                UbicacionOrigen = editable.toString();
                                UbicacionOrigen = UbicacionOrigen.replace("\n","");
                                ActivityTrasladoUbi.ModificarUbicacion task = new ActivityTrasladoUbi.ModificarUbicacion();
                                task.execute();
                            }
                        }
                    }
                }
            }
        });

        EDCantidad.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    EDUbicacionDest.setText("");
                    if (Integer.parseInt(EDCantidad.getText().toString()) > Integer.parseInt(CantidadOrigen)) {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityTrasladoUbi.this);
                        alerta.setMessage("La cantidad de piezas es mayor a la que existe actualmente  en esta ubicacion\n " +
                                "Cantidad: " + CantidadOrigen).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EDProducto.requestFocus();
                                ProducL.setVisibility(View.VISIBLE);
                                TXTCANTIDADVISI.setVisibility(View.GONE);
                                TXTPRODVISI.setVisibility(View.VISIBLE);
                                Instrucciones.setText("Escanear el producto....");
                                MostrarProEd.setText("");
                                MostrarUbicOEd.setText("");
                                EDCantidad.setText(null);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("No hay suficientes piezas  ");
                        titulo.show();

                    } else {
                        Cantidad = EDCantidad.getText().toString();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(EDUbicacionDest.getWindowToken(), 0);
                        TXTCANTIDADVISI.setVisibility(View.GONE);
                        TXTUBICADEST.setVisibility(View.VISIBLE);
                        EDUbicacionDest.requestFocus();
                        Instrucciones.setText("Ingrese ubicacion a cambiar");
                        MostrarCantEd.setText(Cantidad + " PZA");
                        EDCantidad.setText(null);
                    }

                    return false;
                }

                return false;
            }
        });

        EDUbicacionDest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    if (codeBar.equals("Zebra")){
                        UbicacionDestino = editable.toString();
                        EDProducto.requestFocus();
                        ProducL.setVisibility(View.VISIBLE);
                        TXTUBICADEST.setVisibility(View.GONE);
                        TXTPRODVISI.setVisibility(View.VISIBLE);
                        Instrucciones.setText("Escanear el producto....");
                        MostrarUbicDEd.setText(UbicacionDestino);
                        ActivityTrasladoUbi.ModificarUbicDestino task = new ActivityTrasladoUbi.ModificarUbicDestino();
                        task.execute();
                    }else{

                        for(int i = 0; i<editable.length();i++){
                            char ban;
                            ban = editable.charAt(i);
                            if(ban == '\n'){
                                UbicacionDestino = editable.toString();
                                EDProducto.requestFocus();
                                TXTUBICADEST.setVisibility(View.GONE);
                                TXTPRODVISI.setVisibility(View.VISIBLE);
                                Instrucciones.setText("Escanear el producto....");
                                MostrarUbicDEd.setText(UbicacionDestino);
                                ActivityTrasladoUbi.ModificarUbicDestino task = new ActivityTrasladoUbi.ModificarUbicDestino();
                                task.execute();
                            }
                        }
                    }




                }
            }
        });


        switch (StrServer) {
            case "jacve.dyndns.org:9085":
                Picasso.with(getApplicationContext()).
                        load(R.drawable.jacve)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);
                break;
            case "sprautomotive.servehttp.com:9085":
                Picasso.with(getApplicationContext()).
                        load(R.drawable.vipla)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);
                break;
            case "cecra.ath.cx:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.cecra)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "guvi.ath.cx:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.guvi)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "cedistabasco.ddns.net:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.pressa)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "autodis.ath.cx:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.autodis)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "sprautomotive.servehttp.com:9090":


                Picasso.with(getApplicationContext()).
                        load(R.drawable.roda)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "sprautomotive.servehttp.com:9095":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.partech)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "sprautomotive.servehttp.com:9080":


                Picasso.with(getApplicationContext()).
                        load(R.drawable.shark)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
            case "vazlocolombia.dyndns.org:9085":

                Picasso.with(getApplicationContext()).
                        load(R.drawable.bhp)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);


                break;
            default:
                Picasso.with(getApplicationContext()).
                        load(R.drawable.logokepler)
                        .error(R.drawable.logokepler)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                break;
        }


    }

    private class ModificarUbicacion extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            UbicacionModifi();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            if (Validacion1.equals("1")) {
                EDCantidad.requestFocus();
                TXTUBICAORIVISI.setVisibility(View.GONE);
                Ubicacionorigenlinear.setVisibility(View.GONE);
                TXTCANTIDADVISI.setVisibility(View.VISIBLE);
                EDUbicacionOrigen.setText(null);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
                MostrarUbicOEd.setText(UbicacionOrigen);
                Instrucciones.setText(Html.fromHtml("Ingrese la cantidad de piezas....<br> Usted cuenta hasta con: <font color='#27BC0D'> " + CantidadOrigen + " PZA</font> "));


            } else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityTrasladoUbi.this);
                alerta.setMessage("ESTE PRODUCTO NO SE ENCUENTRA EN ESTA UBICACION").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        EDProducto.requestFocus();
                        TXTUBICAORIVISI.setVisibility(View.GONE);
                        TXTPRODVISI.setVisibility(View.VISIBLE);
                        Instrucciones.setText("Escanear el producto....");
                        MostrarProEd.setText("");
                        MostrarUbicOEd.setText("");
                        EDUbicacionOrigen.setText(null);
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("PRODUCTO NO ENCONTRADO");
                titulo.show();
            }
        }
    }

    private void UbicacionModifi() {
        String SOAP_ACTION = "UbicacionModifi";
        String METHOD_NAME = "UbicacionModifi";
        String NAMESPACE = "http://"+StrServer+"/WSk75AlmacenesApp/";
        String URL = "http://"+StrServer+"/WSk75AlmacenesApp";
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLUbicacionModifi soapEnvelope = new XMLUbicacionModifi(SoapEnvelope.VER11);
            soapEnvelope.XMLUbicacionModifi(strusr, strpass, Producto, strcodBra, UbicacionOrigen);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("Almacen");
            Validacion1 = (response.getPropertyAsString("k_ban").equals("anyType{}") ? "" : response.getPropertyAsString("k_ban"));
            if (Validacion1.equals("1")) {
                CantidadOrigen = (response.getPropertyAsString("k_Cantidad").equals("anyType{}") ? "" : response.getPropertyAsString("k_Cantidad"));
            }//if
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            String mensaje = ex.getMessage();
        }
    }

    private class ModificarUbicDestino extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            UbicacionModifiDest();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityTrasladoUbi.this);
            alerta.setMessage(messege).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    MostrarProEd.setText(null);
                    MostrarUbicOEd.setText(null);
                    MostrarCantEd.setText(null);
                    MostrarUbicDEd.setText(null);


                    switch (StrServer) {
                        case "jacve.dyndns.org:9085":
                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.jacve)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);
                            break;
                        case "sprautomotive.servehttp.com:9085":
                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.vipla)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);
                            break;
                        case "cecra.ath.cx:9085":

                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.cecra)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);

                            break;
                        case "guvi.ath.cx:9085":

                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.guvi)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);

                            break;
                        case "cedistabasco.ddns.net:9085":

                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.pressa)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);

                            break;
                        case "autodis.ath.cx:9085":

                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.autodis)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);

                            break;
                        case "sprautomotive.servehttp.com:9090":


                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.roda)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);

                            break;
                        case "sprautomotive.servehttp.com:9095":

                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.shark)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);

                            break;
                        case "sprautomotive.servehttp.com:9080":


                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.partech)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);

                            break;
                        case "vazlocolombia.dyndns.org:9085":

                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.bhp)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);


                            break;
                        default:
                            Picasso.with(getApplicationContext()).
                                    load(R.drawable.logokepler)
                                    .error(R.drawable.logokepler)
                                    .fit()
                                    .centerInside()
                                    .into(imgVi);

                            break;
                    }
                    listaUbicaciones.clear();
                    UbicacionOrigen="";
                    UbicacionDestino="";
                    Producto="";
                    ProductoWeb="";
                    Cantidad="";

                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("AVISO");
            titulo.show();

        }
    }

    private void UbicacionModifiDest() {
        String SOAP_ACTION = "CLArticulo";
        String METHOD_NAME = "CLArticulo";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLCLArticulo soapEnvelope = new XMLCLArticulo(SoapEnvelope.VER11);
            soapEnvelope.XMLCLArticulo(strusr, strpass, UbicacionOrigen, UbicacionDestino, strcodBra, Producto, Cantidad);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("message");
            messege = (response.getPropertyAsString("k_menssage").equals("anyType{}") ? "" : response.getPropertyAsString("k_menssage"));


        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            String mensaje = ex.getMessage();
        }
    }


    private class ListUbicacion extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            Ubicaciones();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            String[] opciones = new String[listaUbicaciones.size()];

            for (int i = 0; i < listaUbicaciones.size(); i++) {
                opciones[i] = listaUbicaciones.get(i).getUbicaciones();
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityTrasladoUbi.this);
            builder.setTitle("SELECCIONE UNA UBICACION");


            builder.setItems(opciones, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UbicacionOrigen = listaUbicaciones.get(which).getUbicaciones();
                    ActivityTrasladoUbi.ModificarUbicacion task = new ActivityTrasladoUbi.ModificarUbicacion();
                    task.execute();

                }
            });
// create and show the alert dialog
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void Ubicaciones() {
        String SOAP_ACTION = "ListaUbica";
        String METHOD_NAME = "ListaUbica";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLListaUbica soapEnvelope = new XMLListaUbica(SoapEnvelope.VER11);
            soapEnvelope.XMLListaUbica(strusr, strpass, Producto, strcodBra);
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

                listaUbicaciones.add(new ListaUbicasSandG(
                        (response0.getPropertyAsString("k_Ubicacion").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Ubicacion"))));


            }

        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }

    public void ubicaciones (View view){
        listaUbicaciones.clear();
        ActivityTrasladoUbi.ListUbicacion task = new ActivityTrasladoUbi.ListUbicacion();
        task.execute();

    }

    public  void BuquedaManual(View view){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        edittext.setFocusable(true);
        alert.setMessage("Inserte una clave de producto manual");
        alert.setTitle("Producto");

        alert.setView(edittext);

        alert.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Producto = edittext.getText().toString();
                ActivityTrasladoUbi.DatosPrincipales task = new ActivityTrasladoUbi.DatosPrincipales();
                task.execute();

            }
        });

        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }




    private class DatosPrincipales extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            conecta();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {


            if (!ProductoWeb.equals("") && Producto.equals(ProductoWeb)) {
                if (codeBar.equals("Zebra")) {
                    EDUbicacionOrigen.requestFocus();
                    ProducL.setVisibility(View.GONE);
                    TXTPRODVISI.setVisibility(View.GONE);
                    TXTUBICAORIVISI.setVisibility(View.VISIBLE);
                    Ubicacionorigenlinear.setVisibility(View.VISIBLE);
                    EDProducto.setText(null);
                    Picasso.with(getApplicationContext()).
                            load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + Producto + ".jpg")
                            .error(R.drawable.aboutlogo)
                            .fit()
                            .centerInside()
                            .into(imgVi);
                    MostrarProEd.setText(Producto);
                    Instrucciones.setText("Escanear Ubicacion Origen....");
                    EDUbicacionOrigen.setInputType(InputType.TYPE_NULL);
                }

            } else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityTrasladoUbi.this);
                alerta.setMessage("El articulo que esta buscando no se encuentra").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Articulo no encontrado");
                titulo.show();
            }

        }
    }


    private void conecta() {
        String SOAP_ACTION = "ConPrincipal";
        String METHOD_NAME = "ConPrincipal";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLConPrincipal soapEnvelope = new XMLConPrincipal(SoapEnvelope.VER11);
            soapEnvelope.XMLConPrincipal(strusr, strpass, Producto, strcodBra);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("Almacen");

            Descripcion = (response.getPropertyAsString("k_Descripcion").equals("anyType{}") ? null : response.getPropertyAsString("k_Descripcion"));
            Linea = (response.getPropertyAsString("k_Linea").equals("anyType{}") ? null : response.getPropertyAsString("k_Linea"));
            Disponibilidad = (response.getPropertyAsString("k_Disponibilida").equals("anyType{}") ? null : response.getPropertyAsString("k_Disponibilida"));
            ProductoWeb = (response.getPropertyAsString("k_Producto").equals("anyType{}") ? null : response.getPropertyAsString("k_Producto"));

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
        }
    }//conecta



}//clase