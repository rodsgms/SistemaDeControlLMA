package com.sistemaasistenciaycomunicados.info;

public class Comunicado {
    private String text;
    private String date;
    private String idITem;

    public Comunicado() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdITem() {
        return idITem;
    }

    public void setIdITem(String idITem) {
        this.idITem = idITem;
    }
}
