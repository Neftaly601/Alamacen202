package com.almacen.alamacen202.Imprecion;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.FontRequest;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.almacen.alamacen202.ActivityMenu;
import com.almacen.alamacen202.ActivitySplash;
import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.CAJASSANDG;
import com.almacen.alamacen202.SetterandGetters.ListProAduSandG;
import com.almacen.alamacen202.SetterandGetters.ListProReceSandG;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import okio.Options;


public class BluetoothPrint extends AppCompatActivity {

    Context context;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;

    OutputStream outputStream;
    InputStream inputStream;
    Thread thread;
    Resources resources;


    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;

    public static final byte[] ESC_ALIGN_LEFT = new byte[]{0x1b, 'a', 0x00};
    public static final byte[] ESC_ALIGN_RIGHT = new byte[]{0x1b, 'a', 0x02};
    public static final byte[] ESC_ALIGN_CENTER = new byte[]{0x1b, 'a', 0x01};
    public static final byte[] ESC_CANCEL_BOLD = new byte[]{0x1B, 0x45, 0};
    public static final byte[] PRINTE_TEST = new byte[]{0x1D, 0x28, 0x41};
    public static byte[] SELECT_FONT_A = {20, 33, 0};


    public static byte[] format = {27, 33, 0};
    public static byte[] arrayOfByte1 = {27, 33, 0};


    public BluetoothPrint(Context context, Resources resources) {
        this.context = context;
        this.resources = resources;


    }

    public void FindBluetoothDevice(String Impresora) {


        try {

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                //  lblPrinterName.setText("No Bluetooth Adapter found");
            }
            if (bluetoothAdapter.isEnabled()) {
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivity(enableBT);
            }

            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();

            if (pairedDevice.size() > 0) {
                for (BluetoothDevice pairedDev : pairedDevice) {
                    //Toast.makeText(context,pairedDev.getName().toString(),Toast.LENGTH_LONG).show();
                    // My Bluetoth printer name is BTP_F09F1A
                    Log.e("device: ", pairedDev.getName());
                    if (pairedDev.getName().equals(Impresora)) {
                        bluetoothDevice = pairedDev;
                        //Toast.makeText(context,"Bluetooth Conectado Correctamente",Toast.LENGTH_LONG).show();
                        //lblPrinterName.setText("Bluetooth Printer Attached: "+pairedDev.getName());
                        break;
                    }
                }
                if (bluetoothDevice == null) {
                    Toast.makeText(context, "No fue posible conectar con la impresora intente de nuevo", Toast.LENGTH_LONG).show();
                }
            }

            //lblPrinterName.setText("Bluetooth Printer Attached");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Open Bluetooth Printer

    public void openBluetoothPrinter() {
        try {

            //Standard uuid from string //
            UUID uuidSting = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuidSting);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            inputStream = bluetoothSocket.getInputStream();

            beginListenData();

        } catch (Exception ex) {

        }
    }

    public boolean checkConnection() {
        if (bluetoothSocket != null) {
            if (bluetoothSocket.isConnected()) {
                Toast.makeText(context, "IMPRIMIENDO", Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }

    public void beginListenData() {
        try {

            final Handler handler = new Handler();
            final byte delimiter = 10;
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            thread = new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                        try {
                            int byteAvailable = inputStream.available();
                            if (byteAvailable > 0) {
                                byte[] packetByte = new byte[byteAvailable];
                                inputStream.read(packetByte);

                                for (int i = 0; i < byteAvailable; i++) {
                                    byte b = packetByte[i];
                                    if (b == delimiter) {
                                        byte[] encodedByte = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedByte, 0,
                                                encodedByte.length
                                        );
                                        final String data = new String(encodedByte, "US-ASCII");
                                        readBufferPosition = 0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                //lblPrinterName.setText(data);
                                            }
                                        });
                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            stopWorker = true;
                        }
                    }

                }
            });

            thread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Printing Text to Bluetooth Printer //
    public void printProductos(String Empresa, String Cliente, String Folio, String viaEmbarque, ArrayList<ListProAduSandG> listaProduAduana ,int imagen) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        String formattedHour = hora.format(c);

