package com.almacen.alamacen202.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ListProAduSandG;
import com.almacen.alamacen202.SetterandGetters.ListProReceSandG;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorListProdRecepcion extends RecyclerView.Adapter<AdaptadorListProdRecepcion.ViewHolderCarrito> implements View.OnClickListener {

    ArrayList<ListProReceSandG> listaProd;
    private View.OnClickListener listener;
    Context context;

    public AdaptadorListProdRecepcion(ArrayList<ListProReceSandG> listaProd , Context context) {
        this.listaProd = listaProd;
        this.context = context;
    }

    @Override
    public AdaptadorListProdRecepcion.ViewHolderCarrito onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.iteam_list_recepcion, null, false);
        view.setOnClickListener(this);
        return new AdaptadorListProdRecepcion.ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorListProdRecepcion.ViewHolderCarrito holder, int position) {
        holder.Producto.setText(listaProd.get(position).getProducto());
        holder.Cantidad.setText(listaProd.get(position).getCantidad());
        holder.Surtido.setText(listaProd.get(position).getCantidadSurtida());

        Picasso.with(context).
                load("https://vazlo.com.mx/assets/img/productos/chica/jpg/" + listaProd.get(position).getProducto()+ ".jpg")
                .error(R.drawable.logokepler)
                .fit()
                .centerInside()
                .into(holder.ImgvProd);

        int cantidad =0,cantidadsurtida=0;
        cantidad =Integer.parseInt(listaProd.get(position).getCantidad());
        cantidadsurtida=Integer.parseInt(listaProd.get(position).getCantidadSurtida());
        if(cantidad == cantidadsurtida){
            holder.Surtido.setTextColor(Color.parseColor("#00BD03"));;
        }else{
            holder.Surtido.setTextColor(Color.parseColor("#F00F0F"));
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
        TextView  Producto,Cantidad,Surtido;
        ImageView ImgvProd;

        public ViewHolderCarrito(View itemView) {
            super(itemView);
            Producto = (TextView) itemView.findViewById(R.id.txtProducto);
            Cantidad = (TextView) itemView.findViewById(R.id.txtCantidad);
            Surtido = (TextView) itemView.findViewById(R.id.txtCantidadSurtida);
            ImgvProd = (ImageView)itemView.findViewById(R.id.imageVi);
        }
    }
}
