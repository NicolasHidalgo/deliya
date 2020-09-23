package com.example.deliya;

import androidx.appcompat.app.AppCompatActivity;
import beans.UsuarioBean;
import db.DatabaseManagerUsuario;
import helper.Session;
import helper.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.auth0.android.jwt.JWT;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SolicitarRecuperarPasswordActivity extends AppCompatActivity {

    EditText txtCorreoSolicitud;
    private Session session;

    public static final String SERVER = "http://3.137.143.14:3000/";
    Context context;

    RequestQueue requestQueue;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_recuperar_password);

        txtCorreoSolicitud = findViewById(R.id.txtCorreoSolicitud);

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

    public void btnSolicitarCodigo(View view) {

        final String correoSolicitud = txtCorreoSolicitud.getText().toString();
        if (correoSolicitud.equals("")){
            Toast.makeText(this,"Debe ingresar un correo.", Toast.LENGTH_LONG).show();
            return;
        }

        if(!(Util.isValidEmail(correoSolicitud))){
            Toast.makeText(this,"Debe ingresar un correo válido.", Toast.LENGTH_LONG).show();
            return;
        }

        OpenProgressBar();
        String ACCION = "users/requestNewPassword";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, SERVER + ACCION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("[]")){
                        CloseProgressBar();
                        Toast.makeText(context, "Correo inválido", Toast.LENGTH_LONG).show();
                    }else if (response.equals("")){
                        CloseProgressBar();
                        Toast.makeText(context, "No se encontraron datos", Toast.LENGTH_LONG).show();
                    }else{
                        JSONObject jsonObject = new JSONObject(response);
                        String userId = jsonObject.getString("userId");
                        String message = jsonObject.getString("message");

                        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                        if (message.equals("Token enviado")){
                            Intent k = new Intent(getApplicationContext(), RecuperarPasswordActivity.class);
                            k.putExtra("userId",userId);
                            startActivity(k);
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CloseProgressBar();
                if(error.getMessage() != null){
                    Toast.makeText(getApplicationContext(), "Fallo el servicio: " + error.getMessage().toString() , Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Usuario no encontrado. ", Toast.LENGTH_LONG).show();
                }


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("username",correoSolicitud);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));



    }

}
