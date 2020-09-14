package com.example.deliya.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.deliya.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import beans.CarritoDetalleBean;
import beans.TiendaBean;

public class AdapterCarritoDetalle extends RecyclerView.Adapter<AdapterCarritoDetalle.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    List<CarritoDetalleBean> model;

    //listener
    private View.OnClickListener listener;

    public AdapterCarritoDetalle(Context context, List<CarritoDetalleBean> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public AdapterCarritoDetalle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_carrito, parent, false);
        view.setOnClickListener(this);
        return new AdapterCarritoDetalle.ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarritoDetalle.ViewHolder holder, int position) {
        String nombre = model.get(position).getProductoBean().getNOMBRE();
        String precio = model.get(position).getProductoBean().getPRECIO();
        int imagen = model.get(position).getProductoBean().getIMAGEN_ID();
        holder.nombres.setText(nombre);
        holder.precio.setText(precio);
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

        TextView nombres, precio;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nombres = itemView.findViewById(R.id.nombre_carrito);
            precio = itemView.findViewById(R.id.precio_carrito);
            imagen = itemView.findViewById(R.id.imagen_carrito);

        }

    }

}

