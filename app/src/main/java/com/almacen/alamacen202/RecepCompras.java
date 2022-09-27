package com.almacen.alamacen202;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.almacen.alamacen202.Activity.ActivityLiberaciones;
import com.almacen.alamacen202.Adapter.AdaptadorCajas;
import com.almacen.alamacen202.Adapter.AdaptadorListFolios;
import com.almacen.alamacen202.Adapter.AdaptadorListFoliosRecepcion;
import com.almacen.alamacen202.Adapter.AdaptadorListProdRecepcion;
import com.almacen.alamacen202.Adapter.AdaptadorListProductos;
import com.almacen.alamacen202.Adapter.AdaptadorListProductosRece;
import com.almacen.alamacen202.Imprecion.BluetoothPrint;
import com.almacen.alamacen202.SetterandGetters.CAJASSANDG;
import com.almacen.alamacen202.SetterandGetters.ListLiberaSandG;
import com.almacen.alamacen202.SetterandGetters.ListProAduSandG;
import com.almacen.alamacen202.SetterandGetters.ListProReceSandG;
import com.almacen.alamacen202.SetterandGetters.ListReceSandG;
import com.almacen.alamacen202.XML.XMLInsertRece;
import com.almacen.alamacen202.XML.XMLInsertSurti;
import com.almacen.alamacen202.XML.XMLListFoliosLibe;
import com.almacen.alamacen202.XML.XMLListRece;
import com.almacen.alamacen202.XML.XMListProAdua;
import com.almacen.alamacen202.XML.XMListProRe;
import com.almacen.alamacen202.includes.MyToolbar;
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

public class RecepCompras extends AppCompatActivity {
    TextView txtFolio, txtProvedor;
    EditText EdArticulo;
    RecyclerView recyclerDialog, recyclerLisRecepcion,recyclerDialog2;
    String FolioRecepcion, Provedor;
    ArrayList<ListReceSandG> listaFolios = new ArrayList<>();
    ArrayList<ListProReceSandG> listaProdRece = new ArrayList<>();
    String strusr, strpass, strname, strlname, strtype, strbran, strma, strcodBra, StrServer, codeBar, impresora;

    AlertDialog.Builder builder;
    AlertDialog dialog = null;
    AlertDialog.Builder builder2;
    AlertDialog dialog2 = null;
    AlertDialog.Builder builder6;
    AlertDialog dialog6 = null;



    Context context = this;

    String fecha;
    String hora;


