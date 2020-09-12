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

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import beans.TiendaBean;
import helper.Session;

public class LocalesFragment extends Fragment {

    Context context;
    Session session;
    List<TiendaBean> listaTienda;
    ListView lvTienda;
    LocalesFragment.MyAdapter adapter;
    RequestQueue requestQueue;
    ProgressBar progressBar;

    String nInfoID[];
    String nInfo1[];
    String nInfo2[];
    String nInfo3[];

    @Nullable
    @Override
    public android.view.View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_locales, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.INVISIBLE);
        Listar();
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context context;

        String nID[];
        String nInfo1[];
        String nInfo2[];
        String nInfo3[];

        MyAdapter(Context c, String id[], String nInfo1[], String nInfo2[], String nInfo3[]){
            super(c,R.layout.row_tienda, R.id.txtInfoTiendaId, id);
            this.context = c;
            this.nID = id;
            this.nInfo1 = nInfo1;
            this.nInfo2 = nInfo2;
            this.nInfo3 = nInfo3;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_tienda, parent, false);
            TextView id = row.findViewById(R.id.txtInfoTiendaId);
            TextView tit = row.findViewById(R.id.txtInfoTienda1);
            TextView sub = row.findViewById(R.id.txtInfoTienda2);
            TextView inf = row.findViewById(R.id.txtInfoTienda3);

            id.setText(nID[position]);
            tit.setText(nInfo1[position]);
            sub.setText(nInfo2[position]);
            String estado = nInfo3[position];

            return row;
        }
    }

    public void Listar(){

        listaTienda = new ArrayList<>();
        TiendaBean obj = new TiendaBean();
        obj.setID("1");
        obj.setNOMBRE("KFC");
        obj.setRUC("12345678");
        obj.setDESCRIPCION("Lalalal");
        listaTienda.add(obj);
        obj.setID("2");
        obj.setNOMBRE("BEMBOS");
        obj.setRUC("12345678");
        obj.setDESCRIPCION("Lalalal");
        listaTienda.add(obj);
        obj.setID("3");
        obj.setNOMBRE("DOLOR");
        obj.setRUC("12345678");
        obj.setDESCRIPCION("Lalalal");
        listaTienda.add(obj);

        TiendaBean bean = null;
        int len = listaTienda.size();
        nInfoID = new String[len];
        nInfo1 = new String[len];
        nInfo2 = new String[len];
        nInfo3 = new String[len];

        for (int i = 0; i < listaTienda.size(); i++) {
            bean = listaTienda.get(i);
            nInfoID[i] = bean.getID();
            nInfo1[i] = bean.getNOMBRE();
            nInfo2[i] = bean.getRUC();
            nInfo3[i] = bean.getDESCRIPCION();
        }
        adapter = new LocalesFragment.MyAdapter(getContext(), nInfoID, nInfo1, nInfo2, nInfo3);
        lvTienda.setAdapter(adapter);
    }
}