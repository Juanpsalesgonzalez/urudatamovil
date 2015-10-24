package com.proyecto.urudatamovil.objects;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by juan on 07/07/15.
 */
public class PeticionWebClient implements Parcelable{

    private Long idPeticion;
    private String inicio, fin;
    private String fechaSolicitud;
    private String descripcion;
    private String certificado;
    private String archivoCertificado;
    private Long  outsourcer;
    private String estado;


    public PeticionWebClient(String petId){
        idPeticion=Long.parseLong(petId);
    }
    public PeticionWebClient(Long idPeticion, String inicio, String fin, Long outsourcer, String descripcion) {
        this.idPeticion = idPeticion;
        this.inicio = inicio;
        this.fin = fin;
        this.outsourcer = outsourcer;
        this.descripcion = descripcion;
    }

    public Long getIdPeticion() {
        return idPeticion;
    }

    public void setIdPeticion(Long idPeticion) {
        this.idPeticion = idPeticion;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    private void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getArchivoCertificado() {
        return archivoCertificado;
    }

    private void setArchivoCertificado(String archivoCertificado) {
        this.archivoCertificado = archivoCertificado;
    }
    public Long getOutsourcer() {
        return outsourcer;
    }

    private void setOutsourcer(Long outsourcer) {
        this.outsourcer = outsourcer;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void readFromParcel(Parcel in ) {

        idPeticion=in.readLong();
        outsourcer=in.readLong();
        inicio=in.readString();
        fin=in.readString();
        fechaSolicitud=in.readString();
        descripcion=in.readString();
        certificado=in.readString();
        archivoCertificado=in.readString();
        estado=in.readString();
    }
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(idPeticion);
        out.writeLong(outsourcer);
        out.writeString(inicio);
        out.writeString(fin);
        out.writeString(fechaSolicitud);
        out.writeString(descripcion);
        out.writeString(certificado);
        out.writeString(archivoCertificado);
        out.writeString(estado);
    }
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PeticionWebClient createFromParcel(Parcel in ) {
            return new PeticionWebClient( in );
        }

        public PeticionWebClient[] newArray(int size) {
            return new PeticionWebClient[size];
        }
    };

     @SuppressWarnings("WeakerAccess")
     public PeticionWebClient(Parcel in) {
            idPeticion=in.readLong();
            outsourcer=in.readLong();
            inicio=in.readString();
            fin=in.readString();
            fechaSolicitud=in.readString();
            descripcion=in.readString();
            certificado=in.readString();
            archivoCertificado=in.readString();
            estado=in.readString();
            PeticionWebClient p = new PeticionWebClient(idPeticion,inicio, fin, outsourcer, descripcion);
            p.setOutsourcer(outsourcer);
            p.setEstado(estado);
            p.setFechaSolicitud(fechaSolicitud);
            p.setArchivoCertificado(archivoCertificado);
            p.setCertificado(certificado);
    }

    public PeticionWebClient createFromParcel(Parcel in) {
            return new PeticionWebClient(in);
        }

    public PeticionWebClient[] newArray(int size) {
            return new PeticionWebClient[size];
        }
}
