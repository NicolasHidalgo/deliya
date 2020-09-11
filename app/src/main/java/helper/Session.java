package helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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

}
