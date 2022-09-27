package com.almacen.alamacen202.SetterandGetters;

public class CarrTempSandG {
String Sucursal;
String Foli ;
String Producto;
String Cantidad;
String Ubicacion;
String NumeCarrito;

    public CarrTempSandG(String sucursal, String foli, String producto, String cantidad, String ubicacion,String  NumeCarrito) {
        this.Sucursal = sucursal;
        this.Foli = foli;
        this.Producto = producto;
        this.Cantidad = cantidad;
        this.Ubicacion = ubicacion;
        this.NumeCarrito = NumeCarrito;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }

    public String getFoli() {
        return Foli;
    }

    public void setFoli(String foli) {
        Foli = foli;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getNumeCarrito() {
        return NumeCarrito;
    }

    public void setNumeCarrito(String numeCarrito) {
        NumeCarrito = numeCarrito;
    }
}
