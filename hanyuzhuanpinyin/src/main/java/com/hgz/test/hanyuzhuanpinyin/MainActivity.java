package com.hgz.test.hanyuzhuanpinyin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.hgz.test.hanyuzhuanpinyin.utils.HanZiToPinYin;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView showText= (TextView) findViewById(R.id.showText);
        String hanziString="人";
        String pinyinString= HanZiToPinYin.toPinYin(hanziString.charAt(0));
        showText.setText("汉字："+hanziString+"\n"+"拼音："+pinyinString);
//        String s = HanZiToPinYin.getPinYinAllChar("大家好", 1);
//        showText.setText(s);
    }
}
