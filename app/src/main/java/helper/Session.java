package helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

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

    public void setToken(String Token) {
        prefs.edit().putString("Token", Token).commit();
    }
    public String getToken() {
        String Token = prefs.getString("Token","");
        return Token;
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

    public void setTiendas(List<TiendaBean> tiendas){
        Gson gson = new Gson();
        String json = gson.toJson(tiendas);
        prefs.edit().putString("Tiendas", json).commit();
    }
    public List<TiendaBean> getTiendas(){
        Gson gson = new Gson();
        String json = prefs.getString("Tiendas", "");
        Type type  = new TypeToken<List<TiendaBean>>(){}.getType();
        List<TiendaBean> tiendas = gson.fromJson(json, type);
        return tiendas;
    }

}
