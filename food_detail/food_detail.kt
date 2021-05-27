package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_home.*


class food_detail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)

        //ActionBar前一頁
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        //button3.setOnClickListener {
            //startActivity(Intent(this, main_diary::class.java))
        //}
        title = "台灣_牛肉"
        val imgId = arrayListOf(R.drawable.beef, R.drawable.beef, R.drawable.beef)
        val Balls = arrayListOf("紅燒牛肉麵", "青椒炒牛肉", "蔥爆牛肉")
        val engNames = arrayListOf("食材需求:牛肋條、白蘿蔔、紅蘿蔔", "食材需求:青椒、牛肉、蔥", "食材需求:牛里肌、洋蔥、辣椒")
        val seaso=arrayListOf("調味料:薑、蔥、辣豆瓣醬...","調味料:鹽巴","調味料:米酒、鹽巴,素蠔油...")
        val time=arrayListOf("50分鐘","30分鐘","20分鐘","10分鐘","10分鐘")
        val people=arrayListOf("2-4人","3人","1人","2人","2人")

        val items = ArrayList<Map<String, Any>>()
        for (i in imgId.indices) {
            val item = HashMap<String, Any>()
            item["imgLogo"] = imgId[i]
            item["txtName"] = Balls[i]
            item["txtengName"] = engNames[i]
            item["txtengName2"] = seaso[i]
            item["txtengName3"] = time[i]
            item["txtengName4"] = people[i]
            items.add(item)
        }
        val adapter = SimpleAdapter(this,
                items, R.layout.mylayout, arrayOf("imgLogo", "txtName", "txtengName","txtengName2","txtengName3","txtengName4"),
                intArrayOf(R.id.imgLogo, R.id.txtName, R.id.txtengName,R.id.txtengName2,R.id.txtengName3,R.id.txtengName4))
        listView.adapter = adapter
        listView.onItemClickListener = object :
                AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position==0)
                {
                    val intent = Intent()
                    intent.setClass(this@food_detail, MainActivity3::class.java)
                    startActivity(intent);
                }
                    //setTitle("aaaa")
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            startActivity(Intent(this, text::class.java))
        }
        return super.onOptionsItemSelected(item)

    }

}




