package com.example.deliya;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliya.adaptadores.AdapterProducto;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import beans.ProductoBean;
import helper.Session;

public class ProductoSeleccionadoFragment extends Fragment {

    Context context;
    Session session;

    AdapterProducto adapter;
    RecyclerView recyclerView;
    List<ProductoBean> listaProductos;
    ProductoBean productoBean;

    ImageView imagenProductoSeleccionado;
    TextView txtIdProductoSeleccionado,txtProductoNombreSeleccionado, txtProductoDescripcionSeleccionado;
    Button btnAgregarProducto;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto_seleccionado, container, false);
        session = new Session(getActivity());

        productoBean = new ProductoBean();
        imagenProductoSeleccionado = view.findViewById(R.id.imagenProductoSeleccionado);
        txtIdProductoSeleccionado = view.findViewById(R.id.txtIdProductoSeleccionado);
        txtProductoNombreSeleccionado = view.findViewById(R.id.txtProductoNombreSeleccionado);
        txtProductoDescripcionSeleccionado = view.findViewById(R.id.txtProductoDescripcionSeleccionado);
        btnAgregarProducto = view.findViewById(R.id.btnAgregarProducto);

        String IdProducto = session.getIdProducto();
        listaProductos = session.getProductos();
        ProductoBean bean = productoBean.getProducto(IdProducto, listaProductos);

        if (bean != null){
            imagenProductoSeleccionado.setImageResource(bean.getIMAGEN_ID());
            txtIdProductoSeleccionado.setText(bean.getID());
            txtProductoNombreSeleccionado.setText(bean.getNOMBRE());
            txtProductoDescripcionSeleccionado.setText(bean.getDESCRIPCION());

        }
        return view;
    }




}
