package com.example.deliya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import beans.UsuarioBean;
import helper.Session;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {

    TextView txtUsuarioNombre;
    private Session session;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        session = new Session(this);
        context = this;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        txtUsuarioNombre = findViewById(R.id.txtUsuarioNombre);
        UsuarioBean user = session.getUsuario();

        txtUsuarioNombre.setText(user.getNOMBRES());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_buscar:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_promo:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_cuenta:
                            selectedFragment = new HomeFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void btnComida(View view){
        Fragment selectedFragment = null;
        selectedFragment = new LocalesFragment();
        getSupportFragmentManager() .beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }
}
