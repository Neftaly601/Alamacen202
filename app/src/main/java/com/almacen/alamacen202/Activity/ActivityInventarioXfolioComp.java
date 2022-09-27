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
import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ComprometidasSandG;
import com.almacen.alamacen202.SetterandGetters.ListProdxFolOrdComp;
import com.almacen.alamacen202.SetterandGetters.UbicacionSandG;
import com.almacen.alamacen202.XML.XMLActualizaOrdenCompra;
import com.almacen.alamacen202.XML.XMLCLArticulo;
import com.almacen.alamacen202.XML.XMLCompromeAlma;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import dmax.dialog.SpotsDialog;

public class ActivityInventarioXfolioComp extends AppCompatActivity {

    private String strusr,strpass,strbran,strServer,mensaje="",folioSelec="";
    private String producto="",ubicacion="",canti="",cantAlm="0",sumAlm="0",sumExist="0";
    private int posicion=0,posicion2=0;
    private EditText txtFolioOrden,txtUbicacion,txtCantProdOrd,txtSumCant,txtExistAlm,txtCantAcum;
    private TextView tvClvProdOrden,tvProdOrden;
    private Button btnBuscarOrd,btnAtras,btnGuardar,btnAdelante,btnTerminar,btnTransf,btnCamUbic;
    private RecyclerView rvOrdenCompras;
    private CheckBox chbUbi,chbProd;
    private ImageView ivProd;
    private SharedPreferences preference;
    private ArrayList<ListProdxFolOrdComp> listProd = new ArrayList<>();
    private AlertDialog mDialog;
    private AdaptadorlistOrdComp adapter = new AdaptadorlistOrdComp(listProd);
    private ArrayList<String> listubicaciones = new ArrayList<>();
    private ArrayList<String> listcantUbi = new ArrayList<>();
    private ArrayList<ComprometidasSandG> listaComprometidas = new ArrayList<>();
    private boolean var=false;//para saber si se selecciona ubicaciones o para el traspaso entre ubicaciones
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invent_por_folio_comp);

        MyToolbar.show(this, "Inventario por folio compra", true);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        strusr = preference.getString("user", "null");
        strpass = preference.getString("pass", "null");
        strbran = preference.getString("codBra", "null");
        strServer = preference.getString("Server", "null");

        mDialog = new SpotsDialog.Builder().setContext(ActivityInventarioXfolioComp.this).
                setMessage("Espere un momento...").build();

        chbUbi          = findViewById(R.id.chbUbi);
        chbProd         = findViewById(R.id.chbProd);
        txtFolioOrden   = findViewById(R.id.txtFolioOrden);
        txtUbicacion    = findViewById(R.id.txtUbicacion);
        txtCantProdOrd  = findViewById(R.id.txtCantProdOrd);
        txtSumCant      = findViewById(R.id.txtSumCant);
        txtExistAlm     = findViewById(R.id.txtExistAlm);

        tvClvProdOrden  = findViewById(R.id.tvClvProdOrden);
        tvProdOrden     = findViewById(R.id.tvProdOrden);
        btnBuscarOrd    = findViewById(R.id.btnBuscarOrd);
        btnAtras        = findViewById(R.id.btnAtras);
        btnGuardar      = findViewById(R.id.btnGuardar);
        btnAdelante     = findViewById(R.id.btnAdelante);
        btnTerminar     = findViewById(R.id.btnTerminar);
        btnTransf       = findViewById(R.id.btnTransf);
        btnCamUbic      = findViewById(R.id.btnCamUbic);
        rvOrdenCompras  = findViewById(R.id.rvOrdenCompras);
        ivProd          = findViewById(R.id.ivProd);
        txtCantAcum     = findViewById(R.id.txtCantAcum);

        GridLayoutManager gl = new GridLayoutManager(ActivityInventarioXfolioComp.this, 1);
        rvOrdenCompras.setLayoutManager(gl);

        chbUbi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chbUbi.setChecked(true);
                chbProd.setChecked(false);
                tipoOrdenamiento(false);
            }//onclick
        });//chbUbi onclick

        chbProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chbProd.setChecked(true);
                chbUbi.setChecked(false);
                tipoOrdenamiento(true);
            }//onclick
        });//chbProd

        chbProd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    chbUbi.setChecked(false);
                }else{
                    chbUbi.setChecked(true);
                    tipoOrdenamiento(true);
                }//else
            }//onCheckedChange
        });//chbprod

        btnBuscarOrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firtMet();
            }
        });//btnBuscarOrd

        btnTransf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var=false;
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtCantProdOrd.getWindowToken(),0);//cerrar teclado si esta abierto
                txtCantProdOrd.setText(listProd.get(posicion).getCantidad());
                txtCantProdOrd.clearFocus();
                new AsyncallUbicaciones().execute();
            }
        });//btnTransf

        btnCamUbic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var=true;
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtCantProdOrd.getWindowToken(),0);//cerrar teclado si esta abierto
                txtCantProdOrd.clearFocus();
                new AsyncallUbicaciones().execute();
            }//onClick
        });//btnCamUbic

        btnTerminar.setOnClickListener(new View.OnClickListener() {//modificar en la web
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityInventarioXfolioComp.this);
                alerta.setMessage("¿Desea terminar de modificar las "+
                        "cantidades de los productos correspondientes al folio: "+folioSelec+"?").setCancelable(false).
                        setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                tvClvProdOrden.setText("Clave");
                                tvProdOrden.setText("Descripción de Producto");
                                txtUbicacion.setText("");
                                txtCantProdOrd.setText("");
                                txtCantProdOrd.clearFocus();
                                ivProd.setImageResource(R.drawable.aboutlogo);
                                btnGuardar.setEnabled(false);
                                btnGuardar.setBackgroundTintList(ColorStateList.
                                        valueOf(getResources().getColor(R.color.ColorGris)));
                                btnAdelante.setEnabled(false);
                                btnAdelante.setBackgroundTintList(ColorStateList.
                                        valueOf(getResources().getColor(R.color.ColorGris)));
                                btnAtras.setEnabled(false);
                                btnAtras.setBackgroundTintList(ColorStateList.
                                        valueOf(getResources().getColor(R.color.ColorGris)));

                                btnTerminar.setEnabled(false);
                                btnTerminar.setBackgroundTintList(ColorStateList.
                                        valueOf(getResources().getColor(R.color.ColorGris)));
                                btnTransf.setEnabled(false);
                                btnTransf.setBackgroundTintList(ColorStateList.
                                        valueOf(getResources().getColor(R.color.ColorGris)));
                                btnCamUbic.setEnabled(false);
                                btnCamUbic.setBackgroundTintList(ColorStateList.
                                        valueOf(getResources().getColor(R.color.ColorGris)));

                                txtFolioOrden.setEnabled(true);
                                txtFolioOrden.setText("");
                                txtSumCant.setText("");
                                txtExistAlm.setText("");
                                txtCantAcum.setText("");

                                txtCantProdOrd.setEnabled(false);
                                btnBuscarOrd.setEnabled(true);
                                btnBuscarOrd.setBackgroundTintList(ColorStateList.
                                        valueOf(getResources().getColor(R.color.AzulBack)));
                                chbUbi.setChecked(true);
                                chbProd.setChecked(false);
                                chbUbi.setEnabled(false);
                                chbUbi.setButtonTintList(ColorStateList.
                                        valueOf(getResources().getColor(R.color.ColorGris)));
                                chbProd.setEnabled(false);
                                chbProd.setButtonTintList(ColorStateList.
                                        valueOf(getResources().getColor(R.color.ColorGris)));
                                listProd.clear();
                                rvOrdenCompras.setAdapter(null);
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
            }//onClick
        });//btnActualizar setonclick
        btnAdelante.setOnClickListener(new View.OnClickListener() {//boton adelante
            @Override
            public void onClick(View view) {
                alertParaCambiar("next");
            }//onclick
        });//btnadelante setonclicklistener

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertParaCambiar("back");
            }//onclick
        });//btnatras setonclicklistener

    }//onCreate

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

        tvClvProdDial.setText(tvClvProdOrden.getText().toString());
        tvDescProdDial.setText(listProd.get(posicion).getArt());
        //btnSeleccione-----------------------------------------
        btnSelecciona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spOrigen.setEnabled(false);
                spOrigen.setBackgroundTintList(ColorStateList.
                        valueOf(getResources().getColor(R.color.ColorGris)));
                btnSelecciona.setBackgroundTintList(ColorStateList.
                        valueOf(getResources().getColor(R.color.ColorGris)));
                ArrayList<String>listaUbicacionesCopia= new ArrayList<>(listubicaciones);
                ArrayList<String>listCantCopia=new ArrayList<>(listcantUbi);
                listaUbicacionesCopia.remove(spOrigen.getSelectedItemPosition());
                listCantCopia.remove(spOrigen.getSelectedItemPosition());
                spDestino.setAdapter(new ArrayAdapter<String>(ActivityInventarioXfolioComp.this,
                        android.R.layout.simple_spinner_item,listaUbicacionesCopia));
                spDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                        tvDestino.setText(listCantCopia.get(pos));
                        txtDestino.setText("");
                    }//onItemselected
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }//onNothingselected
                });//spDestinotsetonitemselected
            }//onclick
        });//btnSelecciona.setonclick
        //spinnerOrigen--------------------------------------------------------
        spOrigen.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,listubicaciones));
        spOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                tvOrigen.setText(listcantUbi.get(pos));
            }//onItemselected
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }//onNothingselected
        });//spOrigentsetonitemselected
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
                            Toast.makeText(ActivityInventarioXfolioComp.this, "Campos vacios o en cero", Toast.LENGTH_SHORT).show();
                        }else {
                            if(Integer.parseInt(tvOrigen.getText().toString())<Integer.parseInt(txtDestino.getText().toString())){
                                Toast.makeText(ActivityInventarioXfolioComp.this, "No existe esa cantidad disponible",
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

    public void alertParaCambiar(String var){//verificar si el edittext tiene el focus para preguntar si se pasa al siguiente
        if(txtCantProdOrd.isFocused()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityInventarioXfolioComp.this);
            alerta.setMessage("¿Quieres pasar al siguiente producto sin guardar?").setCancelable(false).
                    setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(var.equals("next")){
                                posicion++;
                            }else if(var.equals("back")){
                                posicion--;
                            }else if(var.equals("change")){
                                posicion=posicion2;
                                posicion2=0;
                            }
                            dialogInterface.cancel();
                            new AsynCallConsulXprod().execute();
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
        }else{
            if(var.equals("next")){
                posicion++;
            }else if(var.equals("back")){
                posicion--;
            }else if(var.equals("change")){
                posicion=posicion2;
                posicion2=0;
            }
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(txtCantProdOrd.getWindowToken(),0);
            txtCantProdOrd.clearFocus();
            new AsynCallConsulXprod().execute();
        }
    }//alert

    public void firtMet() {//firtMet
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {//si hay conexion a internet
            if (!txtFolioOrden.getText().toString().equals("")){//si hay folio en edittext
                folioSelec = txtFolioOrden.getText().toString();
                new AsynCallOrdenCompra().execute();
                //txtEscanerFol.setText(null);
            }else{
                Toast.makeText(this, "Folio vacío", Toast.LENGTH_SHORT).show();
            }//else
        } else {
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityInventarioXfolioComp.this);
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

    public void guardarXprod(View v){//guardarXprod
        String cant=txtCantProdOrd.getText().toString();
        if(isNumeric(cant)==true){
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityInventarioXfolioComp.this);
            alerta.setMessage("¿Guardar?").setCancelable(false).
                    setPositiveButton("Aceptar",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new AsynCallActualizaLista().execute();
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
        }else{
            Toast.makeText(ActivityInventarioXfolioComp.this, "Cantidad no válida o vacía", Toast.LENGTH_SHORT).show();
        }//else
    }//guardarXProd

    public void onClickLista(View v){//cada vez que se seleccione un producto en la lista
        posicion2 = rvOrdenCompras.getChildPosition(rvOrdenCompras.findContainingItemView(v));
        alertParaCambiar("change");
    }//onClickLista

    public void mostrarProductosOrdenComp(){//mostrar lista traida de webservice
        btnGuardar.setEnabled(true);
        btnGuardar.setBackgroundTintList(ColorStateList.
                valueOf(getResources().getColor(R.color.Amarillo)));
        btnTerminar.setEnabled(true);
        btnTerminar.setBackgroundTintList(ColorStateList.
                valueOf(getResources().getColor(R.color.AzulBack)));
        btnTransf.setEnabled(true);
        btnTransf.setBackgroundTintList(ColorStateList.
                valueOf(getResources().getColor(R.color.AzulBack)));
        btnCamUbic.setEnabled(true);
        btnCamUbic.setBackgroundTintList(ColorStateList.
                valueOf(getResources().getColor(R.color.AzulBack)));

        txtFolioOrden.setEnabled(false);
        txtCantProdOrd.setEnabled(true);
        btnBuscarOrd.setEnabled(false);
        btnBuscarOrd.setBackgroundTintList(ColorStateList.
                valueOf(getResources().getColor(R.color.ColorGris)));
        chbUbi.setEnabled(true);
        chbUbi.setButtonTintList(ColorStateList.
                valueOf(getResources().getColor(R.color.AzulBack)));
        chbProd.setEnabled(true);
        chbProd.setButtonTintList(ColorStateList.
                valueOf(getResources().getColor(R.color.AzulBack)));
        rvOrdenCompras.setAdapter(null);
        rvOrdenCompras.setAdapter(adapter);
        posicion=0;
        new AsynCallConsulXprod().execute();
    }//mostrarOrdCompra

    public void mostrarDetalleProd(){//detalle por producto seleccionado
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtCantProdOrd.getWindowToken(),0);//cerrar teclado si esta abierto
        txtCantProdOrd.clearFocus();
        adapter.index(posicion);
        adapter.notifyDataSetChanged();
        rvOrdenCompras.scrollToPosition(posicion);
        tvClvProdOrden.setText(listProd.get(posicion).getClaveArt());
        tvProdOrden.setText(listProd.get(posicion).getArt());
        txtUbicacion.setText(listProd.get(posicion).getUbicacion());
        txtSumCant.setText(sumAlm);
        txtExistAlm.setText(sumExist);
        new AsynCallCompromeAlma().execute();

        if(Integer.parseInt(txtSumCant.getText().toString())==Integer.parseInt(txtExistAlm.getText().toString())){
            txtCantProdOrd.setEnabled(false);
            btnGuardar.setEnabled(false);
            btnGuardar.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.ColorGris)));
        }else{
            txtCantProdOrd.setEnabled(true);
            btnGuardar.setEnabled(true);
            btnGuardar.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.Amarillo)));
        }//else

        txtCantProdOrd.setText(listProd.get(posicion).getCantidad());
        cambiaProd();
        Picasso.with(getApplicationContext()).
                load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" +
                        tvClvProdOrden.getText().toString() + ".jpg")
                .error(R.drawable.aboutlogo)
                .fit()
                .centerInside()
                .into(ivProd);


    }//mostrarDetalleProd

    public void cambiaProd(){
        if(posicion==0){
            btnAdelante.setEnabled(true);
            btnAdelante.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.colorPrimary)));
            btnAtras.setEnabled(false);
            btnAtras.setBackgroundTintList(ColorStateList.
                    valueOf(getResources().getColor(R.color.ColorGris)));

        }else if(posicion+1==listProd.size()){
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

    public void tipoOrdenamiento(boolean tipo){//false:ubicacion true:producto
        if(tipo==true){
            Collections.sort(listProd,ProductoComparator);
        }else{
            Collections.sort(listProd,UbicacionComparator);
        }//else
        for(int i=0;i<listProd.size();i++){
            listProd.get(i).setNum((i+1)+"");
        }//for
        posicion=0;
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtCantProdOrd.getWindowToken(),0);//cerrar teclado si esta abierto
        txtCantProdOrd.clearFocus();
        mostrarDetalleProd();

    }//tipoOrdenamiento

    //comparator Ubicacion
    public static Comparator<ListProdxFolOrdComp> UbicacionComparator = new Comparator<ListProdxFolOrdComp>() {
        @Override
        public int compare(ListProdxFolOrdComp listProdxFolOrdComp1, ListProdxFolOrdComp listProdxFolOrdComp2) {
            String ubicacion1= listProdxFolOrdComp1.getUbicacion().toUpperCase();
            String ubicacion2= listProdxFolOrdComp2.getUbicacion().toUpperCase();
            return ubicacion1.compareTo(ubicacion2);
        }//compare
    };//Comparator Ubicacion

    //comparar Producto
    public static Comparator<ListProdxFolOrdComp> ProductoComparator = new Comparator<ListProdxFolOrdComp>() {
        @Override
        public int compare(ListProdxFolOrdComp listProdxFolOrdComp1, ListProdxFolOrdComp listProdxFolOrdComp2) {
            String producto1= listProdxFolOrdComp1.getClaveArt().toUpperCase();
            String producto2= listProdxFolOrdComp2.getClaveArt().toUpperCase();
            return producto1.compareTo(producto2);
        }//compare
    };//Comparator Producto

    //WebService ConsultaProductos
    private class AsynCallOrdenCompra extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreejecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            conecta();
            return null;
        }//doInBackground


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if (listProd.size()>0) {
                mostrarProductosOrdenComp();
            }else {
                AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityInventarioXfolioComp.this);
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


    private void conecta() {
        String SOAP_ACTION = "ConsulOrdComp";
        String METHOD_NAME = "ConsulOrdComp";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLConsultaOrdenCompra soapEnvelope = new XMLConsultaOrdenCompra(SoapEnvelope.VER11);
            soapEnvelope.XMLConsultaOrd(strusr,strpass,strbran,folioSelec);
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
                listProd.add(new ListProdxFolOrdComp(""+(i+1),
                        (response0.getPropertyAsString("k_clvArt").equals("anyType{}") ? " " : response0.getPropertyAsString("k_clvArt")),
                        (response0.getPropertyAsString("k_descArt").equals("anyType{}") ? " " : response0.getPropertyAsString("k_descArt")),
                        (response0.getPropertyAsString("k_ubicacion").equals("anyType{}") ? " " : response0.getPropertyAsString("k_ubicacion")),
                        (response0.getPropertyAsString("k_cant").equals("anyType{}") ? " " : response0.getPropertyAsString("k_cant"))));
            }//for
        } catch (SoapFault soapFault) {
            mensaje = "Error :"+soapFault.getMessage();
        } catch (XmlPullParserException e) {
            mensaje = "Error: " + e.getMessage();
        } catch (IOException e) {
            mensaje = "No se encontró servidor";
        } catch (Exception ex) {
            mensaje = "Pude que el folio de compra no exista";
        }//catch
    }//conecta

    //WebService Actualizar Cantidad
    private class AsynCallActualizaLista extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreejecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            producto=tvClvProdOrden.getText().toString();
            ubicacion=txtUbicacion.getText().toString();
            canti=txtCantProdOrd.getText().toString();
            conectaActualizar();
            return null;
        }//doInBackground


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if(mensaje.equals("Cantidad Actualizada")){
                new AsynCallConsulXprod().execute();
                /*listProd.get(posicion).setCantidad(txtCantProdOrd.getText().toString());
                txtCantProdOrd.clearFocus();
                if(posicion+1<listProd.size()){
                    posicion=posicion+1;
                }//if posicion sea menor al tamaño de la lista
                mostrarDetalleProd();*/
            }else{mensaje="Hubó un problema "+mensaje;}
            AlertDialog.Builder alerta = new AlertDialog.Builder(ActivityInventarioXfolioComp.this);
            alerta.setMessage(mensaje).setCancelable(false).
                    setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }//onclick
                    });//alertDialogBuilder
            AlertDialog titulo = alerta.create();
            titulo.setTitle("Aviso");
            titulo.show();
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
            soapEnvelope.XMLActOrd(strusr,strpass,producto,strbran,ubicacion,canti);
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

    //Web service trae ubicaciones de un producto
    private class AsyncallUbicaciones extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreExecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            producto=tvClvProdOrden.getText().toString();
            listubicaciones.clear();listcantUbi.clear();
            conectaUbicaciones();
            return null;
        }//doInBackground


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if(listubicaciones.size()>0){
                if(var==true){//se muestran las ubicaciones para cambiarla en el producto de la lista
                    String[] opciones = new String[listubicaciones.size()];
                    for (int i = 0; i < listubicaciones.size(); i++) {
                        opciones[i] = listubicaciones.get(i);
                    }//for
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityInventarioXfolioComp.this);
                    builder.setTitle("SELECCIONE UNA UBICACION");
                    builder.setCancelable(false);
                    builder.setItems(opciones, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            txtUbicacion.setText(opciones[which]);
                            txtCantProdOrd.setText("0");
                            new AsynCallConsulXprod().execute();
                        }//onClick
                    });//setItems
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    });//negative botton
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{//para el traspaso entre ubicaciones
                    alertTraspasoUbi();
                }//else
            }else{
                Toast.makeText(ActivityInventarioXfolioComp.this, mensaje, Toast.LENGTH_SHORT).show();
            }//else

        }//onPostExecute
    }//AsyncallUbicaciones

    private void conectaUbicaciones() {

        String SOAP_ACTION = "UbicacionAlma";
        String METHOD_NAME = "UbicacionAlma";
        String NAMESPACE = "http://" + strServer + "/WSk75AlmacenesApp/";
        String URL = "http://" + strServer + "/WSk75AlmacenesApp";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            XMLUbicacionAlma soapEnvelope = new XMLUbicacionAlma(SoapEnvelope.VER11);
            soapEnvelope.XMLUbicacionAlma(strusr, strpass,producto, strbran);
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
                listubicaciones.add((response0.getPropertyAsString("k_Ubicacion").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Ubicacion")));
                listcantUbi.add((response0.getPropertyAsString("k_Cantidad").equals("anyType{}") ? " " : response0.getPropertyAsString("k_Cantidad")));
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
            mensaje = "Hubó un problema al consultar ubicaciones";
        }//catch
    }//AsynCall

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
                Toast.makeText(ActivityInventarioXfolioComp.this, "Cantidad de ubicacion destino ha sido modificada", Toast.LENGTH_SHORT).show();
                new AsynCallConsulXprod().execute();
            }else{
                Toast.makeText(ActivityInventarioXfolioComp.this, mensaje, Toast.LENGTH_SHORT).show();
            }
        }//onPosteExecute
    }

    private void consultaUbicacionMod(String UbicacionOrigen,String UbicacionDestino,String Producto,String Cantidad) {
        String SOAP_ACTION = "CLArticulo";
        String METHOD_NAME = "CLArticulo";
        String NAMESPACE = "http://"+strServer+ "/WSk75AlmacenesApp/";
        String URL = "http://" +strServer+"/WSk75AlmacenesApp";


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

    //WebService ConsultaxProducto
    private class AsynCallConsulXprod extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            mDialog.show();
        }//onPreejecute

        @Override
        protected Void doInBackground(Void... params) {
            mensaje="";
            producto=listProd.get(posicion).getClaveArt();
            ubicacion=listProd.get(posicion).getUbicacion();
            cantAlm="0";sumAlm="0";sumExist="0";
            conectaConsulXprod();
            return null;
        }//doInBackground


        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {
            mDialog.dismiss();
            if (mensaje.equals("")) {
                mostrarDetalleProd();
            }else {
                if(listProd.size()>0){
                    mostrarDetalleProd();
                }else{
                    Toast.makeText(ActivityInventarioXfolioComp.this, "Hubó un problema al actualizar datos del producto", Toast.LENGTH_SHORT).show();
                }//else
            }//else
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
            soapEnvelope.XMLConsultaPorProd(strusr,strpass,strbran,producto,ubicacion);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response = (SoapObject) soapEnvelope.bodyIn;
            response = (SoapObject) response.getProperty("DatProd");
            listProd.get(posicion).setUbicacion(response.getPropertyAsString("k_ubicacion").equals("anyType{}") ? null : response.getPropertyAsString("k_ubicacion"));
            listProd.get(posicion).setCantidad(response.getPropertyAsString("k_cant").equals("anyType{}") ? null : response.getPropertyAsString("k_cant"));
            sumAlm=(response.getPropertyAsString("k_sumUbi").equals("anyType{}") ? "0" : response.getPropertyAsString("k_sumUbi"));
            sumExist=(response.getPropertyAsString("k_existAlm").equals("anyType{}") ? "0" : response.getPropertyAsString("k_existAlm"));
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
            soapEnvelope.XMLCompromeAlma(strusr, strpass, producto, strbran);
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
