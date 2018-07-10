package com.freak.android.getpricturedemo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn_select;
    private ImageView img_text;
    private File userImgFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_select = findViewById(R.id.btn_select);
        img_text = findViewById(R.id.img_text);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImgPop();
            }
        });
    }

    public void selectImgPop() {
        PopupGetPictureView popupGetPictureView = new PopupGetPictureView(this, new
                PopupGetPictureView.GetPicture() {
                    @Override
                    public void takePhoto(View v) {
                        if (PermissionUtils.checkTakePhotoPermission(MainActivity.this)) {
                            userImgFile = GetPictureUtils.takePicture(MainActivity.this, IETConstant.GETPICTURE_TAKEPHOTO);
                        }
                    }

                    @Override
                    public void selectPhoto(View v) {
                        if (PermissionUtils.checkAlbumStroagePermission(MainActivity.this)) {
                            GetPictureUtils.selectPhoto(MainActivity.this, IETConstant.GETPICTURE_SELECTPHOTO);
                        }
                    }
                });
        popupGetPictureView.showPop(btn_select);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            return;
        }

        switch (requestCode) {
            //拍照
            case IETConstant.GETPICTURE_TAKEPHOTO:
                userImgFile = GetPictureUtils.cutPicture(MainActivity.this, userImgFile);
                break;
            //选择照片
            case IETConstant.GETPICTURE_SELECTPHOTO:
                userImgFile = GetPictureUtils.getPhotoFromIntent(data, MainActivity.this);
                userImgFile = GetPictureUtils.cutPicture(MainActivity.this, userImgFile);
                break;
            //裁剪照片
            case IETConstant.CUT_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    compressAndcommitImg(userImgFile);
                }
                break;
            default:
                break;
        }

    }

    public void compressAndcommitImg(File file) {
        List<File> list = new ArrayList<>();
        list.add(file);

        BitmapUtil.compressFiles(list, new BitmapUtil.CompressImageResponse() {
            @Override
            public void onSuccess(List<File> imgs) {
                File imgFile = imgs.get(0);
                Uri uri = Uri.fromFile(imgFile);
                img_text.setImageURI(uri);
            }

            @Override
            public void onDo() {
//                showLoading(view.getMContext());
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
