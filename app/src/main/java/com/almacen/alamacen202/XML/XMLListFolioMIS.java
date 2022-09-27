package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLListFolioMIS extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Sucursal="";

    public XMLListFolioMIS(int version) {
        super(version);
    }

    public void XMLListFolioMIS(String usuario, String clave, String Sucursal) {
        this.usuario = usuario;
        this.clave = clave;
        this.Sucursal = Sucursal;
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
        writer.startTag(tem, "ListFLNERequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "MISFO");
        writer.startTag(tem, "k_Usuario");
        writer.text(usuario);
        writer.endTag(tem, "k_Usuario");

        writer.startTag(tem, "k_Sucursal");
        writer.text(Sucursal);
        writer.endTag(tem, "k_Sucursal");

        writer.endTag(tem, "MISFO");


        writer.endTag(tem, "ListFLNERequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}
