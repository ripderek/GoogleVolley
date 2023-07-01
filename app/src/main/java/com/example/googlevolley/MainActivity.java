package com.example.googlevolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {
    //datos para el inicio de sesion
    public String Correo = "carlos@gmail.com";
    public String Clave = "12345678";
    public String Token = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void  ConsumirAPI(View view) {
        TextView txt = (TextView) findViewById(R.id.txtTexto);
        RequestQueue queue = Volley.newRequestQueue(this);
        Intent i = new Intent(this, MainActivity2.class);
        String url ="https://api.uealecpeterson.net/public/login";
       //String url ="https://dummyjson.com/users";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject resp = new JSONObject(response);
                            Token = resp.get("access_token").toString();
                            i.putExtra("Token",Token);
                            startActivity(i);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        txt.setText(error.toString());
                    }
                }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
                //headerMap.put("Public-Merchant-Id", "84e1d0de1fbf437e9779fd6a52a9ca18");
                return headerMap;
            }
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                EditText cr = findViewById(R.id.txtCedula);
                EditText cl = findViewById(R.id.txtNombres);
                params.put("correo", cr.getText().toString());
                params.put("clave", cl.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);
    }

}