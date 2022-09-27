package com.almacen.alamacen202.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ListProAduSandG;
import com.almacen.alamacen202.SetterandGetters.ListProReceSandG;

import java.util.ArrayList;

public class AdaptadorListProductosRece extends RecyclerView.Adapter<AdaptadorListProductosRece.ViewHolderCarrito> implements View.OnClickListener {

    ArrayList<ListProReceSandG> listaProd;
    private View.OnClickListener listener;

    public AdaptadorListProductosRece(ArrayList<ListProReceSandG> listaProd) {
        this.listaProd = listaProd;
    }

    @Override
    public AdaptadorListProductosRece.ViewHolderCarrito onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_productosrece, null, false);
        view.setOnClickListener(this);
        return new AdaptadorListProductosRece.ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorListProductosRece.ViewHolderCarrito holder, int position) {
        holder.Producto.setText(listaProd.get(position).getProducto());
        holder.Ubicacion.setText(listaProd.get(position).getUbicacion());

    }


    @Override
    public int getItemCount() {
        return listaProd.size();
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
        TextView  Producto,Ubicacion;

        public ViewHolderCarrito(View itemView) {
            super(itemView);
            Producto = (TextView) itemView.findViewById(R.id.txtProducto);
            Ubicacion = (TextView) itemView.findViewById(R.id.txtUbicacion);

        }
    }
}
