package com.almacen.alamacen202.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.almacen.alamacen202.Adapter.AdaptadorCajas;
import com.almacen.alamacen202.Adapter.AdaptadorListFolios;
import com.almacen.alamacen202.Adapter.AdaptadorListProductos;
import com.almacen.alamacen202.Imprecion.BluetoothPrint;
import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.CAJASSANDG;
import com.almacen.alamacen202.SetterandGetters.CarrTempSandG;
import com.almacen.alamacen202.SetterandGetters.ListLiberaSandG;
import com.almacen.alamacen202.SetterandGetters.ListProAduSandG;
import com.almacen.alamacen202.SetterandGetters.ListaIncidenciasSandG;
import com.almacen.alamacen202.SetterandGetters.ListaUbicasSandG;
import com.almacen.alamacen202.XML.XMLBitacoraSuper;
import com.almacen.alamacen202.XML.XMLConsultCajas;
import com.almacen.alamacen202.XML.XMLInsertCajas;
import com.almacen.alamacen202.XML.XMLInsertSurti;
import com.almacen.alamacen202.XML.XMLListFolioMIS;
import com.almacen.alamacen202.XML.XMLListFoliosLibe;
import com.almacen.alamacen202.XML.XMLListaUbica;
import com.almacen.alamacen202.XML.XMLMensajeIncidencias;
import com.almacen.alamacen202.XML.XMLRefreshCant;
import com.almacen.alamacen202.XML.XMLReportInici;
import com.almacen.alamacen202.XML.XMLValdiSuper;
import com.almacen.alamacen202.XML.XMListProAdua;
import com.almacen.alamacen202.includes.MyToolbar;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import pl.droidsonroids.gif.GifImageView;

public class ActivityLiberaciones extends AppCompatActivity {
    RecyclerView recyclerDialog2;
    ImageView imgVi;
    TextView txtFolio;
    TextView txtCliente;
    TextView txtVia;
    TextView txtProducto;
    TextView txtCantidad;
    TextView txtCantidadSurtida;
    TextView txtNumeroCajas;
    TextView IntruccionesCantidad, IntruccionesRazon, IntruccionesGafete;
    EditText txtUbicacionDestino;
    EditText ClaveObtenida;
    EditText EscaFolioED;
    EditText EdUbicacion;
    Button BUTTONADDCANT;
    Button CancelarUbicacion;
    EditText UsuarioED, CantidadED, RazonED;
    TextInputLayout TXTSHOWUSERNAME, TEXSHOWCANTI, TEXSHOWRAZON;
    GifImageView txtGifVie;
    LinearLayout CageBack, CageNext, LinearUbicacion;
    TextInputLayout txtVisiUbicacion;

    Switch ProdUbiSwitch;
    Switch AscDescSwitch;


    Context context = this;


    String UserSuper;
    String CantidadSuper;
    String RazonSuper;


    private SoundPool bepp;
    int sonido_de_reproduccion0;
    int sonido_de_reproduccion1;
    int sonido_de_reproduccion2;
    int sonido_de_reproduccion3;
    Spinner SpUbicacion;
    int cont = 0;
    int botonsumores;
    int contlis = 0;
    int contlist2 = 0;
    int band = 0;
    int band1 = 0;
    int band2 = 0;
    int ContCajas = 1;
    int contfiltrocajas = 1;
    int contDialogCajas = 1;

    Button botonAdelante;
    Button botonAtras,CageSave;


    String Documento;
    String Folio;
    String PartidaP;
    String Producto1;
    String Cantidad2;
    String Fecha;
    String Hora;
    String UbicacionOri= "";
    String UbicacionDest = "";
    String ClaveSucursalCajas;
    String FolioDocumentoCajas;
    String ClavedelProdcutoCajas;
    String CantidadUnidadesCajas;
    String NumCajasCajas;


    String UbicacionCarrito;

    String Cantidadbusqueda;
    String ProdcutoRefres;
    String PartidaPreRefres;
    String Foliorefres;
    String mensajeAutoriza, bandAutori, menbitacora;

    String strusr, strpass, strname, strlname, strtype, strbran, strma, strcodBra, StrServer, codeBar, impresora;
    ArrayList<ListLiberaSandG> listaLiberaciones = new ArrayList<>();
    ArrayList<ListLiberaSandG> listaMisLiberaciones = new ArrayList<>();
    ArrayList<CAJASSANDG> listaCajas = new ArrayList<>();
    ArrayList<CAJASSANDG> listaCajasFiltro = new ArrayList<>();
    ArrayList<ListProAduSandG> listaProduAduana = new ArrayList<>();
    ArrayList<ListaUbicasSandG> listaUbicaciones = new ArrayList<>();
    ArrayList<CarrTempSandG> listaCarritoTemporal = new ArrayList<>();
    ArrayList<ListaIncidenciasSandG> listaIncidencias = new ArrayList<>();


    AlertDialog.Builder builder;
    AlertDialog dialog = null;
    AlertDialog.Builder builder2;
    AlertDialog dialog2 = null;
    AlertDialog.Builder builder3;
    AlertDialog dialog3 = null;
    AlertDialog.Builder builder4;
    AlertDialog dialog4 = null;
    AlertDialog.Builder builder5;
    AlertDialog dialog5 = null;
    AlertDialog.Builder builder6;
    AlertDialog dialog6 = null;

