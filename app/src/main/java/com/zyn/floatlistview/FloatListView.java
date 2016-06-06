package com.zyn.floatlistview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zyn.floatlistview.sample.FloatBaseAdapter;

public class FloatListView extends ListView
{

    private final int HEADER_STATE_IDLE = 0;
    private final int HEADER_STATE_SCROLL = 1;


    private Context mContext;
    private View mHeaderView;
    private int mFloatPosition = -1;
    private FloatBaseAdapter mAdapter;

    public FloatListView(Context context)
    {
        this(context, null);
    }

    public FloatListView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public FloatListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initListener();
    }

    private void initView()
    {
        mHeaderView = new View(mContext);
        setDivider(new ColorDrawable(Color.BLUE));
        setDividerHeight(1);
    }

    private void initListener()
    {
        post(new Runnable()
        {
            @Override
            public void run()
            {

                setOnScrollListener(new AbsListView.OnScrollListener()
                {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState)
                    {
                        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)
                        {
                        }
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
                    {
                        int index = 0;
                        for (int i = 0; i < visibleItemCount; i++)
                        {
                            if (isInvisible(getChildAt(i)))
                            {
                                index = i;
                            }
                        }
                        updateHeader(firstVisibleItem + index);
                    }
                });
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        if (mHeaderView != null)
//        {
//            mHeaderView.measure(0,0);
//            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
//        }
        Log.e("test", "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
//        if (mHeaderView != null && mAdapter != null ) {
//            mHeaderView.measure(0,0);
//            mHeaderView.layout(0, 0, mHeaderView.getWidth(), mHeaderView.getMeasuredHeight());
//        }
        Log.e("test", "onLayout");
    }

    protected void dispatchDraw(Canvas canvas)
    {
        super.dispatchDraw(canvas);
        if (mHeaderView != null)
        {
            //分组栏是直接绘制到界面中，而不是加入到ViewGroup中
            drawChild(canvas, mHeaderView, getDrawingTime());
        }
//        Log.e("test", "DispatchDraw");
    }

    private void updateHeader(int position)
    {
        int state = getHeaderState(position);
        int group = getGroup(position);

        if (mHeaderView == null || group != mFloatPosition)
        {
            mHeaderView = mAdapter.getView(group, null, null);
            if (mHeaderView == null)
                return;

            int measureSpecWidth = MeasureSpec.makeMeasureSpec(this.getWidth(),MeasureSpec.EXACTLY);
            int measureSpecHeight = MeasureSpec.makeMeasureSpec(this.getHeight(),MeasureSpec.AT_MOST);

            mHeaderView.measure(measureSpecWidth, measureSpecHeight);
        }

        switch (state)
        {
            case HEADER_STATE_IDLE:
            {
                mHeaderView.layout(0, 0, mHeaderView.getMeasuredWidth(), mHeaderView.getMeasuredHeight());
            }
            break;
            case HEADER_STATE_SCROLL:
            {
                View view = getChildAt(position - getFirstVisiblePosition());

                int bottom;
                if(group != mFloatPosition)
                {
                    bottom = 0+mHeaderView.getMeasuredHeight();
                }
                else {
                    bottom = mHeaderView.getBottom();
                }

                int y = view.getBottom() - bottom;
                if (y > 0)
                {
                    y = 0;
                }
                mHeaderView.layout(0, y, mHeaderView.getMeasuredWidth(), mHeaderView.getMeasuredHeight());


            }
            break;
        }

        mFloatPosition = group;
    }

    private int getHeaderState(int position)
    {
        if (isLastChildrenInGroup(position))
        {
            return HEADER_STATE_SCROLL;
        } else
        {
            return HEADER_STATE_IDLE;
        }
    }

    private boolean isLastChildrenInGroup(int position)
    {
        if (position < 0 || position > mAdapter.getCount() - 1)
            return false;

        if (position == mAdapter.getCount() - 1)
            return true;

        return mAdapter.isFloat(position + 1);
    }

    private int getGroup(int position)
    {
        if (position < 0 || position > mAdapter.getCount() - 1)
            return -1;

        for (int i = position; i >= 0; i--)
        {
            if (mAdapter.isFloat(i))
            {
                return i;
            }
        }

        return -1;
    }


    public void setAdapter(FloatBaseAdapter adapter)
    {
        mAdapter = adapter;
        super.setAdapter(adapter);
    }

    private boolean isInvisible(@NonNull View view)
    {
        return view.getBottom() <= mHeaderView.getBottom();
    }

    private boolean isVisible(@NonNull View view)
    {
        return view.getBottom() > mHeaderView.getBottom();
    }
}
