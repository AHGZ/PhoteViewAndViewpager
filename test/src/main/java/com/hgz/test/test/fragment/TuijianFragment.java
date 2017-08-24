package com.hgz.test.test.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.hgz.test.test.R;
import com.hgz.test.test.adapter.MyListviewAdapter;
import com.hgz.test.test.bean.MenuInfo;
import com.limxing.xlistview.view.XListView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Administrator on 2017/8/4.
 */

public class TuijianFragment extends Fragment implements XListView.IXListViewListener {

    private View view;
    private XListView lv;
    private MyListviewAdapter listviewAdapter;
    private boolean flag;
    private int rn=15;
    private int pn=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tuijian_fragment, container, false);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        x.view().inject(getActivity());
        lv = (XListView) view.findViewById(R.id.lv);

        getDatas();
        lv.setPullLoadEnable(true);
        lv.setXListViewListener(this);
    }

    @Override
    public void onRefresh() {
        pn++;
        getDatas();
        flag=true;
        lv.stopRefresh(true);
    }

    @Override
    public void onLoadMore() {
        pn++;
        getDatas();
        flag=false;
        lv.stopLoadMore();
    }
    private void getDatas(){
//java.net.UnknownHostException: Unable to resolve host "apis.juhe.cn": No address associated with hostname

        String url="http://apis.juhe.cn/cook/query.php";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addBodyParameter("key","1fe0aebec3396c977560e16f14613413");
        requestParams.addBodyParameter("menu",getArguments().getString("text"));
        requestParams.addBodyParameter("pn",pn+"");
        requestParams.addBodyParameter("rn",rn+"");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("========================="+ getArguments().getString("text"));
                System.out.println("fsdaaaaa================="+result);
                Gson gson = new Gson();
                MenuInfo menuInfo = gson.fromJson(result, MenuInfo.class);
                List<MenuInfo.ResultBean.DataBean> data = menuInfo.getResult().getData();
                System.out.println("========================="+data);
                List<MenuInfo.ResultBean.DataBean.StepsBean> steps = menuInfo.getResult().getData().get(0).getSteps();
                System.out.println("======================="+steps.toString());
                if (listviewAdapter==null) {
                    listviewAdapter = new MyListviewAdapter(getActivity(), steps);
                    lv.setAdapter(listviewAdapter);
                }else{
                    listviewAdapter.loadMore(steps,flag);
                    lv.setAdapter(listviewAdapter);
                    listviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("========================="+ getArguments().getString("text"));
            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("========================="+ getArguments().getString("text"));
            }

            @Override
            public void onFinished() {
                System.out.println("========================="+ getArguments().getString("text"));
            }
        });
    }
}
