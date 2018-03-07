package com.hhit.edu.partwork;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhit.edu.view.ObservableScrollView;
import com.hhit.edu.view.SearchView;

/**
 * 滑动固定顶部栏的效果
 */
public class UdmoveActivity extends Activity
    implements ObservableScrollView.OnObservableScrollViewScrollChanged{

    private ObservableScrollView sv_contentView;
    private LinearLayout ll_topView;
    private TextView tv_topView;
    private LinearLayout ll_fixedView;
    //这里是尝试更改代码
    private SearchView searchView;
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
        //尝试
        searchView= (SearchView) findViewById(R.id.search_view);

        sv_contentView.setOnObservableScrollViewScrollChanged(this);
    }

    //这里尝试修改
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            //获取HeaderView的高度，当滑动大于等于这个高度的时候，需要把topView移除当前布局，放入到外层布局
            /*mHeight=ll_topView.getTop();*/
            mHeight=ll_topView.getTop();//mHeight的大小就是固定线性布局顶部到屏幕顶部的距离
            System.out.println("mHeight的变量是"+mHeight);//这里是394
        }
    }
    /**
     * 这里依然尝试修改
     * @param l 当前滑动的X卓距离
     * @param t Y距离
     * @param oldl  上一次X距离
     * @param oldt   上一次Y距离
     */
    @Override
    public void onObservableScrollViewScrollChanged(int l, int t, int oldl, int oldt) {
        /*if(t>=mHeight){//滑动界面向上的距离超过了mHeiht
            System.out.println("当前移动Y轴距离大于线性固定布局高度");
            if(tv_topView.getParent()!=ll_fixedView){//固定文本布局不属于顶部布局
                System.out.println("大于    if");//固定布局替换了fixed布局
                ll_topView.removeView(tv_topView);//线性布局里边去掉了文本，感觉没用
                ll_fixedView.addView(tv_topView);//原本的顶部布局加上了固定布局的内容
            }
        }else{//滑动界面往下移动小于了mHeight
            System.out.println("当前移动Y轴距离小于线性固定布局高度");
            if(tv_topView.getParent()!=ll_topView){//文本固定布局不再线性固定布局上面
                System.out.println("小于     if");//fixed布局替换了固定布局
                ll_fixedView.removeView(tv_topView);//顶部布局去掉不属于他的固定文本布局
                ll_topView.addView(tv_topView);//本来该有的线性布局加上固定文本布局
            }
        }*/
       if (t>=mHeight){//向上互动剧大于mHeight
            if (tv_topView.getParent()!=ll_fixedView){//tv_topView 固定文本   ll_topView 固定线性布局
                ll_fixedView.removeView(searchView);
                ll_topView.removeView(tv_topView);//线性布局里边除去固定文本
                ll_fixedView.addView(tv_topView);//搜索框里边改为tv_topView
            }
        }else {
            if (tv_topView.getParent()!=ll_topView){//这里已经导致了tv_topView的福清是fixed了
                ll_fixedView.removeView(tv_topView);
                ll_topView.addView(tv_topView);
                ll_fixedView.addView(searchView);//添加上布局

            }

        }
    }

}
