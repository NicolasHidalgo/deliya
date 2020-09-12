package ws;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import beans.UsuarioBean;
import db.DatabaseManagerUsuario;

public class WebService {

    public static final String SERVER = "https://deliya.com/";
    public String RESPUESTA = "NADA";
    public String ACCION = "SELECT";
    public String QUERY = "";
    public String TABLA = "";

    DatabaseManagerUsuario dbUsuario;

    RequestQueue requestQueue;
    JSONArray jsonArray;

    Context context;
    public WebService(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void WebServiceUsuario(){
        dbUsuario = new DatabaseManagerUsuario(context);
        final String QUERY = "call SP_USUARIO('" + ACCION  + "',0,'','',0,'');";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER + "login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dbUsuario.eliminarTodo();
                if (response.equals("[]") || response.equals("")){
                    //Toast.makeText(context, "No se encontraron datos SP_EMPRESA", Toast.LENGTH_LONG).show();
                }else{
                    try {
                        UsuarioBean bean  = null;
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = null;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonObject = jsonArray.getJSONObject(i);
                            bean = new UsuarioBean();
                            bean.setID(jsonObject.getString("ID"));
                            bean.setNOMBRES(jsonObject.getString("NOMBRES"));
                            bean.setAPELLIDOS(jsonObject.getString("APELLIDOS"));
                            bean.setCORREO(jsonObject.getString("CORREO"));
                            bean.setTELEFONO(jsonObject.getString("TELEFONO"));
                            bean.setTYPE_CODE(jsonObject.getString("TYPE_CODE"));
                            bean.setIAT(jsonObject.getString("IAT"));
                            bean.setEXP(jsonObject.getString("EXP"));
                            dbUsuario.insertar(bean);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(context, "Error en el registro json USUARIO: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error servicio USUARIO: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("consulta",QUERY);
                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

}
