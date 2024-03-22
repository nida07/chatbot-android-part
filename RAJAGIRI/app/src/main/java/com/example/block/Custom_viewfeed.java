package com.example.block;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom_viewfeed extends BaseAdapter {
    private Context context;
    ArrayList<String> a;
    //    ArrayList<String> b;
    ArrayList<String> c;


    SharedPreferences sh;



    public Custom_viewfeed(Context applicationContext, ArrayList<String> a, ArrayList<String> c) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
//        this.b=b;
        this.c=c;
//        this.e=e;
//        this.f=f;
//        this.g=g;

        sh=PreferenceManager.getDefaultSharedPreferences(applicationContext);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.activity_custom_viewfeed, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.tvhall);
//        ImageView i1=(ImageView) gridView.findViewById(R.id.imgaprtmnt);
        TextView tv2=(TextView)gridView.findViewById(R.id.tvbathroom);
        TextView tv3=(TextView)gridView.findViewById(R.id.tvbalcony);
//        TextView tv4=(TextView)gridView.findViewById(R.id.tvbalcony);
//        TextView tv5=(TextView)gridView.findViewById(R.id.phone1);
//        TextView tv6=(TextView)gridView.findViewById(R.id.email1);

//        java.net.URL thumb_u;
//        try {
//
//            //thumb_u = new java.net.URL("http://192.168.43.57:5000/static/photo/flyer.jpg");
//
//            thumb_u = new java.net.URL("http://"+sh.getString("ip","")+":5000/static/Product/"+b.get(position));
//            Drawable thumb_d = Drawable.createFromStream(thumb_u.openStream(), "src");
//            i1.setImageDrawable(thumb_d);
//
//        }
//        catch (Exception e)
//        {
//            Log.d("errsssssssssssss",""+e);
//        }


        tv1.setText(a.get(position));
        tv2.setText(c.get(position));

//        tv4.setText(e.get(position));
//        tv5.setText(f.get(position));
//        tv6.setText(g.get(position));





        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
//        tv4.setTextColor(Color.BLACK);
//        tv5.setTextColor(Color.BLACK);
//        tv6.setTextColor(Color.BLACK);











        return gridView;

    }

}





