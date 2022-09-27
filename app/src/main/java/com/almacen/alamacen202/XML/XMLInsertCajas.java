package com.almacen.alamacen202.XML;

import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class XMLInsertCajas extends SoapSerializationEnvelope {

    String usuario = "";
    String clave = "";
    String ClaveSucursalCajas = "";
    String FolioDocumentoCajas = "";
    String ClavedelProdcutoCajas = "";
    String CantidadUnidadesCajas = "";
    String NumCajasCajas = "";




    public XMLInsertCajas(int version) {
        super(version);
    }

    public void XMLInsertCajas( String usuario, String clave, String claveSucursalCajas, String folioDocumentoCajas, String clavedelProdcutoCajas, String cantidadUnidadesCajas, String numCajasCajas) {

        this.usuario = usuario;
        this.clave = clave;
        this.ClaveSucursalCajas = claveSucursalCajas;
        this.FolioDocumentoCajas = folioDocumentoCajas;
        this.ClavedelProdcutoCajas = clavedelProdcutoCajas;
        this.CantidadUnidadesCajas = cantidadUnidadesCajas;
        this.NumCajasCajas = numCajasCajas;
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
        writer.startTag(tem, "InsertCajasRequest");


        writer.startTag(tem, "Login");
        writer.startTag(tem, "user");
        writer.text(usuario);
        writer.endTag(tem, "user");
        writer.startTag(tem, "pass");
        writer.text(clave);
        writer.endTag(tem, "pass");
        writer.endTag(tem, "Login");


        writer.startTag(tem, "INSCAJAS");

        writer.startTag(tem, "k_Sucursal");
        writer.text(ClaveSucursalCajas);
        writer.endTag(tem, "k_Sucursal");

        writer.startTag(tem, "k_Folio");
        writer.text(FolioDocumentoCajas);
        writer.endTag(tem, "k_Folio");

        writer.startTag(tem, "k_Producto");
        writer.text(ClavedelProdcutoCajas);
        writer.endTag(tem, "k_Producto");


        writer.startTag(tem, "k_Cantidad");
        writer.text(CantidadUnidadesCajas);
        writer.endTag(tem, "k_Cantidad");

        writer.startTag(tem, "k_NumCajas");
        writer.text(NumCajasCajas);
        writer.endTag(tem, "k_NumCajas");


        writer.endTag(tem, "INSCAJAS");


        writer.endTag(tem, "InsertCajasRequest");
        writer.endTag(env, "Body");
        writer.endTag(env, "Envelope");
        writer.endDocument();

    }
}
