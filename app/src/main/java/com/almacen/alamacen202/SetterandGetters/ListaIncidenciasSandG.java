package com.almacen.alamacen202.SetterandGetters;

public class ListaIncidenciasSandG {
    String Clave;
    String Mensaje;

    public ListaIncidenciasSandG(String clave, String mensaje) {
        Clave = clave;
        Mensaje = mensaje;
    }

    public String getClave() {
        return Clave;
    }

    public void setClave(String clave) {
        Clave = clave;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }
}
