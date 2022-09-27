package com.almacen.alamacen202.SetterandGetters;

public class CarritoUbicTemporalSandG {
    String Sucursal;
    String Folio;
    String ClaveProducto;
    String Cantidad;
    String UbicacionTemporal;

    public CarritoUbicTemporalSandG(String sucursal, String folio, String claveProducto, String cantidad, String ubicacionTemporal) {
        Sucursal = sucursal;
        Folio = folio;
        ClaveProducto = claveProducto;
        Cantidad = cantidad;
        UbicacionTemporal = ubicacionTemporal;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getClaveProducto() {
        return ClaveProducto;
    }

    public void setClaveProducto(String claveProducto) {
        ClaveProducto = claveProducto;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getUbicacionTemporal() {
        return UbicacionTemporal;
    }

    public void setUbicacionTemporal(String ubicacionTemporal) {
        UbicacionTemporal = ubicacionTemporal;
    }
}
