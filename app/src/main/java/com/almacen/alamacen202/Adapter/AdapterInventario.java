package com.almacen.alamacen202.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.Inventario;

import java.util.ArrayList;

public class AdapterInventario extends RecyclerView.Adapter<AdapterInventario.ViewHolderInventario> {

    private ArrayList<Inventario> datos;
    private int index;

    public AdapterInventario(ArrayList<Inventario> datos) {
        this.datos = datos;
    }//constructor

    @Override
    public AdapterInventario.ViewHolderInventario onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_inventario,
                null, false);
        return new AdapterInventario.ViewHolderInventario(view);
    }//oncreateViewHolder

    @Override
    public void onBindViewHolder(AdapterInventario.ViewHolderInventario holder, int position) {
        holder.Producto.setText(datos.get(position).getProducto());
        holder.Cantidad.setText(datos.get(position).getCantidad());
        holder.n.setText(datos.get(position).getNum());
    }//onBindViewHolder

    public int index(int index){
        this.index=index;
        return index;
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }//getItemType

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class ViewHolderInventario extends RecyclerView.ViewHolder {
        TextView n,Producto, Cantidad;
        public ViewHolderInventario (View itemView) {
            super(itemView);
            n= itemView.findViewById(R.id.tvN);
            Producto =  itemView.findViewById(R.id.Producto);
            Cantidad =  itemView.findViewById(R.id.Cantidad);

        }//constructor
    }//AdapterInventarioViewHolder class
}//principal
