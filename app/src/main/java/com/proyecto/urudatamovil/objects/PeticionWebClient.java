package com.proyecto.urudatamovil.objects;


/**
 * Created by juan on 07/07/15.
 */
public class PeticionWebClient {

    private Long idPeticion;
    private String inicio, fin;
    private String fechaSolicitud;
    private String comentario;
    private String certificado;
    private String archivoCertificado;
    private Long  outsourcer;
    private String estado;

    public PeticionWebClient(String petId){
        idPeticion=Long.parseLong(petId);
    }
    public PeticionWebClient(Long idPeticion, String inicio, String fin, Long outsourcer, String comentario) {

        this.idPeticion = idPeticion;
        this.inicio = inicio;
        this.fin = fin;
        this.outsourcer = outsourcer;
        this.comentario = comentario;
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

    public void setFechaSolicitud(String fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public void setArchivoCertificado(String archivoCertificado) {
        this.archivoCertificado = archivoCertificado;
    }

    public Long getOutsourcer() {
        return outsourcer;
    }

    public void setOutsourcer(Long outsourcer) {
        this.outsourcer = outsourcer;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
