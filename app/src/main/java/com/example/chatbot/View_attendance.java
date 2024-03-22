package com.example.chatbot;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class View_attendance extends AppCompatActivity implements JsonResponse{
    ListView t1;
    String[] date,stat,val;
    EditText e1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentviewtimetable);
        t1=(ListView)findViewById(R.id.ttble);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)View_attendance.this;
        String q="/View_attendance?lid="+Login.logid;
        q=q.replace(" ","%20");
        JR.execute(q);
//        e1=(EditText)findViewById(R.id.etsearch);
//        e1.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                String var=e1.getText().toString();
//                JsonReq JR=new JsonReq();
//                JR.json_response=(JsonResponse)View_attendance.this;
//                String q="/studentsearch?search="+var;
//                q=q.replace(" ","%20");
//                JR.execute(q);
//
//
//            }
//        });

    }
    @Override
    public void response(JSONObject jo) {
        try {

            String method=jo.getString("method");
            if(method.equalsIgnoreCase("View_attendance")) {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    //feedback_id=new String[ja1.length()];
                    date = new String[ja1.length()];
                    stat = new String[ja1.length()];
                    val = new String[ja1.length()];


                    for (int i = 0; i < ja1.length(); i++) {
                        date[i] = ja1.getJSONObject(i).getString("course");
                        stat[i] = ja1.getJSONObject(i).getString("semester");

                        val[i] = "Date: " + date[i] + "\nStatus: " + stat[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, val);
                    t1.setAdapter(ar);
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