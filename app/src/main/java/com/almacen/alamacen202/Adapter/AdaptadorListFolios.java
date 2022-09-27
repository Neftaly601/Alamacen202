package com.almacen.alamacen202.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ExistenciaSandG;
import com.almacen.alamacen202.SetterandGetters.ListLiberaSandG;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorListFolios extends RecyclerView.Adapter<AdaptadorListFolios.ViewHolderListFoli> implements View.OnClickListener {

    ArrayList<ListLiberaSandG> listaFolios;
    private View.OnClickListener listener;

    public AdaptadorListFolios(ArrayList<ListLiberaSandG> listaFolios) {
        this.listaFolios = listaFolios;
    }

    @Override
    public AdaptadorListFolios.ViewHolderListFoli onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_folioslibera, null, false);
        view.setOnClickListener(this);
        return new AdaptadorListFolios.ViewHolderListFoli(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorListFolios.ViewHolderListFoli holder, int position) {
        holder.Folio.setText(listaFolios.get(position).getFolio());
        holder.Fecha.setText(listaFolios.get(position).getFecha());
        holder.Cliente.setText(listaFolios.get(position).getCliente());
        holder.Referencia.setText(listaFolios.get(position).getReferencia());

        if(listaFolios.get(position).getUrgenciaPed().equals("NORMAL") || listaFolios.get(position).getUrgenciaPed().equals("")){
            holder.Urgencia.setText("-");
            holder.Urgencia.setTextColor(Color.parseColor("#FF000000"));
        }else{
            holder.Urgencia.setText("*");
            holder.Urgencia.setTextColor(Color.parseColor("#FFFF0000"));
        }

        int cantidad =0,cantidadsurtida=0;
        cantidad =Integer.parseInt(listaFolios.get(position).getCantidad());
        cantidadsurtida=Integer.parseInt(listaFolios.get(position).getCantidDsURT());
        if(cantidadsurtida==0){
            holder.cambio.setBackgroundColor(Color.parseColor("#FFE1E1E1"));;
        }else if(cantidad == cantidadsurtida){
            holder.cambio.setBackgroundColor(Color.parseColor("#00BD03"));
        }else {
            holder.cambio.setBackgroundColor(Color.parseColor("#F7F308"));
        }

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
        TextView Folio, Fecha, Cliente, Referencia,Urgencia;
        LinearLayout cambio;

        public ViewHolderListFoli(View itemView) {
            super(itemView);
            Folio = (TextView) itemView.findViewById(R.id.txtFolio);
            Fecha = (TextView) itemView.findViewById(R.id.txtFecha);
            Cliente = (TextView) itemView.findViewById(R.id.txtCliente);
            Referencia = (TextView) itemView.findViewById(R.id.txtReferencia);
            Urgencia = (TextView)itemView.findViewById(R.id.txtUrgencia);
            cambio = (LinearLayout)itemView.findViewById(R.id.cartable);
        }
    }
}
