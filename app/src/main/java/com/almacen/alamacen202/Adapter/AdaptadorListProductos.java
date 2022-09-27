package com.almacen.alamacen202.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ComprometidasSandG;
import com.almacen.alamacen202.SetterandGetters.ListProAduSandG;

import java.util.ArrayList;

public class AdaptadorListProductos extends RecyclerView.Adapter<AdaptadorListProductos.ViewHolderCarrito> implements View.OnClickListener {

    ArrayList<ListProAduSandG> listaProd;
    private View.OnClickListener listener;

    public AdaptadorListProductos(ArrayList<ListProAduSandG> listaProd) {
        this.listaProd = listaProd;
    }

    @Override
    public AdaptadorListProductos.ViewHolderCarrito onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_productos, null, false);
        view.setOnClickListener(this);
        return new AdaptadorListProductos.ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorListProductos.ViewHolderCarrito holder, int position) {
        holder.Producto.setText(listaProd.get(position).getProducto());
        holder.Ubicacion.setText(listaProd.get(position).getUbicacion());
        holder.Cantidad.setText(listaProd.get(position).getCantidad()/*+" "+listaProd.get(position).getUnidad()*/);
        holder.Surtido.setText(listaProd.get(position).getCantidadSurtida()/*+" "+listaProd.get(position).getUnidad()*/);

        int cantidad =0,cantidadsurtida=0;
        cantidad =Integer.parseInt(listaProd.get(position).getCantidad());
        cantidadsurtida=Integer.parseInt(listaProd.get(position).getCantidadSurtida());
        if(cantidad == cantidadsurtida){
            holder.cardla.setBackgroundColor(Color.rgb(39, 209, 9   ));
        }else{
            holder.cardla.setBackgroundColor(Color.parseColor("#FFE1E1E1"));
        }


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
        TextView  Producto,Ubicacion,Cantidad,Surtido;
        LinearLayout cardla;

        public ViewHolderCarrito(View itemView) {
            super(itemView);
            Producto = (TextView) itemView.findViewById(R.id.txtProducto);
            Ubicacion = (TextView) itemView.findViewById(R.id.txtUbicacion);
            Cantidad = (TextView) itemView.findViewById(R.id.txtCantidad);
            Surtido = (TextView) itemView.findViewById(R.id.txtCantidadSurtida);
            cardla = (LinearLayout)itemView.findViewById(R.id.cartable);
        }
    }
}
