package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.login.ui.ViewpagerAdapater
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main3.*
import kotlinx.android.synthetic.main.activity_main4.*

class MainActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        title = "新增食譜"
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)
//        //ActionBar前一頁
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
//
////
//        fun onOptionsItemSelected(item: MenuItem?): Boolean {
//            //finish()
//            val intent = Intent()
//            intent.setClass(this@MainActivity4, main_home::class.java)
//            startActivity(intent);
//            return true
//        }

        var adapater= ViewPagerAdapter_2(supportFragmentManager,lifecycle)
        viewpager.adapter=adapater
        TabLayoutMediator(tablayout,viewpager,object: TabLayoutMediator.TabConfigurationStrategy{
            override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
                when(position){
                    0->{
                        tab.text="簡介"
                    }
                    1->{
                        tab.text="食譜清單"
                    }
                    2->{
                        tab.text="步驟"
                    }
                }
            }

        }).attach()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_option3, menu);
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.menu_about3 -> {
                startActivity(Intent(this, MainActivity3::class.java))
                //setTitle("你按了關於按鈕")
                return true
            }
//            R.id.exit -> {
//                setTitle("你按了結束按鈕")
//                return true
//            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.getItemId() == android.R.id.home) {
//            val intent = Intent()
//            intent.setClass(this@MainActivity4, main_home::class.java)
//            startActivity(intent);
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }

}
