package com.example.deliya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import beans.CarritoDetalleBean;
import beans.ProductoBean;
import beans.TestBean;
import beans.UsuarioBean;
import helper.Session;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.deliya.adaptadores.AdapterCarritoDetalle;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    TextView txtUsuarioNombre;
    private Session session;
    Context context;
    TextView txtIdProductoSeleccionado, txtDireccionMenu;
    ProductoBean productoBean;

    public static final String SERVER = "http://3.137.143.14:3000/";
    RequestQueue requestQueue;
    ProgressBar progressBar;

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

    public void btnRealizarPedido(View view){

        OpenProgressBar();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();

        UsuarioBean usuarioBean = session.getUsuario();
        ArrayList<TestBean> orders = new ArrayList<>();
        TestBean testBean = new TestBean();
        testBean.productId = "1";
        testBean.quantity = "3";
        orders.add(testBean);

        TestBean testBean2 = new TestBean();
        testBean2.productId = "2";
        testBean2.quantity = "4";
        orders.add(testBean2);

        try {
            JSONArray array=new JSONArray();
            for (TestBean item :orders) {
                JSONObject obj=new JSONObject();
                obj.put("productId",item.productId);
                obj.put("quantity",item.quantity);
                array.put(obj);
            }

            object.put("storeId","1");
            object.put("userId",usuarioBean.getID());
            object.put("orderItems",array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Enter the correct url for your api service site
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SERVER + "orders", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String message = null;
                        try {
                            message = response.getString("message");
                            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        CloseProgressBar();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CloseProgressBar();
                Toast.makeText(context, "Error en el registro: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers =  new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + session.getToken());
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

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
