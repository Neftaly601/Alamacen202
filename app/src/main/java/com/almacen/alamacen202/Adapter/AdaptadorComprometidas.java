package com.almacen.alamacen202.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ComprometidasSandG;
import com.almacen.alamacen202.SetterandGetters.ExistenciaSandG;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorComprometidas extends RecyclerView.Adapter<AdaptadorComprometidas.ViewHolderCarrito> implements View.OnClickListener {

    ArrayList<ComprometidasSandG> listaComprometidas;
    private View.OnClickListener listener;

    public AdaptadorComprometidas(ArrayList<ComprometidasSandG> listaComprometidas) {
        this.listaComprometidas = listaComprometidas;
    }

    @Override
    public AdaptadorComprometidas.ViewHolderCarrito onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_comprometidas, null, false);
        view.setOnClickListener(this);
        return new AdaptadorComprometidas.ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorComprometidas.ViewHolderCarrito holder, int position) {
        holder.tipoDocument.setText(listaComprometidas.get(position).getTipoDocument());
        holder.Folio.setText(listaComprometidas.get(position).getFolio());
        holder.Cliente.setText(listaComprometidas.get(position).getCliente());
        holder.Cantidad.setText(listaComprometidas.get(position).getCantidad());
        holder.Fecha.setText(listaComprometidas.get(position).getFecha());

    }


    @Override
    public int getItemCount() {
        return listaComprometidas.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;

    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onClick(view);

        }
    }

    public class ViewHolderCarrito extends RecyclerView.ViewHolder {
        TextView tipoDocument, Folio, Cliente, Cantidad, Fecha;


        public ViewHolderCarrito(View itemView) {
            super(itemView);
            Folio = (TextView) itemView.findViewById(R.id.txtFolio);
            Cliente = (TextView) itemView.findViewById(R.id.txtCliente);
            Cantidad = (TextView) itemView.findViewById(R.id.txtCantidad);
            Fecha = (TextView) itemView.findViewById(R.id.txtFecha);
            tipoDocument = (TextView) itemView.findViewById(R.id.txtTipDocu);
        }
    }
}
