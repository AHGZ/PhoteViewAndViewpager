package com.hgz.test.zhinengxiazai;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hgz.test.zhinengxiazai.app.MyApplication;

public class MainActivity extends AppCompatActivity {


    private MyBroadcastReceiver myBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(myBroadcastReceiver, filter);
        Button btnSetLoadMode = (Button) findViewById(R.id.btnSetLoadMode);
        Button btnTestLoadMode = (Button) findViewById(R.id.btnTestLoadMode);
        btnSetLoadMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] strings = {"最佳效果", "较省流量", "极省流量"};
                int mode = MyApplication.getAppContext().getSharedPreferences(NetUtils.SP_NAME, Context.MODE_PRIVATE).getInt(NetUtils.PICTURE_LOAD_MODE_KEY, 0);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("非wifi网络流量");
                builder.setSingleChoiceItems(strings, mode, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //// TODO: 2017/8/10  要保存我们的图片加载模式
                        MyApplication.getAppContext().getSharedPreferences(NetUtils.SP_NAME, Context.MODE_PRIVATE).edit().putInt(NetUtils.PICTURE_LOAD_MODE_KEY, which).commit();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
            }
        });
        btnTestLoadMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, NetUtils.getInstance().getBASE_URL(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                boolean isConnectionState = true;
                ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                        isConnectionState = false;
                        Toast.makeText(MainActivity.this, "wifi可用，下载吧", Toast.LENGTH_SHORT).show();
                    } else if (ConnectivityManager.TYPE_MOBILE == networkInfo.getType()) {
                        isConnectionState = true;
                        Toast.makeText(MainActivity.this, "现在是移动网络，当心", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "网络不可用，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "网络不可用，请检查网络", Toast.LENGTH_SHORT).show();
                }
                NetUtils.getInstance().changeNetWorkState(isConnectionState);
            }
        }
    }

    private void showSetLoadModeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(R.layout.set_mode);
        builder.create().show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcastReceiver);
    }
}
