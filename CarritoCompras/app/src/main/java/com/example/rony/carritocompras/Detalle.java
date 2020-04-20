package com.example.rony.carritocompras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Detalle extends AppCompatActivity {
    ImageView imgDeatalle;
    TextView txtDetalleDes,txtDetalleDet,txtDetallePrecio;
    Button btnVer,btnAgregar;

    String cod="",des="",det="",img="";
    double pre=0;

    ArrayList<elemento> cesta =new ArrayList<>();
    SharedPreferences carrito;
    Gson gson =new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        imgDeatalle=(ImageView)findViewById(R.id.imgDetalle);
        txtDetalleDes=(TextView) findViewById(R.id.txtDetalleDes);
        txtDetalleDet=(TextView)findViewById(R.id.txtDetalleDet);
        txtDetallePrecio=(TextView)findViewById(R.id.txtDetallePrecio);
        btnAgregar=(Button) findViewById(R.id.btnAgregar);
        btnVer=(Button)findViewById(R.id.btnVer);


        Bundle recupere = getIntent().getExtras();
        if (recupere !=null){
            cod= recupere.getString("cod");
            des= recupere.getString("des");
            det= recupere.getString("det");
            pre= Double.parseDouble(recupere.getString("pre"));
            img= recupere.getString("img");
        }

        txtDetalleDes.setText(des);
        txtDetalleDet.setText(det);
        txtDetallePrecio.setText(pre+"");
        Picasso.get().load("http://192.168.0.33/Publicaciones/imagenes/"+img).resize(600,600).into(imgDeatalle);


        File f=new File("/data/data/"+getPackageName()+"/shared_prefs/carrito.xml");
        carrito=getSharedPreferences("carrito",MODE_PRIVATE);
        if (f.exists()){
            String guardado=carrito.getString("cesta","");
            Type type=new TypeToken<ArrayList<elemento>>(){}.getType();
            cesta=gson.fromJson(guardado,type);
        }
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elemento e=new elemento(cod,des,pre,1);
                if (agregarProducto(e)==true){
                    Toast.makeText(getApplicationContext(),"se aumento la camtidad",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"se agrego nuevo producto",Toast.LENGTH_SHORT).show();

                }

                String listaGson=gson.toJson(cesta);
                SharedPreferences.Editor editor=carrito.edit();
                editor.putString("cesta",listaGson);
                editor.commit();

            }
        });

        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File f=new File("/data/data/"+getPackageName()+"/shared_prefs/carrito.xml");

                if (f.exists()){
                    Intent i=new Intent(getApplicationContext(),Cesta.class);
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(),"Cesta Vacia",Toast.LENGTH_SHORT).show();

                }


            }
        });
    }
    private  boolean agregarProducto(elemento e){
        for (int i=0;i<cesta.size();i++){
            if (cesta.get(i).getCod().equals(e.getCod())){
                cesta.get(i).setCan(cesta.get(i).getCan()+1);
                return true;
            }
        }
        cesta.add(e);
        return false;
    }
}
