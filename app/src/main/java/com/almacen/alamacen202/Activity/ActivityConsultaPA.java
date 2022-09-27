package com.almacen.alamacen202.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.almacen.alamacen202.Adapter.AdaptadorComprometidas;
import com.almacen.alamacen202.Adapter.AdaptadorExistencia;
import com.almacen.alamacen202.Adapter.AdaptadorUbicacion;
import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ComprometidasSandG;
import com.almacen.alamacen202.SetterandGetters.ExistenciaSandG;
import com.almacen.alamacen202.SetterandGetters.UbicacionSandG;
import com.almacen.alamacen202.XML.XMLCompromeAlma;
import com.almacen.alamacen202.XML.XMLConPrincipal;
import com.almacen.alamacen202.XML.XMLExisteAlma;
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

import dmax.dialog.SpotsDialog;

public class ActivityConsultaPA extends AppCompatActivity {

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    TextView TitutloTable;

    RecyclerView recyclerListas;

    //Layaout
    LinearLayout ToolBarExistencia, ToolBarComprometidas, ToolBarUbicacion;
    AlertDialog mDialog;

    //Variables
    String strusr, strpass, strname, strlname, strtype, strbran, strma, strcodBra, StrServer, codeBar;
    String Producto, Sucursal, mensaje;
    String Descripcion;
    String Linea;
    String Disponibilidad;
    String ProductoWeb="";
    TextView txtDescripcion, txtLinea, txtDisponibilidad;
    EditText ETProducto;
    EditText txtEscaneo;
    ImageView imgVi;


