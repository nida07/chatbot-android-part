package com.example.chatbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class sendcomplaint extends AppCompatActivity implements JsonResponse{
    EditText e1;
    Button b1;
    String complaint;
    ListView l1;
    String[] complaints,reply,date,value;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendcomplaint);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        e1=(EditText)findViewById(R.id.complaint);
        l1=(ListView)findViewById(R.id.snd);

        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)sendcomplaint.this;
        String q="/userviewcomplaints?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);
        b1=(Button)findViewById(R.id.button5);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaint=e1.getText().toString();
                JsonReq JR = new JsonReq();
                JR.json_response = (JsonResponse) sendcomplaint.this;
                String q = "/usermanagecomplaints?complaint=" + complaint + "&lid=" + sh.getString("log_id", "");
                q = q.replace(" ", "%20");
                JR.execute(q);
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("usermanagecomplaints")) {

                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    Toast.makeText(getApplicationContext(), "ADDED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(), sendcomplaint.class));

                } else {

                    Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
                }
            }
            else if(method.equalsIgnoreCase("userviewcomplaints"))
            {
                String status=jo.getString("status");
                Log.d("pearl",status);


                if(status.equalsIgnoreCase("success")){
                    JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                    //feedback_id=new String[ja1.length()];
                    complaints=new String[ja1.length()];
                    reply=new String[ja1.length()];
                    date=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        complaints[i]=ja1.getJSONObject(i).getString("complaint");
                        reply[i]=ja1.getJSONObject(i).getString("reply");
                        date[i]=ja1.getJSONObject(i).getString("date");
                        value[i]="complaints: "+complaints[i]+"\nreply: "+reply[i]+"\ndate: "+date[i];

                    }
                    ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                    l1.setAdapter(ar);
                }
            }

        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }
}