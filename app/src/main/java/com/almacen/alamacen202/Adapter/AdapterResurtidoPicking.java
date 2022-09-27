package com.almacen.alamacen202.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.almacen.alamacen202.R;
import com.almacen.alamacen202.SetterandGetters.ListProdxFolOrdComp;
import com.almacen.alamacen202.SetterandGetters.ResurtidoPicking;

import java.util.ArrayList;

public class AdapterResurtidoPicking extends RecyclerView.Adapter<AdapterResurtidoPicking.ViewHolderResurtidoPicking>{

    ArrayList<ResurtidoPicking> datos;
    private View.OnClickListener listener;
    int index;
    public AdapterResurtidoPicking(ArrayList<ResurtidoPicking> datos) {
        this.datos = datos;
    }//AdaptadorlistOrdComp

    @Override
    public AdapterResurtidoPicking.ViewHolderResurtidoPicking onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_resurt,
                null, false);
        //view.setOnClickListener(this);
        return new AdapterResurtidoPicking.ViewHolderResurtidoPicking(view);
    }//constructor

    @Override
    public void onBindViewHolder(AdapterResurtidoPicking.ViewHolderResurtidoPicking holder, @SuppressLint("RecyclerView") int position) {
        holder.tvIdProd.setText(datos.get(position).getClaveProd());
        holder.tvDescrip.setText(datos.get(position).getDescrip());
        holder.tvUbica.setText(datos.get(position).getPicking());
        holder.tvClasif.setText(datos.get(position).getClasif());
        holder.tvNum.setText(datos.get(position).getNum());
        holder.tvRack.setText(datos.get(position).getRack());
        holder.tvFecha.setText(datos.get(position).getFecha());
        holder.tvHora.setText(datos.get(position).getHora());
        if(datos.get(position).isRevisado()==true){
            holder.ibRevisado.setVisibility(View.VISIBLE);
        }else{
            holder.ibRevisado.setVisibility(View.INVISIBLE);
        }

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

    public class ViewHolderResurtidoPicking extends RecyclerView.ViewHolder {
        TextView tvIdProd,tvDescrip,tvUbica,tvClasif,tvNum,tvRack,tvFecha,tvHora;
        LinearLayout lyItem;
        ImageButton ibRevisado;
        public ViewHolderResurtidoPicking(View itemView) {
            super(itemView);
            tvIdProd = itemView.findViewById(R.id.tvIdProd);
            tvDescrip = itemView.findViewById(R.id.tvDescrip);
            tvUbica = itemView.findViewById(R.id.tvUbica);
            tvClasif = itemView.findViewById(R.id.tvClasif);
            lyItem=itemView.findViewById(R.id.lyItem);
            tvNum=itemView.findViewById(R.id.tvNum);
            tvRack=itemView.findViewById(R.id.tvRack);
            ibRevisado=itemView.findViewById(R.id.ibRevisado);
            tvFecha=itemView.findViewById(R.id.tvFecha);
            tvHora=itemView.findViewById(R.id.tvHora);
        }//constructor
    }//clase ViewHolderResurtidoPicking
}//clase principal
