package com.almacen.alamacen202.SetterandGetters;

public class ListProductoSurSandG {
String  Producto ;
String  CantidadSurtida ;
String  PartidaP ;

    public ListProductoSurSandG(String producto, String cantidadSurtida, String partidaP) {
        Producto = producto;
        CantidadSurtida = cantidadSurtida;
        PartidaP = partidaP;
    }

    public String getProducto() {
        return Producto;
    }

    public void setProducto(String producto) {
        Producto = producto;
    }

    public String getCantidadSurtida() {
        return CantidadSurtida;
    }

    public void setCantidadSurtida(String cantidadSurtida) {
        CantidadSurtida = cantidadSurtida;
    }

    public String getPartidaP() {
        return PartidaP;
    }

    public void setPartidaP(String partidaP) {
        PartidaP = partidaP;
    }
}
