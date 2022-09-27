package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLReportInici extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Producto = "";
    String UsuarioSuper = "";
    String Razon ="";
    String Sucursal = "";
    String Folio ="";

    public XMLReportInici(int version) {
        super(version);
    }

    public void XMLReportInicide(String usuario, String clave, String UsuarioSuper, String Producto, String Razon, String Sucursal, String Folio) {
        this.usuario = usuario;
        this.clave = clave;
        this.UsuarioSuper = UsuarioSuper;
        this.Producto = Producto;
        this.Razon =Razon;
        this.Sucursal = Sucursal;
        this.Folio = Folio;


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
        writer.startTag(tem, "ReportInciRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "ReportIncide");

        writer.startTag(tem, "k_Producto");
        writer.text(Producto);
        writer.endTag(tem, "k_Producto");

        writer.startTag(tem, "k_Usuario");
        writer.text(UsuarioSuper);
        writer.endTag(tem, "k_Usuario");

        writer.startTag(tem, "k_Razon");
        writer.text(Razon);
        writer.endTag(tem, "k_Razon");

        writer.startTag(tem, "k_Sucursal");
        writer.text(Sucursal);
        writer.endTag(tem, "k_Sucursal");

        writer.startTag(tem, "k_Folio");
        writer.text(Folio);
        writer.endTag(tem, "k_Folio");

        writer.endTag(tem, "ReportIncide");


        writer.endTag(tem, "ReportInciRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}

