package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLRecepMultSuc extends SoapSerializationEnvelope {

    private String usuario;
    private String contrasena;
    private String suc;
    private String prod;
    private String cant;

    public XMLRecepMultSuc(int version) {
        super(version);
    }

    public void XMLTrasp(String usuario, String contrasena,String suc,String prod,String cant) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.suc = suc;
        this.prod = prod;
        this.cant=cant;
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
        writer.startTag(tem, "RecepcionMultisucursalRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(contrasena);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "RecepMultiSuc");

        writer.startTag(tem, "k_Sucursal");
        writer.text(suc);
        writer.endTag(tem, "k_Sucursal");

        writer.startTag(tem, "k_Producto");
        writer.text(prod);
        writer.endTag(tem, "k_Producto");

        writer.startTag(tem, "k_Cantidad");
        writer.text(cant);
        writer.endTag(tem, "k_Cantidad");

        writer.startTag(tem, "k_Usuario");
        writer.text(usuario);
        writer.endTag(tem, "k_Usuario");

        writer.endTag(tem, "RecepMultiSuc");

        writer.endTag(tem, "RecepcionMultisucursalRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }//vrite
}
