package com.example.main_home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SearchView

class MainActivity : AppCompatActivity() {

    lateinit var searchView: SearchView
    lateinit var listView: ListView
    lateinit var list: ArrayList<String>
    lateinit var adapter: ArrayAdapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageButton7.setOnClickListener {
            startActivity(Intent(this, main_diary::class.java))
        }
        button2.setOnClickListener {
            startActivity(Intent(this, classification::class.java))
        }



        override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            getMenuInflater().inflate(R.menu.option_menu, menu);
            return super.onCreateOptionsMenu(menu)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            when (item.getItemId()) {
                R.id.menu_about -> {
                    startActivity(Intent(this, MainActivity4::class.java))

                    return true
                }

                else -> return super.onOptionsItemSelected(item)
            }
            return super.onOptionsItemSelected(item)

        }
    }
}
