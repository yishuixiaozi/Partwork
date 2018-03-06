package com.hhit.edu.partwork;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhit.edu.view.ObservableScrollView;

/**
 * 滑动固定顶部栏的效果
 */
public class UdmoveActivity extends Activity
    implements ObservableScrollView.OnObservableScrollViewScrollChanged{

    private ObservableScrollView sv_contentView;
    private LinearLayout ll_topView;
    private TextView tv_topView;
    private LinearLayout ll_fixedView;
    //记录内层固定布局到屏幕顶部的距离
    private int mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题
        setContentView(R.layout.activity_udmove);
        //布局组件初始化
        sv_contentView= (ObservableScrollView) findViewById(R.id.sv_contentView);
        ll_topView= (LinearLayout) findViewById(R.id.ll_topView);
        tv_topView= (TextView) findViewById(R.id.tv_topView);
        ll_fixedView= (LinearLayout) findViewById(R.id.ll_fixedView);
        //滚动布局设置监听
        sv_contentView.setOnObservableScrollViewScrollChanged(this);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            //获取HeaderView的高度，当滑动大于等于这个高度的时候，需要把topView移除当前布局，放入到外层布局
            mHeight=ll_topView.getTop();
        }
    }
    /**
     *
     * @param l 当前滑动的X卓距离
     * @param t Y距离
     * @param oldl  上一次X距离
     * @param oldt   上一次Y距离
     */
    @Override
    public void onObservableScrollViewScrollChanged(int l, int t, int oldl, int oldt) {
        if(t>=mHeight){//滑动距离>内层固定布局到顶部的距离
            if(tv_topView.getParent()!=ll_fixedView){
                ll_topView.removeView(tv_topView);
                ll_fixedView.addView(tv_topView);
            }
        }else{
            if(tv_topView.getParent()!=ll_topView){
                ll_fixedView.removeView(tv_topView);
                ll_topView.addView(tv_topView);
            }
        }
    }

}
