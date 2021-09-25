package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class diary_detail extends AppCompatActivity {

    //顯示詳細資料(detail_diary)
    TextView tvid,tvDate,tvTime_period_id,tvText,tvTitle;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_detail);

        //Initializing Views
        tvid = findViewById(R.id.txtID);
        tvDate = findViewById(R.id.txtDate);
        tvTime_period_id = findViewById(R.id.txtTime_period_id);
        tvText = findViewById(R.id.txText);
        tvTitle= findViewById(R.id.txTitle);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        tvid.setText("ID: "+fragment_diary.searchArrayList.get(position).getId());
        tvDate.setText(fragment_diary.searchArrayList.get(position).getdate());
        tvTime_period_id.setText(fragment_diary.searchArrayList.get(position).getTime_period_id());
        tvTitle.setText(fragment_diary.searchArrayList.get(position).gettitle());
        tvText.setText(fragment_diary.searchArrayList.get(position).gettext());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


//        Intent intent = new Intent();
////        //intent.setClass(new_diary.this,detail_diary.class);
////        //可放所有基本類別
////        intent.putExtra("title",txtTitle.getText().toString().trim());//title標題
////        intent.putExtra("date",txtDate.getText().toString().trim());//date日期
////        intent.putExtra("text",txtText.getText().toString().trim());//text內文
////        //還缺照片跟早/五/晚
//
//        //切換Activity
//        startActivity(intent);


        switch (item.getItemId()){
            case R.id.diary_edit:
                startActivity(new Intent(getApplicationContext(), diary_edit.class)
                        .putExtra("position", position));
                break;
            case R.id.diary_del:
                deleteData(fragment_diary.searchArrayList.get(position).getId());
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editdiary_title,menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void deleteData(final String id) {

        StringRequest request = new StringRequest(Request.Method.POST, "https://louis32132118.000webhostapp.com/diary_delete.php",
                response -> {

                    if (response.equalsIgnoreCase("Data Deleted")) {
                        Toast.makeText(diary_detail.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(diary_detail.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                    }


                }, error -> Toast.makeText(diary_detail.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
