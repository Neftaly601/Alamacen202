package com.almacen.alamacen202.SetterandGetters;

public class ListReceSandG {
    String Provedor;
    String Folio;
    String Fecha;
    String Referencia;
    String NumDocument ;

    public ListReceSandG(String Provedor, String folio, String fecha, String referencia, String numDocument) {
        this.Provedor = Provedor;
        this.Folio = folio;
        this.Fecha = fecha;
        this.Referencia = referencia;
        this.NumDocument = numDocument;
    }

    public String getProvedor() {
        return Provedor;
    }

    public void setProvedor(String provedor) {
        Provedor = provedor;
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
}