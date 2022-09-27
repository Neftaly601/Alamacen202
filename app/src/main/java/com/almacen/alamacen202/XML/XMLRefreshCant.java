package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLRefreshCant extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String folio = "";
    String pariedad = "";
    String producto = "";

    public XMLRefreshCant(int version) {
        super(version);
    }

    public void XMLRefreshCantidad(String usuario, String clave, String folio,String producto,String pariedad) {
        this.usuario = usuario;
        this.clave = clave;
        this.folio = folio;
        this.pariedad = pariedad;
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
        writer.startTag(tem, "RefreshCantRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "RefreshCantidad");
        writer.startTag(tem, "k_Folio");
        writer.text(folio);
        writer.endTag(tem, "k_Folio");

        writer.startTag(tem, "k_Producto");
        writer.text(producto);
        writer.endTag(tem, "k_Producto");

        writer.startTag(tem, "k_Pariedad");
        writer.text(pariedad);
        writer.endTag(tem, "k_Pariedad");


        writer.endTag(tem, "RefreshCantidad");


        writer.endTag(tem, "RefreshCantRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }


}
