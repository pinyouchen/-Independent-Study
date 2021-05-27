package com.example.login

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_classification.*
import kotlinx.android.synthetic.main.activity_diary.*
import java.text.SimpleDateFormat
import java.util.*

class diary : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary)

        //ActionBar前一頁
        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        val name = intent.getStringExtra("nameKey")
        date.text = "$name"


        title = "日記"
        date.setOnClickListener(listener)
        //time_edit.setOnClickListener(listener)
        //go_re.setOnClickListener(listener)
        go_re.setOnClickListener {
            startActivity(Intent(this,main_home::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            startActivity(Intent(this,classification::class.java))
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

            go_re -> {
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
