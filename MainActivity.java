package com.example.yochef;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.yochef.Adapter.ChatMessageAdapter;
import com.example.yochef.Model.ChatMessage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton btnSend;
    EditText edtTextMsg;
    ImageView imageView;
    String ID,name,IMG;

    public Bot bot;
    public static Chat chat;
    private ChatMessageAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        btnSend = findViewById(R.id.btnSend);
        edtTextMsg = findViewById(R.id.edtTextMsg);
        imageView = findViewById(R.id.imageView);


        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);

        adapter = new ChatMessageAdapter(this,new ArrayList<ChatMessage>());
        listView.setAdapter(adapter);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                botsReply(chat.multisentenceRespond("哈囉"));
            }
        },1000);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String message = edtTextMsg.getText().toString();

                String response = chat.multisentenceRespond(edtTextMsg.getText().toString());

                if(TextUtils.isEmpty(message)){
                    Toast.makeText(MainActivity.this,"Please enter a query",Toast.LENGTH_SHORT).show();
                    return;
                }

                sendMessage(message);
                botsReply(response);

                edtTextMsg.setText("");
                listView.setSelection(adapter.getCount() - 1);
            }
        });

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()){
                    custom();
                    Toast.makeText(MainActivity.this,"permission granted...",Toast.LENGTH_SHORT).show();
                }
                if(report.isAnyPermissionPermanentlyDenied()){
                    Toast.makeText(MainActivity.this,"please grant all the permission...",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(MainActivity.this,""+error,Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();

    }

    private void custom() {
        boolean available = isSDCARDAvailable();

        AssetManager assets = getResources().getAssets();
        File fileName = new File(Environment.getExternalStorageDirectory().toString() + "/pinyouchen/bots/pinyouchen");

        boolean makeFile = fileName.mkdirs();

        if (fileName.exists()) {
            try {
                for (String dir : assets.list("pinyouchen")) {

                    File subDir = new File(fileName.getPath() + "/" + dir);
                    boolean subDir_check = subDir.mkdirs();

                    for (String file : assets.list("pinyouchen/" + dir)) {
                        File newFile = new File(fileName.getPath() + "/" + dir + "/" + file);
                        if (newFile.exists()) {
                            continue;
                        }
                        InputStream in;
                        OutputStream out;
                        in = assets.open("pinyouchen/" + dir + "/" + file);
                        out = new FileOutputStream(fileName.getPath() + "/" + dir + "/" + file);

                        copyFile(in, out);
                        in.close();
                        out.flush();
                        out.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //get the working directory
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/pinyouchen";
        AIMLProcessor.extension =  new PCAIMLProcessorExtension();

        bot = new Bot("pinyouchen", MagicStrings.root_path, "chat");
        chat = new Chat(bot);
    }

    private static boolean isSDCARDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)? true :false;
    }

    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        adapter.add(chatMessage);
    }

    private void botsReply(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        adapter.add(chatMessage);
        if (message.contains(":")){
            String[] a =message.split(":");
            Toast.makeText(MainActivity.this,""+a[a.length-1],Toast.LENGTH_SHORT).show();
            getRecipe(a[a.length-1]);
        }
    }

    private void getRecipe(String s) {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, "https://louis32132118.000webhostapp.com/getChatRecipe.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject object = new JSONObject(response);
                            JSONArray jsonArray = object.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    ID=jsonObject.getString("id");
                                    name=jsonObject.getString("recipename");
                                    IMG=jsonObject.getString("picture");
                            }
                            showdialog(ID,name,IMG);
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "aaa", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("name", s);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    private void showdialog(String id,String name,String img) {
        AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this,R.style.AlertDialogTheme);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.success_dialog,findViewById(R.id.dialogContainer));
        builder.setView(view);
        ((TextView) view.findViewById(R.id.textTitle)).setText(name);
        ImageView imageView=((ImageView) view.findViewById(R.id.picture));
        Glide.with(this).load(img).into(imageView);
        ((Button) view.findViewById(R.id.backBTN)).setText("取消");
        ((Button) view.findViewById(R.id.detialBTN)).setText("查看詳細");


        final AlertDialog alertDialog = builder.create();

        view.findViewById(R.id.backBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
//                finish();
                Toast.makeText(MainActivity.this, "cancel", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(CreateActivity.this,MainActivity.class));
            }
        });

        view.findViewById(R.id.detialBTN).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this, MainActivity2.class);
                Bundle bundle=new Bundle();
                bundle.putString("id",id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        if (alertDialog.getWindow()!=null){
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        }

        alertDialog.show();
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
}