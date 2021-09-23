package com.example.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.inputmethodservice.ExtractEditText;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class new_diary extends AppCompatActivity {
    EditText txtDate, txtTime_period_id, txtText, txtUser_Id, txtTitle;
    String time_period_id,encodedImage; //決定時段的
   // Button btn_insert; //按下傳送至資料庫
    ImageButton selectBtn,shootBtn; //選擇照片或拍攝照片
    Bitmap bitmap;
    ImageView upImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_diary);

//        Toolbar toolbar =findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        upImage = (ImageView) findViewById(R.id.up_image);
        selectBtn = (ImageButton) findViewById(R.id.gallery);
        shootBtn = (ImageButton) findViewById(R.id.camera);

        //choose image from phone gallery function
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(new_diary.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent  = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Select Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

            }
        });

        txtTitle = (EditText) findViewById(R.id.ed_title);
        txtText = (EditText) findViewById(R.id.ed_text);


        txtDate = (EditText) findViewById(R.id.ed_date);
        txtDate.setInputType(InputType.TYPE_NULL); //不顯示系統輸入鍵盤
        txtDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {  //可以選日期

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(new_diary.this, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            txtDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });
        txtDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(new_diary.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        txtDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        //製作spinner表格列
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this
                , R.array.time_period_id, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        time_period_id = spinner.getSelectedItem().toString();
//                        Toast.makeText(new_diary.this, "早餐", Toast.LENGTH_LONG).show();
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        btn_insert = (Button) findViewById(R.id.btn_insert);
//        btn_insert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                insertData();
////                uploadImage();
////
////                Intent intent = new Intent();
////                intent.setClass(new_diary.this,detail_diary.class);
////                //可放所有基本類別
////                intent.putExtra("title",txtTitle.getText().toString().trim());//title標題
////                intent.putExtra("date",txtDate.getText().toString().trim());//date日期
////                intent.putExtra("text",txtText.getText().toString().trim());//text內文
////                //還缺照片跟早/五/晚
////
////                //切換Activity
////                startActivity(intent);
//            }
//        }
//
//
//        );
    }


    private void insertData() {
        final String date = txtDate.getText().toString().trim(); //日期
        final String time_period = time_period_id;
        //final String time_period_id = txtTime_period_id.getText().toString().trim(); //時段(早中晚消夜)
        final String title = txtTitle.getText().toString().trim();//title
        final String text = txtText.getText().toString().trim(); //內文
//        final String user_id = txtUser_Id.getText().toString().trim(); //id

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if (date.isEmpty()) {
            Toast.makeText(this, "Enter Date", Toast.LENGTH_SHORT).show();
            return;
        } else if (time_period.isEmpty()) {
            Toast.makeText(this, "Enter 時段", Toast.LENGTH_SHORT).show();
            return;
        } else if (title.isEmpty()) {
            Toast.makeText(this, "Enter 標題", Toast.LENGTH_SHORT).show();
            return;
        } else if (text.isEmpty()) {
            Toast.makeText(this, "Enter 內文", Toast.LENGTH_SHORT).show();
            return;
        }
//        else if(user_id.isEmpty()){
//            Toast.makeText(this, "Enter user_ID", Toast.LENGTH_SHORT).show();
//            return;
//        }


        else {
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "https://louis32132118.000webhostapp.com/diary_insert.php",
                    response -> {

                        if (response.equalsIgnoreCase("Data Inserted")) {
                            Toast.makeText(new_diary.this, "Data Inserted", Toast.LENGTH_SHORT).show();  //成功
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(new_diary.this, response, Toast.LENGTH_SHORT).show(); //失敗
                            progressDialog.dismiss();
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(new_diary.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("date", date);
                    params.put("time_period_id", time_period);
                    params.put("title", title);
                    params.put("text", text);
                    //params.put("user_id",user_id);


                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(new_diary.this);
            requestQueue.add(request);


        }
//
//        Intent intent =new Intent(this,fragment_diary.class);
//        startActivity(intent);


    }

    private void uploadImage(){
        StringRequest request = new StringRequest(Request.Method.POST, "https://louis32132118.000webhostapp.com/diary_uploadImage.php"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(new_diary.this, response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(new_diary.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("image", encodedImage);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(new_diary.this);
        requestQueue.add(request);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1 && resultCode == RESULT_OK && data!=null){

            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                upImage.setImageBitmap(bitmap);

                imageStore(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void imageStore(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        insertData();
        uploadImage();
        Intent intent = new Intent();
        intent.setClass(new_diary.this,detail_diary.class);
        //可放所有基本類別
        intent.putExtra("title",txtTitle.getText().toString().trim());//title標題
        intent.putExtra("date",txtDate.getText().toString().trim());//date日期
        intent.putExtra("text",txtText.getText().toString().trim());//text內文
        //還缺照片跟早/五/晚

        //切換Activity
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.diary_title,menu);
        return super.onCreateOptionsMenu(menu);
    }

//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        return super.onOptionsItemSelected(item);
//
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
}
