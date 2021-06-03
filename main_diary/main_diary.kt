package com.example.login

import android.app.ActionBar
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main_diary.*
import java.text.SimpleDateFormat
import java.util.*


class main_diary : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_diary)

        title = "日記"
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);


        //ActionBar前一頁
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)


        fun onOptionsItemSelected(item: MenuItem?): Boolean {
            //finish()
            val intent = Intent()
            intent.setClass(this@main_diary, main_home::class.java)
            startActivity(intent);
            return true
        }


//        button5.setOnClickListener {
//            textView19.visibility=View.VISIBLE
//            imageButton16.visibility=View.VISIBLE
//            textView20.visibility=View.VISIBLE
//        }

        //visibility=View.INVISIBLE



        button4.setOnClickListener {
            startActivity(Intent(this,diary::class.java))
        }
        date.setOnClickListener(listener)
        //time_edit.setOnClickListener(listener)
        //button.setOnClickListener(listener)
        //activity@ intent.putExtra("date", Date().time)
        button4.setOnClickListener {
            val intent = Intent(this,diary::class.java)
            val name = date.text.toString()   //將ed_name文字轉成String字串
            intent.putExtra("nameKey", name)     //putExtra("Key", value)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            startActivity(Intent(this,main_home::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    val calender = Calendar.getInstance()
    val listener = View.OnClickListener {
        when (it) {
            date -> {

                datePicker()
            }

            /*time_edit -> {
                timePicker()
            }

            button -> {
                Dialog()
            }*/
        }
    }

    fun datePicker() {
        DatePickerDialog(this,
            dateListener,
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)).show()
    }

    /*fun timePicker() {
        TimePickerDialog(this,
            timeListener,
            calender.get(Calendar.HOUR_OF_DAY),
            calender.get(Calendar.MINUTE),
            true
        ).show()
    }*/

    val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        calender.set(year, month, day)
        format("yyyy / MM / dd", date)
        textView19.visibility=View.VISIBLE
        imageButton16.visibility=View.VISIBLE
        textView20.visibility=View.VISIBLE
    }

//    val dateListener = object : DatePickerDialog.OnDateSetListener {
//        override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
//                               dayOfMonth: Int) {
//            calender.set(Calendar.YEAR, year)
//            calender.set(Calendar.MONTH, monthOfYear)
//            calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//            format("yyyy / MM / dd", date_edit)
//        }
//    }

    /*val timeListener = TimePickerDialog.OnTimeSetListener { _, hour, min->
        calender.set(Calendar.HOUR_OF_DAY, hour)
        calender.set(Calendar.MINUTE, min)
        format("HH : mm", time_edit)
    }*/

    fun format(format: String, view: View) {
        val time = SimpleDateFormat(format, Locale.TAIWAN)
        (view as EditText).setText(time.format(calender.time))
    }

    fun Dialog(){
        AlertDialog.Builder(this)
            .setTitle("Your Time")
            //.setMessage("${date_edit.text}   ${time_edit.text}")
            .setNegativeButton("OK"){ dialog, which ->
                dialog.cancel()
            }.create().show()

    }


}
