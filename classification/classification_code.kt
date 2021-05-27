package com.example.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_classification.*

class classification : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classification)
        //ActionBar前一頁
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        title = "分類"
        imageButton10.setOnClickListener {
            startActivity(Intent(this,classification_cooking_methods::class.java))
        }
        imageButton11.setOnClickListener {
            startActivity(Intent(this,classification_category::class.java))
        }
        imageButton12.setOnClickListener {
            startActivity(Intent(this,classification_country::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            startActivity(Intent(this,main_home::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
