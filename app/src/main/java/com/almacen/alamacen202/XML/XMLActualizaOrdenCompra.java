package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLActualizaOrdenCompra extends SoapSerializationEnvelope {

    private String usuario     ="";
    private String contrasena  ="";
    private String sucursal    ="";
    private String producto    ="";
    private String ubicacion   ="";
    private String cant        ="";

    public XMLActualizaOrdenCompra(int version) {
        super(version);
    }

    public void XMLActOrd(String usuario, String contrasena,
                          String producto,String sucursal,
                          String ubicacion,String cant) {
        this.usuario    = usuario;
        this.contrasena = contrasena;
        this.sucursal   = sucursal;
        this.producto   = producto;
        this.ubicacion  = ubicacion;
        this.cant       = cant;
    }//public void XMLConsultaOrdenCompra

    @Override
    public void write(XmlSerializer writer) throws IOException {
        env = "http://schemas.xmlsoap.org/soap/envelope/";
        String tem = "";
        writer.startDocument("UTF-8", true);
        writer.setPrefix("soap", env);
        writer.setPrefix("", tem);
        writer.startTag(env, "Envelope");
        writer.startTag(env, "Body");
        writer.startTag(tem, "ActualizaCantRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(contrasena);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "ActCant");
        writer.startTag(tem, "k_prod");
        writer.text(producto);
        writer.endTag(tem, "k_prod");

        writer.startTag(tem, "k_suc");
        writer.text(sucursal);
        writer.endTag(tem, "k_suc");

        writer.startTag(tem, "k_ubic");
        writer.text(ubicacion);
        writer.endTag(tem, "k_ubic");

        writer.startTag(tem, "k_cant");
        writer.text(cant);
        writer.endTag(tem, "k_cant");

        writer.endTag(tem, "ActCant");


        writer.endTag(tem, "ActualizaCantRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }//vrite
}
