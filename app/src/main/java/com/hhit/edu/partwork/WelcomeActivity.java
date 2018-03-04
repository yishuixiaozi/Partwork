package com.hhit.edu.partwork;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends Activity {
    private static final int DELAY=2000;
    private static final int GO_HOME=1000;
    private static final int GO_GUIDE=1001;
    private boolean IsFirst=false;
    private static final String START_KEY="isFirst";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);//首先来到欢迎页面
        init();
    }

    // 去主界面
    private void gohome() {
        System.out.println("主页面内容--登录界面内容");
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));//去主页面内容方便测试
        finish();
    }

    // 去引导界面
    private void goguide() {
        System.out.println("去引导页面");
        startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));//去引导界面
        finish();
    }
    //接受消息
    private Handler hander = new Handler() {

        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GO_HOME:
                    gohome();
                    break;
                case GO_GUIDE:
                    goguide();
                    break;
            }
        }
    };
    //初始化作用
    private void init() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        //设置操作类型只访问本程序的SharedPreference
        IsFirst = preferences.getBoolean(START_KEY, true);;
        //检索不到KEY值，直接返回后面的true，瞎绕
        if (!IsFirst) {//不是第一次
            hander.sendEmptyMessageDelayed(GO_HOME, DELAY);
            //延迟2秒后发送数据GO_HOME
        } else {//第一次登陆
            hander.sendEmptyMessageDelayed(GO_GUIDE, DELAY);
            //延迟2秒后发送数据GO——GUIDE，然后启动引导页
            SharedPreferences.Editor editor = preferences.edit();
            //让配置参数处于可编辑状态
            editor.putBoolean(START_KEY, false);
            //写入参数键值内容
            editor.commit();
            //保存
        }
    }
}
