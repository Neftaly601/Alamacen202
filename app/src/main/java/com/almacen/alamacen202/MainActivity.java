package com.almacen.alamacen202;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.almacen.alamacen202.Activity.ActivityConsultaPA;
import com.almacen.alamacen202.SetterandGetters.Login;
import com.almacen.alamacen202.XML.xmlLog;
import com.almacen.alamacen202.XML.xmlLogin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Vector;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {
    Login loginSave = new Login();
    private Button btn1;
    private EditText usu;
    private EditText clave;
    int result1 = 0;
    String getUsuario = "", getPass = "", mensaje = "";
    String bien;
    SoapObject response;
    Spinner SERVER;
    AlertDialog mDialog;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    String StrServer = "";
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDialog = new SpotsDialog.Builder().setContext(MainActivity.this).setMessage("Espere un momento...").build();
        SERVER = (Spinner) findViewById(R.id.spinnerserver);
        usu = (EditText) findViewById(R.id.txtinUsu);
        clave = (EditText) findViewById(R.id.txtinCla);

        btn1 = (Button) findViewById(R.id.btnbuscar);
        preference = getSharedPreferences("Login", Context.MODE_PRIVATE);
        editor = preference.edit();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    getUsuario = usu.getText().toString();
                    getPass = clave.getText().toString();
                    /*  token=generateRandomText();*/
                    if (!getUsuario.isEmpty() && !getPass.isEmpty() && SERVER.getSelectedItemPosition() != 0) {
                        if (SERVER.getSelectedItemPosition() == 1) {
                            StrServer = "jacve.dyndns.org:9085";
                            Toast.makeText(MainActivity.this, "Entrando a JACVE", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 2) {
                            StrServer = "sprautomotive.servehttp.com:9085";
                            Toast.makeText(MainActivity.this, "Entrando a VIPLA", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 3) {
                            StrServer = "cecra.ath.cx:9085";
                            Toast.makeText(MainActivity.this, "Entrando a CECRA", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 4) {
                            StrServer = "guvi.ath.cx:9085";
                            Toast.makeText(MainActivity.this, "Entrando a GUVI", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 5) {
                            StrServer = "cedistabasco.ddns.net:9085";
                            Toast.makeText(MainActivity.this, "Entrando a PRESSA", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 6) {
                            StrServer = "autodis.ath.cx:9085";
                            Toast.makeText(MainActivity.this, "Entrando a AUTODIS", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 7) {
                            StrServer = "sprautomotive.servehttp.com:9090";
                            Toast.makeText(MainActivity.this, "Entrando a RODATECH ", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 8) {
                            StrServer = "sprautomotive.servehttp.com:9095";
                            Toast.makeText(MainActivity.this, "Entrando a PARTECH ", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 9) {
                            StrServer = "sprautomotive.servehttp.com:9080";
                            Toast.makeText(MainActivity.this, "Entrando a SHARK", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 10) {
                            StrServer = "vazlocolombia.dyndns.org:9085";
                            Toast.makeText(MainActivity.this, "Entrando a Colombia", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 11) {
                            StrServer = "sprautomotive.servehttp.com:9075";
                            Toast.makeText(MainActivity.this, "Iniciando Pruebas", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 12) {
                            StrServer = "192.168.1.72:9085";
                            Toast.makeText(MainActivity.this, "Iniciando Pruebas", Toast.LENGTH_SHORT).show();
                        } else if (SERVER.getSelectedItemPosition() == 13) {
                            StrServer = "soasem.is-by.us:9085";
                            Toast.makeText(MainActivity.this, "Iniciando Pruebas", Toast.LENGTH_SHORT).show();
                        }

                        id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
                        AsyncCallWS task = new AsyncCallWS();
                        task.execute();
                    } else {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                        alerta.setMessage("Ingrese los datos Faltantes").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                        AlertDialog titulo = alerta.create();
                        titulo.setTitle("Faltan Datos");
                        titulo.show();
                    }

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
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


            }
        });

        String[] opciones1 = {"--Seleccione--", "JACVE", "VIPLA", "CECRA", "GUVI", "PRESSA", "AUTODIS", "RODATECH ", "PARTECH", "SHARK", "COLOMBIA", "PRUEBAS SPR", "PRUEBAS", "PRUEBAS EX"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, opciones1);
        SERVER.setAdapter(adapter1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (preference.contains("user") && preference.contains("pass")) {

            Toast.makeText(this, "Entraste", Toast.LENGTH_SHORT).show();
            Intent perfil = new Intent(this, ActivitySplash.class);
            startActivity(perfil);
            finish();
        }

    }


    private void guardarDatos() {
        editor.putString("user", getUsuario);
        editor.putString("pass", getPass);
        editor.putString("name", loginSave.getName());
        editor.putString("lname", loginSave.getLastname());
        editor.putString("type", loginSave.getType());
        editor.putString("branch", loginSave.getBranch());
        editor.putString("email", loginSave.getEmail());
        editor.putString("codBra", loginSave.getCodeBranch());
        editor.putString("Server", StrServer);
        editor.putString("codeBar","Zebra");
        editor.putString("type2", null);
        //editor.putString("tokenId",token);
        editor.commit();
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mDialog.show();

        }

        @Override
        protected Void doInBackground(Void... params) {
            Conectar();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if (result1 == 0) {
                mDialog.dismiss();
                Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_LONG).show();

            } else if (result1 == 1) {
                String tipo = loginSave.getType();
                if (tipo.equals("ALMACEN") || tipo.equals("SUPERVISOR") ) {
                    mDialog.dismiss();
                    AsyncCallWS2 task2 = new AsyncCallWS2();
                    task2.execute();
                    trasactiv();

                } else {
                    AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                    alerta.setMessage("ESTE USUARIO NO ES PARTE DEL ALMACEN").setCancelable(false).setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            mDialog.dismiss();
                        }
                    });

                    AlertDialog titulo = alerta.create();
                    titulo.setTitle("USUARIO ERRONEO");
                    titulo.show();
                }
            }

        }

    }


    private void Conectar() {

        String SOAP_ACTION = "login";
        String METHOD_NAME = "login";
        String NAMESPACE = "http://" + StrServer + "/WSlogin/";
        String URL = "http://" + StrServer + "/WSlogin";

        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlLogin soapEnvelope = new xmlLogin(SoapEnvelope.VER11);
            soapEnvelope.valoresLogin(getUsuario, getPass);
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.call(SOAP_ACTION, soapEnvelope);
            Vector response0 = (Vector) soapEnvelope.getResponse();
            result1 = Integer.parseInt(response0.get(0).toString());

            if (response0 == null) {
                mensaje = "Null";

            } else {

                if (result1 == 0) {
                    mensaje = "Contraseña y/o Usuario Inconrrecto";
                } else if (result1 == 1) {
                    mensaje = "Correcto";
                    response = (SoapObject) soapEnvelope.bodyIn;
                    response = (SoapObject) response.getProperty("UserInfo");
                    loginSave = new Login();
                    loginSave.setUsers(response.getPropertyAsString("k_usr"));
                    loginSave.setName(response.getPropertyAsString("k_name"));
                    loginSave.setLastname(response.getPropertyAsString("k_lname"));
                    loginSave.setType(response.getPropertyAsString("k_type"));
                    loginSave.setBranch(response.getPropertyAsString("k_dscr"));
                    loginSave.setEmail(response.getPropertyAsString("k_mail1"));
                    loginSave.setCodeBranch(response.getPropertyAsString("k_codB"));

                }

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
            mensaje = e.getMessage();
            e.printStackTrace();
        } catch (Exception ex) {
            mDialog.dismiss();
            mensaje = "Error:" + ex.getMessage();
        }

    }

    private void trasactiv() {
        guardarDatos();
        Intent perfil = new Intent(this, ActivitySplash.class);
        startActivity(perfil);
        finish();


    }


    private class AsyncCallWS2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            conectar2();
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        protected void onPostExecute(Void result) {


        }


    }

    private void conectar2() {
        String SOAP_ACTION = "LogAppUs";
        String METHOD_NAME = "LogAppUs";
        String NAMESPACE = "http://" + StrServer + "/WSk75Branch/";
        String URL = "http://" + StrServer + "/WSk75Branch";


        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            xmlLog soapEnvelope = new xmlLog(SoapEnvelope.VER11);
            soapEnvelope.xmlLog(getUsuario, getPass, id, "LOG IN", "");
            soapEnvelope.dotNet = true;
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE trasport = new HttpTransportSE(URL);
            trasport.debug = true;
            trasport.call(SOAP_ACTION, soapEnvelope);
            SoapObject response0 = (SoapObject) soapEnvelope.bodyIn;


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


}