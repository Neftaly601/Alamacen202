package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLlistInv extends SoapSerializationEnvelope {

    private String usuario;
    private String contrasena;
    private String folio;
    private String suc;

    public XMLlistInv(int version) {
        super(version);
    }

    public void XMLLI(String usuario, String contrasena, String folio, String suc) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.folio = folio;
        this.suc = suc;
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
        writer.startTag(tem, "ListInvRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(contrasena);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "ListInve");

        writer.startTag(tem, "k_folio");
        writer.text(folio);
        writer.endTag(tem, "k_folio");

        writer.startTag(tem, "k_suc");
        writer.text(suc);
        writer.endTag(tem, "k_suc");

        writer.endTag(tem, "ListInve");

        writer.endTag(tem, "ListInvRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }//vrite
}
