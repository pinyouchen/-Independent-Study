package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class Updatepassword extends AppCompatActivity {

    EditText name,password,email;
    Button insert,update,delete;
    private static final String REGISTER_URL = "https://louis32132118.000webhostapp.com/TestDataApp/insertdata.php";
    private static final String DELETE_URL = "https://louis32132118.000webhostapp.com/TestDataApp/delete.php";
    private static final String UPDATE_URL = "https://louis32132118.000webhostapp.com/TestDataApp/update.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepassword);


        name=  (EditText)findViewById(R.id.editTextname);
        password=  (EditText)findViewById(R.id.editTextpassword);
        email = (EditText)findViewById(R.id.editTextemail);

        insert = (Button)findViewById(R.id.buttoninsert);
        update = (Button)findViewById(R.id.buttonupdate);
        delete = (Button)findViewById(R.id.buttondelete);




        // 取得前一個Activity傳過來的資料
        Bundle bundle = this.getIntent().getExtras();
        // 將取得的Bundle資料設定
        if (bundle != null) {
            String result = bundle.getString("username");
            // 顯示結果
            password.setText( result );
        }

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteUser();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = email.getText().toString();
                String passwordVal = "^" +
                        //"(?=.*[0-9])" +         //at least 1 digit
                        "(?=.*[a-z])" +         //at least 1 lower case letter
                        //"(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
//                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{6,}" +               //at least 4 characters
                        "$";

                if (val.isEmpty()) {
                    email.setError("密碼不可空白");
                    return;
                } else if (!val.matches(passwordVal)) {
                    email.setError("密碼長度必須包含英數且6碼以上");
                    return;
                } else {
                    UpdateUser();
                }

//                validatePassword();


                }


        });

    }

    private void registerUser() {

        String Name = name.getText().toString().trim().toLowerCase();
        String Passcode = password.getText().toString().trim().toLowerCase();
        String Email = email.getText().toString().trim().toLowerCase();


        register(Name,Passcode,Email);
    }


    private void register(String name, String passcode, String email) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Updatepassword.this, "Please Wait", null, true, true);

            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("name", params[0]);
                data.put("email", params[1]);
                data.put("password", params[2]);


                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(name,passcode,email);
    }

    private void DeleteUser() {

        String Name = name.getText().toString().trim().toLowerCase();


        register(Name);
    }


    private void register(String name) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Updatepassword.this, "Please Wait", null, true, true);

            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("name", params[0]);

                String result = ruc.sendPostRequest(DELETE_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(name);
    }
    private void UpdateUser() {



        String Name = name.getText().toString().trim().toLowerCase();
        String Password = password.getText().toString().trim().toLowerCase();
        String Email =email.getText().toString().trim().toLowerCase();

        registers(Name,Password,Email);
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }


    private void registers(String name, String password, String email) {
        class RegisterUsers extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Updatepassword.this, "Please Wait", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<String, String>();


                data.put("name", params[0]);
                data.put("email", params[1]);
                data.put("password", params[2]);

                String result = ruc.sendPostRequest(UPDATE_URL, data);

                return result;
            }
        }

        RegisterUsers ru = new RegisterUsers();
        ru.execute(name,password,email);
    }
    private Boolean validatePassword() {
        String val = email.getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
//                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            email.setError("密碼不可空白");
            return false;
        } else if (!val.matches(passwordVal)) {
            email.setError("密碼長度必須包含英數且6碼以上");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

}

