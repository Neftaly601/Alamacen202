package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLActualizaInv extends SoapSerializationEnvelope {

    private String usuario;
    private String contrasena;
    private String folio;
    private String sucursal;
    private String producto;
    private String cant;

    public XMLActualizaInv(int version) {
        super(version);
    }

    public void XMLActInv(String usuario, String contrasena, String folio, String sucursal,
                                  String producto, String cant) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.folio = folio;
        this.sucursal = sucursal;
        this.producto = producto;
        this.cant = cant;
    }//void

    @Override
    public void write(XmlSerializer writer) throws IOException {
        env = "http://schemas.xmlsoap.org/soap/envelope/";
        String tem = "";
        writer.startDocument("UTF-8", true);
        writer.setPrefix("soap", env);
        writer.setPrefix("", tem);
        writer.startTag(env, "Envelope");
        writer.startTag(env, "Body");
        writer.startTag(tem, "ActualizaInvRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(contrasena);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "ActualizaInve");
        writer.startTag(tem, "k_folio");
        writer.text(folio);
        writer.endTag(tem, "k_folio");

        writer.startTag(tem, "k_suc");
        writer.text(sucursal);
        writer.endTag(tem, "k_suc");

        writer.startTag(tem, "k_prod");
        writer.text(producto);
        writer.endTag(tem, "k_prod");

        writer.startTag(tem, "k_cant");
        writer.text(cant);
        writer.endTag(tem, "k_cant");

        writer.startTag(tem, "k_usu");
        writer.text(usuario);
        writer.endTag(tem, "k_usu");

        writer.endTag(tem, "ActualizaInve");


        writer.endTag(tem, "ActualizaInvRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }//vrite
}
