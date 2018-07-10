package com.freak.android.getpricturedemo;

import android.app.Application;
import android.util.DisplayMetrics;

import java.io.File;

/**
 * Created by lzj on 2017/8/21.
 */

public class MAppliaction extends Application {


    public static final int UPLOAD_STEP = 100;//多少步上传一次；

    private static MAppliaction mApplication;
    /**
     * 缓存拍照图片路径
     */
    public File takePhotoCacheDir = null;

    /**
     * 获取Application
     */
    public static MAppliaction getApp() {
        return mApplication;
    }

    public boolean socketOnline = false;

//    private DatabaseHelper dbinstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        baseInit();

    }


    private void baseInit() {
//        Thread.setDefaultUncaughtExceptionHandler(CrashExpection.getInstance(
//                this, CachePathUtil.getCachePathFile("/crash")
//                        .getAbsolutePath()));
        initTakePhotoFile();
        /**
         * 初始化尺寸工具类
         */
        initDisplayOpinion();
    }
    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
    }

    /**
     * 图片存储初始化
     */
    public void initTakePhotoFile() {
        this.takePhotoCacheDir = CachePathUtil.getCachePathFile("/picture/sm_photo");
    }


}
