package com.example.home.homeFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.home.diary.DiaryAdapter;
import com.example.home.R;
import com.example.home.diary.SearchDiary;
import com.example.home.diary.diary_detail;
import com.example.home.diary.new_diary;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_diary#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_diary extends Fragment {

    //MyAdapter adapter;
    EditText edDate;
    TextView morn_text, noon_text, night_text, final_text;
    FloatingActionButton addBtn;
    ImageView calBtn;
    public static ArrayList<SearchDiary> searchArrayList = new ArrayList<>();
    String url = "https://louis32132118.000webhostapp.com/diary_search.php";
    SearchDiary searchDiary;
    DiaryAdapter adapter;
    int position =0;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_diary() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_diary.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_diary newInstance(String param1, String param2) {
        fragment_diary fragment = new fragment_diary();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        TextView TextView1 = (TextView) view.findViewById(R.id.morn_text);
        TextView TextView2 = (TextView) view.findViewById(R.id.noon_text);
        TextView TextView3 = (TextView) view.findViewById(R.id.night_text);
        TextView TextView4 = (TextView) view.findViewById(R.id.final_text);
        EditText ed1 = (EditText) view.findViewById(R.id.edDate);


        Typeface type = Typeface.createFromAsset(view.getContext().getAssets(), "setofont.ttf");
        ed1.setTypeface(type);TextView1.setTypeface(type);TextView2.setTypeface(type);TextView3.setTypeface(type);TextView4.setTypeface(type);

        edDate = (EditText) view.findViewById(R.id.edDate);
        edDate.setInputType(InputType.TYPE_NULL); //不顯示系統輸入鍵盤

        edDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {  //可以選日期

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                if (hasFocus) {
                    Calendar c = Calendar.getInstance();
                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // TODO Auto-generated method stub
                            edDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

                }
            }
        });
        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar c = Calendar.getInstance();
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        edDate.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        adapter = new DiaryAdapter(getActivity(), searchArrayList); //add a new adapter

        calBtn = (ImageView) view.findViewById(R.id.calBtn);
        calBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveData();
            }
        });

        morn_text = (TextView) view.findViewById(R.id.morn_text);
        noon_text = (TextView) view.findViewById(R.id.noon_text);
        night_text = (TextView) view.findViewById(R.id.night_text);
        final_text = (TextView) view.findViewById(R.id.final_text);

        morn_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int position =searchArrayList.indexOf("好吃的早");
                for(int i=0;i<=searchArrayList.size();i++){
                    if(searchArrayList.get(i).gettitle().equals(morn_text.getText().toString())==true){
                        position=i;
                        break;
                    }
                }
                //Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), diary_detail.class)
                        .putExtra("position",position));
            }
        });
        noon_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<=searchArrayList.size();i++){
                    if(searchArrayList.get(i).gettitle().equals(noon_text.getText().toString())==true){
                        position=i;
                        break;
                    }
                }
                //Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), diary_detail.class)
                        .putExtra("position",position));
            }
        });
        night_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(night_text.length()>0){
//                    Intent intent=new Intent(getActivity(),detail_diary.class);
//                    startActivity(intent);
//                }else {
//                    Intent intent=new Intent(getActivity(),new_diary.class);
//                    startActivity(intent);
//                }
                for(int i=0;i<=searchArrayList.size();i++){
                    if(searchArrayList.get(i).gettitle().equals(night_text.getText().toString())==true){
                        position=i;
                        break;
                    }
                }
                //Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), diary_detail.class)
                        .putExtra("position",position));
            }
        });
        final_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(final_text.length()>0){
//                    Intent intent=new Intent(getActivity(),detail_diary.class);
//                    startActivity(intent);
//                }else {
//                    Intent intent=new Intent(getActivity(),new_diary.class);
//                    startActivity(intent);
//                }
                for(int i=0;i<=searchArrayList.size();i++){
                    if(searchArrayList.get(i).gettitle().equals(final_text.getText().toString())==true){
                        position=i;
                        break;
                    }
                }
                //Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), diary_detail.class)
                        .putExtra("position",position));
            }
        });

        addBtn = (FloatingActionButton) view.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), new_diary.class);
                startActivity(intent);
            }
        });
//        addFragment(view);
        return view;
    }

    public void retrieveData() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
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
                                String time_period_id = object.getString("time_period_id");
                                String date = object.getString("date");
                                String title = object.getString("title");
                                String text = object.getString("text");
                                String picture = object.getString("picture");

                                searchDiary = new SearchDiary(id, time_period_id, date, title, text,picture); //將資料庫抓下來的資料存進searchDiary
                                searchArrayList.add(searchDiary); //將searchDiary存進searchArraylist
                                adapter.notifyDataSetChanged();

                                switch (time_period_id) {
                                    case "早餐":
                                        morn_text.setText(title);
                                        break;
                                    case "午餐":
                                        noon_text.setText(title);
                                        break;
                                    case "晚餐":
                                        night_text.setText(title);
                                        break;
                                    case "點心":
                                        final_text.setText(title);
                                        break;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("date", edDate.getText().toString().trim());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
}
