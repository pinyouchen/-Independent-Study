package com.example.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    String urlProfile = "https://louis32132118.000webhostapp.com/profile_search.php";
    public static ArrayList<SearchProfile> searchArrayList = new ArrayList<>();
    SearchProfile searchProfile;
    ProfileAdapter profileAdapter;
    EditText ed_name, ed_email, ed_pwd;
    ImageView user_picture, user_covers;
    Button btn_up;
    String user_id = "96", encodedImage, encodedImage2, userPic, userCover;
    Bitmap bitmap, bitmap2;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        /*定義id*/
        ed_name = findViewById(R.id.ed_name);
        ed_email = findViewById(R.id.ed_mail);
        ed_pwd = findViewById(R.id.ed_pwd);
        user_picture = findViewById(R.id.user_pic);
        user_covers = findViewById(R.id.user_cover);
        btn_up = findViewById(R.id.btnUp);

        /*action bar back button function*/
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        /*setting adapter's function and used it*/
        profileAdapter = new ProfileAdapter(this, searchArrayList); //add a new adapter
        retrieve();

        /*以id 96號為測試對象，功能簡述:進入頁面後會顯示name email 和密碼，以及照片跟封面照片(retrieve)，可以做更改(update)*/

        /*更改後上傳至database*/
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        /*choose image to set person's picture.*/
        user_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(EditProfile.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
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
        /*choose image to set user's cover picture.*/
        user_covers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(EditProfile.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image"), 2);
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
    }

    /*讀取資料庫使用者個人資料並顯示*/
    private void retrieve() {
        StringRequest request = new StringRequest(Request.Method.POST, urlProfile,
                response -> {

                    searchArrayList.clear();
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String sucess = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("user");

                        if (sucess.equals("1")) {

                            for (int i = 0; i < jsonArray.length(); i++) { //取得資料庫的筆數

                                JSONObject object = jsonArray.getJSONObject(i);

                                String id = object.getString("id");
                                String name = object.getString("name");
                                String email = object.getString("email");
                                String password = object.getString("password");
                                String user_pic = object.getString("user_pic");
                                String user_cover = object.getString("user_cover");

                                searchProfile = new SearchProfile(id, name, email, password, user_pic, user_cover); //將資料庫抓下來的資料存進searchDiary
                                searchArrayList.add(searchProfile); //將searchDiary存進searchArraylist
                                profileAdapter.notifyDataSetChanged();

                                ed_name.setText(name);
                                ed_email.setText(email);
                                ed_pwd.setText(password);
                                Glide.with(EditProfile.this).load(searchArrayList.get(0).getUser_pic()).into(user_picture);
                                Glide.with(EditProfile.this).load(searchArrayList.get(0).getUser_cover()).into(user_covers);

                                //userPic=user_pic;
                                //userCover = user_cover;

                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", user_id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    /*按下update 後執行*/
    /*尚未do:密碼二次驗證，照片未更改防呆*/
    private void update() {
        final String name = ed_name.getText().toString();
        final String email = ed_email.getText().toString();
        final String password = ed_pwd.getText().toString();
        final String id = user_id;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://louis32132118.000webhostapp.com/profile_update.php",
                response -> {

                    Toast.makeText(EditProfile.this, response + "編輯成功!請回到首頁重新進入喔", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                    progressDialog.dismiss();
                }, error -> {

            Toast.makeText(EditProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                //params.put("user_pic",user_pic);
//                if(encodedImage==null){
//                    encodedImage=userPic;
//                }
                params.put("image", encodedImage);
                params.put("cover", encodedImage2);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);
        requestQueue.add(request);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                user_picture.setImageBitmap(bitmap);

                imageStore(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {

            Uri filePath = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap2 = BitmapFactory.decodeStream(inputStream);
                user_covers.setImageBitmap(bitmap2);

                imageStore2(bitmap2);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] imageBytes = stream.toByteArray();
        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void imageStore2(Bitmap bitmap2) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] imageBytes = stream.toByteArray();
        encodedImage2 = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Toast.makeText(this, "按下左上角返回鍵", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
