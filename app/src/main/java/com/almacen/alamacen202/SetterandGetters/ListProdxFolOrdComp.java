package com.almacen.alamacen202.SetterandGetters;

public class ListProdxFolOrdComp {
    private String num;
    private String claveArt;
    private String art;
    private String ubicacion;
    private String cantidad;

    public ListProdxFolOrdComp(String num,String claveArt, String art, String ubicacion, String cantidad) {
        this.num = num;
        this.claveArt = claveArt;
        this.art = art;
        this.ubicacion = ubicacion;
        this.cantidad = cantidad;
    }//constructor

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getClaveArt() {
        return claveArt;
    }

    public void setClaveArt(String claveArt) {
        this.claveArt = claveArt;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

}//clase Principal
