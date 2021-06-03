package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.example.login.ui.ViewpagerAdapater
import com.example.login.ui.dashboard.DashboardFragment
import com.example.login.ui.dashboard.chicken_list
import com.example.login.ui.home.HomeFragment
import com.example.login.ui.home.chicken_main
import com.example.login.ui.notifications.NotificationsFragment
import com.example.login.ui.notifications.ViewpagerAdapater_chicken
import com.example.login.ui.notifications.chicken_step
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.sackcentury.shinebuttonlib.ShineButton
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.activity_main_chienck.*
import kotlinx.android.synthetic.main.activity_main_home.*

class MainActivity_chienck : AppCompatActivity() {

    companion object{
        val intrFragment = chicken_main()
        val listFragment = chicken_list()
        val stepFragemnt = chicken_step()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_chienck)
        title = "香菇雞湯"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
//        floatingActionButton3.setOnClickListener(View.OnClickListener {
//            startActivity(Intent(this,  MainActivity4::class.java))
//       })

        //ActionBar前一頁
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)


//        val bHeart = findViewById<View>(R.id.heart_chicken) as ShineButton
//        bHeart.init(this)


        //設定初始時要show哪個fragment
        var adapater = ViewpagerAdapater_chicken(supportFragmentManager, lifecycle)
        viewpager_chicken.adapter = adapater
        TabLayoutMediator(
            tablayout_chicken,
            viewpager_chicken,
            object : TabLayoutMediator.TabConfigurationStrategy {
                override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                    when (position) {
                        0 -> {
                            tab.text = "簡介"
                        }
                        1 -> {
                            tab.text = "清單"
                        }
                        2 -> {
                            tab.text = "步驟"
                        }
                    }
                }

            }).attach()
    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        getMenuInflater().inflate(R.menu.option_menu2, menu);
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.getItemId()) {
//            R.id.menu_about3 -> {
//                startActivity(Intent(this, new_diary::class.java))
//                //setTitle("你按了關於按鈕")
//                return true
//            }
////            R.id.exit -> {
////                setTitle("你按了結束按鈕")
////                return true
////            }
//            else -> return super.onOptionsItemSelected(item)
//        }
////        return super.onOptionsItemSelected(item)
//
//    }
}
