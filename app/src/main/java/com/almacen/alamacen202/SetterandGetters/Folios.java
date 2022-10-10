package com.almacen.alamacen202.SetterandGetters;

public class Folios {
    private String folio;
    private String fecha;
    private String hora;

    public Folios(String folio, String fecha, String hora) {
        this.folio = folio;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}//clase
