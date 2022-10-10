package com.almacen.alamacen202.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.Inventario;
import com.almacen.alamacen202.SetterandGetters.Traspasos;

import java.util.ArrayList;

public class AdaptadorTraspasos extends RecyclerView.Adapter<AdaptadorTraspasos.ViewHolderTraspasos> {

    private ArrayList<Traspasos> datos;
    private int index;
    public AdaptadorTraspasos(ArrayList<Traspasos> datos) {
        this.datos = datos;
    }//constructor

    @Override
    public AdaptadorTraspasos.ViewHolderTraspasos onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_traspaso,
                null, false);
        return new AdaptadorTraspasos.ViewHolderTraspasos(view);
    }//oncreateViewHolder

    @Override
    public void onBindViewHolder(AdaptadorTraspasos.ViewHolderTraspasos holder, int position) {
        holder.Producto.setText(datos.get(position).getProducto());
        holder.Cantidad.setText(datos.get(position).getCantidad());
        holder.n.setText(datos.get(position).getNum());

        if(index==position){
            holder.lyaout.setBackgroundResource(R.color.ColorGris);
        }else{
            holder.lyaout.setBackgroundColor(0);
        }
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

    public static class ViewHolderTraspasos extends RecyclerView.ViewHolder {
        TextView n,Producto, Cantidad;
        LinearLayout lyaout;
        public ViewHolderTraspasos (View itemView) {
            super(itemView);
            n= itemView.findViewById(R.id.tvN);
            Producto =  itemView.findViewById(R.id.Producto);
            Cantidad =  itemView.findViewById(R.id.Cantidad);
            lyaout  = itemView.findViewById(R.id.lyaout);
        }//constructor
    }//AdapterTraspasosViewHolder class
}//principal
