package com.example.deliya;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.deliya.adaptadores.AdapterLocal;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import beans.TiendaBean;
import helper.Session;

public class LocalesFragment extends Fragment {

    Context context;
    Session session;
    //List<TiendaBean> listaTienda;
    ListView lvTienda;

    AdapterLocal adapterLocal;
    RecyclerView recyclerViewLocal;
    List<TiendaBean> listaTienda;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locales, container, false);
        session = new Session(getActivity());

        recyclerViewLocal = view.findViewById(R.id.recyclerViewLocales);
        listaTienda = new ArrayList<>();
        cargarLista();
        mostrarData();
        return view;
    }

    public void cargarLista(){
        listaTienda = session.getTiendas();
    }

    public void mostrarData(){
        recyclerViewLocal.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterLocal = new AdapterLocal(getContext(), listaTienda);
        recyclerViewLocal.setAdapter(adapterLocal);

        adapterLocal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String nombre = listaTienda.get(recyclerViewLocal.getChildAdapterPosition(v)).getRAZON_SOCIAL();
                Toast.makeText(getContext(), "Selecciono: " + nombre, Toast.LENGTH_SHORT).show();
            }
        });
    }

}