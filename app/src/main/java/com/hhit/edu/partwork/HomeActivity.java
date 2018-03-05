package com.hhit.edu.partwork;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hhit.edu.app.TApplication;
import com.hhit.edu.utils.SDPathUtils;
import com.hhit.edu.view.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

public class HomeActivity extends AppCompatActivity {

    private CircleImageView ivHeadLogo;//类对象
    private String localImg;//本地图像字符
    ImageLoader imageLoader= ImageLoader.getInstance();
   /* TApplication imageLoader= TApplication.getInstance();*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imageLoader.init(ImageLoaderConfiguration.createDefault(HomeActivity.this));
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

    /*这个注解的意思是不检测过期的方法，
    这样就过滤掉了很多不必要的检错内容*/
    @SuppressWarnings("deprecation")
    private void showSheetDialog(){
        View view=getLayoutInflater().inflate(R.layout.sp_photo_choose_dialog,null);
        final Dialog dialog=new Dialog(this,R.style.transparentFrameWindowStyle);
        dialog.setContentView(view,new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        //这里只是形成一个dialog在界面底部供自己选择
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
                dialog.dismiss();//销毁dialog，然后进入拍照的内容
                //权限检查,如果权限不足，返回内容提示
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 222);
                    return;
                } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 222);
                    return;
                } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 222);
                    return;
                }else{
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    /*sendBroadcast(openCameraIntent);*/
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {//目前是执行的这个
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(SDPathUtils.getCachePath(), "temp.jpg")));
                        startActivityForResult(openCameraIntent,2);
                    } else {
                        Uri imageUri = FileProvider.getUriForFile(HomeActivity.this, "com.camera_photos.fileprovider", new File(SDPathUtils.getCachePath(), "temp.jpg"));
                        openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(openCameraIntent, 2);
                    }
                }
            }
        });

        //点击的是从相册选择
        Button btnPhoto = (Button) view.findViewById(R.id.btn_to_photo);
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent=new Intent(Intent.ACTION_PICK,null);//创建行动
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,1);//开启行动，并且处理结果内容
            }
        });

        Button btnCancel = (Button) view.findViewById(R.id.btn_to_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });

    }
    /**
     * 意向结果的处理
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {//这个是选取照片的
            startPhotoZoom(data.getData());
        } else if (requestCode == 2) {//这个是拍照的
            File temp = new File(SDPathUtils.getCachePath(), "temp.jpg");
            //temp =/storage/emulated/0/ZFCrash/cache/temp.jpg
            startPhotoZoom(Uri.fromFile(temp));
        } else if (requestCode == 3) {//这个是截图之后过来的
            if (data != null) {
               setPicToView(data);
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent(getActivity(), PreviewActivity.class);
        intent.setDataAndType(uri, "image/*");
       /* System.out.println("uri="+uri);*/
        startActivityForResult(intent, 3);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bitmap bitmap = null;
        byte[] bis = picdata.getByteArrayExtra("bitmap");
        bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
        localImg = System.currentTimeMillis() + ".JPEG";
        System.out.println("localImg="+localImg);
        if (bitmap != null) {
            SDPathUtils.saveBitmap(bitmap, localImg);
            Log.e("本地图片绑定", SDPathUtils.getCachePath() + localImg);
            setImageUrl(ivHeadLogo, "file:/" + SDPathUtils.getCachePath() + localImg, R.mipmap.head_logo);

        }
    }

    /*private DisplayImageOptions options;*/
    private DisplayImageOptions options;
    public void setImageUrl(ImageView ivId, String imageUrl, int emptyImgId) {
        if (options == null) {//每次 创建对象
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(emptyImgId)//图片下载期间显示图片
                    .showImageForEmptyUri(emptyImgId)//Uri错误显示的图片
                    .showImageOnFail(emptyImgId).cacheInMemory(true)//图片加载失败显示图片
                    .cacheOnDisk(true).considerExifParams(true)//缓存图片，旋转方砖
                    .bitmapConfig(Bitmap.Config.RGB_565).build();//图片解码类型
        }
        imageLoader.displayImage(imageUrl, ivId, options);//这里一定要初始化
    }


}
