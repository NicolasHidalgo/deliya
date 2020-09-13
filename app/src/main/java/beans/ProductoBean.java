package beans;

import android.widget.ProgressBar;

public class ProductoBean {

    private String ID;
    private String ID_STORE;
    private String SKU;
    private String NOMBRE;
    private String PRECIO;
    private String DESCRIPCION;
    private int IMAGEN_ID;

    public ProductoBean(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getNOMBRE() {
        return NOMBRE;
    }

    public void setNOMBRE(String NOMBRE) {
        this.NOMBRE = NOMBRE;
    }

    public String getPRECIO() {
        return PRECIO;
    }

    public void setPRECIO(String PRECIO) {
        this.PRECIO = PRECIO;
    }

    public String getDESCRIPCION() {
        return DESCRIPCION;
    }

    public void setDESCRIPCION(String DESCRIPCION) {
        this.DESCRIPCION = DESCRIPCION;
    }

    public int getIMAGEN_ID() {
        return IMAGEN_ID;
    }

    public void setIMAGEN_ID(int IMAGEN_ID) {
        this.IMAGEN_ID = IMAGEN_ID;
    }

    public String getID_STORE() {
        return ID_STORE;
    }

    public void setID_STORE(String ID_STORE) {
        this.ID_STORE = ID_STORE;
    }
}
