package com.almacen.alamacen202.SetterandGetters;

public class ListLiberaSandG {
    String Cliente;
    String Folio;
    String Fecha;
    String Referencia;
    String NumDocument ;
    String Cantidad;
    String CantidDsURT;
    String UrgenciaPed;

    public ListLiberaSandG(String cliente, String folio, String fecha, String referencia, String numDocument, String cantidad, String cantidDsURT, String urgenciaPed) {
        Cliente = cliente;
        Folio = folio;
        Fecha = fecha;
        Referencia = referencia;
        NumDocument = numDocument;
        Cantidad = cantidad;
        CantidDsURT = cantidDsURT;
        UrgenciaPed = urgenciaPed;
    }


    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getReferencia() {
        return Referencia;
    }

    public void setReferencia(String referencia) {
        Referencia = referencia;
    }

    public String getNumDocument() {
        return NumDocument;
    }

    public void setNumDocument(String numDocument) {
        NumDocument = numDocument;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getCantidDsURT() {
        return CantidDsURT;
    }

    public void setCantidDsURT(String cantidDsURT) {
        CantidDsURT = cantidDsURT;
    }

    public String getUrgenciaPed() {
        return UrgenciaPed;
    }

    public void setUrgenciaPed(String urgenciaPed) {
        UrgenciaPed = urgenciaPed;
    }
}