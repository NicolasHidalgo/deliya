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

public class AdapterCarritoDetalle extends RecyclerView.Adapter<AdapterCarritoDetalle.ViewHolder> implements RecyclerViewClickListener {

    LayoutInflater inflater;
    List<CarritoDetalleBean> model;

    //listener
    private RecyclerViewClickListener listener;

    public AdapterCarritoDetalle(Context context, List<CarritoDetalleBean> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public AdapterCarritoDetalle.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_carrito, parent, false);
        //view.setOnClickListener(this);
        return new AdapterCarritoDetalle.ViewHolder(view);
    }

    public void setOnClickListener(RecyclerViewClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCarritoDetalle.ViewHolder holder, int position) {
        String nombre = model.get(position).getProductoBean().getNOMBRE();
        String precio = model.get(position).getProductoBean().getPRECIO();
        String cantidad = String.valueOf(model.get(position).getCantidad());

        Double _precio = Double.parseDouble(precio);
        String precioformateado = String.format("%.2f", _precio);

        int imagen = model.get(position).getProductoBean().getIMAGEN_ID();
        holder.nombres.setText(nombre);
        holder.precio.setText("S/ " + precioformateado);
        holder.cantidad.setText(cantidad);
        holder.imagen.setImageResource(imagen);

    }


    @Override
    public int getItemCount() {
        return model.size();
    }

    @Override
    public void onClick(View v, int position) {
        if (listener != null){
            listener.onClick(v, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nombres, precio, cantidad;
        ImageView imagen, btnMas, btnMenos;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nombres = itemView.findViewById(R.id.nombre_carrito);
            precio = itemView.findViewById(R.id.precio_carrito);
            cantidad = itemView.findViewById(R.id.quantity_carrito);
            imagen = itemView.findViewById(R.id.imagen_carrito);
            btnMas = itemView.findViewById(R.id.btnMas);
            btnMenos = itemView.findViewById(R.id.btnMenos);

            btnMenos.setOnClickListener(this);
            btnMas.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }

    }

}

