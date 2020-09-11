package com.example.deliya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SolicitarRecuperarPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_recuperar_password);
    }

    public void btnSolicitarCodigo(View view) {
        Intent k = new Intent(getApplicationContext(), RecuperarPasswordActivity.class);
        startActivity(k);
    }

}
