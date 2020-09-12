package com.example.deliya;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.auth0.android.jwt.JWT;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import beans.UsuarioBean;
import db.DatabaseManagerUsuario;
import helper.ConnectivityReceiver;

public class SignUpActivity extends AppCompatActivity {

    Button btnSignUp;
    EditText textPersonName, textPersonLastname, textPersonPhone, textPersonEmail,
            txtPassword, txtConfirmPassword;
    CheckBox chbxTermCondition;
    Context context;
    public static final String SERVER = "http://3.137.143.14:3000/";
    ProgressBar progressBar;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }



    @SuppressLint("WrongViewCast")
    public void btnSignUp(View view) {

        textPersonName = (EditText) findViewById(R.id.textPersonName);
        textPersonLastname = (EditText) findViewById(R.id.textPersonLastname);
        textPersonPhone = (EditText) findViewById(R.id.textPersonPhone);
        textPersonEmail = (EditText) findViewById(R.id.textPersonEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        chbxTermCondition = (CheckBox) findViewById(R.id.chbxTermCondition);

        final String Name = textPersonName.getText().toString();
        final String Lastname = textPersonLastname.getText().toString();
        final String Phone = textPersonPhone.getText().toString();
        final String Email = textPersonEmail.getText().toString();
        final String Password = txtPassword.getText().toString();
        final String ConfirmPassword = txtConfirmPassword.getText().toString();
        final Boolean TermCondition = chbxTermCondition.isChecked();

        if (Name.isEmpty()){
            Toast.makeText(this, "Debe ingresar el campo nombres", Toast.LENGTH_LONG).show();
            return;
        }

        if (Lastname.isEmpty()){
            Toast.makeText(this, "Debe ingresar el campo apellidos", Toast.LENGTH_LONG).show();
            return;
        }

        if (Phone.isEmpty()){
            Toast.makeText(this, "Debe ingresar el campo telefono", Toast.LENGTH_LONG).show();
            return;
        }

        if (Email.isEmpty()){
            Toast.makeText(this, "Debe ingresar el campo email", Toast.LENGTH_LONG).show();
            return;
        }

        if (Password.isEmpty()){
            Toast.makeText(this, "Debe ingresar el campo contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        System.out.println(Password);
        System.out.println(ConfirmPassword);
        if (!ConfirmPassword.equals(Password)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            return;
        }

        if (TermCondition == false){
            Toast.makeText(this, "Debe aceptar los términos y condiciones", Toast.LENGTH_LONG).show();
            return;
        }


        if(!(ConnectivityReceiver.isConnected(context))){
            Toast.makeText(this, "Necesitas contectarte a internet para continuar", Toast.LENGTH_LONG).show();
            return;
        }

        //OpenProgressBar();
        //dbUsuario = new DatabaseManagerUsuario(context);
        String ACCION = "users";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER + ACCION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //dbUsuario.eliminarPorUsuarioPassword(Usuario,Password);
                    //dbUsuario.eliminarTodo();
                    if (response.equals("[]")){
                        //CloseProgressBar();
                        Toast.makeText(context, "Datos incorrectos o usuario ya éxiste.", Toast.LENGTH_LONG).show();
                    }else{
                        UsuarioBean bean  = null;
                        //JSONArray jsonArray = new JSONArray(response);
                        //JSONObject jsonObject = new JSONObject(response);
                       // String token = jsonObject.getString("token");
                        //jsonObject = jsonArray.getJSONObject(i);
                        //token = jsonObject.getString("token");

                        //session.setIdUsuario(bean.getID());
                        //session.setIdRol(bean.getID_ROL());

                        //JWT jwt = new JWT(token);


                        Intent k = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(k);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //CloseProgressBar();
                Toast.makeText(getApplicationContext(), "Fallo el servicio de Registro SP_USUARIO: " + error.getMessage().toString() , Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<>();
                parametros.put("names",Name);
                parametros.put("lastnames",Lastname);
                parametros.put("cellphone",Phone);
                parametros.put("userTypeCode","CUST");
                parametros.put("email",Email);
                parametros.put("password",Password);
                return parametros;
            }
        };
        requestQueue.add(stringRequest);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
}