    String FolioLiberacion;
    String Filtro;
    String FiltroAscDesc;
    RadioButton searchPro, searchUbi;
    int n = 2000;
    String[] search1 = new String[n];
    String fecha;
    String hora;


    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liberaciones);
        MyToolbar.show(this, "", true);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();

        botonAdelante = findViewById(R.id.FlechaAde);
        botonAtras = findViewById(R.id.FlechaAtras);
        ClaveObtenida = findViewById(R.id.txtClaveObtenida);

        imgVi = findViewById(R.id.productoImag);
        txtFolio = findViewById(R.id.txtFolio);
        txtCliente = findViewById(R.id.txtCliente);
        txtVia = findViewById(R.id.txtVia);
        txtProducto = findViewById(R.id.txtProducto);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtCantidadSurtida = findViewById(R.id.txtCantidadSurtida);
        txtNumeroCajas = findViewById(R.id.txtNumeroCaja);
        SpUbicacion = findViewById(R.id.spinnerUbicacion);
        imgVi = findViewById(R.id.imageVi);
        EscaFolioED = findViewById(R.id.txtFolioEscaneado);
        LinearUbicacion = findViewById(R.id.LinubicacionVisible);
        txtUbicacionDestino = findViewById(R.id.txtUbicacionDestino);
        txtVisiUbicacion = findViewById(R.id.txtVisiUbicacion);
        ProdUbiSwitch = findViewById(R.id.UbicProSwitch);
        AscDescSwitch = findViewById(R.id.AscDescSwitch);
        CageSave = findViewById(R.id.BotonSolo);

        bepp = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        sonido_de_reproduccion0 = bepp.load(ActivityLiberaciones.this, R.raw.beep, 1);
        sonido_de_reproduccion1 = bepp.load(ActivityLiberaciones.this, R.raw.error, 1);
        sonido_de_reproduccion2 = bepp.load(ActivityLiberaciones.this, R.raw.terminado, 1);
        sonido_de_reproduccion3 = bepp.load(ActivityLiberaciones.this, R.raw.medioter, 1);


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
        impresora = preference.getString("Impresora", "null");
        Filtro = "0";
        FiltroAscDesc = "0";


        ProdUbiSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ProdUbiSwitch.isChecked() == true) {
                    Filtro = "1";
                } else {
                    Filtro = "0";
                }
            }
        });

        AscDescSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AscDescSwitch.isChecked() == true) {
                    FiltroAscDesc = "1";
                } else {
                    FiltroAscDesc = "0";
                }
            }
        });


        ClaveObtenida.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int Cantidad = 0, CantidadSur = 0, Cantidad1 = 0, cantidadCajas = 0;

                if (!editable.toString().equals("")) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo != null && networkInfo.isConnected()) {

                        if (codeBar.equals("Zebra")) {

                        if(listaProduAduana.get(0).getConfiguracion().equals("1")){
                            if(UbicacionDest.equals("")){
                                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                alerta.setMessage("Nos has escaneado una Ubicacion Destino").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                                AlertDialog titulo = alerta.create();
                                titulo.setTitle("Ubicacion invalida");
                                titulo.show();
                            }else{
                                EscaneoCompleto(Cantidad, CantidadSur, Cantidad1, cantidadCajas, editable.toString());
                            }

                            }else{
                            EscaneoCompleto(Cantidad, CantidadSur, Cantidad1, cantidadCajas, editable.toString());
                             }


                        } else {
                            for (int i = 0; i < editable.length(); i++) {
                                char ban;
                                ban = editable.charAt(i);
                                if (ban == '\n') {
                                    String edition = editable.toString();
                                    edition = edition.replace("\n", "");
                                    if(listaProduAduana.get(0).getConfiguracion().equals("1")){
                                        if(UbicacionDest.equals("")){
                                            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                            alerta.setMessage("Nos has escaneado una Ubicacion Destino").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                    dialogInterface.cancel();
                                                }
                                            });

                                            AlertDialog titulo = alerta.create();
                                            titulo.setTitle("Ubicacion invalida");
                                            titulo.show();
                                        }else{
                                            EscaneoCompleto(Cantidad, CantidadSur, Cantidad1, cantidadCajas, editable.toString());
                                        }

                                    }else{
                                        EscaneoCompleto(Cantidad, CantidadSur, Cantidad1, cantidadCajas, editable.toString());
                                    }
                                }
                            }
                        }

                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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
        });


        EscaFolioED.addTextChangedListener(new TextWatcher() {
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
                        txtFolio.setText(editable.toString());
                        listaCajas.clear();
                        listaProduAduana.clear();
                        FolioLiberacion = editable.toString();
                        ActivityLiberaciones.ListPrAdu task = new ActivityLiberaciones.ListPrAdu();
                        task.execute();
                        ClaveObtenida.setFocusable(true);
                        ClaveObtenida.requestFocus();
                        ClaveObtenida.setInputType(InputType.TYPE_NULL);
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("EMPIEZA A ESCANEAR TUS CODIGOS").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡EMPIEZA A ESCANEAR!");
                        titulo.show();
                        dialog.dismiss();
                        dialog3.dismiss();
                        EscaFolioED.setText(null);
                    } else {
                        for (int i = 0; i < editable.length(); i++) {
                            char ban;
                            ban = editable.charAt(i);
                            if (ban == '\n') {

                                txtFolio.setText(editable.toString());
                                listaCajas.clear();
                                listaProduAduana.clear();
                                FolioLiberacion = editable.toString();
                                FolioLiberacion = FolioLiberacion.replace("\n", "");
                                ActivityLiberaciones.ListPrAdu task = new ActivityLiberaciones.ListPrAdu();
                                task.execute();
                                ClaveObtenida.setFocusable(true);
                                ClaveObtenida.requestFocus();
                                ClaveObtenida.setInputType(InputType.TYPE_NULL);
                                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                alerta.setMessage("EMPIEZA A ESCANEAR TUS CODIGOS").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                                AlertDialog titulo = alerta.create();
                                titulo.setTitle("¡EMPIEZA A ESCANEAR!");
                                titulo.show();
                                dialog.dismiss();
                                dialog3.dismiss();
                                EscaFolioED.setText(null);

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


                imgVi.setBackgroundColor(Color.rgb(4, 59, 114));
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

    //Escaneo de de l producto
    public void EscaneoCompleto(int Cantidad, int CantidadSur, int Cantidad1, int cantidadCajas, String editable) {


        String Producto = listaProduAduana.get(contlis).getProducto();
        int totalcantidadsurtida = 0;
        int totalcantidad = 0;

        for (int i = 0; i < listaProduAduana.size(); i++) {
            totalcantidadsurtida = totalcantidadsurtida + Integer.parseInt(listaProduAduana.get(i).getCantidadSurtida());
        }

        for (int i = 0; i < listaProduAduana.size(); i++) {
            totalcantidad = totalcantidad + Integer.parseInt(listaProduAduana.get(i).getCantidad());
        }

        if (totalcantidadsurtida != totalcantidad) {
            if (Producto.equals(editable)) {


                if (listaCajas.size() > 0) {


                    if (Producto.equals(listaProduAduana.get(contlis).getProducto())) {

                        Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                        CantidadSur = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                        if (Cantidad != CantidadSur) {
                            bepp.play(sonido_de_reproduccion0, 1, 1, 1, 0, 0);

                            listaProduAduana.get(contlis).setCantidadSurtida(String.valueOf(Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida()) + 1));
                            txtCantidadSurtida.setText(listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad());
                            band = 1;


                            Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                            CantidadSur = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());

                            if (Cantidad == CantidadSur) {
                                totalcantidadsurtida = 0;
                                totalcantidad = 0;
                                for (int i = 0; i < listaProduAduana.size(); i++) {
                                    totalcantidadsurtida = totalcantidadsurtida + Integer.parseInt(listaProduAduana.get(i).getCantidadSurtida());
                                }

                                for (int i = 0; i < listaProduAduana.size(); i++) {
                                    totalcantidad = totalcantidad + Integer.parseInt(listaProduAduana.get(i).getCantidad());
                                }

                                if (totalcantidadsurtida == totalcantidad) {


                                    imprimirTicketGeneral();

                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("ESTE PEDIDO A SIDO CONCLUIDO").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();


                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("PEDIDO TERMINADO");
                                    titulo.show();
                                }


                                txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSur) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));


                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                                fecha = dateformatActually.format(c.getTime());


                                Calendar calendar1 = Calendar.getInstance();
                                SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
                                hora = dateformatActually1.format(calendar1.getTime());


                                Documento = listaProduAduana.get(contlis).getDocumento();
                                Folio = listaProduAduana.get(contlis).getFolio();
                                PartidaP = listaProduAduana.get(contlis).getPPrevias();
                                Producto1 = listaProduAduana.get(contlis).getProducto();
                                Cantidad2 = listaProduAduana.get(contlis).getCantidad();
                                UbicacionOri = listaUbicaciones.get(SpUbicacion.getSelectedItemPosition()).getUbicaciones();
                                Fecha = fecha;
                                Hora = hora;

                                ActivityLiberaciones.InsertSurtido task = new ActivityLiberaciones.InsertSurtido();
                                task.execute();

                            }


                        } else {
                            bepp.play(sonido_de_reproduccion3, 1, 1, 1, 0, 0);

                            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                            alerta.setMessage("ESTE CODIGO A SIDO TERMINADO DE ESCANEAR").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();

                                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                                    if (networkInfo != null && networkInfo.isConnected()) {


                                        if (contlis < listaProduAduana.size() - 1) {
                                            contlis++;
                                            listaUbicaciones.clear();
                                            txtCliente.setText(listaProduAduana.get(contlis).getNombre());
                                            txtVia.setText(listaProduAduana.get(contlis).getVia());
                                            int CantidadSurtida, Cantidad;
                                            Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                                            CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                                            txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));

                                            if (contlis == listaProduAduana.size() - 1) {
                                                botonAdelante.setVisibility(View.INVISIBLE);
                                            } else {
                                                botonAdelante.setVisibility(View.VISIBLE);
                                            }


                                            if (contlis == 0) {
                                                botonAtras.setVisibility(View.INVISIBLE);
                                            } else {
                                                botonAtras.setVisibility(View.VISIBLE);
                                            }

                                            txtProducto.setText(listaProduAduana.get(contlis).getProducto());
                                            txtCantidad.setText(listaProduAduana.get(contlis).getCantidad() + " " + listaProduAduana.get(contlis).getUnidad());
                                            Picasso.with(getApplicationContext()).
                                                    load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProduAduana.get(contlis).getProducto() + ".jpg")
                                                    .error(R.drawable.aboutlogo)
                                                    .fit()
                                                    .centerInside()
                                                    .into(imgVi);
                                            ActivityLiberaciones.ListUbicacion task = new ActivityLiberaciones.ListUbicacion();
                                            task.execute();
                                        }
                                    } else {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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
                            });

                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("LA CANTIDAD A SIDO ESCANEADA");
                            titulo.show();
                            band = 1;


                        }

                    } else {
                        band = 0;
                    }


                    if (band == 0) {
                        txtCantidadSurtida.setText("1 " + listaProduAduana.get(contlis).getUnidad());
                        listaProduAduana.get(contlis).setCantidadSurtida(String.valueOf(Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida()) + 1));

                        Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                        CantidadSur = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                        if (Cantidad == CantidadSur) {

                            txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSur) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));

                            totalcantidadsurtida = 0;
                            totalcantidad = 0;
                            for (int i = 0; i < listaProduAduana.size(); i++) {
                                totalcantidadsurtida = totalcantidadsurtida + Integer.parseInt(listaProduAduana.get(i).getCantidadSurtida());
                            }

                            for (int i = 0; i < listaProduAduana.size(); i++) {
                                totalcantidad = totalcantidad + Integer.parseInt(listaProduAduana.get(i).getCantidad());
                            }

                            if (totalcantidadsurtida == totalcantidad) {

                                imprimirTicketGeneral();
                                ImprimirTicketCajas(ContCajas);

                                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                alerta.setMessage("ESTE PEDIDO A SIDO CONCLUIDO").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();

                                    }
                                });

                                AlertDialog titulo = alerta.create();
                                titulo.setTitle("PEDIDO TERMINADO");
                                titulo.show();
                            }


                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                            fecha = dateformatActually.format(c.getTime());


                            Calendar calendar1 = Calendar.getInstance();
                            SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
                            hora = dateformatActually1.format(calendar1.getTime());


                            Documento = listaProduAduana.get(contlis).getDocumento();
                            Folio = listaProduAduana.get(contlis).getFolio();
                            PartidaP = listaProduAduana.get(contlis).getPPrevias();
                            Producto1 = listaProduAduana.get(contlis).getProducto();
                            Cantidad2 = listaProduAduana.get(contlis).getCantidad();
                            UbicacionOri = listaUbicaciones.get(SpUbicacion.getSelectedItemPosition()).getUbicaciones();

                            Fecha = fecha;
                            Hora = hora;

                            ActivityLiberaciones.InsertSurtido task = new ActivityLiberaciones.InsertSurtido();
                            task.execute();

                        }


                    }


                    for (int i = 0; i < listaCajas.size(); i++) {

                        if (Producto.equals(listaCajas.get(i).getClavedelProdcuto()) && listaCajas.get(i).getNumCajas().equals(String.valueOf(ContCajas))) {
                            Cantidad1 = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());

                            for (int j = 0; j < listaCajas.size(); j++) {
                                if (listaCajas.get(j).getClavedelProdcuto().equals(Producto)) {
                                    cantidadCajas = cantidadCajas + Integer.parseInt(listaCajas.get(j).getCantidadUnidades());
                                }
                            }


                            if (Cantidad1 != cantidadCajas) {
                                listaCajas.get(i).setCantidadUnidades(String.valueOf(Integer.parseInt(listaCajas.get(i).getCantidadUnidades()) + 1));
                                band1 = 1;
                                cantidadCajas = 0;
                                for (int j = 0; j < listaCajas.size(); j++) {
                                    if (listaCajas.get(j).getClavedelProdcuto().equals(Producto)) {
                                        cantidadCajas = cantidadCajas + Integer.parseInt(listaCajas.get(j).getCantidadUnidades());
                                    }
                                }
                                Cantidad1 = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                                if (Cantidad1 == cantidadCajas) {
                                    ActivityLiberaciones.InsertarCajas task = new ActivityLiberaciones.InsertarCajas();
                                    task.execute();
                                }

                                break;


                            } else {
                                band1 = 1;
                                break;
                            }
                        } else {
                            band1 = 0;
                        }
                    }

                    if (band1 == 0) {
                        listaCajas.add(new CAJASSANDG(listaProduAduana.get(contlis).getSucursal(), "1", listaProduAduana.get(contlis).getFolio(), listaProduAduana.get(contlis).getProducto(), "1", String.valueOf(ContCajas)));


                        for (int j = 0; j < listaCajas.size(); j++) {
                            if (listaCajas.get(j).getClavedelProdcuto().equals(Producto)) {
                                cantidadCajas = cantidadCajas + Integer.parseInt(listaCajas.get(j).getCantidadUnidades());
                            }
                        }
                        Cantidad1 = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                        if (Cantidad1 == cantidadCajas) {
                            ActivityLiberaciones.InsertarCajas task = new ActivityLiberaciones.InsertarCajas();
                            task.execute();

                        }
                    }

                } else {
                    bepp.play(sonido_de_reproduccion0, 1, 1, 1, 0, 0);
                    listaCajas.add(new CAJASSANDG(listaProduAduana.get(contlis).getSucursal(), "1", listaProduAduana.get(contlis).getFolio(), listaProduAduana.get(contlis).getProducto(), "1", String.valueOf(ContCajas)));
                    txtCantidadSurtida.setText("1 " + listaProduAduana.get(contlis).getUnidad());
                    listaProduAduana.get(contlis).setCantidadSurtida(String.valueOf(Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida()) + 1));
                    Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                    CantidadSur = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                    if (Cantidad == CantidadSur) {

                        totalcantidadsurtida = 0;
                        totalcantidad = 0;
                        for (int i = 0; i < listaProduAduana.size(); i++) {
                            totalcantidadsurtida = totalcantidadsurtida + Integer.parseInt(listaProduAduana.get(i).getCantidadSurtida());
                        }

                        for (int i = 0; i < listaProduAduana.size(); i++) {
                            totalcantidad = totalcantidad + Integer.parseInt(listaProduAduana.get(i).getCantidad());
                        }

                        if (totalcantidadsurtida == totalcantidad) {

                            imprimirTicketGeneral();

                            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                            alerta.setMessage("ESTE PEDIDO A SIDO CONCLUIDO").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();

                                }
                            });

                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("PEDIDO TERMINADO");
                            titulo.show();
                        }


                        txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSur) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));


                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                        fecha = dateformatActually.format(c.getTime());


                        Calendar calendar1 = Calendar.getInstance();
                        SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
                        hora = dateformatActually1.format(calendar1.getTime());


                        Documento = listaProduAduana.get(contlis).getDocumento();
                        Folio = listaProduAduana.get(contlis).getFolio();
                        PartidaP = listaProduAduana.get(contlis).getPPrevias();
                        Producto1 = listaProduAduana.get(contlis).getProducto();
                        Cantidad2 = listaProduAduana.get(contlis).getCantidad();
                        UbicacionOri = listaUbicaciones.get(SpUbicacion.getSelectedItemPosition()).getUbicaciones();
                        Fecha = fecha;
                        Hora = hora;

                        ActivityLiberaciones.InsertSurtido task = new ActivityLiberaciones.InsertSurtido();
                        task.execute();


                        for (int j = 0; j < listaCajas.size(); j++) {
                            if (listaCajas.get(j).getClavedelProdcuto().equals(Producto)) {
                                cantidadCajas = cantidadCajas + Integer.parseInt(listaCajas.get(j).getCantidadUnidades());
                            }
                        }
                        Cantidad1 = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                        if (Cantidad1 == cantidadCajas) {
                            ActivityLiberaciones.InsertarCajas task1 = new ActivityLiberaciones.InsertarCajas();
                            task1.execute();

                        }


                    }


                }


            } else {
                bepp.play(sonido_de_reproduccion1, 1, 1, 1, 0, 0);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                alerta.setMessage("ESTE CODIGO POR EL MOMENTO NO SE ESTA ESCANEANDO").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();


                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("ESTE CODIGO NO ES EL MISMO ");
                titulo.show();

            }
        } else {

            bepp.play(sonido_de_reproduccion2, 1, 1, 0, 0, 1);


            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
            alerta.setMessage("ESTE PEDIDO A SIDO CONCLUIDO").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();

                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("PEDIDO TERMINADO");
            titulo.show();

        }


        ClaveObtenida.setText(null);
    }

    private void imprimirTicketGeneral() {
        builder6 = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pantallaimprimiendo, null);
        builder6.setView(dialogView);
        dialog6 = builder6.create();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        dialog6.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog6.dismiss();
            }
        }, 5000);

        switch (StrServer) {
            case "jacve.dyndns.org:9085":


                String Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                String viaEmbarque = txtVia.getText().toString();
                BluetoothPrint imprimir = new BluetoothPrint(context, getResources());


                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                List<String> listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                String[] opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLiberaciones.this);

                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("JACVE", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.jacveprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("JACVE", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.jacveprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }

                break;
            case "sprautomotive.servehttp.com:9085":

                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("VIPLA", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.viplaprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth Habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("VIPLA", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.viplaprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }

                break;
            case "cecra.ath.cx:9085":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("CECRA", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.cecraprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("CECRA", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.cecra);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "guvi.ath.cx:9085":

                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("GUVI", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.guviprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("GUVI", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.guviprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "cedistabasco.ddns.net:9085":

                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("PRESSA", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.pressaprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("PRESSA", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.pressaprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }

                break;
            case "autodis.ath.cx:9085":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("AUTODIS", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.autodisprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("AUTODIS", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.autodisprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "sprautomotive.servehttp.com:9090":


                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("RODATECH", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.rodaprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("RODATECH", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.rodaprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }

                break;
            case "sprautomotive.servehttp.com:9095":


                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("PARTECH", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.partechprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("PARTECH", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.partechprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "sprautomotive.servehttp.com:9080":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("SHARK", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.sharkprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("SHARK", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.sharkprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "vazlocolombia.dyndns.org:9085":

                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("VAZLO COLOMBIA", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.bhpprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("VAZLO COLOMBIA", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.bhpprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }

                break;
            default:
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printProductos("PRUEBAS", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.aboutlogo);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printProductos("PRUEBAS", Cliente, Folio, viaEmbarque, listaProduAduana, R.drawable.aboutlogo);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }

                break;
        }

    }

    public void ImprimirTicketCajas(int Cont) {

        builder6 = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pantallaimprimiendo, null);
        builder6.setView(dialogView);
        dialog6 = builder6.create();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        dialog6.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog6.dismiss();
            }
        }, 5000);


        switch (StrServer) {
            case "jacve.dyndns.org:9085":

                String Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                String viaEmbarque = txtVia.getText().toString();
                BluetoothPrint imprimir = new BluetoothPrint(context, getResources());


                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();

                List<String> listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                String[] opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("JACVE", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.jacveprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("JACVE", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.jacveprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }

                break;
            case "sprautomotive.servehttp.com:9085":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("VIPLA", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.viplaprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("VIPLA", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.viplaprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }


                break;
            case "cecra.ath.cx:9085":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("CECRA", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.cecraprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("CECRA", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.cecraprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "guvi.ath.cx:9085":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("GUVI", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.guviprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("GUVI", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.guviprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }


                break;
            case "cedistabasco.ddns.net:9085":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("PRESSA", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.pressaprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("PRESSA", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.pressaprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "autodis.ath.cx:9085":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("AUTODIS", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.autodisprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("AUTODIS", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.autodisprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "sprautomotive.servehttp.com:9090":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("RODATECH", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.rodaprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("RODATECH", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.rodaprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "sprautomotive.servehttp.com:9095":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("PARTECH", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.partechprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("PARTECH", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.partechprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "sprautomotive.servehttp.com:9080":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("SHARK", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.sharkprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("SHARK", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.sharkprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            case "vazlocolombia.dyndns.org:9085":
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("VAZLO COLOMBIA", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.bhpprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                impresora = preference.getString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");
                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("VAZLO COLOMBIA", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.bhpprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");
                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
            default:
                Cliente = listaProduAduana.get(0).getCliente();
                Folio = listaProduAduana.get(0).getFolio();
                viaEmbarque = txtVia.getText().toString();
                imprimir = new BluetoothPrint(context, getResources());


                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                pairedDevices = mBluetoothAdapter.getBondedDevices();

                listDevices = new ArrayList<String>();

                for (BluetoothDevice btd : pairedDevices) {
                    listDevices.add(btd.getName());
                }
                opciones = new String[listDevices.size()];

                for (int i = 0; i < listDevices.size(); i++) {
                    opciones[i] = listDevices.get(i);
                }

                builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printCajas("PRUEBAS", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.aboutlogo);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth hablitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                editor.putString("Impresora", "null");
                                impresora = preference.getString("Impresora", "null");
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");
                                Intent intent = new Intent(Settings.
                                        ACTION_BLUETOOTH_SETTINGS);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("¡AVISO!");
                        titulo.show();
                    }
                } else {
                    if (opciones.length > 0) {
                        builder.setTitle("Seleccione una impresoras emparejada");
                        builder.setItems(opciones, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                editor.putString("Impresora", opciones[which]);
                                editor.commit();
                                impresora = preference.getString("Impresora", "null");

                                imprimir.FindBluetoothDevice(opciones[which]);
                                imprimir.openBluetoothPrinter();
                                if (imprimir.checkConnection() == true) {
                                    imprimir.printCajas("PRUEBAS", Cliente, Folio, viaEmbarque, listaCajasFiltro, String.valueOf(Cont), R.drawable.aboutlogo);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                    alerta.setMessage("Verifique que la impresora este encendida \n o que tenga el bluetooth habilitado").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();

                                            editor.putString("Impresora", "null");
                                            editor.commit();
                                            impresora = preference.getString("Impresora", "null");

                                            Intent intent = new Intent(Settings.
                                                    ACTION_BLUETOOTH_SETTINGS);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);
                                        }
                                    });

                                    AlertDialog titulo = alerta.create();
                                    titulo.setTitle("¡AVISO!");
                                    titulo.show();
                                }


                            }
                        });
// create and show the alert dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Intent intent = new Intent(Settings.
                                ACTION_BLUETOOTH_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }
                break;
        }
    }

    public void GuardarInicidencias (){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
            fecha = dateformatActually.format(c.getTime());


            Calendar calendar1 = Calendar.getInstance();
            SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
            hora = dateformatActually1.format(calendar1.getTime());


            Documento = listaProduAduana.get(contlis).getDocumento();
            Folio = listaProduAduana.get(contlis).getFolio();
            PartidaP = listaProduAduana.get(contlis).getPPrevias();
            Producto1 = listaProduAduana.get(contlis).getProducto();
            Cantidad2 = listaProduAduana.get(contlis).getCantidadSurtida();


            UbicacionOri = listaUbicaciones.get(SpUbicacion.getSelectedItemPosition()).getUbicaciones();
            Fecha = fecha;
            Hora = hora;
            Toast.makeText(context, "GUARDADO", Toast.LENGTH_SHORT).show();

            if (Integer.parseInt(listaProduAduana.get(contlis).getCantidad()) != Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida())) {

                ActivityLiberaciones.ActualizSurtido task = new ActivityLiberaciones.ActualizSurtido();
                task.execute();

            }

        }

    }



    public void GuardaDatos(View view) {


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
            fecha = dateformatActually.format(c.getTime());


            Calendar calendar1 = Calendar.getInstance();
            SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
            hora = dateformatActually1.format(calendar1.getTime());


            Documento = listaProduAduana.get(contlis).getDocumento();
            Folio = listaProduAduana.get(contlis).getFolio();
            PartidaP = listaProduAduana.get(contlis).getPPrevias();
            Producto1 = listaProduAduana.get(contlis).getProducto();
            Cantidad2 = listaProduAduana.get(contlis).getCantidadSurtida();


            UbicacionOri = listaUbicaciones.get(SpUbicacion.getSelectedItemPosition()).getUbicaciones();
            Fecha = fecha;
            Hora = hora;
            Toast.makeText(context, "GUARDADO", Toast.LENGTH_SHORT).show();

            if (Integer.parseInt(listaProduAduana.get(contlis).getCantidad()) != Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida())) {

                ActivityLiberaciones.ActualizSurtido task = new ActivityLiberaciones.ActualizSurtido();
                task.execute();

            }

        }

    }

    //Escanear  el folio  del pedido que se va realizar
    public void EscanearFolio(View view) {
        EscaFolioED.setFocusable(true);
        EscaFolioED.requestFocus();
        EscaFolioED.setInputType(InputType.TYPE_NULL);
        dialog.dismiss();

        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
        alerta.setMessage("ESCANE PORFAVOR EL CODIGO DE LIBERACION").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog titulo = alerta.create();
        titulo.setTitle("¡AHORA PUEDES ESCANEAR!");
        titulo.show();
    }

    //Boton hacia adelante prodcuto
    public void NextProd(View view) {

        builder6 = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pantallacargacarrito, null);
        builder6.setView(dialogView);
        dialog6 = builder6.create();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
            fecha = dateformatActually.format(c.getTime());


            Calendar calendar1 = Calendar.getInstance();
            SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
            hora = dateformatActually1.format(calendar1.getTime());

            botonsumores = 1;
            Documento = listaProduAduana.get(contlis).getDocumento();
            Folio = listaProduAduana.get(contlis).getFolio();
            PartidaP = listaProduAduana.get(contlis).getPPrevias();
            Producto1 = listaProduAduana.get(contlis).getProducto();
            Cantidad2 = listaProduAduana.get(contlis).getCantidadSurtida();

            dialog6.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog6.dismiss();
                }
            }, 2000);

            if(listaUbicaciones.size()!=0){
                UbicacionOri = listaUbicaciones.get(SpUbicacion.getSelectedItemPosition()).getUbicaciones();
            }else{
                UbicacionOri="";
            }

            Fecha = fecha;
            Hora = hora;
            if (Integer.parseInt(listaProduAduana.get(contlis).getCantidad()) != Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida())) {

                ActivityLiberaciones.ActualizSurtido task = new ActivityLiberaciones.ActualizSurtido();
                task.execute();

            } else {
                if (botonsumores == 1) {
                    if (contlis < listaProduAduana.size() - 1) {
                        contlis++;
                        listaUbicaciones.clear();
                        txtCliente.setText(listaProduAduana.get(contlis).getNombre());
                        txtVia.setText(listaProduAduana.get(contlis).getVia());

                        int CantidadSurtida, Cantidad;
                        Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                        CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                        txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));


                        if (contlis == listaProduAduana.size() - 1) {
                            botonAdelante.setVisibility(View.INVISIBLE);
                        } else {
                            botonAdelante.setVisibility(View.VISIBLE);
                        }


                        if (contlis == 0) {
                            botonAtras.setVisibility(View.INVISIBLE);
                        } else {
                            botonAtras.setVisibility(View.VISIBLE);
                        }


                        txtProducto.setText(listaProduAduana.get(contlis).getProducto());
                        txtCantidad.setText(listaProduAduana.get(contlis).getCantidad() + " " + listaProduAduana.get(contlis).getUnidad());
                        Picasso.with(getApplicationContext()).
                                load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProduAduana.get(contlis).getProducto() + ".jpg")
                                .error(R.drawable.aboutlogo)
                                .fit()
                                .centerInside()
                                .into(imgVi);

                        listaCajas = new ArrayList<>();
                        ActivityLiberaciones.ConsultaCajas2 task = new ActivityLiberaciones.ConsultaCajas2();
                        task.execute();
                    }

                }
            }

        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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

    //Boton hacia atras  producto
    public void BackProd(View view) {


        builder6 = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pantallacargacarrito, null);
        builder6.setView(dialogView);
        dialog6 = builder6.create();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
            fecha = dateformatActually.format(c.getTime());


            Calendar calendar1 = Calendar.getInstance();
            SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
            hora = dateformatActually1.format(calendar1.getTime());

            botonsumores = 0;

            Documento = listaProduAduana.get(contlis).getDocumento();
            Folio = listaProduAduana.get(contlis).getFolio();
            PartidaP = listaProduAduana.get(contlis).getPPrevias();
            Producto1 = listaProduAduana.get(contlis).getProducto();
            Cantidad2 = listaProduAduana.get(contlis).getCantidadSurtida();

            dialog6.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog6.dismiss();
                }
            }, 2000);


            if(listaUbicaciones.size()!=0){
                UbicacionOri = listaUbicaciones.get(SpUbicacion.getSelectedItemPosition()).getUbicaciones();
            }else{
                UbicacionOri = "";
            }

            Fecha = fecha;
            Hora = hora;
            if (Integer.parseInt(listaProduAduana.get(contlis).getCantidad()) != Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida())) {

                ActivityLiberaciones.ActualizSurtido task = new ActivityLiberaciones.ActualizSurtido();
                task.execute();

            } else {

                if (contlis > 0) {


                    contlis--;
                    listaUbicaciones.clear();
                    txtCliente.setText(listaProduAduana.get(contlis).getNombre());
                    txtVia.setText(listaProduAduana.get(contlis).getVia());

                    int CantidadSurtida, Cantidad;
                    Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                    CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                    txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));

                    if (contlis == 0) {
                        botonAtras.setVisibility(View.INVISIBLE);
                    } else {
                        botonAtras.setVisibility(View.VISIBLE);
                    }

                    if (contlis == listaProduAduana.size() - 1) {
                        botonAdelante.setVisibility(View.INVISIBLE);
                    } else {
                        botonAdelante.setVisibility(View.VISIBLE);
                    }

                    txtProducto.setText(listaProduAduana.get(contlis).getProducto());
                    txtCantidad.setText(listaProduAduana.get(contlis).getCantidad() + " " + listaProduAduana.get(contlis).getUnidad());

                    Picasso.with(getApplicationContext()).
                            load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProduAduana.get(contlis).getProducto() + ".jpg")
                            .error(R.drawable.aboutlogo)
                            .fit()
                            .centerInside()
                            .into(imgVi);
                    listaCajas = new ArrayList<>();
                    ActivityLiberaciones.ConsultaCajas2 task = new ActivityLiberaciones.ConsultaCajas2();
                    task.execute();

                }
            }

        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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

