package com.hhit.edu.partwork;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hhit.edu.view.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private CircleImageView ivHeadLogo;//类对象
    private String localImg;//本地图像字符
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //获取组布局对象
        ivHeadLogo= (CircleImageView) findViewById(R.id.iv_head);
        ivHeadLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件showSheetDialog();
                showSheetDialog();//点击头像出现选择文本框
            }
        });
    }

    /*这个就是获得内容的*/
    private HomeActivity getActivity(){
        return this;
    }

    /*这个注解的意思是不检测过期的方法，这样就过滤掉了很多不必要的检错内容*/
    @SuppressWarnings("deprecation")
    private void showSheetDialog(){
        View view=getLayoutInflater().inflate(R.layout.sp_photo_choose_dialog,null);
        /*final Dialog dialog=new Dialog(this,R.style.transparentFrameWindowStyle);*/
        final Dialog dialog=new Dialog(this,R.style.transparentFrameWindowStyle);
        dialog.setContentView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        Window window=dialog.getWindow();
        window.setWindowAnimations(R.style.main_tab_bottom);
        WindowManager.LayoutParams wl = window.getAttributes();

        //设置宽高样式
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height=ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(wl);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        //点击的是拍照按钮
        Button btnCamera = (Button) view.findViewById(R.id.btn_to_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击的是拍照");
            }
        });

        //点击的是从相册选择
        Button btnPhoto = (Button) view.findViewById(R.id.btn_to_photo);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击的是从相册选择");
            }
        });

        Button btnCancel = (Button) view.findViewById(R.id.btn_to_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击的是取消按钮");
            }
        });

    }
}
