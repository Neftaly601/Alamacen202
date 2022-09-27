package com.almacen.alamacen202.SetterandGetters;

public class Inventario {
    private String num;
    private String producto;
    private String cantidad;

    public Inventario(String  num, String producto, String cantidad) {
        this.num = num;
        this.producto = producto;
        this.cantidad = cantidad;
    }//constructor

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}//clase
