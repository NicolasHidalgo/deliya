package beans;

public class TiendaBean {

    private String ID;
    private String RUC;
    private String NOMBRE;
    private String DESCRIPCION;
    private String DIRECCION;
    private String COD_DISTRITO;
    private String COD_CATEGORIA;
    private String LATITUD;
    private String LONGITUD;

    public TiendaBean(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getRUC() {
        return RUC;
    }

    public void setRUC(String RUC) {
        this.RUC = RUC;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public String getDIRECCION() {
        return DIRECCION;
    }

    public void setDIRECCION(String DIRECCION) {
        this.DIRECCION = DIRECCION;
    }

    public String getCOD_DISTRITO() {
        return COD_DISTRITO;
    }

    public void setCOD_DISTRITO(String COD_DISTRITO) {
        this.COD_DISTRITO = COD_DISTRITO;
    }

    public String getCOD_CATEGORIA() {
        return COD_CATEGORIA;
    }

    public void setCOD_CATEGORIA(String COD_CATEGORIA) {
        this.COD_CATEGORIA = COD_CATEGORIA;
    }

    public String getLATITUD() {
        return LATITUD;
    }

    public void setLATITUD(String LATITUD) {
        this.LATITUD = LATITUD;
    }

    public String getLONGITUD() {
        return LONGITUD;
    }

    public void setLONGITUD(String LONGITUD) {
        this.LONGITUD = LONGITUD;
    }
}
