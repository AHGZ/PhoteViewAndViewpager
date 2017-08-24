package com.hgz.test.test;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hgz.test.test.adapter.MyViewPagerAdapter;
import com.hgz.test.test.dao.SQLiteDao;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity{


    @ViewInject(R.id.toggle)
    ImageView toggle;
    @ViewInject(R.id.tabLayout)
    TabLayout tabLayout;
    @ViewInject(R.id.viewpager)
    ViewPager viewPager;
    @ViewInject(R.id.loadPindao)
    ImageView loadPindao;

    private List<String> tabLayoutTitles=new ArrayList<>();
    private List<Fragment> fragments=new ArrayList<>();
    private SlidingMenu slidingMenu;
    List<ChannelBean> list;
    private SQLiteDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
//        dao = new SQLiteDao(this);
//        ArrayList<ChannelBean> channelBeen = dao.findAll();
        tabLayoutTitles.add("红烧肉");
        tabLayoutTitles.add("鱼香肉丝");
        tabLayoutTitles.add("木须肉");
        tabLayoutTitles.add("西红柿炒鸡蛋");
        tabLayoutTitles.add("糖醋里脊");
//        tabLayout.addTab(tabLayout.newTab().setText(tabLayoutTitles.get(0)));
//        tabLayout.addTab(tabLayout.newTab().setText(tabLayoutTitles.get(1)));
//        tabLayout.addTab(tabLayout.newTab().setText(tabLayoutTitles.get(2)));
//        tabLayout.addTab(tabLayout.newTab().setText(tabLayoutTitles.get(3)));
//        tabLayout.addTab(tabLayout.newTab().setText(tabLayoutTitles.get(4)));

//        TuijianFragment tuijianFragment = new TuijianFragment();
//        RedianFragment redianFragment = new RedianFragment();
//        BeijingFragment beijingFragment = new BeijingFragment();
//        ShehuiFragment shehuiFragment = new ShehuiFragment();
//        ShipinFragment shipinFragment = new ShipinFragment();
//        fragments.add(tuijianFragment);
//        fragments.add(redianFragment);
//        fragments.add(beijingFragment);
//        fragments.add(shehuiFragment);
//        fragments.add(shipinFragment);

        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),tabLayoutTitles));
        tabLayout.setupWithViewPager(viewPager);
        initSlidingMenu();
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slidingMenu.toggle();
            }
        });
        ImageView phote = (ImageView) findViewById(R.id.photo);
        phote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI.get(MainActivity.this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
            }
        });
        RadioButton changTheme = (RadioButton) findViewById(R.id.changTheme);
        Button show = (Button) findViewById(R.id.show);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换日夜间模式
                int uiMode;
                uiMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                switch (uiMode) {
                    case Configuration.UI_MODE_NIGHT_YES:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        getSharedPreferences("theme", MODE_PRIVATE).edit().putBoolean("night_theme", false).commit();
                        break;
                    case Configuration.UI_MODE_NIGHT_NO:
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        getSharedPreferences("theme", MODE_PRIVATE).edit().putBoolean("night_theme", true).commit();
                        break;
                }
                //重建
                recreate();
            }
        });
        loadPindao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list=new ArrayList<ChannelBean>();
                ChannelBean channelBean = new ChannelBean("热门",true);
                ChannelBean channelBean1 = new ChannelBean("社会", true);
                ChannelBean channelBean2 = new ChannelBean("国内",true);
                ChannelBean channelBean3 = new ChannelBean("国际", true);
                ChannelBean channelBean4 = new ChannelBean("娱乐",true);
                ChannelBean channelBean5 = new ChannelBean("体育", true);
                ChannelBean channelBean6= new ChannelBean("军事",true);
                ChannelBean channelBean7 = new ChannelBean("科技", true);
                ChannelBean channelBean8 = new ChannelBean("财经", true);
                ChannelBean channelBean9 = new ChannelBean("时尚", true);
                ChannelBean channelBean10 = new ChannelBean("游戏", false);
                ChannelBean channelBean11 = new ChannelBean("百家号", false);
                ChannelBean channelBean12= new ChannelBean("搞笑", false);
                ChannelBean channelBean13 = new ChannelBean("金融", false);
                list.add(channelBean);
                list.add(channelBean1);
                list.add(channelBean2);
                list.add(channelBean3);
                list.add(channelBean4);
                list.add(channelBean5);
                list.add(channelBean6);
                list.add(channelBean7);
                list.add(channelBean8);
                list.add(channelBean9);
                list.add(channelBean10);
                list.add(channelBean11);
                list.add(channelBean12);
                list.add(channelBean13);
                ChannelActivity.startChannelActivity(MainActivity.this,list);
            }
        });
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private void initSlidingMenu(){
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setBehindOffset(300);
        slidingMenu.setFadeDegree(1f);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setMenu(R.layout.slidingmenu);
        slidingMenu.attachToActivity(MainActivity.this,SlidingMenu.SLIDING_CONTENT);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==101){
            String json = data.getStringExtra("json");
            System.out.println("json======================"+json);
            Gson gson = new Gson();
            List<ChannelBean> pinDaos = gson.fromJson(json, new TypeToken<List<ChannelBean>>(){}.getType());
//            for (int i=0;i<pinDaos.size();i++){
//                if (pinDaos.get(i).isSelect()==true){
//                    dao.add(pinDaos.get(i).getName(),0);
//                }
//            }

        }
    }
}
