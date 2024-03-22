package com.example.block;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Product extends Activity {
    TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,tv4,tv5,tv6;
    Button b,b2;
    ImageView img;
    String srno,url,urll;
    SharedPreferences sh;
    @Override
    public void onBackPressed() {
        Intent ins=new Intent(getApplicationContext(),Home.class);
        startActivity(ins);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        t1=(TextView) findViewById(R.id.textView8);
        t2=(TextView) findViewById(R.id.textView10);
        t3=(TextView) findViewById(R.id.textView12);
        t4=(TextView) findViewById(R.id.textView14);
        t5=(TextView) findViewById(R.id.textView16);
        t6=(TextView) findViewById(R.id.textView7);
        t7=(TextView) findViewById(R.id.textView9);
        t8=(TextView) findViewById(R.id.textView11);
        t9=(TextView) findViewById(R.id.textView13);
        t10=(TextView) findViewById(R.id.textView15);
        t11=(TextView) findViewById(R.id.textView17);
//        t12=(TextView) findViewById(R.id.textView19);
        tv4=(TextView) findViewById(R.id.textView4);
        tv5=(TextView) findViewById(R.id.textView5);
        tv6=(TextView) findViewById(R.id.textView6);
        t11.setVisibility(View.INVISIBLE);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        srno = Home.contents;//getting id from scan qr
        String hu = sh.getString("url", "");
        url = hu + "andviewproducts";
        Toast.makeText(getApplicationContext(), url , Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                // Display the response string.

                Toast.makeText(getApplicationContext(), "QR Code Verified" , Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObj1 = new JSONObject(response);
                    if (jsonObj1.getString("task").equalsIgnoreCase("Original")) {

//                        JSONObject jsonObj=jsonObj1.getJSONObject("result");
                        tv4.setText(jsonObj1.getString("manu"));
                        tv5.setText(jsonObj1.getString("dis"));
                        tv6.setText(jsonObj1.getString("pha"));
                        t1.setText(jsonObj1.getString("med"));
                        t2.setText(jsonObj1.getString("mf"));
                        t3.setText(jsonObj1.getString("exp"));
                        t4.setText(jsonObj1.getString("qty"));
                        t5.setText(jsonObj1.getString("price"));
//                        t.setText(jsonObj1.getString("info"));
                    } else {
                        tv4.setVisibility(View.INVISIBLE);
                        tv5.setVisibility(View.INVISIBLE);
                        tv6.setVisibility(View.INVISIBLE);
                        t1.setVisibility(View.INVISIBLE);
                        t2.setVisibility(View.INVISIBLE);
                        t3.setVisibility(View.INVISIBLE);
                        t4.setVisibility(View.INVISIBLE);
                        t5.setVisibility(View.INVISIBLE);
                        t6.setVisibility(View.INVISIBLE);
                        t7.setVisibility(View.INVISIBLE);
                        t8.setVisibility(View.INVISIBLE);
                        t9.setVisibility(View.INVISIBLE);
                        t10.setVisibility(View.INVISIBLE);

                        t11.setVisibility(View.VISIBLE);
//                        b2.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    Log.d("=========", e.toString());
                    Toast.makeText(Product.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            //                value Passing android to python
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("srno", srno);//passing to python
                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS=120000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }


}