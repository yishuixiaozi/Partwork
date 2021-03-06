package com.hhit.edu.partwork;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.hhit.edu.same.GuideViewPagerAdapter;
import java.util.ArrayList;
import java.util.List;
public class GuideActivity extends Activity {
    private ViewPager pager;
    private GuideViewPagerAdapter adapter;// private List<View> views;
    private ImageView[] dots;
    private List<View> views;
    private RelativeLayout layout_guide = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);//启动引导界面
        initViews();
        initdots();
    }
    /**
     *初始化Views
     */
    private void initViews() {
        layout_guide = (RelativeLayout) findViewById(R.id.layout_guide);//获取该页面布局对象
        views = new ArrayList<View>();//存放组件的数组
        //加入图片
        ImageView imageView = new ImageView(this);//创建imageView对象在该页面
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);//填充类型
        imageView.setImageResource(R.drawable.ontheway);//来源资源是某个图片
        views.add(imageView);//数组集合加上这个类型组件

        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.acompany);
        views.add(imageView);

        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.drawable.liuxing);
        views.add(imageView);

        //加入最后一页布局
        LayoutInflater inflater = LayoutInflater.from(this);
        views.add(inflater
                .inflate(R.layout.layout_guide_pager_end, null));
        //设置引导组件页面适配器内容是某个组件
        adapter = new GuideViewPagerAdapter(this, views);
        //获取ViewPager对象
        pager = (ViewPager) findViewById(R.id.pager);
        // 为该组件设置适配器
        pager.setAdapter(adapter);
        // 注册监听器，图片转换监听
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {//继承方法
                for (int i = 0; i < views.size(); i++) {
                    if (arg0 == i) {
                        dots[i].setImageResource(R.drawable.login_point_selected);
                        //选中的页面的对应的点加深颜色通过设置图形背景
                    } else {
                        dots[i].setImageResource(R.drawable.login_point);
                        //没有选中的点加上比较浅的颜色
                    }
                }
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        // 点击按钮跳转到主界面
        views.get(views.size() - 1).findViewById(R.id.btn_start)//最后一个获取按钮组件并且设置点击事件
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GuideActivity.this,
                                MainActivity.class));//跳到主页面，引导界面activity_guide页面结束
                        finish();//无法通过返回键回到上面的内容
                    }
                });
    }
    /**
     * 初始化点
     */
    private void initdots() {
        dots = new ImageView[views.size()];//创建四个点的imageview组件内容代销
        for (int i = 0; i < views.size(); i++) {
            dots[i] = new ImageView(this);//创建imageview组件对象
            if (i == 0) {//第一个点设置深色
                dots[i].setImageResource(R.drawable.login_point_selected);
            } else {//其余的点设置浅色
                dots[i].setImageResource(R.drawable.login_point);
            }
            dots[i].setScaleType(ImageView.ScaleType.FIT_XY);//设置图片填充

            // 为每一个点设置位置
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lParams.topMargin = dm.heightPixels*19/20;
            lParams.leftMargin = dm.widthPixels / 2 + (i - views.size() / 2)
                    * dm.widthPixels/20;
            layout_guide.addView(dots[i], lParams);
        }
    }
}
