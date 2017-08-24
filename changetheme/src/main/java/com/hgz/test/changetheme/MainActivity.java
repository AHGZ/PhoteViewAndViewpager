package com.hgz.test.changetheme;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnChangeTheme = (Button) findViewById(R.id.btnChangeTheme);

        btnChangeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }
}
