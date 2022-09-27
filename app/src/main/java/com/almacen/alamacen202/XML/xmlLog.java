package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class xmlLog extends SoapSerializationEnvelope {
    String usuario = "";
    String clave = "";
    String Identificador = "";
    String Accion = "";
    String Parametros = "";

    public xmlLog(int version) {
        super(version);
    }

    public void xmlLog(String usuario, String clave, String Identificador, String Accion, String Parametros) {
        this.usuario = usuario;
        this.clave = clave;
        this.Identificador = Identificador;
        this.Accion = Accion;
        this.Parametros = Parametros;
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

        writer.startTag(tem, "LogAppUsRequest");

        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");

        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");

        writer.startTag(tem, "LogAppUser");


        writer.startTag(tem, "k_Usuario");
        writer.text(usuario);
        writer.endTag(tem, "k_Usuario");

        writer.startTag(tem, "k_Identif");
        writer.text(Identificador);
        writer.endTag(tem, "k_Identif");

        writer.startTag(tem, "k_Accion");
        writer.text(Accion);
        writer.endTag(tem, "k_Accion");

        writer.startTag(tem, "k_Parametro");
        writer.text(Parametros);
        writer.endTag(tem, "k_Parametro");


        writer.endTag(tem, "LogAppUser");


        writer.endTag(tem, "LogAppUsRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}
