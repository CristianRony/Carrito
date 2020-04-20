package com.example.jesusyx.carritocompras;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Registro extends AppCompatActivity {


    EditText txtNom, txtCor, txtPas;
    Button btnRegistrarUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtNom = (EditText)findViewById(R.id.txtNom);
        txtCor = (EditText)findViewById(R.id.txtCor);
        txtPas = (EditText)findViewById(R.id.txtPas);

        btnRegistrarUsu=(Button)findViewById(R.id.btnRegistrarUsu);
        btnRegistrarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr = new Thread() {
                    @Override
                    public void run() {

                        String NAMESPACE="http://tempuri.org/";
                        String URL="http://192.168.1.35/Publicaciones/WebService1.asmx";
                        String METHOD_NAME="InsertarUsuario";
                        String SOAP_ACTION="http://tempuri.org/InsertarUsuario";

                        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                        request.addProperty("nom", txtNom.getText().toString());
                        request.addProperty("correo", txtCor.getText().toString());
                        request.addProperty("pas", txtPas.getText().toString());

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
                                    Toast.makeText(getApplicationContext(), "Usuario Registrado", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), Cesta.class);
                                    startActivity(i);
                                }
                            });


                        }catch (Exception e){}

                    }
                };
                tr.start();
            }
        });
    }
}
