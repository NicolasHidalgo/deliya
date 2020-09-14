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

    ImageView imagenProductoSeleccionado;
    TextView txtProductoNombreSeleccionado, txtProductoDescripcionSeleccionado;
    Button btnAgregarProducto;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_producto_seleccionado, container, false);
        session = new Session(getActivity());

        imagenProductoSeleccionado = view.findViewById(R.id.imagenProductoSeleccionado);
        txtProductoNombreSeleccionado = view.findViewById(R.id.txtProductoNombreSeleccionado);
        txtProductoDescripcionSeleccionado = view.findViewById(R.id.txtProductoDescripcionSeleccionado);
        btnAgregarProducto = view.findViewById(R.id.btnAgregarProducto);

        String IdProducto = session.getIdProducto();
        listaProductos = session.getProductos();
        ProductoBean bean = getProducto(IdProducto);

        if (bean != null){
            imagenProductoSeleccionado.setImageResource(bean.getIMAGEN_ID());
            txtProductoNombreSeleccionado.setText(bean.getNOMBRE());
            txtProductoDescripcionSeleccionado.setText(bean.getDESCRIPCION());

        }
        return view;
    }

    public ProductoBean getProducto(String IdProducto){
        ProductoBean bean = null;
        for (ProductoBean obj: listaProductos) {
            String _id = obj.getID();
            if (_id.equals(IdProducto)){
                bean = new ProductoBean();
                bean.setID(obj.getID());
                bean.setID_STORE(obj.getID_STORE());
                bean.setNOMBRE(obj.getNOMBRE());
                bean.setDESCRIPCION(obj.getDESCRIPCION());
                bean.setSKU(obj.getSKU());
                bean.setPRECIO(obj.getPRECIO());
                bean.setIMAGEN_ID(obj.getIMAGEN_ID());
            }
        }
        return bean;
    }
}
