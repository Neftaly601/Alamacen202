package com.almacen.alamacen202.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ExistenciaSandG;
import com.almacen.alamacen202.SetterandGetters.UbicacionSandG;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorUbicacion extends RecyclerView.Adapter<AdaptadorUbicacion.ViewHolderCarrito> implements View.OnClickListener {

    ArrayList<UbicacionSandG> listaUbicacion;
    private View.OnClickListener listener;

    public AdaptadorUbicacion(ArrayList<UbicacionSandG> listaUbicacion) {
        this.listaUbicacion = listaUbicacion;
    }

    @Override
    public AdaptadorUbicacion.ViewHolderCarrito onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_ubicacion, null, false);
        view.setOnClickListener(this);
        return new AdaptadorUbicacion.ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorUbicacion.ViewHolderCarrito holder, int position) {
        holder.Ubicacion.setText(listaUbicacion.get(position).getUbicacion());
        holder.Cantidad.setText(listaUbicacion.get(position).getCantidad());
        holder.Fecha.setText(listaUbicacion.get(position).getFecha());
        holder.Tipo.setText(listaUbicacion.get(position).getTipo());


    }


    @Override
    public int getItemCount() {
        return listaUbicacion.size();
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
        TextView Ubicacion, Cantidad, Fecha, Tipo;


        public ViewHolderCarrito(View itemView) {
            super(itemView);
            Ubicacion = (TextView) itemView.findViewById(R.id.txtUbicacion);
            Cantidad = (TextView) itemView.findViewById(R.id.txtUbCantidad);
            Fecha = (TextView) itemView.findViewById(R.id.txtUbFecha);
            Tipo = (TextView) itemView.findViewById(R.id.txtUbTipo);
        }
    }
}
