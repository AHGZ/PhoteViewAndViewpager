package com.hgz.test.photeviewpager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hgz.test.photeviewpager.Config;
import com.hgz.test.photeviewpager.R;
import com.hgz.test.photeviewpager.adapter.MyViewpagerAdapter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2017/8/22.
 */

public class MyDialog extends Dialog {
    List<String> mImgUrls;
    private View view;
    private TextView tvPager;
    private ViewPager myviewPager;
    private List<View> mViewList;
    
    public MyDialog(@NonNull Context context,List<String> imgUrls) {
        //调用父类的构造器,传一个style给dialog
        super(context,R.style.transparentBgDialog);
        initView(context);
        initData(context,imgUrls);
    }

    private void initData(Context context, List<String> imgUrls) {
        mImgUrls=imgUrls;
        mViewList=new ArrayList<>();
        //默认为第一页
        tvPager.setText("1/"+imgUrls.size());

        for (String url:mImgUrls) {
            PhotoView photoView = new PhotoView(context);
            //单击事件  当单击photoView的时候让这个dialog消失
            photoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    //让dialog消失
                    dismiss();
                }
            });
            //设置photoView充满父布局
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            photoView.setLayoutParams(layoutParams);

            DisplayImageOptions options = new DisplayImageOptions.Builder()
                    .showImageOnFail(R.mipmap.ic_launcher)
                    .build();
            ImageLoader.getInstance().displayImage(url, photoView, options);
            mViewList.add(photoView);
        }
        MyViewpagerAdapter myViewpagerAdapter = new MyViewpagerAdapter(mViewList);
        myviewPager.setAdapter(myViewpagerAdapter);
        myviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //viewpager切换的时候去改变当前页数
                tvPager.setText(position + 1 + "/" + mImgUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        myviewPager = (ViewPager) view.findViewById(R.id.myViewpager);
        tvPager = (TextView) view.findViewById(R.id.tvPager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
         setContentView(view);
        //设置dialog 是全屏展示
        //一个dialog一个window,默认dialog的这个window是窗口的形式,不是全屏
        Window window = getWindow();
        //窗口dialog使用getAttributes获取布局
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x=0;
        attributes.y=0;
        attributes.width= Config.WINDOW_WIDTH;
        attributes.height=Config.WINDOW_HEIGHT;
        window.setAttributes(attributes);
    }
}
