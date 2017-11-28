package com.example.zhangpeng30.opencvtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       final Bitmap bitmap = BitmapFactory.decodeResource(getResources() , R.drawable.lyf);
       final  ImageView imageView =findViewById(R.id.native_blur_view);
       // jni 层进行数据处理
        new Thread(){
            @Override
            public void run() {
                ImageUtil imageUtil = new ImageUtil();
               final Bitmap bitmapblur = imageUtil.getBlurBitmapJni(bitmap);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmapblur);
                    }
                });

            }
        }.start();

        // java层进行数据处理
//        new Thread(){
//            @Override
//            public void run() {
//                ImageUtil imageUtil = new ImageUtil();
//                final Bitmap bitmapblur = imageUtil.getBlurBitmapJava(bitmap);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        imageView.setImageBitmap(bitmapblur);
//                    }
//                });
//
//            }
//        }.start();


        // java层 和 jni 层都使用opencv 进行数据处理
//        new Thread(){
//            @Override
//            public void run() {
//                ImageUtil imageUtil = new ImageUtil();
//               final Bitmap bitmapblur = imageUtil.getBlurBitmapJavaAndJni(bitmap);
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        imageView.setImageBitmap(bitmapblur);
//                    }
//                });
//
//            }
//        }.start();
    }

}
