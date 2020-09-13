package com.example.deliya.adaptadores;

import android.content.Context;
import android.text.Layout;
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
import beans.TiendaBean;

public class AdapterLocal extends RecyclerView.Adapter<AdapterLocal.ViewHolder> implements View.OnClickListener {

    LayoutInflater inflater;
    List<TiendaBean> model;

    //listener
    private View.OnClickListener listener;

    public AdapterLocal(Context context, List<TiendaBean> model){
        this.inflater = LayoutInflater.from(context);
        this.model = model;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_locales, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String nombre = model.get(position).getRAZON_SOCIAL();
        String descripcion = model.get(position).getDESCRIPCION();
        int imagen = model.get(position).getIMAGEN_ID();
        holder.nombres.setText(nombre);
        holder.descripcion.setText(descripcion);
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

        TextView nombres, descripcion;
        ImageView imagen;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            nombres = itemView.findViewById(R.id.nombre_local);
            descripcion = itemView.findViewById(R.id.descripcion_local);
            imagen = itemView.findViewById(R.id.imagen_local);

        }

    }

}
