package com.example.chatbot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class student_view_notification extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String[] noti,date,value;
    SharedPreferences sh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_notification);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lvnot);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)student_view_notification.this;
        String q="/student_view_notification?lid="+sh.getString("log_id","");
        q=q.replace(" ","%20");
        JR.execute(q);


    }

    @Override
    public void response(JSONObject jo) {
        try {

            String status=jo.getString("status");
            Log.d("pearl",status);


            if(status.equalsIgnoreCase("success")){
                JSONArray ja1=(JSONArray)jo.getJSONArray("data");
                //feedback_id=new String[ja1.length()];
                noti=new String[ja1.length()];
                date=new String[ja1.length()];
                value=new String[ja1.length()];


                for(int i = 0;i<ja1.length();i++)
                {
                    noti[i]=ja1.getJSONObject(i).getString("notification");
                    date[i]=ja1.getJSONObject(i).getString("date");

                    value[i]="Notification: "+noti[i]+"\nDate: "+date[i];

                }
                ArrayAdapter<String> ar=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,value);
                l1.setAdapter(ar);
            }


        }

        catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    }