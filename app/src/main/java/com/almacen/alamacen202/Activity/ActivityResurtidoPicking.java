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
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.Adapter.AdaptadorlistOrdComp;
import com.almacen.alamacen202.Adapter.AdapterResurtidoPicking;
import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ComprometidasSandG;
import com.almacen.alamacen202.SetterandGetters.ListProdxFolOrdComp;
import com.almacen.alamacen202.SetterandGetters.ResurtidoPicking;
import com.almacen.alamacen202.XML.XMLActualizaOrdenCompra;
import com.almacen.alamacen202.XML.XMLActualizaPick;
import com.almacen.alamacen202.XML.XMLCLArticulo;
import com.almacen.alamacen202.XML.XMLCompromeAlma;
import com.almacen.alamacen202.XML.XMLConsulRack;
import com.almacen.alamacen202.XML.XMLConsulResPick;
import com.almacen.alamacen202.XML.XMLConsulXPicking;
import com.almacen.alamacen202.XML.XMLConsultaOrdenCompra;
import com.almacen.alamacen202.XML.XMLConsultaXprod;
import com.almacen.alamacen202.XML.XMLUbicacionAlma;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import dmax.dialog.SpotsDialog;

public class ActivityResurtidoPicking extends AppCompatActivity {

    private String strusr,strpass,strbran,strServer,mensaje="",fechaReg,horaReg;
    private String totUbi,totAlm,max,min,cant,cantEmpq,clavProd,descProd,ubic,cantAlm;
    private int posicion=0,posGuard=0,ord=1;
    private Button btnBuscar,btnResurtir,btnAdelante,btnAtras,btnFinalizar;
    private CheckBox chbUbic,chbPFech;
    private TextView tvClvProdPick,tvDescPick,tvCantEmpq;
    private EditText txtSumUbiPick,txtSumAlmPick,txtMax,txtMin,txtUbicPick,txtCantUbicPick,txtCantAcum;
    private RecyclerView rvPicking;
    private ImageView ivProdPick;
    private SharedPreferences preference;
    private AlertDialog mDialog;
    private ArrayList<ResurtidoPicking> listPick = new ArrayList<>();
    private ArrayList<String>listaUbic = new ArrayList<>();
    private ArrayList<String>listaCantUbic = new ArrayList<>();
    private AdapterResurtidoPicking adapter = new AdapterResurtidoPicking(listPick);
    private ArrayList<ComprometidasSandG> listaComprometidas = new ArrayList<>();
    private boolean var=false;//para saber si se selecciona ubicaciones o para el traspaso entre ubicaciones
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resurtido_picking);

        MyToolbar.show(this, "Resurtido de Picking", true);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        strusr = preference.getString("user", "null");
        strpass = preference.getString("pass", "null");
        strbran = preference.getString("codBra", "null");
        strServer = preference.getString("Server", "null");

        mDialog = new SpotsDialog.Builder().setContext(ActivityResurtidoPicking.this).
                setMessage("Espere un momento...").build();

        btnBuscar   = findViewById(R.id.btnBusca);
        btnResurtir = findViewById(R.id.btnResurtir);
        btnAtras    = findViewById(R.id.btnAtras);
        btnAdelante = findViewById(R.id.btnAdelante);
        btnFinalizar = findViewById(R.id.btnFinalizar);
        tvClvProdPick= findViewById(R.id.tvClvProdPick);
        tvDescPick = findViewById(R.id.tvDescPick);
        tvCantEmpq = findViewById(R.id.tvCantEmpq);
        txtSumUbiPick = findViewById(R.id.txtSumUbiPick);
        txtSumAlmPick = findViewById(R.id.txtSumAlmPick);
        txtMax = findViewById(R.id.txtMax);
        txtMin = findViewById(R.id.txtMin);
        txtUbicPick = findViewById(R.id.txtUbicPick);
        txtCantUbicPick = findViewById(R.id.txtCantUbicPick);
        txtCantAcum = findViewById(R.id.txtCantAcum);

        rvPicking  = findViewById(R.id.rvPicking);
        ivProdPick = findViewById(R.id.ivProdPick);
        chbUbic = findViewById(R.id.chbUbic);
        chbPFech = findViewById(R.id.chbPFech);

        GridLayoutManager gl = new GridLayoutManager(ActivityResurtidoPicking.this, 1);
        rvPicking.setLayoutManager(gl);


        chbUbic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chbUbic.setChecked(true);
                chbPFech.setChecked(false);
                ord=1;
            }
        });
        chbPFech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chbPFech.setChecked(true);
                chbUbic.setChecked(false);
                ord=2;
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean var=true;
                if(listPick.size()>0){
                    AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityResurtidoPicking.this);
                    alerta.setMessage("Aun existen datos que no ha terminado de revisar,"
                    +"\n¿Desea continuar?").setCancelable(false).
                            setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    fechaReg=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                                    horaReg=new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                                    firtMet();
                                    dialogInterface.cancel();
                                }//onClick
                            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }//onClick
                    });//Alert
                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("Aviso");
                    titulo.show();
                }else{
                    fechaReg=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    horaReg=new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                    firtMet();
                }//else
            }//onclick
        });//btnBuscarOrd

        btnAdelante.setOnClickListener(new View.OnClickListener() {//boton adelante
            @Override
            public void onClick(View view) {
                posicion++;
                clavProd=listPick.get(posicion).getClaveProd();
                descProd=listPick.get(posicion).getDescrip();
                ubic=listPick.get(posicion).getPicking();
                new AsynCallConsulXPicking().execute();
            }//onclick
        });//btnadelante setonclicklistener

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                posicion--;
                clavProd=listPick.get(posicion).getClaveProd();
                descProd=listPick.get(posicion).getDescrip();
                ubic=listPick.get(posicion).getPicking();
                new AsynCallConsulXPicking().execute();
            }//onclick
        });//btnatras setonclicklistener

        btnResurtir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsynCallConsulXPicking().execute();
                new AsyncallUbicaciones().execute();
            }//onclick
        });//btnResurtir setonclick

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityResurtidoPicking.this);
                alerta.setMessage("¿Desea terminar de resurtir?").setCancelable(false).
                        setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new AsyncActualizaPick().execute();
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
            }//onclick
        });//btnFinalizar onclick

    }//onCreate

    public void firtMet() {//firtMet
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {//si hay conexion a internet
            listPick.clear();
            rvPicking.setAdapter(null);
            new AsynCallConsulResPick().execute();
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityResurtidoPicking.this);
            alerta.setMessage("NO HAY CONEXIÓN A INTERNET").setCancelable(false).
                    setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }//onClick
            });//Alert

            AlertDialog titulo = alerta.create();
            titulo.setTitle("¡ERROR DE CONEXIÓN!");
            titulo.show();
        }//else
    }//FirtMet
    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }//isNumeric

    public void resurtir(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_resurtido, null);
        builder.setView(dialogView);

        final TextView tvClvProdDial = dialogView.findViewById(R.id.tvClvProdDial);
        final TextView tvDescProdDial = dialogView.findViewById(R.id.tvDescProdDial);
        final TextView tvCantOrig = dialogView.findViewById(R.id.tvCantOrig);
        final TextView tvCantEmpq = dialogView.findViewById(R.id.tvCantEmpq);
        final EditText txtDestEmpq=dialogView.findViewById(R.id.txtDestEmpq);
        final TextView tvNecesidad = dialogView.findViewById(R.id.tvNecesidad);
        final TextView tvOrigenR = dialogView.findViewById(R.id.tvOrigenR);

        tvClvProdDial.setText(tvClvProdPick.getText().toString());
        tvDescProdDial.setText(tvDescPick.getText().toString());
        tvCantEmpq.setText(cantEmpq);
        int nec=Integer.parseInt(max)-Integer.parseInt(cant)-Integer.parseInt(cantEmpq);
        tvNecesidad.setText(nec+"");

        //spinner--------------------------------------------------------
        for(int i=0;i<listaUbic.size();i++){
            if(listaUbic.get(i).equals(listPick.get(posicion).getRack())){
                tvOrigenR.setText(listaUbic.get(i));
                tvCantOrig.setText(listaCantUbic.get(i));
                txtDestEmpq.setText(listaCantUbic.get(i));
                break;
            }//if
        }//for

        builder.setCancelable(false);
        builder.setPositiveButton("Aceptar",null);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new AsynCallConsulXPicking().execute();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isNumeric(txtDestEmpq.getText().toString())==false || tvOrigenR.getText().toString().equals("")){
                            Toast.makeText(ActivityResurtidoPicking.this, "Campos vacios o en cero", Toast.LENGTH_SHORT).show();
                        }else {
                            if(Integer.parseInt(tvCantOrig.getText().toString())<Integer.parseInt(txtDestEmpq.getText().toString())){
                                Toast.makeText(ActivityResurtidoPicking.this, "No existe esa cantidad disponible",
                                        Toast.LENGTH_SHORT).show();
                            }else if(Integer.parseInt(tvNecesidad.getText().toString())<Integer.parseInt(txtDestEmpq.getText().toString())){
                                Toast.makeText(ActivityResurtidoPicking.this, "Cantidad excede a necesidad",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                new AsyncModificarUbicDestino(tvOrigenR.getText().toString(),"EMPAQUE",
                                        tvClvProdDial.getText().toString(),txtDestEmpq.getText().toString(),dialogInterface).execute();
                            }//else if
                        }//else
                    }//ONCLICK
                });//SET ON CLICK
            }//onShow
        });//setonshowlistener
        dialog.show();


    }//resurtir

    public void onClickLista(View v){//cada vez que se seleccione un producto en la lista
        posicion = rvPicking.getChildPosition(rvPicking.findContainingItemView(v));
        clavProd=listPick.get(posicion).getClaveProd();
        descProd=listPick.get(posicion).getDescrip();
        ubic=listPick.get(posicion).getPicking();
        new AsynCallConsulXPicking().execute();
    }//onClickLista

    public void mostrarProductos(){
        btnResurtir.setEnabled(true);
        btnResurtir.setBackgroundTintList(ColorStateList.
                valueOf(getResources().getColor(R.color.Amarillo)));
        btnFinalizar.setEnabled(true);
        btnFinalizar.setBackgroundTintList(ColorStateList.
                valueOf(getResources().getColor(R.color.AzulBack)));
        rvPicking.setAdapter(null);
        rvPicking.setAdapter(adapter);
        posicion=0;
        new AsynCallConsulXPicking().execute();
    }//mostrarProductos

    public void mostrarDetalleProd(){//detalle por producto seleccionado
        adapter.index(posicion);
        adapter.notifyDataSetChanged();
        rvPicking.scrollToPosition(posicion);
        posGuard=posicion;//guardar en caso de que se necesite saber posicion anterior
        tvClvProdPick.setText(clavProd);
        tvDescPick.setText(descProd);
        txtUbicPick.setText(ubic);
        txtSumUbiPick.setText(totUbi);
        txtSumAlmPick.setText(totAlm);
        txtMax.setText(max);
        txtMin.setText(min);
        txtCantUbicPick.setText(cant);
        tvCantEmpq.setText(cantEmpq);
        new AsynCallCompromeAlma().execute();

        Picasso.with(getApplicationContext()).
                load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" +
                        tvClvProdPick.getText().toString() + ".jpg")
                .error(R.drawable.aboutlogo)
                .fit()
                .centerInside()
                .into(ivProdPick);
        cambiaProd();
    }//mostrarDetalleProd

    public void cambiaProd(){
        if(posicion==0){
            btnAdelante.setEnabled(true);
            btnAdelante.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.colorPrimary)));
            btnAtras.setEnabled(false);
            btnAtras.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.ColorGris)));

        }else if(posicion+1==listPick.size()){
            btnAtras.setEnabled(true);
            btnAtras.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.colorPrimary)));
            btnAdelante.setEnabled(false);
            btnAdelante.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.ColorGris)));
        }else{
            btnAtras.setEnabled(true);
            btnAtras.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.colorPrimary)));
            btnAdelante.setEnabled(true);
            btnAdelante.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.colorPrimary)));
        }//else
    }//cambiaProd



    //WebService Consulta Prod
    private class AsynCallConsulResPick extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreejecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            listPick.clear();
            conectaConsulResPick();
            return null;
        }//doInBackground


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if (listPick.size()>0) {
                fechaReg="";horaReg="";
                mostrarProductos();
            }else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityResurtidoPicking.this);
                alerta.setMessage(mensaje).setCancelable(false).
                        setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }//onclick
                });//alertDialogBuilder

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Atención");
                titulo.show();
            }//else
        }//OnpostEjecute
    }//class AsynCall


    private void conectaConsulResPick() {
        String SOAP_ACTION = "ConsulResPick";
        String METHOD_NAME = "ConsulResPick";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLConsulResPick soapEnvelope = new XMLConsulResPick(SoapEnvelope.VER11);
            soapEnvelope.XMLConsulResPick(strusr,strpass,strbran,fechaReg,horaReg,ord+"");
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
                listPick.add(new ResurtidoPicking(""+(i+1),
                        (response0.getPropertyAsString("k_clvProd").equals("anyType{}") ? " " : response0.getPropertyAsString("k_clvProd")),
                        (response0.getPropertyAsString("k_descrip").equals("anyType{}") ? " " : response0.getPropertyAsString("k_descrip")),
                        (response0.getPropertyAsString("k_fecha").equals("anyType{}") ? " " : response0.getPropertyAsString("k_fecha")),
                        (response0.getPropertyAsString("k_hora").equals("anyType{}") ? " " : response0.getPropertyAsString("k_hora")),
                        (response0.getPropertyAsString("k_picking").equals("anyType{}") ? " " : response0.getPropertyAsString("k_picking")),
                        (response0.getPropertyAsString("k_clasif").equals("anyType{}") ? " " : response0.getPropertyAsString("k_clasif")),
                        (response0.getPropertyAsString("k_rack").equals("anyType{}") ? " " : response0.getPropertyAsString("k_rack")),false));
            }//for
        } catch (SoapFault soapFault) {
            mensaje = "Error :"+soapFault.getMessage();
        } catch (XmlPullParserException e) {
            mensaje = "Error: " + e.getMessage();
        } catch (IOException e) {
            mensaje = "No se encontró servidor";
        } catch (Exception ex) {
            mensaje = "Hubó un problema";
        }//catch
    }//conecta

    //Webservice modifica cantidad de ubicacion destino y origen
    private class AsyncModificarUbicDestino extends AsyncTask<Void, Void, Void> {
        String UbicacionOrigen,UbicacionDestino,Producto,Cantidad;
        DialogInterface dialogInterface;

        public AsyncModificarUbicDestino(String ubicacionOrigen, String ubicacionDestino, String producto,
                                         String cantidad,DialogInterface dialogInterface) {
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
            int c=Integer.parseInt(Cantidad);
            Cantidad=c+"";
            consultaUbicacionMod(UbicacionOrigen,UbicacionDestino,Producto,Cantidad);
            return null;
        }//doInBackground


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if(mensaje.equals("LA UBICACION A SIDO INSERTADO") || mensaje.equals("LA UBICACION A SIDO ACTUALIZADA")){
                dialogInterface.dismiss();
                Toast.makeText(ActivityResurtidoPicking.this, "Cantidad de ubicacion EMPAQUE ha sido modificada", Toast.LENGTH_SHORT).show();
                new AsynCallConsulXPicking().execute();
            }else{
                Toast.makeText(ActivityResurtidoPicking.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }//onPosteExecute
    }//AsynModificar

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
            /*listPick.get(posicion).setRevisado(true);
            int ca=Integer.parseInt(tvCantEmpq.getText().toString())+Integer.parseInt(Cantidad);
            listPick.get(posicion).setCantEmpq(ca+"");*/
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

    private class AsyncallUbicaciones extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreejecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            listaUbic.clear();
            listaCantUbic.clear();
            conectaUbicaciones();
            return null;
        }//doInBackground


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if (listaUbic.size()>0) {
                resurtir();
            }else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityResurtidoPicking.this);
                alerta.setMessage(mensaje).setCancelable(false).
                        setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }//onclick
                        });//alertDialogBuilder

                AlertDialog titulo = alerta.create();
                titulo.setTitle("Atención");
                titulo.show();
            }//else
        }//OnpostEjecute
    }//class AsynCall


    private void conectaUbicaciones() {

        String SOAP_ACTION = "UbicacionAlma";
        String METHOD_NAME = "UbicacionAlma";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLUbicacionAlma soapEnvelope = new XMLUbicacionAlma(SoapEnvelope.VER11);
            soapEnvelope.XMLUbicacionAlma(strusr, strpass,tvClvProdPick.getText().toString(), strbran);
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
                listaUbic.add(response0.getPropertyAsString("k_Ubicacion").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Ubicacion"));
                listaCantUbic.add(response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Cantidad"));
            }//for
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
            mensaje = "Hubó un problema";
        }//catch
    }//AsynCall

    //WebService ConsultaxProducto
    private class AsynCallConsulXPicking extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreejecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";totUbi="0";totAlm="0";max="0";min="0";cant="0";cantEmpq="0";cantAlm="0";
            clavProd=listPick.get(posicion).getClaveProd();
            descProd=listPick.get(posicion).getDescrip();
            ubic=listPick.get(posicion).getPicking();
            conectaConsulXPicking();
            return null;
        }//doInBackground


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if (mensaje.equals("")) {
                mostrarDetalleProd();
            }else {
                if(listPick.size()>0){
                    Toast.makeText(ActivityResurtidoPicking.this, "No fue posible obtener detalles del producto", Toast.LENGTH_SHORT).show();
                    posicion=posGuard;
                    mostrarDetalleProd();
                }else{
                    Toast.makeText(ActivityResurtidoPicking.this, "Hubó un problema al actualizar datos del producto", Toast.LENGTH_SHORT).show();
                }//else
            }//else
        }//OnpostEjecute
    }//class AsynCall


    private void conectaConsulXPicking() {
        String SOAP_ACTION = "ConsulXPicking";
        String METHOD_NAME = "ConsulXPicking";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLConsulXPicking soapEnvelope = new XMLConsulXPicking(SoapEnvelope.VER11);
            soapEnvelope.XMLConsulXPicking(strusr,strpass,strbran,clavProd,ubic);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("DatP");
            totUbi=response.getPropertyAsString("k_totUbi").equals("anyType{}") ? "0" : response.getPropertyAsString("k_totUbi");
            totAlm=response.getPropertyAsString("k_totAlm").equals("anyType{}") ? "0" : response.getPropertyAsString("k_totAlm");
            max=response.getPropertyAsString("k_maxi").equals("anyType{}") ? "0" : response.getPropertyAsString("k_maxi");
            min=response.getPropertyAsString("k_mini").equals("anyType{}") ? "0" : response.getPropertyAsString("k_mini");
            cant=response.getPropertyAsString("k_cant").equals("anyType{}") ? "0" : response.getPropertyAsString("k_cant");
            cantEmpq=response.getPropertyAsString("k_cantEmp").equals("anyType{}") ? "0" : response.getPropertyAsString("k_cantEmp");
            cantAlm=(response.getPropertyAsString("k_existProc").equals("anyType{}") ? "0" : response.getPropertyAsString("k_existProc"));
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

    private class AsyncActualizaPick extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreexecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            consultaActualizaPick();
            return null;
        }//doInBackground


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if(mensaje.equals("Dato modificado correctamente")){
                listPick.get(posicion).setRevisado(true);
                Toast.makeText(ActivityResurtidoPicking.this, "Producto Revisado", Toast.LENGTH_SHORT).show();
                new AsynCallConsulXPicking().execute();
            }else{
                Toast.makeText(ActivityResurtidoPicking.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }//onPosteExecute
    }//AsynModificar

    private void consultaActualizaPick() {
        String SOAP_ACTION = "ActualizaPick";
        String METHOD_NAME = "ActualizaPick";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";


        try {
            String fechaT=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String horaT=new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLActualizaPick soapEnvelope = new XMLActualizaPick(SoapEnvelope.VER11);
            soapEnvelope.XMLActualizaPicking(strusr, strpass,strbran,clavProd, listPick.get(posicion).getFecha(),
                    listPick.get(posicion).getHora(), fechaT,horaT);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            Vector response = (Vector) soapEnvelope.getResponse();
            mensaje = response.get(0).toString();

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
            soapEnvelope.XMLCompromeAlma(strusr, strpass, tvClvProdPick.getText().toString(), strbran);
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
}// clase
