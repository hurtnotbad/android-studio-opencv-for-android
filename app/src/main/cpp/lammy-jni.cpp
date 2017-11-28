//
// Created by zhangpeng30 on 2017/11/27.
//

#include <jni.h>
#include "opencv2/opencv.hpp"
#ifndef OPENCVTEST_LAMMY_JNI_H
#define OPENCVTEST_LAMMY_JNI_H
#endif //OPENCVTEST_LAMMY_JNI_H

#define __DEBUG__ANDROID__ON
//write debug images
#ifdef  __DEBUG__ANDROID__ON
#include <android/log.h>
// Define the LOGI and others for print debug infomation like the log.i in java
#define LOG_TAG    "Idphoto -- JNILOG"
//#undef LOG
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG, __VA_ARGS__)
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG, __VA_ARGS__)
#define LOGW(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG, __VA_ARGS__)
#define LOGF(...)  __android_log_print(ANDROID_LOG_FATAL,LOG_TAG, __VA_ARGS__)
#else
#ifdef __DEBUG__WIN__ON
#define LOGI(...)  printf( __VA_ARGS__); printf("\n")
#define LOGD(...)  printf( __VA_ARGS__); printf("\n")
#define LOGW(...)  printf( __VA_ARGS__); printf("\n")
#define LOGE(...)  printf( __VA_ARGS__); printf("\n")
#define LOGF(...)  printf( __VA_ARGS__); printf("\n")
#else
#define LOGI(...)
#define LOGD(...)
#define LOGW(...)
#define LOGE(...)
#define LOGF(...)
#endif
#endif


extern "C" {
JNIEXPORT jintArray
JNICALL
Java_com_example_zhangpeng30_opencvtest_ImageUtil_getBlurBitmap(
        JNIEnv *env,
        jobject /* this */, jint w, jint h, jintArray pix) {
    int *cpix = env->GetIntArrayElements(pix, JNI_FALSE);
    cv::Mat mat = cv::Mat(h, w, CV_8UC4, (unsigned char *) cpix);
    cv::Mat outMat;
    //转化为3通道
    cv::cvtColor(mat, outMat, CV_BGRA2BGR);
    //处理图像
    blur(outMat, outMat, cv::Size(20, 20));

    //处理完图像后，转化为4通道
    cv::cvtColor(outMat, outMat, CV_BGR2BGRA);
    //这里传回int数组。
    uchar *ptr = outMat.data;
    int size = w * h;
    jintArray blurArray = env->NewIntArray(size);
    env->SetIntArrayRegion(blurArray, 0, size, (const jint *) ptr);
    env->ReleaseIntArrayElements(pix, cpix, 0);
    return blurArray;
}

JNIEXPORT void
JNICALL
Java_com_example_zhangpeng30_opencvtest_ImageUtil_getBlurBitmap2(
        JNIEnv *env,
        jobject /* this */ ,jint w , jint h ,jlong matAddress , jlong outMatAddress) {

    const cv::Mat &srcMat = *((const cv::Mat*)matAddress);
    cv::Mat &outMat = *(( cv::Mat*)outMatAddress);
    blur(srcMat, outMat, cv::Size(20, 20));
}

}