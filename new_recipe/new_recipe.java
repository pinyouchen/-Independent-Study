package com.example.new_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((ImageButton)findViewById(R.id.gallery)).setOnClickListener(this::onClick);
        ((ImageButton)findViewById(R.id.camera)).setOnClickListener(this::onClick);

        //人數設定
        Spinner spinner7 = (Spinner) findViewById(R.id.spinner7);
        final String[] people = {"Select","1-2人", "3-4人", "5-6人", "6人以上"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, people);
        spinner7.setAdapter(lunchList);

        //葷素
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final String[] lunch = {"Select","葷食", "全素", "蛋奶", "五辛"};
        ArrayAdapter<String> lunchList1 = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, lunch);
        spinner.setAdapter(lunchList1);

        //菜系分類
        Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
        final String[] veg = {"Select","牛肉","豬肉", "雞肉", "羊肉", "魚肉", "海鮮","蛋", "蔬菜", "其他"};
        ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, veg);
        spinner4.setAdapter(lunchList2);

        //國家分類
        Spinner spinner5 = (Spinner) findViewById(R.id.spinner5);
        final String[] country = {"Select","台式","日式","韓式","美式","越式","西式","其他"};
        ArrayAdapter<String> lunchList3 = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, country);
        spinner5.setAdapter(lunchList3);

        //烹飪分類
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        final String[] cook = {"Select","蒸","炒","炸","煎","煮/滷","烤","涼拌","其他"};
        ArrayAdapter<String> lunchList4 = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, cook);
        spinner2.setAdapter(lunchList4);

        //時間
        Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
        final String[] time = {"Select","0-30 mins","30-60 mins","1-2 hours","more than 2 hours"};
        ArrayAdapter<String> lunchList5 = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, time);
        spinner3.setAdapter(lunchList5);
        
    }
    public void onClick(View v) {
        //imageButton1click指定動作程式碼
        //待新增相簿
        switch (v.getId()) {
            case R.id.gallery:
                //toast測試
                Toast.makeText(MainActivity.this, "相簿測試成功!", Toast.LENGTH_LONG).show();
                break;
        }

        //待新增相機
        switch (v.getId()) {
            case R.id.camera:
                //toast測試
                Toast.makeText(MainActivity.this, "相機測試成功!", Toast.LENGTH_LONG).show();
                break;
        }
    }
}
