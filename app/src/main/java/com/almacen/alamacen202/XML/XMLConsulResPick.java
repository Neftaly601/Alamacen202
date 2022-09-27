package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLConsulResPick extends SoapSerializationEnvelope {

    private String usuario;
    private String contrasena;
    private String sucursal;
    private String fecha;
    private String hora;
    private String ord;

    public XMLConsulResPick(int version) {
        super(version);
    }

    public void XMLConsulResPick(String usuario, String contrasena,
                                  String sucursal,String fecha, String hora, String ord) {
        this.usuario    = usuario;
        this.contrasena = contrasena;
        this.sucursal   = sucursal;
        this.fecha      = fecha;
        this.hora       = hora;
        this.ord        = ord;
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
        writer.startTag(tem, "ConsulResPickRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(contrasena);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "ConsulResPicking");
        writer.startTag(tem, "k_suc");
        writer.text(sucursal);
        writer.endTag(tem, "k_suc");

        writer.startTag(tem, "k_fecha");
        writer.text(fecha);
        writer.endTag(tem, "k_fecha");

        writer.startTag(tem, "k_hora");
        writer.text(hora);
        writer.endTag(tem, "k_hora");

        writer.startTag(tem, "k_tipOrd");
        writer.text(ord);
        writer.endTag(tem, "k_tipOrd");

        writer.endTag(tem, "ConsulResPicking");


        writer.endTag(tem, "ConsulResPickRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }//vrite
}
