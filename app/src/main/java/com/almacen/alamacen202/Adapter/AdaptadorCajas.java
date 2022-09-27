package com.almacen.alamacen202.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.CAJASSANDG;
import com.almacen.alamacen202.SetterandGetters.ComprometidasSandG;

import java.util.ArrayList;

public class AdaptadorCajas extends RecyclerView.Adapter<AdaptadorCajas.ViewHolderCarrito> implements View.OnClickListener {

    ArrayList<CAJASSANDG> listaCajas;
    private View.OnClickListener listener;

    public AdaptadorCajas(ArrayList<CAJASSANDG> listaCajas) {
        this.listaCajas = listaCajas;
    }

    @Override
    public AdaptadorCajas.ViewHolderCarrito onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_cajas, null, false);
        view.setOnClickListener(this);
        return new AdaptadorCajas.ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorCajas.ViewHolderCarrito holder, int position) {
        holder.Articulo.setText(listaCajas.get(position).getClavedelProdcuto());
        holder.Cantidad.setText(listaCajas.get(position).getCantidadUnidades());
        holder.Caja.setText(listaCajas.get(position).getNumCajas());

    }


    @Override
    public int getItemCount() {
        return listaCajas.size();
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
        TextView Articulo, Cantidad, Caja;


        public ViewHolderCarrito(View itemView) {
            super(itemView);
            Articulo = (TextView) itemView.findViewById(R.id.txtArticulo);
            Cantidad = (TextView) itemView.findViewById(R.id.txtCantidad);
            Caja = (TextView) itemView.findViewById(R.id.txtCaja);

        }
    }
}
