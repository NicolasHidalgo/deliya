package com.example.deliya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import beans.CarritoDetalleBean;
import beans.ProductoBean;
import beans.UsuarioBean;
import helper.Session;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliya.adaptadores.AdapterCarritoDetalle;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    TextView txtUsuarioNombre;
    private Session session;
    Context context;
    TextView txtIdProductoSeleccionado, txtDireccionMenu;
    ProductoBean productoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        productoBean = new ProductoBean();
        session = new Session(this);
        context = this;

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

        txtUsuarioNombre = findViewById(R.id.txtUsuarioNombre);
        txtDireccionMenu = findViewById(R.id.txtDireccionMenu);
        UsuarioBean user = session.getUsuario();

        txtUsuarioNombre.setText(user.getNOMBRES());
        txtDireccionMenu.setText(user.getDIRECCION());

    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        List<Fragment> list = fragmentManager.getFragments();
        String fragmentTag = list.get(list.size() - 1).getClass().getSimpleName();

        if (fragmentTag.equals("LocalesFragment")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
        }else if (fragmentTag.equals("ProductosFragment")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new LocalesFragment()).commit();
        }else if (fragmentTag.equals("ProductoSeleccionadoFragment")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProductosFragment()).commit();
        }else if (fragmentTag.equals("CarritoFragment")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ProductoSeleccionadoFragment()).commit();
        }else if (fragmentTag.equals("ResumenPedidoFragment")){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CarritoFragment()).commit();
        }else{
            super.onBackPressed();
        }


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
                        case R.id.nav_carrito:
                            selectedFragment = new CarritoFragment();
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

    public void btnFOOD(View view){
        session.setCategoria("FOOD");
        Fragment selectedFragment = null;
        selectedFragment = new LocalesFragment();
        getSupportFragmentManager() .beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }

    public void btnSeleccionarCategoria(View view){

        switch (view.getId()){
            case R.id.imgFOOD:
                session.setCategoria("FOOD");
                break;
            case R.id.imgSTORES:
                session.setCategoria("STORES");
                break;
            case R.id.imgDRUGSTORE:
                session.setCategoria("DRUGSTORE");
                break;
            case R.id.imgOTHERS:
                session.setCategoria("OTHERS");
                break;

        }
        Fragment selectedFragment = null;
        selectedFragment = new LocalesFragment();
        getSupportFragmentManager() .beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }


    public void btnAgregarProducto(View view){

        List<CarritoDetalleBean> listaCarrito;
        if (session.getCarritoDetalle() != null){
            listaCarrito = session.getCarritoDetalle();
        }else{
            listaCarrito = new ArrayList<>();
        }

        txtIdProductoSeleccionado = findViewById(R.id.txtIdProductoSeleccionado);
        session.setIdProductoSeleccionado(txtIdProductoSeleccionado.getText().toString());

        String IdProducto = session.getIdProductoSeleccionado();
        boolean esRepetido = false;
        for (CarritoDetalleBean obj:listaCarrito) {
            if (obj.getProductoBean().getID().equals(IdProducto)){
                esRepetido = true;
            }
        }

        if (!esRepetido){
            ProductoBean producto = productoBean.getProducto(IdProducto,session.getProductos());

            CarritoDetalleBean carritoDetalleBean = new CarritoDetalleBean();
            carritoDetalleBean.setProductoBean(producto);
            carritoDetalleBean.setCantidad(1);
            listaCarrito.add(carritoDetalleBean);

            session.setCarritoDetalle(listaCarrito);
        }

        Fragment selectedFragment = null;
        selectedFragment = new CarritoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }

    public void btnVaciarCarrito(View view){

        if (session.getCarritoDetalle() == null){
            Toast.makeText(this,"Su carrito ya está vacío.", Toast.LENGTH_LONG).show();
            return;
        }

        session.setCarritoDetalle(null);
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCarrito);
        recyclerView.setAdapter(null);
    }
    public void btnContinuarAResumenPedido(View view){

        if (session.getCarritoDetalle() == null){
            Toast.makeText(this,"Su carrito está vacío. No puede continuar", Toast.LENGTH_LONG).show();
            return;
        }

        TextView cant, price;
        Double totalPagar = 0.0;
        RecyclerView recyclerView = findViewById(R.id.recyclerViewCarrito);
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View v = recyclerView.findViewHolderForAdapterPosition(i).itemView;
            cant = v.findViewById(R.id.quantity_carrito);
            price = v.findViewById(R.id.precio_carrito);

            String precio = price.getText().toString();
            String number = precio.replaceAll("[^0-9?!\\.]","");
            Double dprecio = Double.parseDouble(number);
            Integer cantidad = Integer.parseInt(cant.getText().toString());

            Double importe = dprecio * cantidad;
            totalPagar = totalPagar + importe;
        }
        session.setTotalPagar(totalPagar.toString());


        Fragment selectedFragment = null;
        selectedFragment = new ResumenPedidoFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                selectedFragment).commit();
    }

    public void btnRealizarPedido(View view){
        Toast.makeText(this,"Pedido realizado con éxito.", Toast.LENGTH_LONG).show();

        final int millisUntilLaunch = 5000;
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Fragment selectedFragment = null;
                selectedFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
            }
        }, millisUntilLaunch);

    }
}
