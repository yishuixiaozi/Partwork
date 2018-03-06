package com.hhit.edu.partwork;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*去菜单页面*/
    public void goMenu(View view){
        startActivity(new Intent(this,MoveActivity.class));
    }
    /*去登陆界面*/
    public void goLogin(View view){
        startActivity(new Intent(this,LoginActivity.class));
    }
    /*上传图片测试界面*/
    public void goUpload(View view){
        startActivity(new Intent(this,HomeActivity.class));
    }
    /*去登陆界面*/
    public void goYulan(View view){
        startActivity(new Intent(this,PreviewActivity.class));
    }

    /*去上下滑动界面*/
    public void goGundong(View view){
        startActivity(new Intent(this,UdmoveActivity.class));
    }
    /*去搜索界面*/
    public void goSearch(View view){
        System.out.println("这是搜索的内容");
        startActivity(new Intent(this,SearchDemo.class));
    }
}
