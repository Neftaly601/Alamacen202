package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLConsultaXprod extends SoapSerializationEnvelope {

    private String usuario;
    private String contrasena;
    private String sucursal;
    private String producto;
    private String ubicacion;

    public XMLConsultaXprod(int version) {
        super(version);
    }

    public void XMLConsultaPorProd(String usuario, String contrasena,
                                  String sucursal, String producto,String ubicacion) {
        this.usuario    = usuario;
        this.contrasena = contrasena;
        this.sucursal   = sucursal;
        this.producto   = producto;
        this.ubicacion  = ubicacion;
    }//public void XMLConsultaPorProd

    @Override
    public void write(XmlSerializer writer) throws IOException {
        env = "http://schemas.xmlsoap.org/soap/envelope/";
        String tem = "";
        writer.startDocument("UTF-8", true);
        writer.setPrefix("soap", env);
        writer.setPrefix("", tem);
        writer.startTag(env, "Envelope");
        writer.startTag(env, "Body");
        writer.startTag(tem, "ConsulXprodRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(contrasena);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "ConsulXP");
        writer.startTag(tem, "k_suc");
        writer.text(sucursal);
        writer.endTag(tem, "k_suc");

        writer.startTag(tem, "k_prod");
        writer.text(producto);
        writer.endTag(tem, "k_prod");

        writer.startTag(tem, "k_ubic");
        writer.text(ubicacion);
        writer.endTag(tem, "k_ubic");

        writer.endTag(tem, "ConsulXP");


        writer.endTag(tem, "ConsulXprodRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }//vrite
}
