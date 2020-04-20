package com.example.jesusyx.carritocompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recycler;
    RecyclerView.Adapter adap;
    ArrayList<elemento> listaCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = (RecyclerView)findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Thread tr = new Thread(){
            @Override
            public void run() {
               String NAMESPACE="http://tempuri.org/";
               String URL="http://192.168.1.35/Publicaciones/WebService1.asmx";
               String METHOD_NAME="listarProductos";
               String SOAP_ACTION="http://tempuri.org/listarProductos";

               SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.dotNet=true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE trasporte = new HttpTransportSE(URL);

                try{
                    trasporte.call(SOAP_ACTION, envelope);
                    final SoapObject soap = (SoapObject)envelope.getResponse();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            listarProductos(soap);
                        }
                    });

                }catch (Exception e){

                }
            }
        };
        tr.start();


        ((Adaptador) adap).setOnItemClickListener(new Adaptador.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                elemento item = listaCard.get(position);

                //Toast.makeText(getApplication(), item.getDescripcion()+"", Toast.LENGTH_SHORT).show();


                Intent i = new Intent(getApplicationContext(), Detalle.class);
                i.putExtra("cod", item.getCod());
                i.putExtra("des", item.getDescripcion());
                i.putExtra("det", item.getDetalle());
                i.putExtra("pre", item.getPrecio()+"");
                i.putExtra("img", item.getImg());
                startActivity(i);
            }
        });

    }

    public void listarProductos(SoapObject soap) {
        listaCard = new ArrayList<elemento>();
        for(int i=0; i<soap.getPropertyCount();i++){
            SoapObject reg = (SoapObject)soap.getProperty(i);
            elemento el = new elemento(reg.getProperty(0).toString(), reg.getProperty(1).toString(),
                    reg.getProperty(2).toString(), Double.parseDouble(reg.getProperty(4).toString()),
                    reg.getProperty(5).toString());
            listaCard.add(el);
        }
        adap = new Adaptador(listaCard, getApplication());
        recycler.setAdapter(adap);
    }
}
