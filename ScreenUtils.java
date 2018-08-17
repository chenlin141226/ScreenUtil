package com.example.user.screenutil;

import android.app.Application;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

/**
 * Created by 52927 on 2018/3/9.
 * ScreenUtils
 */
public class ScreenUtils {

    public  static void activateScreenAdapt(Application context) {
       // new ScreenAdaptHelper(context, 375).activate();
    }

    public static float pt2px(float px) {
        //return ScreenAdaptHelper.pt2px(MyApplication.getInstance(), px);
        return 1;
    }


    /**
     * 关联要监听的视图
     *
     * @param viewObserving
     */
    public void assistActivity(View viewObserving) {
        reset(viewObserving);
    }

    private  View mViewObserved;//被监听的视图
    private  int usableHeightPrevious;//视图变化前的可用高度
    private  ViewGroup.LayoutParams frameLayoutParams;

    private void reset(final View viewObserving) {
        mViewObserved = viewObserving;
        //给View添加全局的布局监听器
        mViewObserved.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                Log.e("onGlobalLayout", "onGlobalLayout: " );
                resetLayoutByUsableHeight(computeUsableHeight());
            }
        });


        frameLayoutParams = mViewObserved.getLayoutParams();
    }

    private void resetLayoutByUsableHeight(int usableHeightNow) {
        //比较布局变化前后的View的可用高度
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致
            //将当前的View的可用高度设置成View的实际高度
            frameLayoutParams.height = usableHeightNow;
            mViewObserved.requestLayout();//请求重新布局
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 计算视图可视高度
     *
     * @return
     */
    private int computeUsableHeight() {
        Rect r = new Rect();
        mViewObserved.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }
}
