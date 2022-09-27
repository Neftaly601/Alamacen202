package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLCLArticulo extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String UbicacionOri = "";
    String UbicacionDest = "";
    String Sucursal = "";
    String Producto = "";
    String Cantidad = "";

    public XMLCLArticulo(int version) {
        super(version);
    }

    public void XMLCLArticulo(String usuario, String clave, String UbicacionOri, String UbicacionDest, String Sucursal, String Producto, String Cantidad) {
        this.usuario = usuario;
        this.clave = clave;
        this.UbicacionOri = UbicacionOri;
        this.UbicacionDest = UbicacionDest;
        this.Sucursal = Sucursal;
        this.Producto = Producto;
        this.Cantidad = Cantidad;


    }


    @Override
    public void write(XmlSerializer writer) throws IOException {
        env = "http://schemas.xmlsoap.org/soap/envelope/";
        String tem = "";
        writer.startDocument("UTF-8", true);
        writer.setPrefix("soap", env);
        writer.setPrefix("", tem);
        writer.startTag(env, "Envelope");
        writer.startTag(env, "Body");
        writer.startTag(tem, "CLArticuloRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "CLArticulos");

        writer.startTag(tem, "k_UbicacionOri");
        writer.text(UbicacionOri);
        writer.endTag(tem, "k_UbicacionOri");

        writer.startTag(tem, "k_UbicacionDest");
        writer.text(UbicacionDest);
        writer.endTag(tem, "k_UbicacionDest");

        writer.startTag(tem, "k_Sucursal");
        writer.text(Sucursal);
        writer.endTag(tem, "k_Sucursal");


        writer.startTag(tem, "k_Producto");
        writer.text(Producto);
        writer.endTag(tem, "k_Producto");

        writer.startTag(tem, "k_Cantidad");
        writer.text(Cantidad);
        writer.endTag(tem, "k_Cantidad");


        writer.startTag(tem, "k_Usuario");
        writer.text(usuario);
        writer.endTag(tem, "k_Usuario");


        writer.endTag(tem, "CLArticulos");


        writer.endTag(tem, "CLArticuloRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}

