package com.zyn.floatlistview.sample;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends FloatBaseAdapter
{
    List<String> mList;
    Context mContext;

    public MyAdapter(Context context, @NonNull List<String> list)
    {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView textView;
        if (convertView == null)
        {
            convertView = new LinearLayout(mContext);

            textView = new TextView(mContext);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setGravity(Gravity.CENTER);

            convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ((ViewGroup)convertView).addView(textView);
            convertView.setTag(textView);
        } else
        {
            textView = (TextView) convertView.getTag();
        }

        if (position % 5 == 0)
        {
            textView.setBackgroundColor(Color.GRAY);
            textView.setTextColor(Color.WHITE);
            textView.setPadding(20, 80, 20, 80);
        } else
        {
            textView.setBackgroundColor(Color.WHITE);
            textView.setTextColor(Color.BLACK);
            textView.setPadding(20, 60, 20, 60);
        }
        textView.setText(mList.get(position));

        return convertView;
    }

    @Override
    public boolean isFloat(int position)
    {
        return position % 5 == 0;
    }
}
