package com.almacen.alamacen202.SetterandGetters;

public class ResurtidoPicking {
    private String num;
    private String claveProd;
    private String descrip;
    private String fecha;
    private String hora;
    private String picking;
    private String clasif;
    private String rack;
    private boolean revisado;

    public ResurtidoPicking(String num, String claveProd, String descrip,
                            String fecha, String hora, String picking,
                            String clasif, String rack, boolean revisado) {
        this.num = num;
        this.claveProd = claveProd;
        this.descrip = descrip;
        this.fecha = fecha;
        this.hora = hora;
        this.picking = picking;
        this.clasif = clasif;
        this.rack = rack;
        this.revisado = revisado;
    }//constructor

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getClaveProd() {
        return claveProd;
    }

    public void setClaveProd(String claveProd) {
        this.claveProd = claveProd;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
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

    public String getPicking() {
        return picking;
    }

    public void setPicking(String picking) {
        this.picking = picking;
    }

    public String getClasif() {
        return clasif;
    }

    public void setClasif(String clasif) {
        this.clasif = clasif;
    }

    public String getRack() {
        return rack;
    }

    public void setRack(String rack) {
        this.rack = rack;
    }

    public boolean isRevisado() {
        return revisado;
    }

    public void setRevisado(boolean revisado) {
        this.revisado = revisado;
    }
}//clase Principal
