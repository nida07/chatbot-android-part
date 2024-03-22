package com.example.chatbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements JsonResponse{
    EditText e1,e2;
    Button b1;
    public static String user,pass,logid,usertype;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.user);
        e2=(EditText)findViewById(R.id.pass);
        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user=e1.getText().toString();
                pass=e2.getText().toString();
                if(user.equalsIgnoreCase(""))
                {
                    e1.setError("Enter your Username");
                    e1.setFocusable(true);
                }
                else if(pass.equalsIgnoreCase(""))
                {
                    e2.setError("Enter your Password");
                    e2.setFocusable(true);
                }
                else{
                    JsonReq JR=new JsonReq();
                    JR.json_response=(JsonResponse)Login.this;
                    String q="/login?username=" + user + "&password=" + pass;
                    q = q.replace(" ", "%20");
                    JR.execute(q);

                }


            }
        });
    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (status.equalsIgnoreCase("success")) {
                JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                logid = ja1.getJSONObject(0).getString("login_id");
                usertype = ja1.getJSONObject(0).getString("usertype");

                SharedPreferences.Editor e = sh.edit();
                e.putString("log_id", logid);
                e.commit();
//                Toast.makeText(getApplicationContext(), "aaaaaaaaaaaaaaa", Toast.LENGTH_LONG).show();

                if (usertype.equalsIgnoreCase("student")) {
                    Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_LONG).show();
//                    startActivity(new Intent(getApplicationContext(),ChatHere.class));
                    startActivity(new Intent(getApplicationContext(),studenthome.class));
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),ipsettings.class);
        startActivity(b);
    }
}