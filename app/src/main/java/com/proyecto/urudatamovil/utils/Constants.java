package com.proyecto.urudatamovil.utils;

/**
 * Created by juan on 26/04/15.
 * Constantes utilizadas en el resto de los modulos.
 */
public class Constants {

  //  private static final String ip_ctr="200.71.4.212:8443";
    private static final String ip_ctr="192.168.1.4:8080";
    public static final String URL_CONFIRM="http://" + ip_ctr + "/urudata/rest/confirm";
    public static final String URL_SET_LICENSE="http://" + ip_ctr + "/urudata/rest/setLicence";
    public static final String URL_LOGIN_PROCESS="http://" + ip_ctr + "/urudata/loginProcess";
    public static final String URL_UPLOAD_CERT="http://" + ip_ctr + "/urudata/rest/uploadCert";
    public static final String URL_LIST_PET="http://" + ip_ctr + "/urudata/rest/listaPet";
    public static final String URL_LIST_ASIG="http://" + ip_ctr + "/urudata/rest/listAsig";

    public static final int LOGIN_REQUEST = 100;
    public static final int LOGIN_OK = 101;
    public static final int LOGIN_FAILED = 102;
    public static final int ACTION_MARCA = 110;
    public static final int ACTION_LICENCIA = 111;
    public static final int ACTION_PETICION = 112;
    public static final int ACTION_ASIGNACIONES = 113;
    public final static int PHOTO_REQUEST_CODE = 123;
    public final static int PETITION_DETAILS_CODE = 126;
    public final static int CONFIRM_LICENCE_CODE = 124;
    public final static int RESULT_OK=130;
    public final static int RESULT_FAILED=131;
    public final static int ACTION_CERTIFICATE=140;

}
