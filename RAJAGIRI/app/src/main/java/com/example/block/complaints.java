package com.example.block;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class complaints extends AppCompatActivity {
    EditText e1;
    Button b1;
    ListView l1;
    SharedPreferences sh;
    String url,url1,complaint;
    ArrayList<String> complaints,date,reply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);
        e1=findViewById(R.id.editTextTextPersonName7);
        b1=findViewById(R.id.button7);
        l1=findViewById(R.id.viewreply);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        url1 ="http://"+sh.getString("ip", "") + ":5000/viewreply";
        RequestQueue queue = Volley.newRequestQueue(complaints.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(view_property.this, response+"", Toast.LENGTH_SHORT).show();

                    JSONArray ar=new JSONArray(response);
                    complaints= new ArrayList<>();
                    date=new ArrayList<>();

                    reply= new ArrayList<>();
//


                    for(int i=0;i<ar.length();i++)
                    {
                        JSONObject jo=ar.getJSONObject(i);
//                        name.add(jo.getString("fname")+ "" +jo.getString("lname"));
                        complaints.add(jo.getString("complaint"));

                        date.add(jo.getString("date"));
//                        Address.add(jo.getString("place")+ " \n "  + jo.getString("pin")+ " \n " +jo.getString("post"));
                        reply.add(jo.getString("reply"));
                    }

                    // ArrayAdapter<String> ad=new ArrayAdapter<>(Home.this,android.R.layout.simple_list_item_1,name);
                    //lv.setAdapter(ad);

                    l1.setAdapter(new Custom_viewreply(complaints.this,complaints,date,reply));
//                    l1.setOnItemClickListener(view_notification.this);

                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(complaints.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("lid", sh.getString("lid", ""));





                return params;
            }
        };
        queue.add(stringRequest);





        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                complaint = e1.getText().toString();

                if (complaint.equalsIgnoreCase("")) {
                    e1.setError("Enter the complaint");
                } else {

//                Intent i=new Intent(getApplicationContext(),home.class);
//                startActivity(i);

                    RequestQueue queue = Volley.newRequestQueue(complaints.this);
                    url = "http://" + sh.getString("ip", "") + ":5000/sendcomplaint";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("task");

                                if (res.equalsIgnoreCase("valid")) {


                                    Intent ik = new Intent(getApplicationContext(), complaints.class);
                                    startActivity(ik);
                                    Toast.makeText(complaints.this, "Complaint send!!", Toast.LENGTH_SHORT).show();


                                } else {

                                    Toast.makeText(complaints.this, "Not Send!!", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("lid", sh.getString("lid", ""));
                            params.put("complaint", complaint);

                            return params;
                        }
                    };
                    queue.add(stringRequest);

                }
            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent ii = new Intent(getApplicationContext(), Home.class);
        startActivity(ii);
    }

}