package com.example.deliya;

import androidx.appcompat.app.AppCompatActivity;
import beans.UsuarioBean;
import db.DatabaseManagerUsuario;
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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RecuperarPasswordActivity extends AppCompatActivity {

    EditText txtTokenEnviado, txtNuevoPassword, txtConfirmarPassword;
    private Session session;

    public static final String SERVER = "http://3.137.143.14:3000/";
    Context context;

    RequestQueue requestQueue;
    ProgressBar progressBar;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);

        txtTokenEnviado = findViewById(R.id.txtTokenEnviado);
        txtNuevoPassword = findViewById(R.id.txtNuevoPassword);
        txtConfirmarPassword = findViewById(R.id.txtConfirmarPassword);

        session = new Session(this);
        context = this;

        Intent myIntent = getIntent(); // gets the previously created intent
        userId = myIntent.getStringExtra("userId");

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

    public void btnRecuperarPassword(View view) {

        final String tokenEnviado = txtTokenEnviado.getText().toString();
        final String nuevoPassword = txtNuevoPassword.getText().toString();
        final String confirmarPassword = txtConfirmarPassword.getText().toString();

        if (tokenEnviado.equals("")){
            Toast.makeText(this,"Debe ingresar el token", Toast.LENGTH_LONG).show();
            return;
        }
        if (nuevoPassword.equals("")){
            Toast.makeText(this,"Debe ingresar su nueva contraseña.", Toast.LENGTH_LONG).show();
            return;
        }
        if (confirmarPassword.equals("")){
            Toast.makeText(this,"Debe repetir su contraseña.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!nuevoPassword.equals(confirmarPassword)){
            Toast.makeText(this,"Su contraseña no coincide.", Toast.LENGTH_LONG).show();
            return;
        }

        OpenProgressBar();
        String ACCION = "users/updatePassword";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, SERVER + ACCION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("[]")){
                        CloseProgressBar();
                        Toast.makeText(context, "No se encontraron datos", Toast.LENGTH_LONG).show();
                    }else if (response.equals("")){
                        CloseProgressBar();
                        Toast.makeText(context, "No se encontraron datos", Toast.LENGTH_LONG).show();
                    }else{
                        JSONObject jsonObject = new JSONObject(response);
                        String message = jsonObject.getString("message");

                        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                        if (message.contains("actualizada")){
                            Intent k = new Intent(getApplicationContext(), LoginActivity.class);
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
                parametros.put("userId", userId);
                parametros.put("token",tokenEnviado);
                parametros.put("newPassword",nuevoPassword);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}
