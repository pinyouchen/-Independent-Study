package com.example.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.common.internal.Objects;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EditRecipeIntro extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private Spinner people, category, time, vege, inter, cooking;
    private EditText Name, IntroEdit;
    private ImageView imageView;
    private CheckBox privacy;
    private FloatingActionButton floatingActionButton;
    private Bitmap bitmap;
    private String recipe_id, encodedImage, Image, name, intro, Vege, Time, Category, Cooking, People, Inter, finalPrivacy;
    ArrayList<SearchEditRecipe> editRecipeArrayList = new ArrayList<>();
    ArrayList<editStep> editStepArrayList = new ArrayList<>();
    ArrayList<editList> editListArrayList = new ArrayList<>();
    int listCnt, stepCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe_intro);

        Bundle bundle2 =this.getIntent().getExtras();
        if (bundle2 != null) {
            recipe_id = bundle2.getString("id");
        }
        getData();
        //recipe_id = getArguments().getString("Id");
        //Toast.makeText(EditRecipeIntro.this,"id"+recipe_id,Toast.LENGTH_SHORT).show();

        privacy = findViewById(R.id.privacy);
        Name = findViewById(R.id.recipeName);
        IntroEdit = findViewById(R.id.recipeIntor);
        people =findViewById(R.id.people);
        category =findViewById(R.id.category);
        vege =findViewById(R.id.vege);
        cooking =findViewById(R.id.cooking);
        inter =findViewById(R.id.inter);
        time =findViewById(R.id.time);
        imageView = findViewById(R.id.recipeImg1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(EditRecipeIntro.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080,1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
        floatingActionButton =findViewById(R.id.FAB_next);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                Uri uri = data.getData();
//        imageView.setImageURI(uri);
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    bitmap = BitmapFactory.decodeStream(inputStream);
            imageView.setImageBitmap(bitmap);

            imageStore(bitmap);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void imageStore(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        byte[] imageBytes = stream.toByteArray();

        encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private static Bitmap Tobitmap(String st) {
        // OutputStream out;
        Bitmap bitmap = null;
        try {
            // out = new FileOutputStream("/sdcard/aa.jpg");
            byte[] bitmapArray;
            bitmapArray = Base64.decode(st, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
            // bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    private void getData() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://louis32132118.000webhostapp.com/edit_recipe_get.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
//                            Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String name = jsonObject.getString("recipe");
                                Name.setText(name);
//                                String user=jsonObject.getString("username");
//                                de_author.setText(user);

                                String editcooking = jsonObject.getString("cooking"); //烹飪方式
                                cooking(editcooking);

                                String picture = jsonObject.getString("picture");
                                Glide.with(EditRecipeIntro.this).load(picture).into(imageView);

                                String editcategory = jsonObject.getString("category"); //主食
                                category(editcategory);

                                String editInter = jsonObject.getString("inter"); //菜系
                                inter(editInter);

                                String Intro = jsonObject.getString("intro"); //簡介
                                IntroEdit.setText(Intro);

                                String editvege = jsonObject.getString("vege"); //葷素
                                vege(editvege);

                                String editpeople = jsonObject.getString("people"); //人數
                                quantity(editpeople);

                                String editcotime = jsonObject.getString("cookingtime"); //烹飪時間
                                time(editcotime);

                                String editprivacy = jsonObject.getString("privacy");
//                                Toast.makeText(EditRecipeIntro.this,editprivacy, Toast.LENGTH_SHORT).show();
                                if (editprivacy.equals("1")) {
                                    privacy.setChecked(true);
                                }
                            }
                        } catch (JSONException e) {
                            Toast.makeText(EditRecipeIntro.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditRecipeIntro.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("ID", recipe_id);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditRecipeIntro.this);
        requestQueue.add(request);

    }

    private void insertData(){
        String Privacy ="0";
        if (privacy.isChecked()){
            Privacy ="1";
        }
        String upPrivacy = Privacy;

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date current = new Date();
        final String retime = sdFormat.format(current);

        StringRequest request = new StringRequest(Request.Method.POST, "https://louis32132118.000webhostapp.com/edit_recipe_up.php",
                response -> {
                    if(response.equalsIgnoreCase("Data updated")){
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(response);
                            recipe_id=jsonObject.getString("insert_id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(EditRecipeIntro.this, "recipe updated"+recipe_id, Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(CreateActivity.this,MainActivity.class));
//                            progressDialog.dismiss();
                    }
                    else{
                        Toast.makeText(EditRecipeIntro.this, response, Toast.LENGTH_SHORT).show();
//                            progressDialog.dismiss();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditRecipeIntro.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        ){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<String,String>();

                params.put("recipe_id",recipe_id);
                //params.put("userid",MainActivity.searchArrayList.get(0).getId());
                params.put("category_id",category.getSelectedItem().toString().trim());
                params.put("cooking_id",cooking.getSelectedItem().toString().trim());
                params.put("recipe_name",Name.getText().toString().trim());
                params.put("quantity",people.getSelectedItem().toString().trim());

                params.put("release_time",retime);
                params.put("international",inter.getSelectedItem().toString().trim());
                params.put("vegetarian",vege.getSelectedItem().toString().trim());
//                params.put("degree","0");
                params.put("introduction",IntroEdit.getText().toString().trim());
                params.put("cooking_time",time.getSelectedItem().toString().trim());
//                params.put("likes","0");

                params.put("privacy", upPrivacy);
                params.put("recipe_picture", encodedImage);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(EditRecipeIntro.this);
        requestQueue.add(request);

    }
    private void quantity(String editpeople) {
        final String[] peoples = {"Select", "1-2人", "3-4人", "5-6人", "6人以上"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(EditRecipeIntro.this,
                android.R.layout.simple_spinner_dropdown_item, peoples);
        people.setAdapter(lunchList);
        if (editpeople != null) {
            int peoplePo = lunchList.getPosition(editpeople);
            people.setSelection(peoplePo);
//            Toast.makeText(getActivity(),"people"+peoplePo,Toast.LENGTH_SHORT).show();
        }
    }

    private void category(String editcategory) {
        final String[] veg = {"Select", "牛", "豬", "雞", "羊", "魚", "海鮮", "蛋", "蔬菜", "其他"};
        ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(EditRecipeIntro.this,
                android.R.layout.simple_spinner_dropdown_item, veg);
        category.setAdapter(lunchList2);
        if (editcategory != null) {
            int catePo = lunchList2.getPosition(editcategory);
            category.setSelection(catePo);
        }

    }

    private void time(String edittime) {
        final String[] times = {"Select", "0-30 mins", "30-60 mins", "1-2 hours", "more than 2 hours"};
        ArrayAdapter<String> lunchList5 = new ArrayAdapter<>(EditRecipeIntro.this,
                android.R.layout.simple_spinner_dropdown_item, times);
        time.setAdapter(lunchList5);
        if (edittime != null) {
            int timePo = lunchList5.getPosition(edittime);
            time.setSelection(timePo);
        }
    }

    private void cooking(String editcooking) {
        final String[] cook = {"Select", "蒸", "炒", "炸", "煎", "煮/滷", "烤", "涼拌", "其他"};
        ArrayAdapter<String> lunchList4 = new ArrayAdapter<>(EditRecipeIntro.this,
                android.R.layout.simple_spinner_dropdown_item, cook);
        cooking.setAdapter(lunchList4);
        if (editcooking != null) {
            int cookPo = lunchList4.getPosition(editcooking);
            cooking.setSelection(cookPo);
        }

    }

    private void inter(String editinter) {
        final String[] country = {"Select", "台式", "日式", "韓式", "美式", "西式", "越式", "泰式", "其他"};
        ArrayAdapter<String> lunchList3 = new ArrayAdapter<>(EditRecipeIntro.this,
                android.R.layout.simple_spinner_dropdown_item, country);
        inter.setAdapter(lunchList3);
        if (editinter != null) {
            int interPo = lunchList3.getPosition(editinter);
            inter.setSelection(interPo);
        }
    }

    private void vege(String editvege) {
        final String[] lunch = {"Select", "葷食", "全素", "蛋奶", "五辛"};
        ArrayAdapter<String> lunchList1 = new ArrayAdapter<>(EditRecipeIntro.this,
                android.R.layout.simple_spinner_dropdown_item, lunch);
        vege.setAdapter(lunchList1);
        if (editvege != null) {
            int vegePo = lunchList1.getPosition(editvege);
            vege.setSelection(vegePo);
        }
    }



}
