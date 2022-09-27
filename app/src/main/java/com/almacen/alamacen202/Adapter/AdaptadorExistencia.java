package com.almacen.alamacen202.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ExistenciaSandG;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdaptadorExistencia extends RecyclerView.Adapter<AdaptadorExistencia.ViewHolderCarrito> implements View.OnClickListener {

    ArrayList<ExistenciaSandG> listaExistencia;
    private View.OnClickListener listener;

    public AdaptadorExistencia(ArrayList<ExistenciaSandG> listaExistencia) {
        this.listaExistencia = listaExistencia;
    }

    @Override
    public AdaptadorExistencia.ViewHolderCarrito onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_existencias, null, false);
        view.setOnClickListener(this);
        return new AdaptadorExistencia.ViewHolderCarrito(view);
    }

    @Override
    public void onBindViewHolder(AdaptadorExistencia.ViewHolderCarrito holder, int position) {
        holder.Almacen.setText(listaExistencia.get(position).getAlmacen());
        holder.Existencia.setText(listaExistencia.get(position).getExistencia());

    }

    private static String formatNumberCurrency(String number) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        return formatter.format(Double.parseDouble(number));
    }

    @Override
    public int getItemCount() {
        return listaExistencia.size();
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
        TextView Almacen, Existencia;


        public ViewHolderCarrito(View itemView) {
            super(itemView);
            Almacen = (TextView) itemView.findViewById(R.id.txtRAlmacen);
            Existencia = (TextView) itemView.findViewById(R.id.txtRexistencia);
        }
    }
}
