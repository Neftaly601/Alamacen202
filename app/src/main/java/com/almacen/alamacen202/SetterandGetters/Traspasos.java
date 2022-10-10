package com.almacen.alamacen202.SetterandGetters;

public class Traspasos {
    private String num;
    private String producto;
    private String cantidad;
    private String cantSurt;

    public Traspasos(String  num, String producto, String cantidad,String cantSurt) {
        this.num = num;
        this.producto = producto;
        this.cantidad = cantidad;
        this.cantSurt = cantSurt;
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

    public String getCantSurt() {
        return cantSurt;
    }

    public void setCantSurt(String cantSurt) {
        this.cantSurt = cantSurt;
    }
}//clase
