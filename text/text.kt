package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_lin.*
import kotlinx.android.synthetic.main.activity_main_home.*


class text : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text)

        //ActionBar前一頁
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        title = "台灣"
        btnBeef.setOnClickListener {
            startActivity(Intent(this, food_detail::class.java))
        }
        val imgId = arrayListOf(R.drawable.chicken_min,R.drawable.beef, R.drawable.vege, R.drawable.fish,R.drawable.egg)
        val Balls = arrayListOf("香菇雞湯",  "清燉牛肉", "涼拌黃瓜","清蒸鱸魚","菜脯蛋")
        val engNames = arrayListOf("食材需求:土雞雞腿肉、乾香菇、薑片","食材需求:牛肉、薑絲、青蔥少許", "食材需求:小黃瓜、辣椒、蒜末", "食材需求:鱸魚、豆豉、青蔥", "食材需求:蛋、菜脯")
        val seaso=arrayListOf("調味料:米酒、鹽巴、薑片...","調味料:醬油、鹽巴...","調味料:香油、白醋、砂糖...","調味料:米酒、黑醋...","調味料:香油、胡椒粉...")
        val time=arrayListOf("30分鐘","30分鐘","20分鐘","10分鐘","10分鐘")
        val people=arrayListOf("3人","3人","1人","2人","2人")


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
                    intent.setClass(this@text, MainActivity_chienck::class.java)
                    startActivity(intent);
                }
                //setTitle("aaaa")
            }

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            startActivity(Intent(this,classification_country::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

}

