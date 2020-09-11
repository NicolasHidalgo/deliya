package beans;

public class UsuarioBean {

    private String ID;
    private String NOMBRES;
    private String APELLIDOS;
    private String CORREO;
    private String TELEFONO;
    private String ESTADO;

    private String ID_ROL;
    private RolBean ROL;

    public UsuarioBean(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCORREO() {
        return CORREO;
    }

    public void setCORREO(String CORREO) {
        this.CORREO = CORREO;
    }

    public String getTELEFONO() {
        return TELEFONO;
    }

    public void setTELEFONO(String TELEFONO) {
        this.TELEFONO = TELEFONO;
    }

    public String getNOMBRES() {
        return NOMBRES;
    }

    public void setNOMBRES(String NOMBRES) {
        this.NOMBRES = NOMBRES;
    }

    public String getAPELLIDOS() {
        return APELLIDOS;
    }

    public void setAPELLIDOS(String APELLIDOS) {
        this.APELLIDOS = APELLIDOS;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public void setESTADO(String ESTADO) {
        this.ESTADO = ESTADO;
    }

    public RolBean getROL() {
        return ROL;
    }

    public void setROL(RolBean ROL) {
        this.ROL = ROL;
    }

    public String getID_ROL() {
        return ID_ROL;
    }

    public void setID_ROL(String ID_ROL) {
        this.ID_ROL = ID_ROL;
    }
}
