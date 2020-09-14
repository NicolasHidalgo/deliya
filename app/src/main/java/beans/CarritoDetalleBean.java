package beans;

public class CarritoDetalleBean {

    private ProductoBean productoBean;
    private int cantidad;

    public CarritoDetalleBean(){

    }

    public ProductoBean getProductoBean() {
        return productoBean;
    }

    public void setProductoBean(ProductoBean productoBean) {
        this.productoBean = productoBean;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
