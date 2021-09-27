package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ForgotPassActivity extends AppCompatActivity {
    EditText txtvalue,h;
    Button btnfetch;
    ListView listview;
    MyAdapter adapter;

    public static ArrayList<Employee> employeeArrayList = new ArrayList<>();
    String url = "https://louis32132118.000webhostapp.com/retrieve.php";
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);


        txtvalue = (EditText)findViewById(R.id.txtvalue);

        btnfetch = (Button)findViewById(R.id.buttonfetch);
        listview = (ListView)findViewById(R.id.listView);

        adapter = new MyAdapter(this, employeeArrayList);

        btnfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();





            }
        });

        listview.setOnItemClickListener((parent, view, position, id) -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
            ProgressDialog progressDialog = new ProgressDialog(view.getContext());

            CharSequence[] dialogItem = {"變更密碼"};

//            builder.setTitle(employeeArrayList.get(position).getName());
            builder.setItems(dialogItem, (dialog, i) -> {

                switch (i) {

                    case 0:
//                        Toast.makeText(ForgotPassActivity.this,"goo",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Updatepassword.class)
                                .putExtra("position", position));
                        Intent intent = new Intent();
                        intent.setClass(ForgotPassActivity.this, Updatepassword.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", txtvalue.getText().toString());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;



//                    case 1:
//                        startActivity(new Intent(getApplicationContext(),MainActivity.class)
//                                .putExtra("position",position));
//
//                        break;
                }

            });

            builder.create().show();

        });

        retrieveData();

    }
    private void getData() {

        String value = txtvalue.getText().toString().trim();

        String emailPattern = "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+";

        if (value.equals("")) {
            Toast.makeText(this, "請輸入電子郵件", Toast.LENGTH_LONG).show();
            return;

        }else if(!value.matches(emailPattern)) {
            txtvalue.setError("電子郵件格式錯誤");
            return;

        }else {
            txtvalue.setError(null);
        }

        String url = Config5.DATA_URL + txtvalue.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ForgotPassActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config5.JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Config5.KEY_ID);
                String name = jo.getString(Config5.KEY_NAME);
                String email = jo.getString(Config5.KEY_EMAIL);
                String password = jo.getString(Config5.KEY_PASS);

                final HashMap<String, String> employees = new HashMap<>();
                employees.put(Config5.KEY_ID, id);
                employees.put(Config5.KEY_NAME,name);
                employees.put(Config5.KEY_EMAIL,email);
                employees.put(Config5.KEY_PASS,password);

                list.add(employees);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                ForgotPassActivity.this, list, R.layout.activity_mylist,
                new String[]{ Config5.KEY_NAME, Config5.KEY_EMAIL, Config5.KEY_PASS},
                new int[]{R.id.name,R.id.password});

        listview.setAdapter(adapter);

    }

    public void retrieveData() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {

//                    showJSON(response);

//                    employeeArrayList.clear();
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        String sucess = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("user");
//
                        if (sucess.equals("1")) {

                            for (int i = 5; i < jsonArray.length(); i++) {

                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String email = object.getString("email");
                                String password = object.getString("password");

                                    employee = new Employee(id, name, email, password);
                                    employeeArrayList.add(employee);

//                                    adapter.notifyDataSetChanged();
                         }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();

                    }


                }, error -> Toast.makeText(ForgotPassActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }




}
