package com.hhit.edu.partwork;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.hhit.edu.library.TabItem;
import com.hhit.edu.library.XFragmentTabHost;
public class MoveActivity extends AppCompatActivity {
    private XFragmentTabHost mTabHost;//创建分段标签栏对象
    String[] mTabTitle = new String[] {"首页", "软件", "游戏", "管理"};
    //获取菜单栏四个drawable对象ID
    int[] mImageResId = new int[] {R.drawable.sel_tab_home, R.drawable.sel_tab_app,
            R.drawable.sel_tab_game, R.drawable.sel_tab_mag};
    //创建class类名，后面有方法需要使用
    Class[] mFragClass = new Class[] {TabFragment.class, TabFragment.class,
            TabFragment.class, TabFragment.class};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_move);//启动菜单栏视图
        initTabHost();//初始化标签，加载视图
    }

    private void initTabHost() {
        //获取菜单页面引用布局tabhost对象
        mTabHost = (XFragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.relate_tab_content);
        mTabHost.setTabMode(XFragmentTabHost.TabMode.MoveToTop);//将mTabMode设置为MoveToTop
        mTabHost.setTextActiveColor(Color.BLUE);//将XFragmentTabHost的mTextActiveColor变量设置为blue
        /*添加标签到*/
        for (int i = 0; i < mFragClass.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putString(TabFragment.FRAG_KEY, mTabTitle[i]);//更改FRAG_KEY的变量值然后放入bundle
            //下面的参数一次是对应标签的title、imgres、TableFragment类名、捆绑数据mTableItem[i]
            System.out.println("有没有1111111111111111111");
            mTabHost.addTabItem(new TabItem(mTabTitle[i], mImageResId[i]), mFragClass[i], bundle);
        }
    }
}