        try {
            /*outputStream.flush();
            printPhoto(imagen);
            outputStream.flush();*/

            String msg = "\n";
            msg += Empresa;
            msg += "\n";
            outputStream.write(format);
            outputStream.write(ESC_ALIGN_CENTER);
            outputStream.write(msg.getBytes());
            outputStream.flush();

            msg = "Fecha:" + formattedDate + "\n";
            msg += "Hora:" + formattedHour + "\n";
            outputStream.write(ESC_ALIGN_RIGHT);
            outputStream.write(msg.getBytes());
            outputStream.flush();

            msg = "CLIENTE:" + Cliente + "\n";
            msg += "FOLIO:" + Folio + "\n";
            msg += "ViaEmbarque:" + viaEmbarque + "\n";
            msg += "\n";
            outputStream.write(ESC_ALIGN_LEFT);
            outputStream.write(msg.getBytes());
            outputStream.flush();

            msg = "Ticket Validacion de Surtido \n";
            msg += "____________________________ \n";
            msg += "PRODUCTO    SURTIDA   ESTADO\n";
            outputStream.write(msg.getBytes());
            outputStream.flush();


            for (int i = 0; i < listaProduAduana.size(); i++) {
                msg = "";
                String Producto = listaProduAduana.get(i).getProducto();
                String Cantidad = " " + listaProduAduana.get(i).getCantidadSurtida();
                if (Producto.length() < 13) {
                    int espacios = Producto.length();
                    int opera = 0;
                    opera = 13 - espacios;

                    for (int k = 0; k < opera; k++) {
                        Producto += " ";
                    }
                }

                if (Cantidad.length() < 5) {

                    int espacios = Cantidad.length();
                    int opera = 0;
                    opera = 9 - espacios;

                    for (int k = 0; k < opera; k++) {

                        Cantidad += " ";

                    }
                }
                String Check = "";

                if (Integer.parseInt(listaProduAduana.get(i).getCantidad()) == Integer.parseInt(listaProduAduana.get(i).getCantidadSurtida())) {
                    Check = "Completo";
                } else {
                    Check = "Incompleto";
                }

                msg += Producto + Cantidad + Check + "\n";

                outputStream.write(msg.getBytes());
                outputStream.flush();
            }


            msg = "";
            int cantidatotal = 0;
            for (int k = 0; k < listaProduAduana.size(); k++) {

                cantidatotal = cantidatotal + Integer.parseInt(listaProduAduana.get(k).getCantidadSurtida());

            }
            msg += "\n";
            outputStream.write(msg.getBytes());
            outputStream.flush();
            msg = "Total:" + cantidatotal + "\n";
            outputStream.flush();
            outputStream.write(ESC_ALIGN_RIGHT);
            outputStream.write(msg.getBytes());
            outputStream.flush();
            msg = "\n";
            msg += "\n";
            outputStream.write(msg.getBytes());
            outputStream.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "" + ex, Toast.LENGTH_LONG).show();
        }


    }

    public void printCajas(String Empresa, String Cliente, String Folio, String viaEmbarque, ArrayList<CAJASSANDG> listaCajasFiltro, String contDialogCajas,int imagen) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        String formattedHour = hora.format(c);


        try {
            /*outputStream.flush();
            printPhoto(imagen);
            outputStream.flush();*/
            String msg = Empresa;
            msg += "\n";
            outputStream.write(format);
            outputStream.write(ESC_ALIGN_CENTER);
            outputStream.write(msg.getBytes());
            msg = "Fecha:" + formattedDate + "\n";
            msg += "Hora:" + formattedHour + "\n";
            outputStream.write(ESC_ALIGN_RIGHT);
            outputStream.write(msg.getBytes());
            msg = "CLIENTE:" + Cliente + "\n";
            msg += "FOLIO:" + Folio + "\n";
            msg += "ViaEmbarque:" + viaEmbarque + "\n";
            msg += "\n";
            outputStream.write(ESC_ALIGN_LEFT);
            outputStream.write(msg.getBytes());
            msg = "Ticket Caja " + contDialogCajas + "\n";
            msg += "____________________________ \n";
            msg += "PRODUCTO    SURTIDA   CAJA\n";
            outputStream.write(msg.getBytes());


            for (int i = 0; i < listaCajasFiltro.size(); i++) {
                msg = "";
                String Producto = listaCajasFiltro.get(i).getClavedelProdcuto();
                String Cantidad = " " + listaCajasFiltro.get(i).getCantidadUnidades();
                String Caja = listaCajasFiltro.get(i).getNumCajas();
                if (Producto.length() < 13) {
                    int espacios = Producto.length();
                    int opera = 0;
                    opera = 13 - espacios;

                    for (int k = 0; k < opera; k++) {
                        Producto += " ";
                    }
                }

                if (Cantidad.length() < 10) {

                    int espacios = Cantidad.length();
                    int opera = 0;
                    opera = 10 - espacios;

                    for (int k = 0; k < opera; k++) {

                        Cantidad += " ";

                    }
                }


                msg += Producto + Cantidad + Caja + "\n";
                outputStream.write(msg.getBytes());
                outputStream.flush();
            }

            msg = "\n";
            int cantidatotal = 0;
            for (int k = 0; k < listaCajasFiltro.size(); k++) {

                cantidatotal = cantidatotal + Integer.parseInt(listaCajasFiltro.get(k).getCantidadUnidades());
            }

            msg += "Total:" + cantidatotal + "\n";
            outputStream.write(ESC_ALIGN_RIGHT);
            outputStream.write(msg.getBytes());
            msg = "\n";
            msg += "\n";
            outputStream.write(msg.getBytes());

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "" + ex, Toast.LENGTH_LONG).show();
        }
    }



    // Printing Text to Bluetooth Printer //
    public void printRece(String Empresa, String Provedor, String Folio, ArrayList<ListProReceSandG> listaRecepcion , int imagen) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        SimpleDateFormat hora = new SimpleDateFormat("HH:mm");
        String formattedHour = hora.format(c);

        try {
            /*outputStream.flush();
            printPhoto(imagen);
            outputStream.flush();*/

            String msg = "\n";
            msg += Empresa;
            msg += "\n";
            outputStream.write(format);
            outputStream.write(ESC_ALIGN_CENTER);
            outputStream.write(msg.getBytes());
            outputStream.flush();
            msg = "Fecha:" + formattedDate + "\n";
            msg += "Hora:" + formattedHour + "\n";
            outputStream.write(ESC_ALIGN_RIGHT);
            outputStream.write(msg.getBytes());
            outputStream.flush();
            msg += "FOLIO:" + Folio + "\n";
            msg =   "Provedor:" + Provedor + "\n";
            msg += "\n";
            outputStream.write(ESC_ALIGN_LEFT);
            outputStream.write(msg.getBytes());
            outputStream.flush();

            msg = "Ticket De Orden de Compra\n";
            msg += "____________________________ \n";
            msg += "PRODUCTO      UBICACION\n";
            outputStream.write(msg.getBytes());
            outputStream.flush();


            for (int i = 0; i < listaRecepcion.size(); i++) {
                msg = "";
                String Producto = listaRecepcion.get(i).getProducto();
                String Ubicacion = " " + listaRecepcion.get(i).getUbicacion();
                if (Producto.length() < 13) {
                    int espacios = Producto.length();
                    int opera = 0;
                    opera = 13 - espacios;

                    for (int k = 0; k < opera; k++) {
                        Producto += " ";
                    }
                }

                if (Ubicacion.length() < 10) {

                    int espacios = Ubicacion.length();
                    int opera = 0;
                    opera = 9 - espacios;

                    for (int k = 0; k < opera; k++) {

                        Ubicacion += " ";

                    }
                }
                msg += Producto + Ubicacion + "\n";

                outputStream.write(msg.getBytes());
                outputStream.flush();
            }

            msg = "\n";
            msg += "\n";
            outputStream.write(msg.getBytes());
            outputStream.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "" + ex, Toast.LENGTH_LONG).show();
        }


    }



    // Disconnect Printer //
    public void disconnectBT() {
        try {
            stopWorker = true;
            outputStream.close();
            inputStream.close();
            bluetoothSocket.close();
            //lblPrinterName.setText("Printer Disconnected.");
        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public void printPhoto(int img) {
        try {
            Bitmap bmp = BitmapFactory.decodeResource(resources,
                    img);

            if (bmp != null) {
                byte[] command = Utils.decodeBitmap(bmp);
                outputStream.write(command);
                outputStream.flush();

            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
            Toast.makeText(context, "Ahora estoy aca en imagen" + e, Toast.LENGTH_LONG).show();
        }
    }

}
