package com.almacen.alamacen202.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ListProdxFolOrdComp;

import java.util.ArrayList;

public class AdaptadorlistOrdComp extends RecyclerView.Adapter<AdaptadorlistOrdComp.ViewHolderOrdenCompra>{

    ArrayList<ListProdxFolOrdComp> datos;
    private View.OnClickListener listener;
    int index;
    public AdaptadorlistOrdComp(ArrayList<ListProdxFolOrdComp> datos) {
        this.datos = datos;
    }//AdaptadorlistOrdComp

    @Override
    public AdaptadorlistOrdComp.ViewHolderOrdenCompra onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_prod_ord_comp,
                null, false);
        //view.setOnClickListener(this);
        return new AdaptadorlistOrdComp.ViewHolderOrdenCompra(view);
    }//constructor

    @Override
    public void onBindViewHolder(AdaptadorlistOrdComp.ViewHolderOrdenCompra holder, @SuppressLint("RecyclerView") int position) {
        holder.idArt.setText(datos.get(position).getClaveArt());
        holder.nArt.setText(datos.get(position).getArt());
        holder.ubic.setText(datos.get(position).getUbicacion());
        holder.cant.setText(datos.get(position).getCantidad());
        holder.num.setText(datos.get(position).getNum());

        if(index==position){
            holder.lyItem.setBackgroundResource(R.color.ColorGris);
        }else{
            holder.lyItem.setBackgroundColor(0);
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


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }//setonClickListener

    public class ViewHolderOrdenCompra extends RecyclerView.ViewHolder {
        TextView idArt,nArt,ubic,cant,num;
        LinearLayout lyItem;
        public ViewHolderOrdenCompra(View itemView) {
            super(itemView);
            idArt = itemView.findViewById(R.id.txtidArt);
            nArt = itemView.findViewById(R.id.txtnameArt);
            ubic = itemView.findViewById(R.id.txtUbica);
            cant = itemView.findViewById(R.id.txtCantInv);
            lyItem=itemView.findViewById(R.id.lyItem);
            num=itemView.findViewById(R.id.txtNum);
        }//constructor
    }//clase ViewHolderOrdenCompra
}//clase principal
