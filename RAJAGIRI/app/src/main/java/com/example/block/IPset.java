package com.example.block;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IPset extends Activity implements View.OnClickListener {
    EditText ip;
    Button save;
    @Override
    public void onBackPressed() {
        Intent ins=new Intent(Intent.ACTION_MAIN);
        ins.addCategory(Intent.CATEGORY_HOME);
        ins.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(ins);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipset);
        ip=(EditText)findViewById(R.id.editTextTextPersonName);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip.setText(sh.getString("ip",""));
        save=(Button)findViewById(R.id.button);
        save.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String IP=ip.getText().toString();
        int flag=0;
        if(IP.equals("")){
            ip.setError("*");
            flag++;
        }
        if (flag==0) {
            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = sh.edit();
            ed.putString("ip", IP);
            ed.putString("url", "http://"+IP+":5000/");
            ed.commit();
            Intent i = new Intent(getApplicationContext(), login.class);
            startActivity(i);
        }
    }
}