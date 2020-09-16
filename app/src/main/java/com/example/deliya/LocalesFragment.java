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
import beans.ProductoBean;
import beans.TiendaBean;
import helper.Session;

public class LocalesFragment extends Fragment {

    Context context;
    Session session;

    AdapterLocal adapter;
    RecyclerView recyclerView;
    List<TiendaBean> listaTienda;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_locales, container, false);
        session = new Session(getActivity());

        recyclerView = view.findViewById(R.id.recyclerViewLocales);
        listaTienda = new ArrayList<>();
        cargarLista();
        mostrarData();
        return view;
    }


    public void cargarLista(){
        String Categoria = session.getCategoria();
        List<TiendaBean> data = session.getTiendas();
        TiendaBean bean = null;
        for (TiendaBean obj: data) {
            if (obj.getCOD_CATEGORIA().equals(Categoria)){
                bean = new TiendaBean();
                bean.setID(obj.getID());
                bean.setRUC(obj.getRUC());
                bean.setRAZON_SOCIAL(obj.getRAZON_SOCIAL());
                bean.setDESCRIPCION(obj.getDESCRIPCION());
                bean.setDIRECCION(obj.getDIRECCION());
                bean.setEMAIL(obj.getEMAIL());
                bean.setTELEFONO(obj.getTELEFONO());
                bean.setUBIGEO(obj.getUBIGEO());
                bean.setCOD_CATEGORIA(obj.getCOD_CATEGORIA());
                bean.setLATITUD(obj.getLATITUD());
                bean.setLONGITUD(obj.getLONGITUD());
                bean.setIMAGEN_ID(obj.getIMAGEN_ID());
                listaTienda.add(obj);
            }
        }
    }

    public void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterLocal(getContext(), listaTienda);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String Id = listaTienda.get(recyclerView.getChildAdapterPosition(v)).getID();
                session.setIdStore(Id);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProductosFragment()).commit();
            }
        });
    }

}