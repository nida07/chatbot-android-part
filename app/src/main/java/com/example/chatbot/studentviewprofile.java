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

public class studentviewprofile extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String[] course,name,sem,place,email,value;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentviewprofile);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        l1=(ListView)findViewById(R.id.lvview);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)studentviewprofile.this;
        String q="/studentviewprofile?lid="+sh.getString("log_id","");
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
                    course=new String[ja1.length()];
                    name=new String[ja1.length()];
                    sem=new String[ja1.length()];
                    place=new String[ja1.length()];
                    email=new String[ja1.length()];
                    value=new String[ja1.length()];

                    for(int i = 0;i<ja1.length();i++)
                    {
                        course[i]=ja1.getJSONObject(i).getString("course");
                        name[i]=ja1.getJSONObject(i).getString("first_name")+" "+ja1.getJSONObject(i).getString("last_name");
                        sem[i]=ja1.getJSONObject(i).getString("semester");
                        place[i]=ja1.getJSONObject(i).getString("place");
                        email[i]=ja1.getJSONObject(i).getString("email");
                        value[i]="course: "+course[i]+"\nname: "+name[i]+"\nsemester: "+sem[i]+"\nplace: "+place[i]+"\nemail: "+email[i];

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