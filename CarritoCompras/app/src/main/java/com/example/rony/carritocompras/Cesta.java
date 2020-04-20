package com.example.rony.carritocompras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Cesta extends AppCompatActivity {

    ArrayList<elemento> cesta=new ArrayList<>();

    SharedPreferences carrito;
    Gson gson=new Gson();
    double total=0;
    Button btnPagar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesta);

        carrito=getSharedPreferences("carrito",MODE_PRIVATE);
        String guardado=carrito.getString("cesta","");

        Type type=new TypeToken<ArrayList<elemento>>(){}.getType();
        cesta=gson.fromJson(guardado,type);

        btnPagar=(Button)findViewById(R.id.btnPagar);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Login.class);
                i.putExtra("total",total);
                startActivity(i);

            }

        });
        cuerpocesta();

    }

    private void cuerpocesta(){
        final LinearLayout ll=(LinearLayout)findViewById(R.id.llCesta);
        ll.removeAllViews();
        LinearLayout linearLayout=null;

        total=0;

        for (int i=0;i<cesta.size();i++){
            elemento e=cesta.get(i);
            linearLayout=new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);

            final TextView txtPro=new TextView(this);
            txtPro.setText(e.getDescripcion());
            txtPro.setLayoutParams(new LinearLayout.LayoutParams(270, ViewGroup.LayoutParams.WRAP_CONTENT));

            final TextView txtPre=new TextView(this);
            txtPre.setText(e.getPrecio()+"");
            txtPre.setLayoutParams(new LinearLayout.LayoutParams(280, ViewGroup.LayoutParams.WRAP_CONTENT));

            final TextView txtCan=new TextView(this);
            txtCan.setText(e.getCan()+"");
            txtCan.setLayoutParams(new LinearLayout.LayoutParams(150, ViewGroup.LayoutParams.WRAP_CONTENT));

            final TextView txtMonto=new TextView(this);
            double montoCompra=e.getPrecio()*e.getCan();
            txtMonto.setText(montoCompra+"");
            txtMonto.setLayoutParams(new LinearLayout.LayoutParams(270, ViewGroup.LayoutParams.WRAP_CONTENT));

            final Button btnRestar=new Button(this);
            btnRestar.setText("-");
            btnRestar.setLayoutParams(new LinearLayout.LayoutParams(80, ViewGroup.LayoutParams.WRAP_CONTENT));

            final String cod=cesta.get(i).getCod();

            btnRestar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < cesta.size(); i++) {
                        if (cesta.get(i).getCod().equalsIgnoreCase(cod)) {
                            cesta.get(i).setCan(cesta.get(i).getCan() - 1);
                            if (cesta.get(i).getCan() < 1) ;
                            cesta.remove(i);

                        }
                    }

                    String jsonList = gson.toJson(cesta);

                    carrito = getSharedPreferences("carrito12", MODE_PRIVATE);
                    SharedPreferences.Editor editor = carrito.edit();
                    editor.putString("cesta", jsonList);
                    editor.commit();

                    cuerpocesta();

                }
            });

            linearLayout.addView(txtPro);
            linearLayout.addView(txtPre);
            linearLayout.addView(txtCan);
            linearLayout.addView(txtMonto);

            linearLayout.addView(btnRestar);

            ll.addView(linearLayout);
            total+=montoCompra;



        }

        final TextView txtTextoTotal =new TextView(this);
        txtTextoTotal.setText("Total a Pagar");
        txtTextoTotal.setLayoutParams(new LinearLayout.LayoutParams(620, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTextoTotal.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

        final TextView txtTotal =new TextView(this);
        txtTotal.setText(total+"");
        txtTotal.setLayoutParams(new LinearLayout.LayoutParams(200, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTotal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        LinearLayout linearLayout2=new LinearLayout(this);
        linearLayout2.setOrientation(LinearLayout.HORIZONTAL);

        linearLayout2.addView(txtTextoTotal);
        linearLayout2.addView(txtTotal);
        ll.addView(linearLayout2);

    }
}
