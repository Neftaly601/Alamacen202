package com.almacen.alamacen202.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ListLiberaSandG;
import com.almacen.alamacen202.SetterandGetters.ListReceSandG;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorListFoliosRecepcion extends RecyclerView.Adapter<AdaptadorListFoliosRecepcion.ViewHolderListFoli> implements View.OnClickListener {

    ArrayList<ListReceSandG> listaFolios;
    private View.OnClickListener listener;

    public AdaptadorListFoliosRecepcion(ArrayList<ListReceSandG> listaFolios) {
        this.listaFolios = listaFolios;
    }

    @Override
    public AdaptadorListFoliosRecepcion.ViewHolderListFoli onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_foliosrece, null, false);
        view.setOnClickListener(this);
        return new AdaptadorListFoliosRecepcion.ViewHolderListFoli(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorListFoliosRecepcion.ViewHolderListFoli holder, int position) {
        holder.Folio.setText(listaFolios.get(position).getFolio());
        holder.Fecha.setText(listaFolios.get(position).getFecha());
        holder.Provedor.setText(listaFolios.get(position).getProvedor());

    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }

    @Override
    public int getItemCount() {
        return listaFolios.size();
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);

        }
    }


    public class ViewHolderListFoli extends RecyclerView.ViewHolder {
        TextView Folio, Fecha, Provedor, Referencia;


        public ViewHolderListFoli(View itemView) {
            super(itemView);
            Folio = (TextView) itemView.findViewById(R.id.txtFolio);
            Fecha = (TextView) itemView.findViewById(R.id.txtFecha);
            Provedor = (TextView) itemView.findViewById(R.id.txtProvedor);
            Referencia = (TextView) itemView.findViewById(R.id.txtReferencia);
        }
    }
}
