package com.hgz.test.tishigengxinbanbenxiazai;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private int versionCode;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            showUpgradeDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //设置跟新对话框
    private void showUpgradeDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("跟新版本");
        builder.setMessage("当前版本是" + versionCode + " 需要升级");
        builder.setPositiveButton("跟新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downLoad();
            }
        });
        builder.setNegativeButton("取消",null);
        builder.create().show();
    }
    //设置进度条对话框
    private void showProgressDialog(){
        progressDialog = new ProgressDialog(this);
        //设置progressDialog显示样式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("请等待");
        progressDialog.setMessage("正在下载！！！");
        progressDialog.setProgress(0);
        progressDialog.show();
    }

    private void downLoad(){
        String url="http://gdown.baidu.com/data/wisegame/f98d235e39e29031/baiduxinwen.apk";
        RequestParams requestParams = new RequestParams(url);
        requestParams.setAutoResume(true);
        requestParams.setAutoRename(false);
        String path= Environment.getExternalStorageDirectory()+"/myapk.apk";
//        File file=new File(path);
        //设置保存路径
        requestParams.setSaveFilePath(path);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                installDownloadApk(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                progressDialog.dismiss();
            }

            @Override
            public void onWaiting() {

            }

            @Override
            public void onStarted() {
                showProgressDialog();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                float progress=(float) current/(float) total*100;
                if (progress >= 0 && progress <= 100) {
                    updataProgressDialog((int) progress);
                }
            }
        });
    }
    //让进度条对话框消失
    private void dismissProgressDialog(){
        if (progressDialog == null) {
            return;
        }
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
    private void updataProgressDialog(int progress) {
        if (progressDialog == null) {
            return;
        }
        progressDialog.setProgress(progress);
    }
    private void installDownloadApk(File result){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
        startActivity(intent);
    }
}
