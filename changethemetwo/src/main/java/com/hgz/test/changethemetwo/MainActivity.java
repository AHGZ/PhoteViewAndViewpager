package com.hgz.test.changethemetwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置对应的主题 ，在ui创建好之后设置主题无效，所以要放到setContentView（）方法前面setTheme()
        ThemeUtil.onActivityCreateSetTheme(MainActivity.this);
        setContentView(R.layout.activity_main);
        Button btnChangeTheme = (Button) findViewById(R.id.btnChangeTheme);
        btnChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //切换日夜间模式
                ThemeUtil.ChangeCurrentTheme(MainActivity.this);
            }
        });
    }
}
