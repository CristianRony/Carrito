package com.example.jesusyx.carritocompras;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.math.BigDecimal;
import java.sql.BatchUpdateException;
import java.util.ArrayList;

public class Login extends AppCompatActivity {

    EditText txtValUsu, txtValPas;

    TextView txtRegistrese;
    Button btnValidarUsuario;

    double total = 0;
    String a="";
    ArrayList<Usuario> listaUsu;


    ArrayList<elemento> cesta = new ArrayList<elemento>();
    SharedPreferences carrito;
    Gson gson = new Gson();

    PayPalConfiguration m_configuration;
    String m_paypalClientId="AaUpr5Kelipfw0Fjy6XQBVPaM7hoXb_4_Zr4mBj7e0x41Rv4jAb-5InyXWpxnLjlLgQUWJxELlSLd3yq";
    Intent m_service;
    int m_paypalRequestCode=999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle recupera = getIntent().getExtras();
        if (recupera != null) {
            total = recupera.getDouble("total");
        }


        txtValUsu = (EditText)findViewById(R.id.txtValUsu);
        txtValPas = (EditText)findViewById(R.id.txtValPas);



        txtRegistrese=(TextView)findViewById(R.id.txtRegistrate);
        txtRegistrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Registro.class);
                startActivity(i);
            }
        });

        m_configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(m_paypalClientId);
        m_service = new Intent(this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
        startService(m_service);

        btnValidarUsuario=(Button)findViewById(R.id.btnIngresar);
        btnValidarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread tr = new Thread() {
                    @Override
                    public void run() {

                        String NAMESPACE="http://tempuri.org/";
                        String URL="http://192.168.1.35/Publicaciones/WebService1.asmx";
                        String METHOD_NAME="ValidarUsuario";
                        String SOAP_ACTION="http://tempuri.org/ValidarUsuario";

                        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                        request.addProperty("cor", txtValUsu.getText().toString());
                        request.addProperty("pas", txtValPas.getText().toString());


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
                                    ValidarUsuario(soap);
                                }
                            });


                        }catch (Exception e){}

                    }
                };
                tr.start();

            }
        });
    }

    public void ValidarUsuario(SoapObject soap) {
        listaUsu = new ArrayList<Usuario>();
        for (int i=0; i<soap.getPropertyCount();i++){

            SoapObject reg = (SoapObject)soap.getProperty(i);

            Usuario u1 = new Usuario(Integer.parseInt(reg.getProperty(0).toString()),
                    reg.getProperty(1).toString(), reg.getProperty(2).toString(),
                    reg.getProperty(3).toString());

            listaUsu.add(u1);
        }

        if (listaUsu.size()>0){
            //Toast.makeText(getApplicationContext(), "Usuario Correcto", Toast.LENGTH_SHORT).show();
            PayPalPayment payment = new PayPalPayment(new BigDecimal(total), "USD", "Test de pago con paypal", PayPalPayment.PAYMENT_INTENT_SALE);
            Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
            startActivityForResult(intent, m_paypalRequestCode);

        }else {
            Toast.makeText(getApplicationContext(), "Usuario o Password Incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}
