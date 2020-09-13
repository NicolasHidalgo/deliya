package com.example.deliya;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.deliya.adaptadores.AdapterLocal;
import com.example.deliya.adaptadores.AdapterProducto;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import beans.ProductoBean;
import beans.TiendaBean;
import helper.Session;

public class ProductosFragment extends Fragment {

    Context context;
    Session session;

    AdapterProducto adapter;
    RecyclerView recyclerView;
    List<ProductoBean> listaProductos;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos, container, false);
        session = new Session(getActivity());

        recyclerView = view.findViewById(R.id.recyclerViewProductos);
        listaProductos = new ArrayList<>();
        cargarLista();
        mostrarData();
        return view;
    }

    public void cargarLista(){
        String IdStore = session.getIdStore();
        List<ProductoBean> data = session.getProductos();
        ProductoBean bean = null;
        for (ProductoBean obj: data) {
            if (obj.getID_STORE().equals(IdStore)){
                bean = new ProductoBean();
                bean.setID(obj.getID());
                bean.setID_STORE(obj.getID_STORE());
                bean.setSKU(obj.getSKU());
                bean.setNOMBRE(obj.getNOMBRE());
                bean.setPRECIO(obj.getPRECIO());
                bean.setDESCRIPCION(obj.getDESCRIPCION());
                bean.setIMAGEN_ID(obj.getIMAGEN_ID());
                listaProductos.add(obj);
            }
        }
    }

    public void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterProducto(getContext(), listaProductos);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String nombre = listaProductos.get(recyclerView.getChildAdapterPosition(v)).getNOMBRE();
                Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();

            }
        });
    }

}
