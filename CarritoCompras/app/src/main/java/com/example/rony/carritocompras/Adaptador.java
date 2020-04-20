package com.example.rony.carritocompras;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {
   private ArrayList<elemento> elementos;
    private Context context;

    public Adaptador(ArrayList<elemento> elementos, Context context){
        this.elementos=elementos;
        this.context=context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.elemento,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        elemento lista=elementos.get(i);

        viewHolder.TxtDes.setText(lista.getDescripcion());
        viewHolder.TxtDetalle.setText(lista.getDetalle());
        Picasso.get().load("http://192.168.43.178/Publicaciones/imagenes/"+lista.getImg()).resize(400,400).into(viewHolder.img);

    }
    public static interface  OnItemClickListener{
        public void onItemClick(View view,int position);
    }
    private static OnItemClickListener onItemClickListener;
    public  static  OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }
    public static  void  setOnItemClickListener(OnItemClickListener onItemClickListener){
        Adaptador.onItemClickListener=onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return elementos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView TxtDes,TxtDetalle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=(ImageView)itemView.findViewById(R.id.img);
            TxtDes=(TextView)itemView.findViewById(R.id.txtDesPro);
            TxtDetalle=(TextView)itemView.findViewById(R.id.txtDetalle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = ViewHolder.super.getAdapterPosition();
                    onItemClickListener.onItemClick(view,position);
                }
            });

        }
    }
}
