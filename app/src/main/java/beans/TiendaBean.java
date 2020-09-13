package beans;

public class TiendaBean {

    private String ID;
    private String RUC;
    private String RAZON_SOCIAL;
    private String DESCRIPCION;
    private String DIRECCION;
    private String EMAIL;
    private String TELEFONO;
    private String UBIGEO;
    private String COD_CATEGORIA;
    private String LATITUD;
    private String LONGITUD;
    private int IMAGEN_ID;

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

    public int getIMAGEN_ID() {
        return IMAGEN_ID;
    }

    public void setIMAGEN_ID(int IMAGEN_ID) {
        this.IMAGEN_ID = IMAGEN_ID;
    }

    public String getRAZON_SOCIAL() {
        return RAZON_SOCIAL;
    }

    public void setRAZON_SOCIAL(String RAZON_SOCIAL) {
        this.RAZON_SOCIAL = RAZON_SOCIAL;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getUBIGEO() {
        return UBIGEO;
    }

    public void setUBIGEO(String UBIGEO) {
        this.UBIGEO = UBIGEO;
    }
}
