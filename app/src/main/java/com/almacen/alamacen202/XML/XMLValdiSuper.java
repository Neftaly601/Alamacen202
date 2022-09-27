package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLValdiSuper extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String USERSUPER="";
    String PASSSUPER="";

    public XMLValdiSuper(int version) {
        super(version);
    }

    public void XMLValdiSuper(String usuario, String clave,String userSuper) {
        this.usuario = usuario;
        this.clave = clave;
        this.USERSUPER=userSuper;
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
        writer.startTag(tem, "ValdiSuperRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "ValdiSupervi");

        writer.startTag(tem, "k_Usuario");
        writer.text(USERSUPER);
        writer.endTag(tem, "k_Usuario");


        writer.endTag(tem, "ValdiSupervi");


        writer.endTag(tem, "ValdiSuperRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}

