package com.proyecto.urudatamovil.objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by juan on 15/02/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class OutsourcerWebClient {



    private String id;
    private String nombre;
    private String markIn;
    private String MarkOut;
    private String getMarkOut;
    private String saldo;
    private String cel;
    private String direccion;

    public OutsourcerWebClient(String n, String i){
        id=i;
        nombre=n;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String n){
        nombre=n;
    }
    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
    public String getSaldo() {
        return saldo;
    }

    public String getCel() {
        return cel;
    }
    public void setCel(String cel) {
        this.cel = cel;
    }

    public void setMarkOut(String markOut) {
        MarkOut = markOut;
    }
    public void setGetMarkOut(String getMarkOut) {
        this.getMarkOut = getMarkOut;
    }

    public String getMarkOut() {
        return MarkOut;
    }

    public String getMarkIn() {
        return markIn;
    }
    public void setMarkIn(String markIn) {
        this.markIn = markIn;
    }

    public String getId() {
        return this.id;
    }
    public void setId(String i){
        id=i;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGetMarkOut() {
        return getMarkOut;
    }

    public String getDireccion() {
        return direccion;
    }
}

