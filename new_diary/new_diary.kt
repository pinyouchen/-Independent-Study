package com.example.login

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_new_diary.*
import java.text.SimpleDateFormat
import java.util.*

class new_diary : AppCompatActivity() {

//    var saveUri: Uri? = null
//
//    private companion object {
//        val PHOTO_FROM_GALLERY = 0
//        val PHOTO_FROM_CAMERA = 1
//
//
//    }

    private val IMAGE_PICK_CODE = 1000
    private val PERMISSION_CODE1=1001
    private val PERMISSION_CODE = 1000
    private val IMAGE_CAPTURE_CODE =1001
    var image_uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_diary)

        val actionBar: androidx.appcompat.app.ActionBar? = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)


        title = "新增日記"
        camera.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivity(intent)

        }

        gallery.setOnClickListener {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                    val permission= arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE1)
                }
                else{
                    pickImageFromGallery()
                }
            }
            else{
                pickImageFromGallery()
            }
        }
        camera.setOnClickListener {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.CAMERA)
                        ==PackageManager.PERMISSION_DENIED ||checkSelfPermission
                        (android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ==PackageManager.PERMISSION_DENIED ){
                    val permission= arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, PERMISSION_CODE)
                }
                else{
                    openCamera()
                }
            }
            else{
                openCamera()
            }
        }


        date_edit.setOnClickListener(listener)
        //time_edit.setOnClickListener(listener)
        //button.setOnClickListener(listener)


        activity@ val date = Date(intent.getLongExtra("date", 0))
        val lunch = arrayListOf("早", "午", "晚")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, lunch)
        spinner.adapter = adapter
//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) =
//                Toast.makeText(this@new_diary, "你選的是" + lunch[pos], Toast.LENGTH_SHORT).show()
//
//            override fun onNothingSelected(parent: AdapterView<*>) {
//
//            }
//        }
    }


//PERMISSION_CODE
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent, PERMISSION_CODE1)
        startActivityForResult(intent, PERMISSION_CODE)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera Intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
            PERMISSION_CODE1 -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    image1.setImageURI(image_uri)
                }
            }
            PERMISSION_CODE1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    image1.setImageURI(data?.data)
                }
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_option3, menu);
        return super.onCreateOptionsMenu(menu)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.menu_about3 -> {
                startActivity(Intent(this, diary::class.java))
                //setTitle("你按了關於按鈕")
                return true
            }
//            R.id.exit -> {
//                setTitle("你按了結束按鈕")
//                return true
//            }
            else -> return super.onOptionsItemSelected(item)
        }
//        return super.onOptionsItemSelected(item)

//        if (item.getItemId() == android.R.id.home) {
//            startActivity(Intent(this,new_diary::class.java))
//        }
//        return super.onOptionsItemSelected(item)
    }

//    private fun toAlbum() {
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.setType("image/*")
//        startActivityForResult(intent, PHOTO_FROM_GALLERY)
//    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        when (requestCode) {
//            PHOTO_FROM_GALLERY -> {
//                when (resultCode) {
//                    Activity.RESULT_OK -> {
//                        val uri = data!!.data
//                        //imageView.setImageURI(uri)
//                    }
//                    Activity.RESULT_CANCELED -> {
//                        Log.wtf("getImageResult", resultCode.toString())
//                    }
//                }
//            }
//        }
//    }

    val calender = Calendar.getInstance()

    val listener = View.OnClickListener {
        when (it) {
            date_edit -> {
                datePicker()
            }

//                time_edit -> {
//                    timePicker()
//                }
//
//                button -> {
//                    Dialog()
//                }
        }
    }

    fun datePicker() {
        DatePickerDialog(
            this,
            dateListener,
            calender.get(Calendar.YEAR),
            calender.get(Calendar.MONTH),
            calender.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

//        fun timePicker() {
//            TimePickerDialog(this,
//                timeListener,
//                calender.get(Calendar.HOUR_OF_DAY),
//                calender.get(Calendar.MINUTE),
//                true
//            ).show()
//        }

    val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        calender.set(year, month, day)
        format("yyyy / MM / dd", date_edit)
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

//    val timeListener = TimePickerDialog.OnTimeSetListener { _, hour, min ->
//        calender.set(Calendar.HOUR_OF_DAY, hour)
//        calender.set(Calendar.MINUTE, min)
//        format("HH : mm", time_edit)
//    }

    fun format(format: String, view: View) {
        val time = SimpleDateFormat(format, Locale.TAIWAN)
        (view as EditText).setText(time.format(calender.time))
    }

    fun Dialog() {
        AlertDialog.Builder(this)
            .setTitle("Your Time")
            //.setMessage("${date_edit.text}   ${time_edit.text}")
            .setNegativeButton("OK") { dialog, which ->
                dialog.cancel()
            }.create().show()
    }



}