//Guardar datos de cuando cambie por medio de la lista
private class ActualizaSurtidoLista extends AsyncTask<Void, Void, Void> {

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(Void... params) {

        if (Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida()) > 0) {
            ActualizarSurti();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        ActivityLiberaciones.ActualizarCajasLista task = new ActivityLiberaciones.ActualizarCajasLista();
        task.execute();
    }

}
    private class ActualizarCajasLista extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            if (Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida()) > 0) {
                ActualizCajas();
            }
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            contlis = contlist2;
            listaUbicaciones.clear();
            txtCliente.setText(listaProduAduana.get(contlis).getNombre());
            txtVia.setText(listaProduAduana.get(contlis).getVia());

            int CantidadSurtida, Cantidad;
            Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
            CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
            txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));


            if (contlis == listaProduAduana.size() - 1) {
                botonAdelante.setVisibility(View.INVISIBLE);
            } else {
                botonAdelante.setVisibility(View.VISIBLE);
            }


            if (contlis == 0) {
                botonAtras.setVisibility(View.INVISIBLE);
            } else {
                botonAtras.setVisibility(View.VISIBLE);
            }

            txtProducto.setText(listaProduAduana.get(contlis).getProducto());
            txtCantidad.setText(listaProduAduana.get(contlis).getCantidad() + " " + listaProduAduana.get(contlis).getUnidad());
            Picasso.with(getApplicationContext()).
                    load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProduAduana.get(contlis).getProducto() + ".jpg")
                    .error(R.drawable.aboutlogo)
                    .fit()
                    .centerInside()
                    .into(imgVi);
            ActivityLiberaciones.ListUbicacion task = new ActivityLiberaciones.ListUbicacion();
            task.execute();
        }
    }



    //Insercion de piezas suertidas en las listas

    private class ActualizSurtido extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {

            if (Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida()) > 0) {
                ActualizarSurti();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ActivityLiberaciones.ActualizarCajas task = new ActivityLiberaciones.ActualizarCajas();
            task.execute();
        }
    }


    private void ActualizarSurti() {
        String SOAP_ACTION = "InsertSurti";
        String METHOD_NAME = "InsertSurti";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLInsertSurti soapEnvelope = new XMLInsertSurti(SoapEnvelope.VER11);
            soapEnvelope.XMLInsertSurti(strusr, strpass, strcodBra, Documento, Folio, PartidaP, Producto1, Cantidad2, Fecha, Hora, UbicacionOri, UbicacionDest);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("INSURTREQ");

        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }

    //Insercion de Cajas de las listas

    private class ActualizarCajas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            if (Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida()) > 0) {
                ActualizCajas();
            }
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            if (botonsumores == 1) {
                if (contlis < listaProduAduana.size() - 1) {
                    contlis++;
                    listaUbicaciones.clear();
                    txtCliente.setText(listaProduAduana.get(contlis).getNombre());
                    txtVia.setText(listaProduAduana.get(contlis).getVia());

                    int CantidadSurtida, Cantidad;
                    Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                    CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                    txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));


                    if (contlis == listaProduAduana.size() - 1) {
                        botonAdelante.setVisibility(View.INVISIBLE);
                    } else {
                        botonAdelante.setVisibility(View.VISIBLE);
                    }


                    if (contlis == 0) {
                        botonAtras.setVisibility(View.INVISIBLE);
                    } else {
                        botonAtras.setVisibility(View.VISIBLE);
                    }

                    txtProducto.setText(listaProduAduana.get(contlis).getProducto());
                    txtCantidad.setText(listaProduAduana.get(contlis).getCantidad() + " " + listaProduAduana.get(contlis).getUnidad());
                    Picasso.with(getApplicationContext()).
                            load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProduAduana.get(contlis).getProducto() + ".jpg")
                            .error(R.drawable.aboutlogo)
                            .fit()
                            .centerInside()
                            .into(imgVi);

                    listaCajas = new ArrayList<>();
                    ActivityLiberaciones.ConsultaCajas2 task = new ActivityLiberaciones.ConsultaCajas2();
                    task.execute();
                }

            } else {
                if (contlis > 0) {
                    contlis--;
                    listaUbicaciones.clear();
                    txtCliente.setText(listaProduAduana.get(contlis).getNombre());
                    txtVia.setText(listaProduAduana.get(contlis).getVia());

                    int CantidadSurtida, Cantidad;
                    Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                    CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                    txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));

                    if (contlis == 0) {
                        botonAtras.setVisibility(View.INVISIBLE);
                    } else {
                        botonAtras.setVisibility(View.VISIBLE);
                    }

                    if (contlis == listaProduAduana.size() - 1) {
                        botonAdelante.setVisibility(View.INVISIBLE);
                    } else {
                        botonAdelante.setVisibility(View.VISIBLE);
                    }

                    txtProducto.setText(listaProduAduana.get(contlis).getProducto());
                    txtCantidad.setText(listaProduAduana.get(contlis).getCantidad() + " " + listaProduAduana.get(contlis).getUnidad());

                    Picasso.with(getApplicationContext()).
                            load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProduAduana.get(contlis).getProducto() + ".jpg")
                            .error(R.drawable.aboutlogo)
                            .fit()
                            .centerInside()
                            .into(imgVi);

                    listaCajas = new ArrayList<>();
                    ActivityLiberaciones.ConsultaCajas2 task = new ActivityLiberaciones.ConsultaCajas2();
                    task.execute();
                }
            }
        }
    }

    private void ActualizCajas() {
        String SOAP_ACTION = "InsertCajas";
        String METHOD_NAME = "InsertCajas";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";

        for (int k = 0; k < listaCajas.size(); k++) {

            String Prod;
            Prod = listaProduAduana.get(contlis).getProducto();
            String ProdCajas = listaCajas.get(k).getClavedelProdcuto();
            if (Prod.equals(ProdCajas)) {
                ClaveSucursalCajas = listaCajas.get(k).getClaveSucursal();
                FolioDocumentoCajas = listaCajas.get(k).getFolioDocumento();
                ClavedelProdcutoCajas = listaCajas.get(k).getClavedelProdcuto();
                CantidadUnidadesCajas = listaCajas.get(k).getCantidadUnidades();
                NumCajasCajas = listaCajas.get(k).getNumCajas();

                try {
                    SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                    XMLInsertCajas soapEnvelope = new XMLInsertCajas(SoapEnvelope.VER11);
                    soapEnvelope.XMLInsertCajas(strusr, strpass, ClaveSucursalCajas, FolioDocumentoCajas, ClavedelProdcutoCajas, CantidadUnidadesCajas, NumCajasCajas);
                    soapEnvelope.dotNet = true;
                    soapEnvelope.implicitTypes = true;
                    soapEnvelope.setOutputSoapObject(Request);
                    HttpTransportSE trasport = new HttpTransportSE(URL);
                    trasport.debug = true;
                    trasport.call(SOAP_ACTION, soapEnvelope);
                    SoapObject response = (SoapObject) soapEnvelope.bodyIn;
                    response = (SoapObject) response.getProperty("INSURTREQ");

                } catch (SoapFault soapFault) {
                    soapFault.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                }


            }
        }


    }

    //Calcula y analiza las listas si han cumplido con las cantidades  si se realizo la autorizacion
    public void Bitacora(View view) {
        CantidadSuper = CantidadED.getText().toString();
        RazonSuper = RazonED.getText().toString();
        int Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
        int CantSurtidaEn = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
        int CantidadSuperEn = Integer.parseInt(CantidadSuper);
        int Cantidadlista, CantidadSurlista;
        int totalcantidadsurtida = 0;
        int totalcantidad = 0;
        int Cantidad1 = 0, cantidadCajas = 0, CantidadCarritotemporal = 0;
        if (CantSurtidaEn < Cantidad) {
            CantidadSuperEn = CantidadSuperEn + CantSurtidaEn;
            if (CantidadSuperEn <= Cantidad) {

                if (listaCajas.size() > 0) {


                    if (Cantidad != CantSurtidaEn) {


                        int CantidadSurtida;
                        Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                        CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());

                        listaProduAduana.get(contlis).setCantidadSurtida(String.valueOf(CantidadSuperEn));
                        txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));
                        band = 1;


                        Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                        CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());

                        if (Cantidad == CantidadSurtida) {

                            txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));

                            totalcantidadsurtida = 0;
                            totalcantidad = 0;

                            for (int i = 0; i < listaProduAduana.size(); i++) {
                                totalcantidadsurtida = totalcantidadsurtida + Integer.parseInt(listaProduAduana.get(i).getCantidadSurtida());
                            }

                            for (int i = 0; i < listaProduAduana.size(); i++) {
                                totalcantidad = totalcantidad + Integer.parseInt(listaProduAduana.get(i).getCantidad());
                            }

                            if (totalcantidadsurtida == totalcantidad) {

                                imprimirTicketGeneral();

                                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                                alerta.setMessage("ESTE PEDIDO A SIDO CONCLUIDO").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();

                                    }
                                });

                                AlertDialog titulo = alerta.create();
                                titulo.setTitle("PEDIDO TERMINADO");
                                titulo.show();
                            }
                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                            fecha = dateformatActually.format(c.getTime());


                            Calendar calendar1 = Calendar.getInstance();
                            SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
                            hora = dateformatActually1.format(calendar1.getTime());


                            Documento = listaProduAduana.get(contlis).getDocumento();
                            Folio = listaProduAduana.get(contlis).getFolio();
                            PartidaP = listaProduAduana.get(contlis).getPPrevias();
                            Producto1 = listaProduAduana.get(contlis).getProducto();
                            Cantidad2 = listaProduAduana.get(contlis).getCantidad();
                            UbicacionOri = listaUbicaciones.get(SpUbicacion.getSelectedItemPosition()).getUbicaciones();
                            Fecha = fecha;
                            Hora = hora;

                            ActivityLiberaciones.InsertSurtido task = new ActivityLiberaciones.InsertSurtido();
                            task.execute();

                        }
                        for (int i = 0; i < listaCajas.size(); i++) {

                            if (listaProduAduana.get(contlis).getProducto().equals(listaCajas.get(i).getClavedelProdcuto()) && listaCajas.get(i).getNumCajas().equals(String.valueOf(ContCajas))) {
                                Cantidad1 = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());

                                for (int j = 0; j < listaCajas.size(); j++) {
                                    if (listaCajas.get(j).getClavedelProdcuto().equals(listaProduAduana.get(contlis).getProducto())) {
                                        cantidadCajas = cantidadCajas + Integer.parseInt(listaCajas.get(j).getCantidadUnidades());
                                    }
                                }


                                if (Cantidad1 != cantidadCajas) {
                                    CantidadSuperEn = Integer.parseInt(CantidadSuper);
                                    cantidadCajas = cantidadCajas + CantidadSuperEn;
                                    if (Cantidad1 != cantidadCajas || Cantidad1 == cantidadCajas) {
                                        listaCajas.get(i).setCantidadUnidades(String.valueOf(cantidadCajas));
                                        band1 = 1;
                                        if (Cantidad1 == cantidadCajas) {
                                            ActivityLiberaciones.InsertarCajas task = new ActivityLiberaciones.InsertarCajas();
                                            task.execute();
                                        }

                                        break;

                                    }


                                } else {
                                    band1 = 1;
                                    break;
                                }
                            } else {
                                band1 = 0;
                            }
                        }

                        if (band1 == 0) {
                            listaCajas.add(new CAJASSANDG(listaProduAduana.get(contlis).getSucursal(), "1", listaProduAduana.get(contlis).getFolio(), listaProduAduana.get(contlis).getProducto(), String.valueOf(CantidadSuperEn), String.valueOf(ContCajas)));


                            for (int j = 0; j < listaCajas.size(); j++) {
                                if (listaCajas.get(j).getClavedelProdcuto().equals(listaProduAduana.get(contlis).getProducto())) {
                                    cantidadCajas = cantidadCajas + Integer.parseInt(listaCajas.get(j).getCantidadUnidades());
                                }
                            }
                            Cantidad1 = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                            if (Cantidad1 == cantidadCajas) {
                                ActivityLiberaciones.InsertarCajas task = new ActivityLiberaciones.InsertarCajas();
                                task.execute();

                            }
                        }


                    } else {
                        bepp.play(sonido_de_reproduccion3, 1, 1, 1, 0, 0);

                        AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                        alerta.setMessage("ESTE CODIGO A SIDO TERMINADO DE ESCANEAR").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();

                                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                                if (networkInfo != null && networkInfo.isConnected()) {


                                    if (contlis < listaProduAduana.size() - 1) {
                                        contlis++;
                                        listaUbicaciones.clear();
                                        txtCliente.setText(listaProduAduana.get(contlis).getNombre());
                                        txtVia.setText(listaProduAduana.get(contlis).getVia());
                                        int CantidadSurtida, Cantidad;
                                        Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                                        CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                                        txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));

                                        if (contlis == listaProduAduana.size() - 1) {
                                            botonAdelante.setVisibility(View.INVISIBLE);
                                        } else {
                                            botonAdelante.setVisibility(View.VISIBLE);
                                        }


                                        if (contlis == 0) {
                                            botonAtras.setVisibility(View.INVISIBLE);
                                        } else {
                                            botonAtras.setVisibility(View.VISIBLE);
                                        }

                                        txtProducto.setText(listaProduAduana.get(contlis).getProducto());
                                        txtCantidad.setText(listaProduAduana.get(contlis).getCantidad() + " " + listaProduAduana.get(contlis).getUnidad());
                                        Picasso.with(getApplicationContext()).
                                                load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProduAduana.get(contlis).getProducto() + ".jpg")
                                                .error(R.drawable.aboutlogo)
                                                .fit()
                                                .centerInside()
                                                .into(imgVi);
                                        ActivityLiberaciones.ListUbicacion task = new ActivityLiberaciones.ListUbicacion();
                                        task.execute();
                                    }
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("LA CANTIDAD A SIDO ESCANEADA");
                        titulo.show();
                        band = 1;


                    }


                } else {
                    listaCajas.add(new CAJASSANDG(listaProduAduana.get(contlis).getSucursal(), "1", listaProduAduana.get(contlis).getFolio(), listaProduAduana.get(contlis).getProducto(), String.valueOf(CantidadSuperEn), String.valueOf(ContCajas)));
                    txtCantidadSurtida.setText(CantidadSuperEn + " " + listaProduAduana.get(contlis).getUnidad());
                    listaProduAduana.get(contlis).setCantidadSurtida(String.valueOf(CantidadSuperEn));
                    //listaCarritoTemporal.add(new CarrTempSandG(strcodBra, listaProduAduana.get(contlis).getFolio(), listaProduAduana.get(contlis).getProducto(), String.valueOf(CantidadSuperEn), "Carrito " + CarrTemporal, String.valueOf(CarrTemporal)));

                    Producto1 = listaProduAduana.get(contlis).getProducto();
                    listaProduAduana.get(contlis).setCantidadSurtida(String.valueOf(CantidadSuperEn));
                    Cantidadlista = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                    CantidadSurlista = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());


                    if (Cantidadlista == CantidadSurlista) {


                        txtCantidadSurtida.setText(Html.fromHtml((Cantidadlista > CantidadSurlista) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));
                        totalcantidadsurtida = 0;
                        totalcantidad = 0;

                        for (int i = 0; i < listaProduAduana.size(); i++) {
                            totalcantidadsurtida = totalcantidadsurtida + Integer.parseInt(listaProduAduana.get(i).getCantidadSurtida());
                        }

                        for (int i = 0; i < listaProduAduana.size(); i++) {
                            totalcantidad = totalcantidad + Integer.parseInt(listaProduAduana.get(i).getCantidad());
                        }

                        if (totalcantidadsurtida == totalcantidad) {

                            imprimirTicketGeneral();


                            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                            alerta.setMessage("ESTE PEDIDO A SIDO CONCLUIDO").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();

                                }
                            });

                            AlertDialog titulo = alerta.create();
                            titulo.setTitle("PEDIDO TERMINADO");
                            titulo.show();
                        }


                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                        fecha = dateformatActually.format(c.getTime());


                        Calendar calendar1 = Calendar.getInstance();
                        SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
                        hora = dateformatActually1.format(calendar1.getTime());


                        Documento = listaProduAduana.get(contlis).getDocumento();
                        Folio = listaProduAduana.get(contlis).getFolio();
                        PartidaP = listaProduAduana.get(contlis).getPPrevias();
                        Producto1 = listaProduAduana.get(contlis).getProducto();
                        Cantidad2 = listaProduAduana.get(contlis).getCantidad();
                        UbicacionOri = listaUbicaciones.get(SpUbicacion.getSelectedItemPosition()).getUbicaciones();
                        Fecha = fecha;
                        Hora = hora;

                        ActivityLiberaciones.InsertSurtido task = new ActivityLiberaciones.InsertSurtido();
                        task.execute();


                        for (int j = 0; j < listaCajas.size(); j++) {
                            if (listaCajas.get(j).getClavedelProdcuto().equals(listaProduAduana.get(contlis).getProducto())) {
                                cantidadCajas = cantidadCajas + Integer.parseInt(listaCajas.get(j).getCantidadUnidades());
                            }
                        }
                        Cantidad1 = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                        if (Cantidad1 == cantidadCajas) {
                            ActivityLiberaciones.InsertarCajas task1 = new ActivityLiberaciones.InsertarCajas();
                            task1.execute();

                        }


                    }

                }
                dialog5.dismiss();

                ActivityLiberaciones.BitacoraSuperv task = new ActivityLiberaciones.BitacoraSuperv();
                task.execute();


            } else {

                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                alerta.setMessage("LA CANTIDAD INGRESADA ES MAYOR A LA QUE ESTA  SOLICITADA EN EL PEDIDO ").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("ESTA CANDIDAD NO ES VALIDA");
                titulo.show();

            }

        } else {

            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
            alerta.setMessage("NO SE PUEDEN INGRESAR MAS PIEZAS").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("ESTE PRODUCTO AH SIDO TERMINADO");
            titulo.show();

        }

    }

    //Registra la bitacora  la autorizacion que se realizo
    private class BitacoraSuperv extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            BitacoraCall();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {

            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
            alerta.setMessage(menbitacora).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("¡AVISO!");
            titulo.show();

        }
    }


    private void BitacoraCall() {
        String SOAP_ACTION = "BitacoraSuper";
        String METHOD_NAME = "BitacoraSuper";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLBitacoraSuper soapEnvelope = new XMLBitacoraSuper(SoapEnvelope.VER11);
            soapEnvelope.XMLBitacoraSupervi(strusr, strpass, UserSuper, Producto1, CantidadSuper, FolioLiberacion, RazonSuper);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("message");
            menbitacora = response.getPropertyAsString("k_menssage").equals("anyType{}") ? "" : response.getPropertyAsString("k_menssage");

        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }

    //Valida si esta aurotirado el supervisor
    public void Autorizacion(String Usuario) {

        UserSuper = Usuario;

        if (!UserSuper.equals("")) {
            ActivityLiberaciones.AutorizacionSuperv task = new ActivityLiberaciones.AutorizacionSuperv();
            task.execute();
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
            alerta.setMessage("Confirme que todos los campos hallan sido llenados").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("¡ERROR!");
            titulo.show();
        }

    }


    private class AutorizacionSuperv extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            AutorizCall();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            if (bandAutori.equals("1")) {
                TXTSHOWUSERNAME.setVisibility(View.GONE);
                IntruccionesCantidad.setVisibility(View.VISIBLE);
                TEXSHOWCANTI.setVisibility(View.VISIBLE);
                IntruccionesRazon.setVisibility(View.VISIBLE);
                TEXSHOWRAZON.setVisibility(View.VISIBLE);
                BUTTONADDCANT.setVisibility(View.VISIBLE);
                txtGifVie.setVisibility(View.GONE);
                IntruccionesGafete.setVisibility(View.GONE);
                ClaveObtenida.setFocusable(true);
                ClaveObtenida.requestFocus();
                ClaveObtenida.setInputType(InputType.TYPE_NULL);


            } else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                alerta.setMessage(mensajeAutoriza).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();

                        dialog5.dismiss();

                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("¡ERROR!");
                titulo.show();

            }
        }
    }


    private void AutorizCall() {
        String SOAP_ACTION = "ValdiSuper";
        String METHOD_NAME = "ValdiSuper";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLValdiSuper soapEnvelope = new XMLValdiSuper(SoapEnvelope.VER11);
            soapEnvelope.XMLValdiSuper(strusr, strpass, UserSuper);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("message");
            bandAutori = response.getPropertyAsString("k_Val").equals("anyType{}") ? "" : response.getPropertyAsString("k_Val");
            mensajeAutoriza = response.getPropertyAsString("k_menssage").equals("anyType{}") ? "" : response.getPropertyAsString("k_menssage");

        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }


    //Lista de folios liberados todos
    private class FoliosLiberados extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            ListaFolios();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            dialog3 = builder3.create();
            dialog3.show();

        }
    }


    private void ListaFolios() {
        String SOAP_ACTION = "ListFolLib";
        String METHOD_NAME = "ListFolLib";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLListFoliosLibe soapEnvelope = new XMLListFoliosLibe(SoapEnvelope.VER11);
            soapEnvelope.XMLListFoliosLibe(strusr, strpass, strcodBra);
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

                listaLiberaciones.add(new ListLiberaSandG(
                        (response0.getPropertyAsString("k_Cliente").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Cliente")),
                        (response0.getPropertyAsString("k_Folio").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("k_Fecha").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Fecha")),
                        (response0.getPropertyAsString("k_Referencia").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Referencia")),
                        (response0.getPropertyAsString("k_Documento").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Documento")),
                        (response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Cantidad")),
                        (response0.getPropertyAsString("k_CantidSurt").equals("anyType{}") ? "" : response0.getPropertyAsString("k_CantidSurt")),
                        (response0.getPropertyAsString("k_Urgencias").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Urgencias"))));


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

    //Busca al web service los folios liberados asigados al usuario
    private class MisFoliosLiberados extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            ListFLNE();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            dialog3 = builder3.create();
            dialog3.show();
        }
    }

    private void ListFLNE() {
        String SOAP_ACTION = "ListFLNE";
        String METHOD_NAME = "ListFLNE";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLListFolioMIS soapEnvelope = new XMLListFolioMIS(SoapEnvelope.VER11);
            soapEnvelope.XMLListFolioMIS(strusr, strpass, strcodBra);
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

                listaMisLiberaciones.add(new ListLiberaSandG(
                        (response0.getPropertyAsString("k_Cliente").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Cliente")),
                        (response0.getPropertyAsString("k_Folio").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("k_Fecha").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Fecha")),
                        (response0.getPropertyAsString("k_Referencia").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Referencia")),
                        (response0.getPropertyAsString("k_Documento").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Documento")),
                        (response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Cantidad")),
                        (response0.getPropertyAsString("k_CantidSurt").equals("anyType{}") ? "" : response0.getPropertyAsString("k_CantidSurt")),
                        (response0.getPropertyAsString("k_Urgencias").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Urgencias"))));


            }

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

    //Muestra la pantalla  para seleccionar folios
    public void ListaLiberaciones(View view) {
        builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_info_selecttypefol, null);
        builder.setView(dialogView);
        dialog = builder.create();
        dialog.show();

    }

    //Muestra los folios asignados por usuario
    public void MISFOLIOS(View view) {


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            listaMisLiberaciones.clear();
            listaLiberaciones.clear();
            ActivityLiberaciones.MisFoliosLiberados task = new ActivityLiberaciones.MisFoliosLiberados();
            task.execute();


            builder3 = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_info_listafolios, null);
            builder3.setView(dialogView);
            recyclerDialog2 = (RecyclerView) dialogView.findViewById(R.id.recyclerFacturas);
            GridLayoutManager gl = new GridLayoutManager(this, 1);
            recyclerDialog2.setLayoutManager(gl);

            AdaptadorListFolios adapter = new AdaptadorListFolios(listaMisLiberaciones);
            recyclerDialog2.setAdapter(null);
            recyclerDialog2.setAdapter(adapter);


        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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

    //Muestra todos los folios
    public void TODFOLIOS(View view) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


            listaMisLiberaciones.clear();
            listaLiberaciones.clear();
            ActivityLiberaciones.FoliosLiberados task = new ActivityLiberaciones.FoliosLiberados();
            task.execute();


            builder3 = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_info_listafolios, null);
            builder3.setView(dialogView);
            recyclerDialog2 = (RecyclerView) dialogView.findViewById(R.id.recyclerFacturas);
            GridLayoutManager gl = new GridLayoutManager(this, 1);
            recyclerDialog2.setLayoutManager(gl);

            AdaptadorListFolios adapter = new AdaptadorListFolios(listaLiberaciones);
            recyclerDialog2.setAdapter(null);
            recyclerDialog2.setAdapter(adapter);
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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


    public void cancelar(View view) {

        dialog6.dismiss();

    }

    //Seleccionar un folio para empezar a susrtir el pedido
    public void SelectFolio(View view) {

        SpUbicacion.setAdapter(null);
        listaUbicaciones = new ArrayList<>();
        builder6 = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pantallacargaubicacion, null);
        CancelarUbicacion = (Button) dialogView.findViewById(R.id.cancelar);
        EdUbicacion = dialogView.findViewById(R.id.txtUbicacionIsla);
        builder6.setView(dialogView);
        dialog6 = builder6.create();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


            int position = recyclerDialog2.getChildAdapterPosition(recyclerDialog2.findContainingItemView(view));


            if (listaLiberaciones.size() > 0) {
                listaLiberaciones.get(position).getFolio();
                txtFolio.setText(listaLiberaciones.get(position).getFolio());
                listaProduAduana.clear();
                listaCajas.clear();
                FolioLiberacion = txtFolio.getText().toString();
                ActivityLiberaciones.ListPrAdu task = new ActivityLiberaciones.ListPrAdu();
                task.execute();

                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                alerta.setMessage("EMPIEZA A ESCANEAR TUS CODIGOS").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();

                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("¡EMPIEZA A ESCANEAR!");
                titulo.show();

                EdUbicacion.addTextChangedListener(new TextWatcher() {
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

                                UbicacionDest = editable.toString();
                                dialog6.dismiss();
                                ClaveObtenida.setFocusable(true);
                                ClaveObtenida.requestFocus();
                                ClaveObtenida.setInputType(InputType.TYPE_NULL);
                                txtUbicacionDestino.setText(UbicacionDest);


                            } else {
                                for (int i = 0; i < editable.length(); i++) {
                                    char ban;
                                    ban = editable.charAt(i);
                                    if (ban == '\n') {
                                        UbicacionDest = editable.toString();
                                        UbicacionDest = UbicacionCarrito.replace("\n", "");
                                        dialog6.dismiss();
                                        ClaveObtenida.setFocusable(true);
                                        ClaveObtenida.requestFocus();
                                        ClaveObtenida.setInputType(InputType.TYPE_NULL);
                                        txtUbicacionDestino.setText(UbicacionDest);
                                    }
                                }
                            }
                        }
                    }
                });


                dialog.dismiss();
                dialog3.dismiss();


            } else if (listaMisLiberaciones.size() > 0) {

                listaMisLiberaciones.get(position).getFolio();
                txtFolio.setText(listaMisLiberaciones.get(position).getFolio());
                listaCajas.clear();
                listaProduAduana.clear();
                FolioLiberacion = txtFolio.getText().toString();
                ActivityLiberaciones.ListPrAdu task = new ActivityLiberaciones.ListPrAdu();
                task.execute();
                ClaveObtenida.setFocusable(true);
                ClaveObtenida.requestFocus();
                ClaveObtenida.setInputType(InputType.TYPE_NULL);
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                alerta.setMessage("EMPIEZA A ESCANEAR TUS CODIGOS").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("¡EMPIEZA A ESCANEAR!");
                titulo.show();
                EdUbicacion.addTextChangedListener(new TextWatcher() {
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

                                UbicacionDest = editable.toString();
                                ClaveObtenida.setFocusable(true);
                                ClaveObtenida.requestFocus();
                                dialog6.dismiss();
                                ClaveObtenida.setInputType(InputType.TYPE_NULL);
                                txtUbicacionDestino.setText(UbicacionDest);


                            } else {
                                for (int i = 0; i < editable.length(); i++) {
                                    char ban;
                                    ban = editable.charAt(i);
                                    if (ban == '\n') {
                                        UbicacionDest = editable.toString();
                                        UbicacionDest = UbicacionCarrito.replace("\n", "");
                                        ClaveObtenida.setFocusable(true);
                                        ClaveObtenida.requestFocus();
                                        dialog6.dismiss();
                                        ClaveObtenida.setInputType(InputType.TYPE_NULL);
                                        txtUbicacionDestino.setText(UbicacionDest);
                                    }
                                }
                            }
                        }
                    }
                });


                dialog.dismiss();
                dialog3.dismiss();

            }
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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

    public void newUbica(View view) {
        builder6 = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pantallacargaubicacion, null);
        CancelarUbicacion = (Button) dialogView.findViewById(R.id.cancelar);
        EdUbicacion = dialogView.findViewById(R.id.txtUbicacionIsla);
        builder6.setView(dialogView);
        dialog6 = builder6.create();
        dialog6.show();
        EdUbicacion.setFocusable(true);
        EdUbicacion.requestFocus();
        EdUbicacion.setInputType(InputType.TYPE_NULL);

        EdUbicacion.addTextChangedListener(new TextWatcher() {
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

                        UbicacionDest = editable.toString();
                        dialog6.dismiss();
                        ClaveObtenida.setFocusable(true);
                        ClaveObtenida.requestFocus();
                        ClaveObtenida.setInputType(InputType.TYPE_NULL);
                        txtUbicacionDestino.setText(UbicacionDest);


                    } else {
                        for (int i = 0; i < editable.length(); i++) {
                            char ban;
                            ban = editable.charAt(i);
                            if (ban == '\n') {
                                UbicacionDest = editable.toString();
                                UbicacionDest = UbicacionCarrito.replace("\n", "");
                                dialog6.dismiss();
                                ClaveObtenida.setFocusable(true);
                                ClaveObtenida.requestFocus();
                                ClaveObtenida.setInputType(InputType.TYPE_NULL);
                                txtUbicacionDestino.setText(UbicacionDest);
                            }
                        }
                    }
                }
            }
        });
    }

    //Seleccionar item de una lista de productos
    public void Selectitem(View view) {

        builder6 = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.pantallacargacarrito, null);
        builder6.setView(dialogView);
        dialog6 = builder6.create();
        int position = recyclerDialog2.getChildAdapterPosition(recyclerDialog2.findContainingItemView(view));
        contlist2 =position;
        dialog2.dismiss();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected()) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
            fecha = dateformatActually.format(c.getTime());


            Calendar calendar1 = Calendar.getInstance();
            SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
            hora = dateformatActually1.format(calendar1.getTime());


            Documento = listaProduAduana.get(contlis).getDocumento();
            Folio = listaProduAduana.get(contlis).getFolio();
            PartidaP = listaProduAduana.get(contlis).getPPrevias();
            Producto1 = listaProduAduana.get(contlis).getProducto();
            Cantidad2 = listaProduAduana.get(contlis).getCantidadSurtida();

            dialog6.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog6.dismiss();
                }
            }, 2000);

            UbicacionOri = listaUbicaciones.get(SpUbicacion.getSelectedItemPosition()).getUbicaciones();
            Fecha = fecha;
            Hora = hora;


            if (Integer.parseInt(listaProduAduana.get(contlis).getCantidad()) != Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida())) {


                ActivityLiberaciones.ActualizaSurtidoLista task = new ActivityLiberaciones.ActualizaSurtidoLista();
                task.execute();
            }else {
                contlis = position;
                listaUbicaciones.clear();
                txtCliente.setText(listaProduAduana.get(contlis).getNombre());
                txtVia.setText(listaProduAduana.get(contlis).getVia());

                int CantidadSurtida, Cantidad;
                Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));


                if (contlis == listaProduAduana.size() - 1) {
                    botonAdelante.setVisibility(View.INVISIBLE);
                } else {
                    botonAdelante.setVisibility(View.VISIBLE);
                }


                if (contlis == 0) {
                    botonAtras.setVisibility(View.INVISIBLE);
                } else {
                    botonAtras.setVisibility(View.VISIBLE);
                }

                txtProducto.setText(listaProduAduana.get(contlis).getProducto());
                txtCantidad.setText(listaProduAduana.get(contlis).getCantidad() + " " + listaProduAduana.get(contlis).getUnidad());
                Picasso.with(getApplicationContext()).
                        load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProduAduana.get(contlis).getProducto() + ".jpg")
                        .error(R.drawable.aboutlogo)
                        .fit()
                        .centerInside()
                        .into(imgVi);

                listaCajas = new ArrayList<>();
                ActivityLiberaciones.ConsultaCajas2 task = new ActivityLiberaciones.ConsultaCajas2();
                task.execute();
            }


        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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

    //Cerrar en dado caso de averse llenado
    public void NumeroCaja(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            if (listaProduAduana.size() > 0) {

                builder4 = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.pantallacargacaja, null);
                builder4.setView(dialogView);
                dialog4 = builder4.create();


                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                alerta.setMessage("¿ESTAS SEGURO DE CERRAR LA CAJA?").setCancelable(false).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialog4.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listaCajasFiltro.clear();
                                for (int i = 0; i < listaCajas.size(); i++) {
                                    if (listaCajas.get(i).getNumCajas().equals(String.valueOf(ContCajas))) {
                                        listaCajasFiltro.add(new CAJASSANDG(listaCajas.get(0).getClaveSucursal(), listaCajas.get(i).getClaveAlamacen(), listaCajas.get(i).getFolioDocumento(), listaCajas.get(i).getClavedelProdcuto(), listaCajas.get(i).getCantidadUnidades(), listaCajas.get(i).getNumCajas()));
                                    }
                                }

                                ImprimirTicketCajas(ContCajas);

                                ContCajas++;
                                txtNumeroCajas.setText("N° " + ContCajas);
                                dialog4.dismiss();


                            }
                        }, 5000);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("CERRAR LA CAJA");
                titulo.show();

            } else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                alerta.setMessage("NO HAY PRODUCTOS POR DESPACHAR").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("¡AVISO!");
                titulo.show();
            }


        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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


    //Lista Productos del pedido

    private class ListPrAdu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            Productos();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {

            contlis = 0;
            cont = 0;
            txtCliente.setText(listaProduAduana.get(0).getNombre());
            txtVia.setText(listaProduAduana.get(0).getVia());
            txtProducto.setText(listaProduAduana.get(0).getProducto());
            txtCantidad.setText(listaProduAduana.get(0).getCantidad() + "  PZA");
            txtCantidadSurtida.setText(listaProduAduana.get(0).getCantidadSurtida() + " " + listaProduAduana.get(0).getUnidad());
            imgVi.setBackgroundColor(Color.rgb(255, 255, 255));
            Picasso.with(getApplicationContext()).
                    load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProduAduana.get(0).getProducto() + ".jpg")
                    .error(R.drawable.aboutlogo)
                    .fit()
                    .centerInside()
                    .into(imgVi);

            if (listaProduAduana.size() > 1) {
                botonAdelante.setVisibility(View.VISIBLE);
                botonAtras.setVisibility(View.INVISIBLE);
                CageSave.setVisibility(View.GONE);

            } else {
                botonAdelante.setVisibility(View.GONE);
                botonAtras.setVisibility(View.GONE);

                if (listaProduAduana.size() == 1) {
                    CageSave.setVisibility(View.VISIBLE);
                } else {
                    CageSave.setVisibility(View.GONE);
                }
            }


            String Configuracion = listaProduAduana.get(0).getConfiguracion();
            if (Configuracion.equals("1")) {
                LinearUbicacion.setVisibility(View.VISIBLE);
                dialog6.show();
                EdUbicacion.setFocusable(true);
                EdUbicacion.requestFocus();
                EdUbicacion.setInputType(InputType.TYPE_NULL);
                txtVisiUbicacion.setVisibility(View.VISIBLE);


            } else {
                LinearUbicacion.setVisibility(View.GONE);
                ClaveObtenida.setFocusable(true);
                ClaveObtenida.requestFocus();
                ClaveObtenida.setInputType(InputType.TYPE_NULL);
                txtUbicacionDestino.setVisibility(View.GONE);
                txtVisiUbicacion.setVisibility(View.GONE);
            }
            ActivityLiberaciones.ConsultaCajas task1 = new ActivityLiberaciones.ConsultaCajas();
            task1.execute();


        }
    }


    private void Productos() {
        String SOAP_ACTION = "ListProAdu";
        String METHOD_NAME = "ListProAdu";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMListProAdua soapEnvelope = new XMListProAdua(SoapEnvelope.VER11);
            soapEnvelope.XMListProAdua(strusr, strpass, strcodBra, FolioLiberacion, Filtro, FiltroAscDesc);
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

                listaProduAduana.add(new ListProAduSandG(
                        (response0.getPropertyAsString("k_Cliente").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Cliente")),
                        (response0.getPropertyAsString("k_Nombre").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Nombre")),
                        (response0.getPropertyAsString("k_Via").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Via")),
                        (response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? "0" : response0.getPropertyAsString("k_Cantidad")),
                        (response0.getPropertyAsString("k_Producto").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Producto")),
                        (response0.getPropertyAsString("k_Ubicacion").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Ubicacion")),
                        (response0.getPropertyAsString("k_Descripcion").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Descripcion")),
                        (response0.getPropertyAsString("k_Unidad").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Unidad")),
                        (response0.getPropertyAsString("k_Precio").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Precio")),
                        (response0.getPropertyAsString("k_PPrevias").equals("anyType{}") ? "" : response0.getPropertyAsString("k_PPrevias")),
                        (response0.getPropertyAsString("k_Documento").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Documento")),
                        (response0.getPropertyAsString("k_Folio").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("k_Sucursal").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Sucursal")),
                        (response0.getPropertyAsString("k_CantidadSurt").equals("anyType{}") ? "" : response0.getPropertyAsString("k_CantidadSurt")),
                        (response0.getPropertyAsString("k_Configuracion").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Configuracion"))));


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

    //Ubicaciones por productos

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
                search1[i] = listaUbicaciones.get(i).getUbicaciones();
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, opciones);
            SpUbicacion.setPadding(5, 5, 5, 5);
            SpUbicacion.setAdapter(adapter);
            char letra;
            for (int i = 0; i < listaUbicaciones.size(); i++) {
                letra = listaUbicaciones.get(i).getUbicaciones().charAt(0);
                if ('C' == letra || 'P' == letra) {
                    SpUbicacion.setSelection(i);
                    break;
                }
            }

            ProdcutoRefres = listaProduAduana.get(contlis).getProducto();
            PartidaPreRefres = listaProduAduana.get(contlis).getPPrevias();
            Foliorefres = listaProduAduana.get(contlis).getFolio();
            ActivityLiberaciones.RefreshCntid task = new ActivityLiberaciones.RefreshCntid();
            task.execute();
        }
    }

    private void Ubicaciones() {
        String SOAP_ACTION = "ListaUbica";
        String METHOD_NAME = "ListaUbica";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {
            String Prodcuto = listaProduAduana.get(contlis).getProducto();
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLListaUbica soapEnvelope = new XMLListaUbica(SoapEnvelope.VER11);
            soapEnvelope.XMLListaUbica(strusr, strpass, Prodcuto, strcodBra);
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

    //Insercion de piezas suertidas en las listas

    private class InsertSurtido extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            InsertSurti();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(ActivityLiberaciones.this, "Guardando...", Toast.LENGTH_SHORT).show();
        }
    }


    private void InsertSurti() {
        String SOAP_ACTION = "InsertSurti";
        String METHOD_NAME = "InsertSurti";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLInsertSurti soapEnvelope = new XMLInsertSurti(SoapEnvelope.VER11);
            soapEnvelope.XMLInsertSurti(strusr, strpass, strcodBra, Documento, Folio, PartidaP, Producto1, Cantidad2, Fecha, Hora, UbicacionOri, UbicacionDest);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("INSURTREQ");

        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }

    //Insercion de Cajas de las listas

    private class InsertarCajas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            InsertCajas();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {


                if (contlis < listaProduAduana.size() - 1) {

                    int CantidadSurt = 0, Cantidadasur = 0;
                    CantidadSurt = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                    Cantidadasur = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());

                    if (CantidadSurt == Cantidadasur) {
                        contlis++;
                        listaUbicaciones.clear();
                        txtCliente.setText(listaProduAduana.get(contlis).getNombre());
                        txtVia.setText(listaProduAduana.get(contlis).getVia());
                        int CantidadSurtida, Cantidad;
                        Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                        CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                        txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));

                        if (contlis == listaProduAduana.size() - 1) {
                            botonAdelante.setVisibility(View.INVISIBLE);
                        } else {
                            botonAdelante.setVisibility(View.VISIBLE);
                        }


                        if (contlis == 0) {
                            botonAtras.setVisibility(View.INVISIBLE);
                        } else {
                            botonAtras.setVisibility(View.VISIBLE);
                        }

                        txtProducto.setText(listaProduAduana.get(contlis).getProducto());
                        txtCantidad.setText(listaProduAduana.get(contlis).getCantidad() + " " + listaProduAduana.get(contlis).getUnidad());
                        Picasso.with(getApplicationContext()).
                                load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProduAduana.get(contlis).getProducto() + ".jpg")
                                .error(R.drawable.aboutlogo)
                                .fit()
                                .centerInside()
                                .into(imgVi);
                        ActivityLiberaciones.ListUbicacion task = new ActivityLiberaciones.ListUbicacion();
                        task.execute();
                    }
                }
            } else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
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

    private void InsertCajas() {
        String SOAP_ACTION = "InsertCajas";
        String METHOD_NAME = "InsertCajas";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";

        for (int k = 0; k < listaCajas.size(); k++) {
            if (listaProduAduana.get(contlis).getProducto().equals(listaCajas.get(k).getClavedelProdcuto())) {
                ClaveSucursalCajas = listaCajas.get(k).getClaveSucursal();
                FolioDocumentoCajas = listaCajas.get(k).getFolioDocumento();
                ClavedelProdcutoCajas = listaCajas.get(k).getClavedelProdcuto();
                CantidadUnidadesCajas = listaCajas.get(k).getCantidadUnidades();
                NumCajasCajas = listaCajas.get(k).getNumCajas();

                try {
                    SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                    XMLInsertCajas soapEnvelope = new XMLInsertCajas(SoapEnvelope.VER11);
                    soapEnvelope.XMLInsertCajas(strusr, strpass, ClaveSucursalCajas, FolioDocumentoCajas, ClavedelProdcutoCajas, CantidadUnidadesCajas, NumCajasCajas);
                    soapEnvelope.dotNet = true;
                    soapEnvelope.implicitTypes = true;
                    soapEnvelope.setOutputSoapObject(Request);
                    HttpTransportSE trasport = new HttpTransportSE(URL);
                    trasport.debug = true;
                    trasport.call(SOAP_ACTION, soapEnvelope);
                    SoapObject response = (SoapObject) soapEnvelope.bodyIn;
                    response = (SoapObject) response.getProperty("INSURTREQ");

                } catch (SoapFault soapFault) {
                    soapFault.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                }


            }
        }


    }


    //Consulta de Cajas Existentes del pedido actual
    private class ConsultaCajas extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            ConsultCajas();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {

            ContCajas = 1;
            if (listaCajas.size() > 0) {
                for (int i = 0; i < listaCajas.size(); i++) {
                    if (Integer.parseInt(listaCajas.get(i).getNumCajas()) == ContCajas) {

                    } else if (Integer.parseInt(listaCajas.get(i).getNumCajas()) <= ContCajas) {

                    } else if (Integer.parseInt(listaCajas.get(i).getNumCajas()) > ContCajas) {
                        ContCajas++;
                    }
                }


            }
            txtNumeroCajas.setText("N° " + ContCajas);

            ActivityLiberaciones.ListUbicacion task = new ActivityLiberaciones.ListUbicacion();
            task.execute();

        }
    }

    //Consulta de Cajas Existentes del pedido actual
    private class ConsultaCajas2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            ConsultCajas();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {

            ActivityLiberaciones.ListUbicacion task = new ActivityLiberaciones.ListUbicacion();
            task.execute();

        }
    }


    private void ConsultCajas() {
        String SOAP_ACTION = "ConsultCajas";
        String METHOD_NAME = "ConsultCajas";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLConsultCajas soapEnvelope = new XMLConsultCajas(SoapEnvelope.VER11);
            soapEnvelope.XMLConsultCajas(strusr, strpass, FolioLiberacion, strcodBra);
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

                listaCajas.add(new CAJASSANDG(
                        (response0.getPropertyAsString("k_Sucursal").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Sucursal")),
                        (response0.getPropertyAsString("k_Almacen").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Almacen")),
                        (response0.getPropertyAsString("k_Folio").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("k_Producto").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Producto")),
                        (response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Cantidad")),
                        (response0.getPropertyAsString("k_NumeroCajas").equals("anyType{}") ? "" : response0.getPropertyAsString("k_NumeroCajas"))));

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

    private class RefreshCntid extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            RefreshCant();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {


            if (!Cantidadbusqueda.equals("0")) {

                txtCantidadSurtida.setText(Cantidadbusqueda + " " + listaProduAduana.get(contlis).getUnidad());
                if (ProdcutoRefres.equals(listaProduAduana.get(contlis).getProducto()) && PartidaPreRefres.equals(listaProduAduana.get(contlis).getPPrevias())) {
                    listaProduAduana.get(contlis).setCantidadSurtida(Cantidadbusqueda);

                    int CantidadSurtida, Cantidad;
                    Cantidad = Integer.parseInt(listaProduAduana.get(contlis).getCantidad());
                    CantidadSurtida = Integer.parseInt(listaProduAduana.get(contlis).getCantidadSurtida());
                    txtCantidadSurtida.setText(Html.fromHtml((Cantidad > CantidadSurtida) ? "<font color ='#FF0000'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>" : "<font color ='#4CAF50'>" + listaProduAduana.get(contlis).getCantidadSurtida() + " " + listaProduAduana.get(contlis).getUnidad() + "</font>"));
                }
            }
        }
    }


    private void RefreshCant() {
        String SOAP_ACTION = "RefreshCant";
        String METHOD_NAME = "RefreshCant";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";

        Cantidadbusqueda = "0";
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLRefreshCant soapEnvelope = new XMLRefreshCant(SoapEnvelope.VER11);
            soapEnvelope.XMLRefreshCantidad(strusr, strpass, Foliorefres, ProdcutoRefres, PartidaPreRefres);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("Almacen");
            Cantidadbusqueda = (response.getPropertyAsString("k_Cantidad").equals("anyType{}") ? "0" : response.getPropertyAsString("k_Cantidad"));


        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }

    }

    //Reporte de Incidencias
    private class ReporteInici extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            ReporInci();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
            alerta.setMessage(menbitacora).setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    GuardarInicidencias();



                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("AVISO");
            titulo.show();
        }
    }


    private void ReporInci() {
        String SOAP_ACTION = "ReportInci";
        String METHOD_NAME = "ReportInci";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLReportInici soapEnvelope = new XMLReportInici(SoapEnvelope.VER11);
            soapEnvelope.XMLReportInicide(strusr, strpass, strusr, Producto1, RazonSuper, strcodBra, FolioLiberacion);

            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("message");
            menbitacora = response.getPropertyAsString("k_menssage").equals("anyType{}") ? "" : response.getPropertyAsString("k_menssage");

        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
        }
    }


    //Reporte de Incidencias
    private class MensajeList extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            listInci();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            if (listaProduAduana.size() > 0) {

                String[] opciones = new String[listaIncidencias.size()];

                for (int i = 0; i < listaIncidencias.size(); i++) {
                    opciones[i] = listaIncidencias.get(i).getClave() + ".-" + listaIncidencias.get(i).getMensaje();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLiberaciones.this);
                builder.setIcon(R.drawable.icons8_error_52).setTitle("Seleccione la Incidencia");


                builder.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Producto1 = listaProduAduana.get(contlis).getProducto();
                        RazonSuper = opciones[which];
                        ActivityLiberaciones.ReporteInici task = new ActivityLiberaciones.ReporteInici();
                        task.execute();


                    }
                });

// create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                alerta.setMessage("No hay productos surtidos").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog titulo = alerta.create();
                titulo.setTitle("LISTA DE SURTIDO VACIA");
                titulo.show();
            }
        }
    }


    private void listInci() {
        String SOAP_ACTION = "MensaInci";
        String METHOD_NAME = "MensaInci";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLMensajeIncidencias soapEnvelope = new XMLMensajeIncidencias(SoapEnvelope.VER11);
            soapEnvelope.XMLMensajeInci(strusr, strpass);

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

                listaIncidencias.add(new ListaIncidenciasSandG(
                        (response0.getPropertyAsString("k_Clave").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Clave")),
                        (response0.getPropertyAsString("k_Mensaje").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Mensaje"))));

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

    public void nextliscage(View view) {
        contfiltrocajas = 0;
        contDialogCajas++;
        listaCajasFiltro.clear();
        for (int i = 0; i < listaCajas.size(); i++) {
            if (Integer.parseInt(listaCajas.get(i).getNumCajas()) == contfiltrocajas) {

            } else if (Integer.parseInt(listaCajas.get(i).getNumCajas()) <= contfiltrocajas) {

            } else if (Integer.parseInt(listaCajas.get(i).getNumCajas()) > contfiltrocajas) {
                contfiltrocajas++;
            }
        }

        if (contDialogCajas == contfiltrocajas) {
            CageNext.setVisibility(View.GONE);
            CageBack.setVisibility(View.VISIBLE);
        } else {
            CageNext.setVisibility(View.VISIBLE);
            CageBack.setVisibility(View.VISIBLE);
        }


        if (contDialogCajas == contfiltrocajas) {

            if (listaCajas.size() > 0) {

                for (int i = 0; i < listaCajas.size(); i++) {
                    if (listaCajas.get(i).getNumCajas().equals(String.valueOf(contDialogCajas))) {
                        listaCajasFiltro.add(new CAJASSANDG(listaCajas.get(0).getClaveSucursal(), listaCajas.get(i).getClaveAlamacen(), listaCajas.get(i).getFolioDocumento(), listaCajas.get(i).getClavedelProdcuto(), listaCajas.get(i).getCantidadUnidades(), listaCajas.get(i).getNumCajas()));
                    }
                }
                AdaptadorCajas adapter = new AdaptadorCajas(listaCajasFiltro);
                recyclerDialog2.setAdapter(null);
                recyclerDialog2.setAdapter(adapter);
            }


        } else {
            if (listaCajas.size() > 0) {

                for (int i = 0; i < listaCajas.size(); i++) {
                    if (listaCajas.get(i).getNumCajas().equals(String.valueOf(contDialogCajas))) {
                        listaCajasFiltro.add(new CAJASSANDG(listaCajas.get(0).getClaveSucursal(), listaCajas.get(i).getClaveAlamacen(), listaCajas.get(i).getFolioDocumento(), listaCajas.get(i).getClavedelProdcuto(), listaCajas.get(i).getCantidadUnidades(), listaCajas.get(i).getNumCajas()));
                    }
                }
                AdaptadorCajas adapter = new AdaptadorCajas(listaCajasFiltro);
                recyclerDialog2.setAdapter(null);
                recyclerDialog2.setAdapter(adapter);
            }
        }

    }


    public void backliscage(View view) {
        contfiltrocajas = 0;
        contDialogCajas--;

        if (contDialogCajas == 1) {
            CageNext.setVisibility(View.VISIBLE);
            CageBack.setVisibility(View.GONE);
        } else {
            CageNext.setVisibility(View.VISIBLE);
            CageBack.setVisibility(View.VISIBLE);
        }


        if (contDialogCajas == 1) {
            listaCajasFiltro.clear();
            if (listaCajas.size() > 0) {
                for (int i = 0; i < listaCajas.size(); i++) {
                    if (Integer.parseInt(listaCajas.get(i).getNumCajas()) == contfiltrocajas) {

                    } else if (Integer.parseInt(listaCajas.get(i).getNumCajas()) <= contfiltrocajas) {

                    } else if (Integer.parseInt(listaCajas.get(i).getNumCajas()) > contfiltrocajas) {
                        contfiltrocajas++;
                    }
                }


                for (int i = 0; i < listaCajas.size(); i++) {
                    if (listaCajas.get(i).getNumCajas().equals("1")) {
                        listaCajasFiltro.add(new CAJASSANDG(listaCajas.get(0).getClaveSucursal(), listaCajas.get(i).getClaveAlamacen(), listaCajas.get(i).getFolioDocumento(), listaCajas.get(i).getClavedelProdcuto(), listaCajas.get(i).getCantidadUnidades(), listaCajas.get(i).getNumCajas()));
                    }
                }
                AdaptadorCajas adapter = new AdaptadorCajas(listaCajasFiltro);
                recyclerDialog2.setAdapter(null);
                recyclerDialog2.setAdapter(adapter);
            }
        } else {
            if (listaCajas.size() > 0) {
                listaCajasFiltro.clear();
                for (int i = 0; i < listaCajas.size(); i++) {
                    if (Integer.parseInt(listaCajas.get(i).getNumCajas()) == contfiltrocajas) {

                    } else if (Integer.parseInt(listaCajas.get(i).getNumCajas()) <= contfiltrocajas) {

                    } else if (Integer.parseInt(listaCajas.get(i).getNumCajas()) > contfiltrocajas) {
                        contfiltrocajas++;
                    }
                }


                for (int i = 0; i < listaCajas.size(); i++) {
                    if (listaCajas.get(i).getNumCajas().equals(String.valueOf(contDialogCajas))) {
                        listaCajasFiltro.add(new CAJASSANDG(listaCajas.get(0).getClaveSucursal(), listaCajas.get(i).getClaveAlamacen(), listaCajas.get(i).getFolioDocumento(), listaCajas.get(i).getClavedelProdcuto(), listaCajas.get(i).getCantidadUnidades(), listaCajas.get(i).getNumCajas()));
                    }
                }
                AdaptadorCajas adapter = new AdaptadorCajas(listaCajasFiltro);
                recyclerDialog2.setAdapter(null);
                recyclerDialog2.setAdapter(adapter);
            }
        }

    }


    public void Imprimirtodo(View view) {

        imprimirTicketGeneral();


    }


    private class InsertCuadrar extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            Cuadrar();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(ActivityLiberaciones.this, "Guardando...", Toast.LENGTH_SHORT).show();
        }
    }


    private void Cuadrar() {
        String SOAP_ACTION = "InsertSurti";
        String METHOD_NAME = "InsertSurti";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
        fecha = dateformatActually.format(c.getTime());


        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
        hora = dateformatActually1.format(calendar1.getTime());

        for (int i = 0; i < listaProduAduana.size(); i++) {
            int cantidadsurt = Integer.parseInt(listaProduAduana.get(i).getCantidadSurtida());
            int cantidad = Integer.parseInt(listaProduAduana.get(i).getCantidad());
            if (cantidadsurt == cantidad || cantidadsurt > 0) {
                Documento = listaProduAduana.get(i).getDocumento();
                Folio = listaProduAduana.get(i).getFolio();
                PartidaP = listaProduAduana.get(i).getPPrevias();
                Producto1 = listaProduAduana.get(i).getProducto();
                Cantidad2 = listaProduAduana.get(i).getCantidad();
                UbicacionOri = "";
                Fecha = fecha;
                Hora = hora;
                try {
                    SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
                    XMLInsertSurti soapEnvelope = new XMLInsertSurti(SoapEnvelope.VER11);
                    soapEnvelope.XMLInsertSurti(strusr, strpass, strcodBra, Documento, Folio, PartidaP, Producto1, Cantidad2, Fecha, Hora, UbicacionOri, UbicacionDest);
                    soapEnvelope.dotNet = true;
                    soapEnvelope.implicitTypes = true;
                    soapEnvelope.setOutputSoapObject(Request);
                    HttpTransportSE trasport = new HttpTransportSE(URL);
                    trasport.debug = true;
                    trasport.call(SOAP_ACTION, soapEnvelope);
                    SoapObject response = (SoapObject) soapEnvelope.bodyIn;
                    response = (SoapObject) response.getProperty("INSURTREQ");
                } catch (SoapFault soapFault) {
                    soapFault.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception ex) {
                }
            }
        }
    }
/*
    public void cuadrar(View view) {

        for (int i = 0; i < listaProduAduana.size(); i++) {
            for (int j = 0; j < listaCajas.size(); j++) {
                String productolis=listaProduAduana.get(i).getProducto();
                String prodcajas = listaCajas.get(j).getClavedelProdcuto();
                if (productolis.equals(prodcajas)) {
                listaProduAduana.get(i).setCantidadSurtida(listaCajas.get(j).getCantidadUnidades());
                }
            }
        }

        ActivityLiberaciones.InsertCuadrar task = new ActivityLiberaciones.InsertCuadrar();
        task.execute();


    }*/

    public void ImpreCajas(View view) {

        ImprimirTicketCajas(contfiltrocajas);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoverflow2, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (id == R.id.Productos) {
                if (listaProduAduana.size() > 0) {
                    builder2 = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_info_listaproductos, null);
                    builder2.setView(dialogView);

                    recyclerDialog2 = (RecyclerView) dialogView.findViewById(R.id.recyclerListProductos);
                    GridLayoutManager gl = new GridLayoutManager(this, 1);
                    recyclerDialog2.setLayoutManager(gl);

                    AdaptadorListProductos adapter = new AdaptadorListProductos(listaProduAduana);
                    recyclerDialog2.setAdapter(null);
                    recyclerDialog2.setAdapter(adapter);

                    dialog2 = builder2.create();
                    dialog2.show();

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                    alerta.setMessage("No hay productos por revisar").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("LISTA DE PRODUCTOS VACIA");
                    titulo.show();
                }

            } else if (id == R.id.Autorizacion) {
                if (listaProduAduana.size() > 0) {


                    builder5 = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_info_autorizacion, null);
                    builder5.setView(dialogView);


                    UsuarioED = (EditText) dialogView.findViewById(R.id.txtUserNamED);
                    CantidadED = (EditText) dialogView.findViewById(R.id.txtCantiAutoED);
                    RazonED = (EditText) dialogView.findViewById(R.id.RazonAutorizarEd);
                    BUTTONADDCANT = (Button) dialogView.findViewById(R.id.BUTTONADDCANT);
                    IntruccionesCantidad = (TextView) dialogView.findViewById(R.id.instruccionesAuto);
                    IntruccionesRazon = (TextView) dialogView.findViewById(R.id.RazonAutorizacionSHOW);
                    TXTSHOWUSERNAME = dialogView.findViewById(R.id.TXTSHOWUSERNAME);
                    TEXSHOWCANTI = dialogView.findViewById(R.id.TEXSHOWCANTI);
                    TEXSHOWRAZON = dialogView.findViewById(R.id.TEXSHOWRAZON);
                    IntruccionesGafete = dialogView.findViewById(R.id.InstruccionesEscaneo);
                    txtGifVie = dialogView.findViewById(R.id.gifocult);

                    dialog5 = builder5.create();
                    dialog5.show();
                    UsuarioED.requestFocus();
                    UsuarioED.setInputType(InputType.TYPE_NULL);
                    UsuarioED.addTextChangedListener(new TextWatcher() {
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
                                    String Usuario = editable.toString();
                                    Autorizacion(Usuario);

                                    UsuarioED.setText(null);
                                } else {
                                    for (int i = 0; i < editable.length(); i++) {
                                        char ban;
                                        ban = editable.charAt(i);
                                        if (ban == '\n') {

                                            String Usuario = editable.toString();
                                            Usuario = Usuario.replace("\n", "");
                                            Autorizacion(Usuario);

                                            UsuarioED.setText(null);

                                        }
                                    }
                                }
                            }
                        }
                    });


                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                    alerta.setMessage("No hay pedido en seguimiento").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("¡ERROR!");
                    titulo.show();

                }


            } else if (id == R.id.Cajas) {
                contfiltrocajas = 0;
                if (listaCajas.size() > 0) {
                    listaCajasFiltro.clear();
                    builder2 = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_info_cajas, null);
                    builder2.setView(dialogView);

                    recyclerDialog2 = (RecyclerView) dialogView.findViewById(R.id.recyclerCaja);
                    CageNext = dialogView.findViewById(R.id.CageNext);
                    CageBack = dialogView.findViewById(R.id.CageBack);

                    GridLayoutManager gl = new GridLayoutManager(this, 1);
                    recyclerDialog2.setLayoutManager(gl);

                    for (int i = 0; i < listaCajas.size(); i++) {
                        if (Integer.parseInt(listaCajas.get(i).getNumCajas()) == contfiltrocajas) {

                        } else if (Integer.parseInt(listaCajas.get(i).getNumCajas()) <= contfiltrocajas) {

                        } else if (Integer.parseInt(listaCajas.get(i).getNumCajas()) > contfiltrocajas) {
                            contfiltrocajas++;
                        }
                    }

                    if (contfiltrocajas > 1) {
                        CageNext.setVisibility(View.VISIBLE);
                        CageBack.setVisibility(View.GONE);
                    } else {
                        CageNext.setVisibility(View.GONE);
                        CageBack.setVisibility(View.GONE);
                    }

                    for (int i = 0; i < listaCajas.size(); i++) {
                        if (listaCajas.get(i).getNumCajas().equals("1")) {
                            listaCajasFiltro.add(new CAJASSANDG(listaCajas.get(0).getClaveSucursal(), listaCajas.get(i).getClaveAlamacen(), listaCajas.get(i).getFolioDocumento(), listaCajas.get(i).getClavedelProdcuto(), listaCajas.get(i).getCantidadUnidades(), listaCajas.get(i).getNumCajas()));
                        }
                    }

                    AdaptadorCajas adapter = new AdaptadorCajas(listaCajasFiltro);
                    recyclerDialog2.setAdapter(null);
                    recyclerDialog2.setAdapter(adapter);

                    dialog2 = builder2.create();
                    dialog2.show();

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
                    alerta.setMessage("No hay productos surtidos").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("LISTA DE SURTIDO VACIA");
                    titulo.show();
                }

            } else if (id == R.id.ReportInc) {

                listaIncidencias.clear();

                ActivityLiberaciones.MensajeList task = new ActivityLiberaciones.MensajeList();
                task.execute();

            }/*else if(id == R.id.ActivarCarrito){
                if(UbicacionesIslas == 1 ){
                    UbicacionesIslas = 0;
                    editor.putInt("UbicacionIslas",UbicacionesIslas);
                    editor.commit();
                }else{
                    UbicacionesIslas = 1;
                    editor.putInt("UbicacionIslas",UbicacionesIslas);
                    editor.commit();
                }
            }*/

        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityLiberaciones.this);
            alerta.setMessage("No hay Conexion a Internet").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });

            AlertDialog titulo = alerta.create();
            titulo.setTitle("!ERROR! CONEXION");
            titulo.show();

        }


        return super.onOptionsItemSelected(item);
    }
}

//Nuevo Record