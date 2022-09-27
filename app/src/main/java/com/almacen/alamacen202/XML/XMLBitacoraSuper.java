package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.RandomAccessFile;

public class XMLBitacoraSuper extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String Producto = "";
    String Cantidad = "";
    String UsuarioSuper = "";
    String Folio = "";
    String Razon ="";

    public XMLBitacoraSuper(int version) {
        super(version);
    }

    public void XMLBitacoraSupervi(String usuario, String clave, String UsuarioSuper, String Producto, String Cantidad, String Folio, String Razon) {
        this.usuario = usuario;
        this.clave = clave;
        this.UsuarioSuper = UsuarioSuper;
        this.Producto = Producto;
        this.Cantidad = Cantidad;
        this.Folio = Folio;
        this.Razon =Razon;


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
        writer.startTag(tem, "BitacoraSuperRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "BitacoraSupervi");

        writer.startTag(tem, "k_Usuario");
        writer.text(UsuarioSuper);
        writer.endTag(tem, "k_Usuario");

        writer.startTag(tem, "k_Producto");
        writer.text(Producto);
        writer.endTag(tem, "k_Producto");

        writer.startTag(tem, "k_Cantidad");
        writer.text(Cantidad);
        writer.endTag(tem, "k_Cantidad");


        writer.startTag(tem, "k_Folio");
        writer.text(Folio);
        writer.endTag(tem, "k_Folio");

        writer.startTag(tem, "k_Razon");
        writer.text(Razon);
        writer.endTag(tem, "k_Razon");



        writer.endTag(tem, "BitacoraSupervi");


        writer.endTag(tem, "BitacoraSuperRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}

