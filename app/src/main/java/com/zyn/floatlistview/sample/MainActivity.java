package com.zyn.floatlistview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.zyn.floatlistview.FloatListView;
import com.zyn.floatlistview.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> list = new ArrayList<>();
        for(int i=0;i<30;i++)
        {
            if(i%5==0)
                list.add("第"+(i)+"个Title");
            else
                list.add("第"+(i)+"个Item");
        }

        FloatListView floatListView = (FloatListView) findViewById(R.id.flv);
        floatListView.setAdapter(new MyAdapter(this,list));
    }
}
