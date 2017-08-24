package com.hgz.test.photeviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hgz.test.photeviewpager.dialog.MyDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WebView webView = (WebView) findViewById(R.id.webview);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //给html中的img设置点击事件,当点击的时候遍历所有图片传给安卓
                //// TODO: 2017/8/21 调用js 中的方法,去遍历, 如果没有这个方法我们需要自己写一个遍历的方法
                webView.loadUrl("javascript:(function(){" +
                        "var objs = document.getElementsByTagName(\"img\"); " +
                        "var imgUrl = \"\";" +
                        "var filter = [\"img//EventHead.png\",\"img//kong.png\",\"hdtz//button.png\"];" +
                        "var isShow = true;" +
                        "for(var i=0;i<objs.length;i++){" +
                        "for(var j=0;j<filter.length;j++){" +
                        "if(objs[i].src.indexOf(filter[j])>=0) {" +
                        "isShow = false; break;}}" +
                        "if(isShow && objs[i].width>100){" +
                        "imgUrl += objs[i].src + ',';isShow = true;" +
                        "    objs[i].onclick=function()  " +
                        "    {  "
                        + "        window.imageListener.openImage(imgUrl);" +
                        "    }" +
                        "}" +
                        "}" +
                        "})()");
            }
        });
        webView.loadUrl("http://news.sina.com.cn/o/2017-08-20/doc-ifykcirz3302042.shtml");

        //javascript调用安卓的方法  window.安卓一个类的别名.安卓这个类的方法名(传入对应的参数)
        //通过这个addJavascriptInterface方法,将安卓中的类和他对应的别名传给js
        webView.addJavascriptInterface(new JavascriptInterface(),"imageListener");

        //去拿屏幕的宽高
        getWindowHeightAndWidth();
    }

    public void getWindowHeightAndWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = getWindowManager();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        Config.WINDOW_WIDTH=displayMetrics.widthPixels;
        Config.WINDOW_HEIGHT=displayMetrics.heightPixels;
    }

    /**
     * js调用安卓的类
     */
    public class JavascriptInterface{
        //js将所有图片url用逗号分隔拼接成字符串,通过 openImage(String imageUrl)这个方法的参数传给我们
        @android.webkit.JavascriptInterface
        public void openImage(String imageUrl){
            String[] imgs=imageUrl.split(",");
            //将字符串解析成图片url的集合
            final ArrayList<String> imgUrlList = new ArrayList<>();
            for (String s:imgs) {
                imgUrlList.add(s);
            }
            //从js回调回来的方法默认是在js的一个线程中是子线程,所以要做更新UI的操作的话需要放到主线程
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //拿到图片url集合去显示我们的viewpager
                    new MyDialog(MainActivity.this,imgUrlList).show();
                }
            });
        }
    }
}
