package com.almacen.alamacen202.SetterandGetters;

public class ListProReceSandG {

    String Provedor;
    String Nombre;
    String Cantidad;
    String Producto;
    String Ubicacion;
    String Descripcion;
    String Unidad;
    String PPrevias;
    String Documento;
    String Folio;
    String Sucursal;
    String CantidadSurtida;


    public ListProReceSandG(String provedor, String nombre, String cantidad, String producto, String ubicacion, String descripcion, String unidad, String PPrevias, String documento, String folio, String sucursal, String cantidadSurtida) {
        Provedor = provedor;
        Nombre = nombre;
        Cantidad = cantidad;
        Producto = producto;
        Ubicacion = ubicacion;
        Descripcion = descripcion;
        Unidad = unidad;
        this.PPrevias = PPrevias;
        Documento = documento;
        Folio = folio;
        Sucursal = sucursal;
        CantidadSurtida = cantidadSurtida;
    }

    public String getProvedor() {
        return Provedor;
    }

    public void setProvedor(String provedor) {
        Provedor = provedor;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getUnidad() {
        return Unidad;
    }

    public void setUnidad(String unidad) {
        Unidad = unidad;
    }

    public String getPPrevias() {
        return PPrevias;
    }

    public void setPPrevias(String PPrevias) {
        this.PPrevias = PPrevias;
    }

    public String getDocumento() {
        return Documento;
    }

    public void setDocumento(String documento) {
        Documento = documento;
    }

    public String getFolio() {
        return Folio;
    }

    public void setFolio(String folio) {
        Folio = folio;
    }

    public String getSucursal() {
        return Sucursal;
    }

    public void setSucursal(String sucursal) {
        Sucursal = sucursal;
    }

    public String getCantidadSurtida() {
        return CantidadSurtida;
    }

    public void setCantidadSurtida(String cantidadSurtida) {
        CantidadSurtida = cantidadSurtida;
    }
}