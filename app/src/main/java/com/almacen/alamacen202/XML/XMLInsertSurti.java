package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLInsertSurti extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Sucursal = "";
    String Documento = "";
    String Folio = "";
    String PartidaP = "";
    String Producto = "";
    String Cantidad = "";
    String Fecha = "";
    String Hora = "";
    String UbicacionOri ="";
    String UbicacionDes ="";

    public XMLInsertSurti(int version) {
        super(version);
    }

    public void XMLInsertSurti(String usuario, String clave, String sucursal, String documento, String folio, String partidaP, String producto, String cantidad, String fecha, String hora, String UbicacionOri , String UbicacionDes) {
        this.usuario = usuario;
        this.clave = clave;
        this.Sucursal = sucursal;
        this.Documento = documento;
        this.Folio = folio;
        this.PartidaP = partidaP;
        this.Producto = producto;
        this.Cantidad = cantidad;
        this.Fecha = fecha;
        this.Hora = hora;
        this.UbicacionOri =UbicacionOri;
        this.UbicacionDes =UbicacionDes;

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
        writer.startTag(tem, "InsertSurtiRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "INSURTREQ");

        writer.startTag(tem, "k_Sucursal");
        writer.text(Sucursal);
        writer.endTag(tem, "k_Sucursal");

        writer.startTag(tem, "k_Documento");
        writer.text(Documento);
        writer.endTag(tem, "k_Documento");

        writer.startTag(tem, "k_Folio");
        writer.text(Folio);
        writer.endTag(tem, "k_Folio");

        writer.startTag(tem, "k_PartidaP");
        writer.text(PartidaP);
        writer.endTag(tem, "k_PartidaP");


        writer.startTag(tem, "k_Producto");
        writer.text(Producto);
        writer.endTag(tem, "k_Producto");

        writer.startTag(tem, "k_Cantidad");
        writer.text(Cantidad);
        writer.endTag(tem, "k_Cantidad");

        writer.startTag(tem, "k_Fecha");
        writer.text(Fecha);
        writer.endTag(tem, "k_Fecha");


        writer.startTag(tem, "k_Hora");
        writer.text(Hora);
        writer.endTag(tem, "k_Hora");



        writer.startTag(tem, "k_UUsuario");
        writer.text(usuario);
        writer.endTag(tem, "k_UUsuario");

        writer.startTag(tem, "k_UbicacionOri");
        writer.text(UbicacionOri);
        writer.endTag(tem, "k_UbicacionOri");

        writer.startTag(tem, "k_UbicacionDes");
        writer.text(UbicacionDes);
        writer.endTag(tem, "k_UbicacionDes");




        writer.endTag(tem, "INSURTREQ");


        writer.endTag(tem, "InsertSurtiRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();


    }
}
