package com.example.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.home.homeFragment.MainPagerAdapter;
import com.example.home.homeFragment.fragment_chatbot;
import com.example.home.homeFragment.fragment_diary;
import com.example.home.homeFragment.fragment_profile;
import com.example.home.homeFragment.fragment_recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MenuItem menuItem;
    private BottomNavigationView bottomNavigationView;

    // Step01-製作BottomNavigationView按下個方法:
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Step02-BottomNavigationView按下時判斷Menu的ID，讓ViewPaper跳去相對應的Fragment:
            switch (item.getItemId()) {
                case R.id.nav_recipe:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.nav_diary:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.nav_chatbot:
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.nav_profile:
                    viewPager.setCurrentItem(3);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //設定隱藏標題
//        getSupportActionBar().hide();
        // Step03-設定BottomNavigationView的按下事件監聽器:
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        // Step04-設定ViewPaper的適配器:
        MainPagerAdapter adapter = new MainPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new fragment_recipe(), "recipe");
        adapter.addFragment(new fragment_diary(), "diary");
        adapter.addFragment(new fragment_chatbot(), "chatbot");
        adapter.addFragment(new fragment_profile(), "profile");

        viewPager = findViewById(R.id.viewPagerMain);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        // Step05-設定ViewPaper的事件監聽器:
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            // ViewPaper選擇到其他頁面時:
            @Override
            public void onPageSelected(int position) {
                // Step06-將相對應的bottomNavigationView選項選取:
                menuItem = bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}
