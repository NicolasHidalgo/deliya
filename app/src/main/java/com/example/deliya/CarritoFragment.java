package com.example.deliya;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.deliya.adaptadores.AdapterCarritoDetalle;
import com.example.deliya.adaptadores.AdapterLocal;
import com.example.deliya.adaptadores.RecyclerViewClickListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import beans.CarritoDetalleBean;
import beans.TiendaBean;
import helper.Session;

public class CarritoFragment extends Fragment {

    Context context;
    Session session;

    AdapterCarritoDetalle adapter;
    RecyclerView recyclerView;
    List<CarritoDetalleBean> listaCarritoDetalle;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);
        session = new Session(getActivity());

        recyclerView = view.findViewById(R.id.recyclerViewCarrito);
        listaCarritoDetalle = new ArrayList<>();
        cargarLista();
        mostrarData();
        return view;
    }

    public void cargarLista(){
        listaCarritoDetalle = session.getCarritoDetalle();
    }

    public void mostrarData(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdapterCarritoDetalle(getContext(), listaCarritoDetalle);
        recyclerView.setAdapter(adapter);

        adapter.setOnClickListener(new RecyclerViewClickListener(){

            TextView cant;
            @Override
            public void onClick(View view, int position) {
                //Integer cantidad = listaCarritoDetalle.get(position).getCantidad();
                //View v = recyclerView.getLayoutManager().findViewByPosition(position);
                View v = recyclerView.findViewHolderForAdapterPosition(position).itemView;
                cant = v.findViewById(R.id.quantity_carrito);
                String cantidad = cant.getText().toString();
                Integer quantity = Integer.parseInt(cantidad);

                switch (view.getId()){
                    case R.id.btnMas:
                        quantity = quantity + 1;
                        break;
                    case R.id.btnMenos:
                        if (quantity <= 1){
                            break;
                        }
                        quantity = quantity - 1;
                        break;
                }

                listaCarritoDetalle.get(position).setCantidad(quantity);
                session.setCarritoDetalle(listaCarritoDetalle);
                cant.setText(quantity.toString());
            }


        });
    }

}
