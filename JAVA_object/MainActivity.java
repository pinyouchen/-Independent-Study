package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private int mYear, mMonth, mDay;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //buttonclick宣告
            ((Button) findViewById(R.id.button1)).setOnClickListener(this::onClick);

            //ImageButtonclick宣告
            ((ImageButton)findViewById(R.id.imageButton1)).setOnClickListener(this::onClick);

            //Spinner
            Spinner spinner = (Spinner) findViewById(R.id.spinner);
            final String[] lunch = {"魯肉飯", "控肉飯", "雞排飯", "炸醬麵", "水餃"};
            ArrayAdapter<String> lunchList = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_spinner_dropdown_item, lunch);
            spinner.setAdapter(lunchList);

            //DatePicker
            final TextView dateText = (TextView) findViewById(R.id.dateText);
            Button dateButton = (Button) findViewById(R.id.dateButton);
            dateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            String format = "您設定的日期為:" + setDateFormat(year, month, day);
                            dateText.setText(format);
                        }
                    }, mYear, mMonth, mDay).show();
                }
            });
        }
            private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
                return String.valueOf(year) + "-" + String.valueOf(monthOfYear + 1) + "-" + String.valueOf(dayOfMonth);
            }

        //buttonclick指定動作程式碼
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:
                    //Intent跳到第二頁
                    Intent intent2 = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(intent2);
                    break;
            }

        //imageButton1click指定動作程式碼
            switch (v.getId()) {
                case R.id.imageButton1:
                    //toast測試
                    Toast.makeText(MainActivity.this, "ImageButton測試成功!", Toast.LENGTH_LONG).show();
                    break;
            }
        }

        @Override
        public void onPointerCaptureChanged(boolean hasCapture) {
        }
    }
