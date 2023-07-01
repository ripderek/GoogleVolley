package com.example.googlevolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {
    public String Token = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Token=getIntent().getStringExtra("Token");

       // TextView txt = (TextView) findViewById(R.id.txtText);
        //txt.setText("This token is"+Token);
        ConsumirAPI();
    }
    public void  ConsumirAPI() {
        TextView txt = (TextView) findViewById(R.id.txtText);
        RequestQueue queue = Volley.newRequestQueue(this);
        Intent i = new Intent(this, MainActivity2.class);
        String url ="https://api.uealecpeterson.net/public/productos/search";
        //String url ="https://dummyjson.com/users";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            String Name="";
                            JSONObject resp = new JSONObject(response);
                            JSONArray users = resp.getJSONArray("productos");
                            for(int i=0; i<users.length();i++){
                                JSONObject firstName = users.getJSONObject(i);
                                Name = Name+ "\n" + firstName.getString("barcode").toString() +", " +firstName.getString("descripcion").toString() + ", "+ firstName.getString("precio_unidad").toString();
                            }
                            txt.setText(Name);


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
                headerMap.put("Authorization", "Bearer " + Token);
                return headerMap;
            }
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("fuente","1");
                return params;
            }
        };
        queue.add(stringRequest);
    }
}