    String Documento;
    String Folio;
    String PartidaP;
    String Producto1;
    String Cantidad2;
    String Fecha;
    String Hora;
    String UbicacionOri  = "";
    String UbicacionDest = "";

    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recep_compras);

        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();

        MyToolbar.show(this, "", true);


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

        txtFolio = findViewById(R.id.txtFolio);
        txtProvedor = findViewById(R.id.txtProvedor);
        EdArticulo = findViewById(R.id.edArticulo);
        recyclerLisRecepcion = findViewById(R.id.lisRecepcion);

        listaProdRece = new ArrayList<>();
        recyclerLisRecepcion.setLayoutManager(new LinearLayoutManager(this));


        EdArticulo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().equals("")) {
                            ConnectivityManager connectivityManager =(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


                    if (networkInfo != null && networkInfo.isConnected()) {
                        if (codeBar.equals("Zebra")) {

                            Escaneo(editable.toString());


                        } else {
                            for (int i = 0; i < editable.length(); i++) {
                                char ban;

                                ban = editable.charAt(i);

                                if (ban == '\n') {

                                    String edition = editable.toString();

                                    edition = edition.replace("\n", "");

                                    Escaneo(edition);
                                }
                            }
                        }
                    }

                }

            }
        });

    }


    public void Escaneo (String editable){
        int band = 0;
        for (int i=0; i<listaProdRece.size();i++){
            if(editable.equals(listaProdRece.get(i).getProducto())){
                if(Integer.parseInt(listaProdRece.get(i).getCantidad())!=Integer.parseInt(listaProdRece.get(i).getCantidadSurtida())){
                    int CantidadSurtida =Integer.parseInt(listaProdRece.get(i).getCantidadSurtida())+1;
                    listaProdRece.get(i).setCantidadSurtida(String.valueOf(CantidadSurtida));
                    AdaptadorListProdRecepcion adapter = new AdaptadorListProdRecepcion(listaProdRece, context);
                    recyclerLisRecepcion.setAdapter(adapter);
                    recyclerLisRecepcion.scrollToPosition(i);
                    band=0;

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateformatActually = new SimpleDateFormat("yyyy-MM-dd");
                    fecha = dateformatActually.format(c.getTime());


                    Calendar calendar1 = Calendar.getInstance();
                    SimpleDateFormat dateformatActually1 = new SimpleDateFormat("HH:mm:ss");
                    hora = dateformatActually1.format(calendar1.getTime());


                    Documento = listaProdRece.get(i).getDocumento();
                    Folio = listaProdRece.get(i).getFolio();
                    PartidaP = listaProdRece.get(i).getPPrevias();
                    Producto1 = listaProdRece.get(i).getProducto();
                    Cantidad2 = listaProdRece.get(i).getCantidadSurtida();
                    Fecha = fecha;
                    Hora = hora;

                    RecepCompras.InsertRece task = new RecepCompras.InsertRece();
                    task.execute();


                    break;
                }else{
                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
                    alerta.setMessage("El producto a sido terminado de escanear exitosamente").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("EL ARTICULO A SIDO  ESCANEADO");
                    titulo.show();
                    band=0;
                    break;
                }

            }else{
                band=1;
            }
        }


        if(band==1){
        }
        EdArticulo.setText(null);

    }






    //Muestra todos los folios
    public void TODFOLIOS(View view) {

        builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_info_listarece, null);
        builder.setView(dialogView);
        recyclerDialog = (RecyclerView) dialogView.findViewById(R.id.recyclerFacturas);
        GridLayoutManager gl = new GridLayoutManager(this, 1);
        recyclerDialog.setLayoutManager(gl);

        AdaptadorListFoliosRecepcion adapter = new AdaptadorListFoliosRecepcion(listaFolios);
        recyclerDialog.setAdapter(null);
        recyclerDialog.setAdapter(adapter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {


            listaFolios.clear();
            RecepCompras.FoliosLiberados task = new RecepCompras.FoliosLiberados();
            task.execute();


        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
            dialog = builder.create();
            dialog.show();

        }
    }


    private void ListaFolios() {
        String SOAP_ACTION = "ListRece";
        String METHOD_NAME = "ListRece";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLListRece soapEnvelope = new XMLListRece(SoapEnvelope.VER11);
            soapEnvelope.XMLListRecepci(strusr, strpass, strcodBra);
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

                listaFolios.add(new ListReceSandG(
                        (response0.getPropertyAsString("k_Provedor").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Provedor")),
                        (response0.getPropertyAsString("k_Folio").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("k_Fecha").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Fecha")),
                        (response0.getPropertyAsString("k_Referencia").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Referencia")),
                        (response0.getPropertyAsString("k_Documento").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Documento"))));


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


    private class ListPrRece extends AsyncTask<Void, Void, Void> {

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

            txtProvedor.setText(listaProdRece.get(0).getNombre());
            txtFolio.setText(listaProdRece.get(0).getFolio());
            AdaptadorListProdRecepcion adapter = new AdaptadorListProdRecepcion(listaProdRece, context);
            recyclerLisRecepcion.setAdapter(adapter);
            dialog.dismiss();
            EdArticulo.setFocusable(true);
            EdArticulo.requestFocus();
            EdArticulo.setInputType(InputType.TYPE_NULL);

        }
    }


    private void Productos() {
        String SOAP_ACTION = "ListProRe";
        String METHOD_NAME = "ListProRe";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMListProRe soapEnvelope = new XMListProRe(SoapEnvelope.VER11);
            soapEnvelope.XMListProRe(strusr, strpass, strcodBra, FolioRecepcion, Provedor);
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

                listaProdRece.add(new ListProReceSandG(
                        (response0.getPropertyAsString("k_Probedor").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Probedor")),
                        (response0.getPropertyAsString("k_Nombre").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Nombre")),
                        (response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? "0" : response0.getPropertyAsString("k_Cantidad")),
                        (response0.getPropertyAsString("k_Producto").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Producto")),
                        (response0.getPropertyAsString("k_Ubicacion").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Ubicacion")),
                        (response0.getPropertyAsString("k_Descripcion").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Descripcion")),
                        (response0.getPropertyAsString("k_Unidad").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Unidad")),
                        (response0.getPropertyAsString("k_PPrevias").equals("anyType{}") ? "" : response0.getPropertyAsString("k_PPrevias")),
                        (response0.getPropertyAsString("k_Documento").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Documento")),
                        (response0.getPropertyAsString("k_Folio").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Folio")),
                        (response0.getPropertyAsString("k_Sucursal").equals("anyType{}") ? "" : response0.getPropertyAsString("k_Sucursal")),
                        (response0.getPropertyAsString("k_CantidadSurt").equals("anyType{}") ? "" : response0.getPropertyAsString("k_CantidadSurt"))));


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


    public void SelectFolio(View view) {
        int position = recyclerDialog.getChildAdapterPosition(recyclerDialog.findContainingItemView(view));
        Provedor = listaFolios.get(position).getProvedor();
        FolioRecepcion = listaFolios.get(position).getFolio();
        RecepCompras.ListPrRece task = new RecepCompras.ListPrRece();
        task.execute();


    }

    //Insercion de piezas suertidas en las listas

    private class  InsertRece extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            InsertRece();
            return null;
        }


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(RecepCompras.this, "Guardando...", Toast.LENGTH_SHORT).show();
        }
    }


    private void InsertRece() {
        String SOAP_ACTION = "InsertRece";
        String METHOD_NAME = "InsertRece";
        String NAMESPACE = "http://" + StrServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + StrServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLInsertRece soapEnvelope = new XMLInsertRece(SoapEnvelope.VER11);
            soapEnvelope.XMLInsertRecepcion(strusr, strpass, strcodBra, Documento, Folio, PartidaP, Producto1, Cantidad2, Fecha, Hora, "", "");
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



    //Imprimir ticket


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


                String Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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

                AlertDialog.Builder builder = new AlertDialog.Builder(RecepCompras.this);

                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("JACVE", Provedor, Folio , listaProdRece , R.drawable.jacveprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("JACVE", Provedor, Folio,listaProdRece, R.drawable.jacveprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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

                Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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

                builder = new AlertDialog.Builder(RecepCompras.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("VIPLA", Provedor, Folio,  listaProdRece , R.drawable.viplaprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("VIPLA", Provedor, Folio, listaProdRece , R.drawable.viplaprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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

                builder = new AlertDialog.Builder(RecepCompras.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("CECRA", Provedor, Folio, listaProdRece, R.drawable.cecraprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("CECRA", Provedor, Folio, listaProdRece , R.drawable.cecra);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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

                Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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

                builder = new AlertDialog.Builder(RecepCompras.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("GUVI", Provedor, Folio, listaProdRece , R.drawable.guviprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("GUVI", Provedor, Folio , listaProdRece , R.drawable.guviprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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

                Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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

                builder = new AlertDialog.Builder(RecepCompras.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("PRESSA", Provedor, Folio,  listaProdRece , R.drawable.pressaprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("PRESSA", Provedor, Folio, listaProdRece , R.drawable.pressaprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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

                builder = new AlertDialog.Builder(RecepCompras.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("AUTODIS", Provedor, Folio, listaProdRece,R.drawable.autodisprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("AUTODIS", Provedor, Folio, listaProdRece, R.drawable.autodisprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
            case "sprautomotive.servehttp.com:9090":


                Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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

                builder = new AlertDialog.Builder(RecepCompras.this);                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("RODATECH", Provedor, Folio, listaProdRece, R.drawable.rodaprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("RODATECH", Provedor, Folio, listaProdRece, R.drawable.rodaprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
            case "sprautomotive.servehttp.com:9095":


                Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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

                builder = new AlertDialog.Builder(RecepCompras.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("PARTECH", Provedor, Folio, listaProdRece,R.drawable.partechprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("PARTECH", Provedor, Folio, listaProdRece,R.drawable.partechprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
            case "sprautomotive.servehttp.com:9080":
                Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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


                builder = new AlertDialog.Builder(RecepCompras.this);   if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("SHARK", Provedor, Folio, listaProdRece, R.drawable.sharkprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("SHARK", Provedor, Folio, listaProdRece, R.drawable.sharkprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
            case "vazlocolombia.dyndns.org:9085":

                Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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


                builder = new AlertDialog.Builder(RecepCompras.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("VAZLO COLOMBIA", Provedor, Folio, listaProdRece,R.drawable.bhpprint);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("VAZLO COLOMBIA", Provedor, Folio, listaProdRece, R.drawable.bhpprint);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
            default:
                Provedor = listaProdRece.get(0).getProvedor();
                Folio = listaProdRece.get(0).getFolio();
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

                builder = new AlertDialog.Builder(RecepCompras.this);
                if (!impresora.equals("null")) {
                    imprimir.FindBluetoothDevice(impresora);
                    imprimir.openBluetoothPrinter();
                    if (imprimir.checkConnection() == true) {
                        imprimir.printRece("PRUEBAS", Provedor, Folio, listaProdRece, R.drawable.aboutlogo);
                        imprimir.disconnectBT();
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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
                                    imprimir.printRece("PRUEBAS", Provedor, Folio, listaProdRece, R.drawable.aboutlogo);
                                    imprimir.disconnectBT();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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

public void ImprimirTodo (View view){
    imprimirTicketGeneral();
}


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoverflow3, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (id == R.id.Productos) {
                if (listaProdRece.size() > 0) {
                    builder2 = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.dialog_info_listaproductosrece, null);
                    builder2.setView(dialogView);

                    recyclerDialog2 = (RecyclerView) dialogView.findViewById(R.id.recyclerListProductos);
                    GridLayoutManager gl = new GridLayoutManager(this, 1);
                    recyclerDialog2.setLayoutManager(gl);

                    AdaptadorListProductosRece adapter = new AdaptadorListProductosRece(listaProdRece);
                    recyclerDialog2.setAdapter(null);
                    recyclerDialog2.setAdapter(adapter);

                    dialog2 = builder2.create();
                    dialog2.show();

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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

            }

        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(RecepCompras.this);
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







