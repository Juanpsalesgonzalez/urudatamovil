package com.proyecto.urudatamovil.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by juan on 15/02/15.
 */

@JsonIgnoreProperties(ignoreUnknown = true)

public class OutsourcerWebClient implements Parcelable {

    private String id;
    private String nombre;
    private String markIn;
    private String markOut;
    private String saldo;
    private String cel;
    private String direccion;
    private String cliente;
    private String role;

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
    public void setMarkOut(String m) {
        markOut = m;
    }
    public String getMarkOut() {
        return markOut;
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
    public String getDireccion() {
        return direccion;
    }
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void readFromParcel(Parcel in ) {

        id= in.readString();
        nombre= in.readString();
        markIn= in.readString();
        markOut= in.readString();
        saldo= in.readString();
        cel= in.readString();
        direccion= in.readString();
        cliente= in.readString();
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(nombre);
        out.writeString(markIn);
        out.writeString(markOut);
        out.writeString(saldo);
        out.writeString(cel);
        out.writeString(direccion);
        out.writeString(cliente);
    }
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public OutsourcerWebClient createFromParcel(Parcel in ) {
            return new OutsourcerWebClient( in );
        }

        public OutsourcerWebClient [] newArray(int size) {
            return new OutsourcerWebClient[size];
        }
    };

    @SuppressWarnings("WeakerAccess")
    public OutsourcerWebClient (Parcel in) {
        id= in.readString();
        nombre= in.readString();
        markIn= in.readString();
        markOut= in.readString();
        saldo= in.readString();
        cel= in.readString();
        direccion= in.readString();
        cliente= in.readString();
        OutsourcerWebClient o = new OutsourcerWebClient(id,nombre);
        o.setCliente(cliente);
        o.setSaldo(saldo);
        o.setMarkIn(markIn);
        o.setMarkOut(markOut);
        o.setCel(cel);
        o.setDireccion(direccion);
    }

    public OutsourcerWebClient createFromParcel(Parcel in) {
        return new OutsourcerWebClient(in);
    }

    public OutsourcerWebClient[] newArray(int size) {
        return new OutsourcerWebClient[size];
    }
}


