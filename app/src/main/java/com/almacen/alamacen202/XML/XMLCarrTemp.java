package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLCarrTemp extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Sucursales = "";
    String Folio = "";
    String Producto = "";
    String Cantidad = "";
    String Ubicacion ="";
    String NumCarr="";

    public XMLCarrTemp(int version) {
        super(version);
    }

    public void XMLCarrTemporal(String usuario, String clave, String Sucursales, String Folio, String Producto, String Cantidad, String Ubicacion, String NumCarr) {
        this.usuario = usuario;
        this.clave = clave;
        this.Sucursales = Sucursales;
        this.Folio = Folio;
        this.Producto = Producto;
        this.Cantidad = Cantidad;
        this.Ubicacion =Ubicacion;
        this.NumCarr = NumCarr;

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
        writer.startTag(tem, "CarrTempRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "CarrTemporal");

        writer.startTag(tem, "k_Sucursal");
        writer.text(Sucursales);
        writer.endTag(tem, "k_Sucursal");

        writer.startTag(tem, "k_Folio");
        writer.text(Folio);
        writer.endTag(tem, "k_Folio");

        writer.startTag(tem, "k_Producto");
        writer.text(Producto);
        writer.endTag(tem, "k_Producto");


        writer.startTag(tem, "k_Cantidad");
        writer.text(Cantidad);
        writer.endTag(tem, "k_Cantidad");

        writer.startTag(tem, "k_Ubicacion");
        writer.text(Ubicacion);
        writer.endTag(tem, "k_Ubicacion");

        writer.startTag(tem, "k_NumCar");
        writer.text(NumCarr);
        writer.endTag(tem, "k_NumCar");



        writer.endTag(tem, "CarrTemporal");


        writer.endTag(tem, "CarrTempRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}