    //Listas
    ArrayList<ComprometidasSandG> listaComprometidas = new ArrayList<>();
    ArrayList<ExistenciaSandG> listaExistencia = new ArrayList<>();
    ArrayList<UbicacionSandG> listaUbicaciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_p);

        MyToolbar.show(this, "BUSCAR PRODUCTO", true);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        mDialog = new SpotsDialog.Builder().setContext(ActivityConsultaPA.this).setMessage("Espere un momento...").build();
        editor = preference.edit();

        TitutloTable = findViewById(R.id.nomTabla);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        txtLinea = findViewById(R.id.txtLinea);
        txtDisponibilidad = findViewById(R.id.txtDiponibilidad);
        ETProducto = findViewById(R.id.ETProducto);
        txtEscaneo = findViewById(R.id.txtEscaner);
        imgVi = findViewById(R.id.imageVi);
        recyclerListas = findViewById(R.id.lisFacturas);
        ToolBarComprometidas = findViewById(R.id.ComprometidastoolBar);
        ToolBarExistencia = findViewById(R.id.ExistenciatoolBar);
        ToolBarUbicacion = findViewById(R.id.UbicacionToolBar);

        strusr = preference.getString("user", "null");
        strpass = preference.getString("pass", "null");
        strname = preference.getString("name", "null");
        strlname = preference.getString("lname", "null");
        strtype = preference.getString("type", "null");
        strbran = preference.getString("branch", "null");
        strma = preference.getString("email", "null");
        strcodBra = preference.getString("codBra", "null");
        StrServer = preference.getString("Server", "null");
        codeBar = preference.getString("codeBar", "null");


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


        ETProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {

                        if (codeBar.equals("Zebra")) {
                            txtEscaneo.setText("");
                            txtEscaneo.setText(editable);
                            Producto = editable.toString();
                            listaExistencia = new ArrayList<>();
                            GridLayoutManager gl = new GridLayoutManager(ActivityConsultaPA.this, 1);
                            recyclerListas.setLayoutManager(gl);
                            ActivityConsultaPA.DatosPrincipales task = new ActivityConsultaPA.DatosPrincipales();
                            task.execute();
                        } else {
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
                                }
                            }
                        }



                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsultaPA.this);
                        alerta.setMessage("NO HAY CONEXION A INTERNET").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡ERROR DE CONEXION!");
                        titulo.show();

                    }
                    ETProducto.setText(null);
                }

            }
        });

    }


    public void Escanear(View view) {
        ETProducto.requestFocus();
        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsultaPA.this);
        alerta.setMessage("Presione el boton lateral").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog titulo = alerta.create();
        titulo.setTitle("Empieza a escanear");
        titulo.show();
    }

    public void FirtMet(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            if (!txtEscaneo.getText().equals(null)){
                Producto = txtEscaneo.getText().toString();
                listaExistencia = new ArrayList<>();
                GridLayoutManager gl = new GridLayoutManager(ActivityConsultaPA.this, 1);
                recyclerListas.setLayoutManager(gl);
                ActivityConsultaPA.DatosPrincipales task = new ActivityConsultaPA.DatosPrincipales();
                task.execute();
                txtEscaneo.setText(null);
            }else{

                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsultaPA.this);
                alerta.setMessage("El campo producto esta vacio").setIcon(R.drawable.icons8_error_52).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("¡Error!");
                titulo.show();

            }
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsultaPA.this);
            alerta.setMessage("NO HAY CONEXION A INTERNET").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("¡ERROR DE CONEXION!");
            titulo.show();

        }

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
                txtDescripcion.setText(Descripcion);
                txtLinea.setText(Linea);
                txtDisponibilidad.setText(Disponibilidad);

                Picasso.with(getApplicationContext()).
                        load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + Producto + ".jpg")
                        .error(R.drawable.aboutlogo)
                        .fit()
                        .centerInside()
                        .into(imgVi);
                ActivityConsultaPA.TableExist task = new ActivityConsultaPA.TableExist();
                task.execute();
            } else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsultaPA.this);
                alerta.setMessage("El articulo que esta buscando no se encuentra").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("NO SE ENCONTRO ");
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
        } catch (IOException e) {
            mDialog.dismiss();
            mensaje = "No se encontro servidor";
            e.printStackTrace();
        } catch (Exception ex) {
            mDialog.dismiss();
            mensaje = "Error:" + ex.getMessage();
        }
    }

    private class TableCompro extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            conectaTableCompro();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            TitutloTable.setText("Comprometidas");
            ToolBarComprometidas.setVisibility(View.VISIBLE);
            ToolBarExistencia.setVisibility(View.GONE);
            ToolBarUbicacion.setVisibility(View.GONE);
            AdaptadorComprometidas adapter = new AdaptadorComprometidas(listaComprometidas);
            recyclerListas.setAdapter(null);
            recyclerListas.setAdapter(adapter);

        }
    }

    private void conectaTableCompro() {

        String SOAP_ACTION = "CompromeAlma";
        String METHOD_NAME = "CompromeAlma";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLCompromeAlma soapEnvelope = new XMLCompromeAlma(SoapEnvelope.VER11);
            soapEnvelope.XMLCompromeAlma(strusr, strpass, Producto, strcodBra);
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
                        (response0.getPropertyAsString("k_Folio").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("k_Cliente").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Cliente")),
                        (response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Cantidad")),
                        (response0.getPropertyAsString("k_Fecha").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Fecha")),
                        (response0.getPropertyAsString("k_TipoDocumento").equals("anyType{}") ? " " : response0.getPropertyAsString("k_TipoDocumento"))));


            }


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
    }


    private class TableExist extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            conectaTableExistencia();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            TitutloTable.setText("Existencia");
            ToolBarComprometidas.setVisibility(View.GONE);
            ToolBarExistencia.setVisibility(View.VISIBLE);
            ToolBarUbicacion.setVisibility(View.GONE);
            AdaptadorExistencia adapter = new AdaptadorExistencia(listaExistencia);
            recyclerListas.setAdapter(null);
            recyclerListas.setAdapter(adapter);

        }
    }

    private void conectaTableExistencia() {

        String SOAP_ACTION = "ExisteAlma";
        String METHOD_NAME = "ExisteAlma";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLExisteAlma soapEnvelope = new XMLExisteAlma(SoapEnvelope.VER11);
            soapEnvelope.XMLExisteAlma(strusr, strpass, Producto, strcodBra);
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
                listaExistencia.add(new ExistenciaSandG(
                        (response0.getPropertyAsString("k_Almacen").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Almacen")),
                        (response0.getPropertyAsString("k_Existencia").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Existencia"))));


            }


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
    }


    private class TableUbicacion extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            conectaTableUbicacion();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            TitutloTable.setText("Ubicación");
            ToolBarComprometidas.setVisibility(View.GONE);
            ToolBarExistencia.setVisibility(View.GONE);
            ToolBarUbicacion.setVisibility(View.VISIBLE);
            AdaptadorUbicacion adapter = new AdaptadorUbicacion(listaUbicaciones);
            recyclerListas.setAdapter(null);
            recyclerListas.setAdapter(adapter);

        }
    }

    private void conectaTableUbicacion() {

        String SOAP_ACTION = "UbicacionAlma";
        String METHOD_NAME = "UbicacionAlma";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLUbicacionAlma soapEnvelope = new XMLUbicacionAlma(SoapEnvelope.VER11);
            soapEnvelope.XMLUbicacionAlma(strusr, strpass, Producto, strcodBra);
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


            }


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
    }


    public void tablaComprometidas(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            TitutloTable.setText("Comprometidas");
            listaComprometidas = new ArrayList<>();
            listaExistencia = new ArrayList<>();
            listaUbicaciones = new ArrayList<>();
            GridLayoutManager gl = new GridLayoutManager(this, 1);
            recyclerListas.setLayoutManager(gl);
            ActivityConsultaPA.TableCompro task = new ActivityConsultaPA.TableCompro();
            task.execute();
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsultaPA.this);
            alerta.setMessage("NO HAY CONEXION A INTERNET").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("¡ERROR DE CONEXION!");
            titulo.show();

        }
    }

    public void tablasExistencia(View view) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            TitutloTable.setText("Existencia");
            listaComprometidas = new ArrayList<>();
            listaExistencia = new ArrayList<>();
            listaUbicaciones = new ArrayList<>();
            GridLayoutManager gl = new GridLayoutManager(this, 1);
            recyclerListas.setLayoutManager(gl);
            ActivityConsultaPA.TableExist task = new ActivityConsultaPA.TableExist();
            task.execute();

        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsultaPA.this);
            alerta.setMessage("NO HAY CONEXION A INTERNET").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("¡ERROR DE CONEXION!");
            titulo.show();


        }
    }

    public void tablasUbicacion(View view) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            TitutloTable.setText("Ubicaciones");
            listaComprometidas = new ArrayList<>();
            listaExistencia = new ArrayList<>();
            listaUbicaciones = new ArrayList<>();
            GridLayoutManager gl = new GridLayoutManager(this, 1);
            recyclerListas.setLayoutManager(gl);
            ActivityConsultaPA.TableUbicacion task = new ActivityConsultaPA.TableUbicacion();
            task.execute();

        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityConsultaPA.this);
            alerta.setMessage("NO HAY CONEXION A INTERNET").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("¡ERROR DE CONEXION!");
            titulo.show();


        }
    }

}