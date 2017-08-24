package com.hgz.test.test.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgz.test.test.R;
import com.hgz.test.test.bean.MenuInfo;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class MyListviewAdapter extends BaseAdapter {
    private Context context;
    private List<MenuInfo.ResultBean.DataBean.StepsBean> steps;
    public MyListviewAdapter(Context context, List<MenuInfo.ResultBean.DataBean.StepsBean> steps) {
        this.context=context;
        this.steps=steps;
    }
    public void loadMore(List<MenuInfo.ResultBean.DataBean.StepsBean> stepes,boolean flag){
        for (MenuInfo.ResultBean.DataBean.StepsBean stepess:stepes) {
            if (flag){
                steps.add(0,stepess);
            }else{
                steps.add(stepess);
            }
        }
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object getItem(int position) {
        return steps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if (convertView==null){
            convertView=convertView.inflate(context, R.layout.listview_item1,null);
            holder=new ViewHolder();
            holder.image= (ImageView) convertView.findViewById(R.id.item1Iv);
            holder.text= (TextView) convertView.findViewById(R.id.item1Tv);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.text.setText(steps.get(position).getStep());
        getImage(holder.image,steps.get(position).getImg());
        return convertView;
    }
    class ViewHolder{
        private ImageView image;
        private TextView text;
    }
    public void getImage(ImageView imageView,String path){
        ImageOptions options=new ImageOptions.Builder()
                .setFadeIn(true)
                .setCrop(true)
                .setSize(100,100)
                .setUseMemCache(true)
                .build();
        x.image().bind(imageView,path,options);
    }

}
