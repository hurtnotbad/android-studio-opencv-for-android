package com.example.zhangpeng30.opencvtest;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

/**
 * Created by zhangpeng30 on 2017/11/27.
 */

public class ImageUtil {

    /********    jni层使用opencv 模糊图像         ******/
    static {
        System.loadLibrary("lammy-jni");
    }

    public Bitmap getBlurBitmapJni(Bitmap bitmap){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[w * h];
        bitmap.getPixels(pix , 0,w,0,0, w, h);
        int[] edgeArray = getBlurBitmap(w, h ,pix);
        Bitmap edgeBitmap = Bitmap.createBitmap(edgeArray,w,h, Bitmap.Config.ARGB_8888);
        return edgeBitmap;
    }

    private native  int[] getBlurBitmap(int w , int h ,int[] pix);


    /********    java层使用opencv         ******/

    static {
        System.loadLibrary("opencv_java3");
    }
    public Bitmap getBlurBitmapJava(Bitmap bitmap){
        Mat mat = new Mat();
        Mat matOut = new Mat();
        Utils.bitmapToMat(bitmap , mat);
        Imgproc.blur(mat,matOut,new Size(20,20) );

        Bitmap blurBitmap = Bitmap.createBitmap(bitmap.getWidth() ,bitmap.getHeight() , Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(matOut , blurBitmap);
        return blurBitmap;
    }

    /********    java层 JNI层同时使用opencv ******/

    public Bitmap getBlurBitmapJavaAndJni(Bitmap bitmap){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Mat mat = new Mat();
        Mat outMat = new Mat();
        Utils.bitmapToMat(bitmap , mat);
        getBlurBitmap2(w , h , mat.getNativeObjAddr() , outMat.getNativeObjAddr());

        Bitmap blurBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(outMat , blurBitmap);
        return blurBitmap;
    }

    private native  void getBlurBitmap2(int w , int h ,long MatAddress , long outAddress);

}
