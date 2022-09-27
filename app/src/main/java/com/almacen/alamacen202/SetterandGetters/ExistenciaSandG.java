package com.almacen.alamacen202.SetterandGetters;

public class ExistenciaSandG {
    String Almacen;
    String Existencia;

    public ExistenciaSandG(String almacen, String existencia) {
        Almacen = almacen;
        Existencia = existencia;
    }

    public String getAlmacen() {
        return Almacen;
    }

    public void setAlmacen(String almacen) {
        Almacen = almacen;
    }

    public String getExistencia() {
        return Existencia;
    }

    public void setExistencia(String existencia) {
        Existencia = existencia;
    }
}
