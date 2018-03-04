package com.hhit.edu.partwork;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText loginId;
    private EditText loginPassword;
    private Button loginBtn;
    private Button loginMissps;
    private Button loginNewUser;
    private Button loginChangePw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();//初始化组件内容
    }
    /*获得各个组件内容*/
    private void initView(){
        //获取三个按钮的对象
        loginId = (EditText) findViewById(R.id.loginId);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        loginMissps = (Button) findViewById(R.id.loginMissps);
        loginMissps.setOnClickListener(this);
        loginNewUser = (Button) findViewById(R.id.loginNewUser);
        loginNewUser.setOnClickListener(this);
        loginChangePw = (Button) findViewById(R.id.loginChangePw);
        loginChangePw.setOnClickListener(this);

    }
    /*
    * 通过点击的事件
    * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                //进行一个数据库的操作
                /*
                * 1.获取页面输入的值
                * 2.与服务器端数据库比较
                * 3.看看结果，然后是否跳转*/
                Toast.makeText(LoginActivity.this, "暂时没写方法呢",
                        Toast.LENGTH_SHORT).show();
            case R.id.loginMissps:
                System.out.println("忘记密码");
                Intent a = new Intent(LoginActivity.this,
                        findpasswordActivity.class);
                startActivity(a);
                break;
            case R.id.loginNewUser:
                System.out.println("注册");
                Intent i = new Intent(LoginActivity.this, registerActivity.class);
                startActivity(i);
                break;
            case R.id.loginChangePw:
                System.out.println("更改密码");
                Intent l = new Intent(LoginActivity.this,
                        changepasswordActivity.class);
                startActivity(l);
                break;
            default:
                break;
        }
    }
}
