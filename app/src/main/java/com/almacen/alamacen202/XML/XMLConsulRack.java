package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLConsulRack extends SoapSerializationEnvelope {

    private String usuario;
    private String contrasena;
    private String sucursal;
    private String producto;

    public XMLConsulRack(int version) {
        super(version);
    }

    public void XMLConsulRack(String usuario, String contrasena, String sucursal, String producto) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.sucursal = sucursal;
        this.producto = producto;
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
        writer.startTag(tem, "ConsulRackRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(contrasena);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "ConsulRackk");

        writer.startTag(tem, "k_suc");
        writer.text(sucursal);
        writer.endTag(tem, "k_suc");

        writer.startTag(tem, "k_prod");
        writer.text(producto);
        writer.endTag(tem, "k_prod");

        writer.endTag(tem, "ConsulRackk");


        writer.endTag(tem, "ConsulRackRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }//vrite
}
