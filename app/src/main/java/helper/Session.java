package helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import beans.CarritoDetalleBean;
import beans.ProductoBean;
import beans.TiendaBean;
import beans.UsuarioBean;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    /* Ejemplo */
    public void setIdUsuario(String Id) {
        prefs.edit().putString("IdUsuario", Id).commit();
    }
    public String getIdUsuario() {
        String IdUsuario = prefs.getString("IdUsuario","");
        return IdUsuario;
    }

    public void setCategoria(String valor) {
        prefs.edit().putString("Categoria", valor).commit();
    }
    public String getCategoria() {
        String valor = prefs.getString("Categoria","");
        return valor;
    }

    public void setToken(String Token) {
        prefs.edit().putString("Token", Token).commit();
    }
    public String getToken() {
        String Token = prefs.getString("Token","");
        return Token;
    }

    public void setIdStore(String Id) {
        prefs.edit().putString("IdStore", Id).commit();
    }
    public String getIdStore() {
        String data = prefs.getString("IdStore","");
        return data;
    }
    public void setIdProducto(String Id) {
        prefs.edit().putString("IdProducto", Id).commit();
    }
    public String getIdProducto() {
        String data = prefs.getString("IdProducto","");
        return data;
    }
    public void setIdProductoSeleccionado(String Id) {
        prefs.edit().putString("IdProductoSeleccionado", Id).commit();
    }
    public String getIdProductoSeleccionado() {
        String data = prefs.getString("IdProductoSeleccionado","");
        return data;
    }

    public void setUsuario(UsuarioBean bean){
        Gson gson = new Gson();
        String json = gson.toJson(bean);
        prefs.edit().putString("Usuario", json).commit();
    }

    public UsuarioBean getUsuario(){
        Gson gson = new Gson();
        String json = prefs.getString("Usuario", "");
        UsuarioBean obj = gson.fromJson(json, UsuarioBean.class);
        return obj;
    }

    public void setTiendas(List<TiendaBean> data){
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefs.edit().putString("Tiendas", json).commit();
    }
    public List<TiendaBean> getTiendas(){
        Gson gson = new Gson();
        String json = prefs.getString("Tiendas", "");
        Type type  = new TypeToken<List<TiendaBean>>(){}.getType();
        List<TiendaBean> data = gson.fromJson(json, type);
        return data;
    }

    public void setProductos(List<ProductoBean> data){
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefs.edit().putString("Productos", json).commit();
    }
    public List<ProductoBean> getProductos(){
        Gson gson = new Gson();
        String json = prefs.getString("Productos", "");
        Type type  = new TypeToken<List<ProductoBean>>(){}.getType();
        List<ProductoBean> data = gson.fromJson(json, type);
        return data;
    }

    public void setCarritoDetalle(List<CarritoDetalleBean> data){
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefs.edit().putString("CarritoDetalle", json).commit();
    }
    public List<CarritoDetalleBean> getCarritoDetalle(){
        Gson gson = new Gson();
        String json = prefs.getString("CarritoDetalle", "");
        Type type  = new TypeToken<List<CarritoDetalleBean>>(){}.getType();
        List<CarritoDetalleBean> data = gson.fromJson(json, type);
        return data;
    }

    public void setTotalPagar(String valor) {
        prefs.edit().putString("TotalPagar", valor).commit();
    }
    public String getTotalPagar() {
        String valor = prefs.getString("TotalPagar","");
        return valor;
    }

}
