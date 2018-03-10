package com.hhit.edu.partwork;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.hhit.edu.view.ICallBack;
import com.hhit.edu.view.SearchView;
import com.hhit.edu.view.bCallBack;

/**
 * Created by Carson_Ho on 17/8/11.
 */

public class SearchDemo extends AppCompatActivity {

    // 1. 初始化搜索框变量
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 2. 绑定视图
        setContentView(R.layout.activity_search);
        // 3. 绑定组件
        searchView = (SearchView) findViewById(R.id.search_view);
        // 4. 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                System.out.println("我收到了" + string);
            }
        });
        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                System.out.println("返回的内容");
                finish();//结束搜索
            }
        });
        /*searchView.findViewById(R.id.et_search).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    System.out.println("您出发了按下触摸事件");
                }
                return false;
            }
        });*/
    }
}