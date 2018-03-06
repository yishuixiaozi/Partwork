package com.hhit.edu.partwork;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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
        System.out.println("2121212121212");
        // 2. 绑定视图
        setContentView(R.layout.activity_search);
        System.out.println("1111111111111");
        // 3. 绑定组件
        searchView = (SearchView) findViewById(R.id.search_view);

        // 4. 设置点击搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        System.out.println("22222122222");
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {
                System.out.println("我收到了" + string);
            }
        });
        System.out.println("33333333333");
        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
        System.out.println("44444444444");


    }
}