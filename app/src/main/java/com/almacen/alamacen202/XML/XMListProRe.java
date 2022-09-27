package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMListProRe extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Sucursal = "";
    String Folio = "";
    String Provedor = "";


    public XMListProRe(int version) {
        super(version);
    }

    public void XMListProRe(String usuario, String clave, String Sucursal, String Folio, String Provedor) {
        this.usuario = usuario;
        this.clave = clave;
        this.Sucursal = Sucursal;
        this.Folio = Folio;
        this.Provedor = Provedor;
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
        writer.startTag(tem, "ListProReRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "Productos");
        writer.startTag(tem, "k_Sucursal");
        writer.text(Sucursal);
        writer.endTag(tem, "k_Sucursal");


        writer.startTag(tem, "k_Folio");
        writer.text(Folio);
        writer.endTag(tem, "k_Folio");


        writer.startTag(tem, "k_Provedor");
        writer.text(Provedor);
        writer.endTag(tem, "k_Provedor");


        writer.endTag(tem, "Productos");


        writer.endTag(tem, "ListProReRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}