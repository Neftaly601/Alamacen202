package com.almacen.alamacen202.SetterandGetters;

public class ComprometidasSandG {
    String tipoDocument;
    String Folio;
    String Cliente;
    String Cantidad;
    String Fecha;

    public ComprometidasSandG(String tipoDocument, String folio, String cliente, String cantidad, String fecha) {
        this.tipoDocument = tipoDocument;
        Folio = folio;
        Cliente = cliente;
        Cantidad = cantidad;
        Fecha = fecha;
    }

    public String getTipoDocument() {
        return tipoDocument;
    }

    public void setTipoDocument(String tipoDocument) {
        this.tipoDocument = tipoDocument;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getCliente() {
        return Cliente;
    }

    public void setCliente(String cliente) {
        Cliente = cliente;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }
}
