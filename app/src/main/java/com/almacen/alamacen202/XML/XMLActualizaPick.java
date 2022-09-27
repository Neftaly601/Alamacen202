package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLActualizaPick extends SoapSerializationEnvelope {

    String usuario;
    String contrasena;
    String sucursal;
    String producto;
    String fechaR;
    String horaR;
    String fechaT;
    String horaT;

    public XMLActualizaPick(int version) {
        super(version);
    }

    public void XMLActualizaPicking(String usuario, String contrasena, String sucursal,
                                    String producto, String fechaR, String horaR, String fechaT, String horaT) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.sucursal = sucursal;
        this.producto = producto;
        this.fechaR = fechaR;
        this.horaR = horaR;
        this.fechaT = fechaT;
        this.horaT = horaT;
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
        writer.startTag(tem, "ActualizaPickRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(contrasena);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "ActualizaPicking");

        writer.startTag(tem, "k_suc");
        writer.text(sucursal);
        writer.endTag(tem, "k_suc");

        writer.startTag(tem, "k_prod");
        writer.text(producto);
        writer.endTag(tem, "k_prod");

        writer.startTag(tem, "k_fechaR");
        writer.text(fechaR);
        writer.endTag(tem, "k_fechaR");


        writer.startTag(tem, "k_horaR");
        writer.text(horaR);
        writer.endTag(tem, "k_horaR");

        writer.startTag(tem, "k_fechaT");
        writer.text(fechaT);
        writer.endTag(tem, "k_fechaT");


        writer.startTag(tem, "k_horaT");
        writer.text(horaT);
        writer.endTag(tem, "k_horaT");

        writer.startTag(tem, "k_usu");
        writer.text(usuario);
        writer.endTag(tem, "k_usu");


        writer.endTag(tem, "ActualizaPicking");


        writer.endTag(tem, "ActualizaPickRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}

