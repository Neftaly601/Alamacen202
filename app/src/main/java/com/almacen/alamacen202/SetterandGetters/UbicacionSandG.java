package com.almacen.alamacen202.SetterandGetters;

public class UbicacionSandG {
    String Ubicacion;
    String Cantidad;
    String Fecha;
    String Tipo;

    public UbicacionSandG(String ubicacion, String cantidad, String fecha, String tipo) {
        Ubicacion = ubicacion;
        Cantidad = cantidad;
        Fecha = fecha;
        Tipo = tipo;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
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

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}
