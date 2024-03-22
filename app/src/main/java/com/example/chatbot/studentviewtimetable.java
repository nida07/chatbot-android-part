package com.example.chatbot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class studentviewtimetable extends AppCompatActivity implements JsonResponse{
    ListView t1;
    String[] course,sem,date,time,value,sub2,sub3,sub4,sub5,sub6;
    EditText e1;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentviewtimetable);
        t1=(ListView)findViewById(R.id.ttble);
        JsonReq JR=new JsonReq();
        JR.json_response=(JsonResponse)studentviewtimetable.this;
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String q="/studentviewtimetable?lid="+ sh.getString("log_id", "");
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
//                JR.json_response=(JsonResponse)studentviewtimetable.this;
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
            if(method.equalsIgnoreCase("studentviewtimetable")) {
                String status = jo.getString("status");
                Log.d("pearl", status);


                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = (JSONArray) jo.getJSONArray("data");
                    //feedback_id=new String[ja1.length()];
                    course = new String[ja1.length()];
                    sem = new String[ja1.length()];
                    date = new String[ja1.length()];
                    time = new String[ja1.length()];
                    value = new String[ja1.length()];
                    sub2 = new String[ja1.length()];
                    sub3 = new String[ja1.length()];
                    sub4 = new String[ja1.length()];
                    sub5 = new String[ja1.length()];
                    sub6 = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        course[i] = ja1.getJSONObject(i).getString("course");
                        sem[i] = ja1.getJSONObject(i).getString("semester");
                        date[i] = ja1.getJSONObject(i).getString("date");
                        time[i] = ja1.getJSONObject(i).getString("time");
//                        sub2[i] = ja1.getJSONObject(i).getString("sub2");
//                        sub3[i] = ja1.getJSONObject(i).getString("sub3");
//                        sub4[i] = ja1.getJSONObject(i).getString("sub4");
//                        sub5[i] = ja1.getJSONObject(i).getString("sub5");
//                        sub6[i] = ja1.getJSONObject(i).getString("sub6");
                        value[i] = "course: " + course[i] + "\nsemester: " + sem[i] + "\nDate: " + date[i] + "\ntime: " + time[i];

                    }
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, value);
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