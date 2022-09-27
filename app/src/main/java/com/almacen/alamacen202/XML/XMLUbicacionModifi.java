package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLUbicacionModifi extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Producto = "";
    String Sucursal = "";
    String Ubicacion = "";


    public XMLUbicacionModifi(int version) {
        super(version);
    }

    public void XMLUbicacionModifi(String usuario, String clave, String Producto ,String Sucursal,String Ubicacion) {
        this.usuario = usuario;
        this.clave = clave;
        this.Producto = Producto;
        this.Sucursal = Sucursal;
        this.Ubicacion =Ubicacion;

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
        writer.startTag(tem, "UbicacionModifiRequest");


        writer.startTag(tem, "Login");

        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");

        writer.endTag(tem, "Login");


        writer.startTag(tem, "UbicacionModifica");

        writer.startTag(tem, "k_Productos");
        writer.text(Producto);
        writer.endTag(tem, "k_Productos");

        writer.startTag(tem, "k_Sucursal");
        writer.text(Sucursal);
        writer.endTag(tem, "k_Sucursal");


        writer.startTag(tem, "k_Ubicacion");
        writer.text(Ubicacion);
        writer.endTag(tem, "k_Ubicacion");

        writer.endTag(tem, "UbicacionModifica");


        writer.endTag(tem, "UbicacionModifiRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}
