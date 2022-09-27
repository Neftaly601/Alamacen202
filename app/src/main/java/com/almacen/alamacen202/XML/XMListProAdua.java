package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMListProAdua  extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Sucursal = "";
    String Folio = "";
    String Filtro = "";
    String Filtro2 = "";


    public XMListProAdua(int version) {
        super(version);
    }

    public void XMListProAdua(String usuario, String clave, String Sucursal ,String Folio , String Filtro ,String Filtro2) {
        this.usuario = usuario;
        this.clave = clave;
        this.Sucursal = Sucursal;
        this.Folio = Folio;
        this.Filtro = Filtro;
        this.Filtro2 = Filtro2;

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
        writer.startTag(tem, "ListProAduRequest");


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


        writer.startTag(tem, "k_Filtro");
        writer.text(Filtro);
        writer.endTag(tem, "k_Filtro");


        writer.startTag(tem, "k_Filtro2");
        writer.text(Filtro2);
        writer.endTag(tem, "k_Filtro2");


        writer.endTag(tem, "Productos");


        writer.endTag(tem, "ListProAduRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}