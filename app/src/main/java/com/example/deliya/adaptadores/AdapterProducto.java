package com.example.deliya.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliya.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import beans.ProductoBean;
import beans.TiendaBean;

public class AdapterProducto extends RecyclerView.Adapter<AdapterProducto.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    List<ProductoBean> model;

    //listener
    private View.OnClickListener listener;

    public AdapterProducto(Context context, List<ProductoBean> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public AdapterProducto.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_productos, parent, false);
        view.setOnClickListener(this);
        return new AdapterProducto.ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterProducto.ViewHolder holder, int position) {
        String nombre = model.get(position).getNOMBRE();
        String descripcion = model.get(position).getDESCRIPCION();
        String precio = model.get(position).getPRECIO();
        int imagen = model.get(position).getIMAGEN_ID();

        Double _precio = Double.parseDouble(precio);
        String precioformateado = String.format("%.2f", _precio);

        holder.nombres.setText(nombre);
        holder.descripcion.setText(descripcion);
        holder.precio.setText("S/ " + precioformateado);
        holder.imagen.setImageResource(imagen);

    }


    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nombres, descripcion, precio;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nombres = itemView.findViewById(R.id.nombre_producto);
            descripcion = itemView.findViewById(R.id.descripcion_producto);
            precio = itemView.findViewById(R.id.precio_producto);
            imagen = itemView.findViewById(R.id.imagen_producto);

        }

    }

}

