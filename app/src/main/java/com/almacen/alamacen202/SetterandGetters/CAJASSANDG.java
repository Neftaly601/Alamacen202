package com.almacen.alamacen202.SetterandGetters;

public class CAJASSANDG {
    String ClaveSucursal;
    String ClaveAlamacen;
    String FolioDocumento;
    String ClavedelProdcuto;
    String CantidadUnidades;
    String NumCajas;

    public CAJASSANDG(String claveSucursal, String claveAlamacen, String folioDocumento, String clavedelProdcuto, String cantidadUnidades, String numCajas) {
        ClaveSucursal = claveSucursal;
        ClaveAlamacen = claveAlamacen;
        FolioDocumento = folioDocumento;
        ClavedelProdcuto = clavedelProdcuto;
        CantidadUnidades = cantidadUnidades;
        NumCajas = numCajas;
    }

    public String getClaveSucursal() {
        return ClaveSucursal;
    }

    public void setClaveSucursal(String claveSucursal) {
        ClaveSucursal = claveSucursal;
    }

    public String getClaveAlamacen() {
        return ClaveAlamacen;
    }

    public void setClaveAlamacen(String claveAlamacen) {
        ClaveAlamacen = claveAlamacen;
    }

    public String getFolioDocumento() {
        return FolioDocumento;
    }

    public void setFolioDocumento(String folioDocumento) {
        FolioDocumento = folioDocumento;
    }

    public String getClavedelProdcuto() {
        return ClavedelProdcuto;
    }

    public void setClavedelProdcuto(String clavedelProdcuto) {
        ClavedelProdcuto = clavedelProdcuto;
    }

    public String getCantidadUnidades() {
        return CantidadUnidades;
    }

    public void setCantidadUnidades(String cantidadUnidades) {
        CantidadUnidades = cantidadUnidades;
    }

    public String getNumCajas() {
        return NumCajas;
    }

    public void setNumCajas(String numCajas) {
        NumCajas = numCajas;
    }
}
