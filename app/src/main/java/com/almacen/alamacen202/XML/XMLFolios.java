package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLFolios extends SoapSerializationEnvelope {

    private String usuario;
    private String contrasena;
    private String cerrar;
    private String folio;
    private String suc;

    public XMLFolios(int version) {
        super(version);
    }

    public void XMLFol(String usuario, String contrasena,String cerrar, String folio, String suc) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.cerrar = cerrar;
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
        writer.startTag(tem, "FoliosRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(contrasena);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "Folio");

        writer.startTag(tem, "k_cierra");
        writer.text(cerrar);
        writer.endTag(tem, "k_cierra");

        writer.startTag(tem, "k_folio");
        writer.text(folio);
        writer.endTag(tem, "k_folio");

        writer.startTag(tem, "k_suc");
        writer.text(suc);
        writer.endTag(tem, "k_suc");

        writer.endTag(tem, "Folio");


        writer.endTag(tem, "FoliosRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }//vrite
}
