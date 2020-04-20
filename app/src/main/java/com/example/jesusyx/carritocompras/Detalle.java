package com.example.jesusyx.carritocompras;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Detalle extends AppCompatActivity {


    ImageView imgDetalle;
    TextView txtDetalleDes, txtDetalleDet, txtDetallePrecio;
    Button btnVer, btnAgregar;

    String cod="", des="", det="", img="";
    double pre=0;

    ArrayList<elemento> cesta = new ArrayList<>();
    SharedPreferences carrito;
    Gson gson=new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        imgDetalle=(ImageView)findViewById(R.id.imgDetalle);
        txtDetalleDes=(TextView)findViewById(R.id.txtDetalleDescripcion);
        txtDetalleDet=(TextView)findViewById(R.id.txtDetalleDet);
        txtDetallePrecio=(TextView)findViewById(R.id.txtDetallePrecio);
        btnAgregar=(Button)findViewById(R.id.btnAgregar);
        btnVer=(Button)findViewById(R.id.btnVer);

        Bundle recupera = getIntent().getExtras();

        if (recupera != null) {
            cod = recupera.getString("cod");
            des = recupera.getString("des");
            det = recupera.getString("det");
            pre = Double.parseDouble(recupera.getString("pre"));
            img = recupera.getString("img");

        }

        txtDetalleDes.setText(des);
        txtDetalleDet.setText(det);
        txtDetallePrecio.setText(pre+"");
        Picasso.with(getApplicationContext()).load("http://192.168.1.35/Publicaciones/Imagenes/"+img)
                .resize(600,600).into(imgDetalle);


        //recuperando datos de la preferencia
        File f = new File("/data/data/"+getPackageName()+"/shared_prefs/carrito.xml");
        carrito=getSharedPreferences("carrito", MODE_PRIVATE);
        if (f.exists()) {
            String guardado = carrito.getString("cesta", "");
            Type type=new TypeToken<ArrayList<elemento>>(){}.getType();
            cesta=gson.fromJson(guardado, type);
        }


        btnAgregar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                elemento e = new elemento(cod,des,pre,1);
                if (agregarProducto(e)==true) {
                    Toast.makeText(getApplicationContext(),"se aumento la cantidad", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"se agrego nuevo producto", Toast.LENGTH_SHORT).show();
                }

                String listaGson = gson.toJson(cesta);
                carrito=getSharedPreferences("carrito", MODE_PRIVATE);
                SharedPreferences.Editor editor= carrito.edit();
                editor.putString("cesta", listaGson);
                editor.commit();
            }
        });

                btnVer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        File f = new File("/data/data/"+getPackageName()+"/shared_prefs/carrito.xml");
                        if (f.exists()) {
                            Intent i = new Intent(getApplicationContext(), Cesta.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(),"Cesta vacia", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

    private boolean agregarProducto(elemento e) {
        for (int i=0; i<cesta.size();i++) {
            if (cesta.get(i).getCod().equals(e.getCod())) {
                cesta.get(i).setCan(cesta.get(i).getCan()+1);
                return true;
            }
        }
        cesta.add(e);
        return false;
    }
}
