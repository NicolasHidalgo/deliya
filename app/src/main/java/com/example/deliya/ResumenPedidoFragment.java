package com.example.deliya;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deliya.adaptadores.AdapterProducto;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import beans.ProductoBean;
import beans.UsuarioBean;
import helper.Session;

public class ResumenPedidoFragment extends Fragment {

    Context context;
    Session session;
    TextView txtTotalPagar, txtDireccionPedido;

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_resumen_pedido, container, false);
        session = new Session(getActivity());

        txtTotalPagar = view.findViewById(R.id.txtTotalPagar);
        txtTotalPagar.setText("S/. " + session.getTotalPagar());

        txtDireccionPedido = view.findViewById(R.id.txtDireccionPedido);
        UsuarioBean user = session.getUsuario();

        txtDireccionPedido.setText("Calle " + user.getDIRECCION());

        return view;
    }

}
