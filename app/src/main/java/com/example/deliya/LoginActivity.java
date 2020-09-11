package com.example.deliya;

import androidx.appcompat.app.AppCompatActivity;
import beans.UsuarioBean;
import db.DatabaseManagerUsuario;
import helper.ConnectivityReceiver;
import helper.Session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.android.jwt.JWT;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btnIngresar;
    EditText txtUsuario, txtPassword;
    private Session session;

    public static final String SERVER = "http://3.137.143.14:3000/";
    public String QUERY = "";
    public String TABLA = "";
    Context context;

    DatabaseManagerUsuario dbUsuario;
    RequestQueue requestQueue;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new Session(this);
        context = this;

        requestQueue = Volley.newRequestQueue(context);
        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            try {
                ProviderInstaller.installIfNeeded(getApplicationContext());
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }

        ProgressBarHandler(context);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void CloseProgressBar(){
        progressBar.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    public void OpenProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    public void ProgressBarHandler(Context context) {
        ViewGroup layout = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        RelativeLayout rl = new RelativeLayout(context);
        rl.setGravity(Gravity.CENTER);
        rl.addView(progressBar);
        layout.addView(rl, params);
    }

    public void btnIngresar(View view) {

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        final String Usuario = txtUsuario.getText().toString();
        final String Password = txtPassword.getText().toString();

        if (Usuario.isEmpty()){
            Toast.makeText(this, "Debe ingresar Usuario y Clave", Toast.LENGTH_LONG).show();
            return;
        }

        if (Password.isEmpty()){
            Toast.makeText(this, "Debe ingresar Usuario y Clave", Toast.LENGTH_LONG).show();
            return;
        }

        if(!(ConnectivityReceiver.isConnected(context))){
            Toast.makeText(this, "Necesitas contectarte a internet para continuar", Toast.LENGTH_LONG).show();
            return;
        }

        OpenProgressBar();
        dbUsuario = new DatabaseManagerUsuario(context);
        String ACCION = "login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER + ACCION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //dbUsuario.eliminarPorUsuarioPassword(Usuario,Password);
                    //dbUsuario.eliminarTodo();
                    if (response.equals("[]")){
                        CloseProgressBar();
                        Toast.makeText(context, "Usuario y Password invalidos", Toast.LENGTH_LONG).show();
                    }else if (response.equals("")){
                        CloseProgressBar();
                        Toast.makeText(context, "No se encontraron datos SP_USUARIO", Toast.LENGTH_LONG).show();
                    }else{
                        UsuarioBean bean  = null;
                        //JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = new JSONObject(response);
                        String token = jsonObject.getString("token");
                        //jsonObject = jsonArray.getJSONObject(i);
                        //token = jsonObject.getString("token");

                        //session.setIdUsuario(bean.getID());
                        //session.setIdRol(bean.getID_ROL());

                        JWT jwt = new JWT(token);


                        Intent k = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(k);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CloseProgressBar();
                Toast.makeText(getApplicationContext(), "Fallo el servicio de Login SP_USUARIO: " + error.getMessage().toString() , Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("username",Usuario);
                parametros.put("password",Password);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }

    public void btnRegistrarse(View view) {
        Intent k = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(k);
    }

    public void btnSolicitarRecuperarPassword(View view) {
        Intent k = new Intent(getApplicationContext(), SolicitarRecuperarPasswordActivity.class);
        startActivity(k);
    }
}