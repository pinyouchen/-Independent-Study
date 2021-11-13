package com.example.home.homeFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.home.EditProfile;
import com.example.home.GetRecipeAdapter;
import com.example.home.NotiActivity;
import com.example.home.R;
import com.example.home.SearchLikeRecipe;
import com.example.home.SearchRecipePost;
import com.example.home.likeRecipeAdapter;
import com.example.home.like_recipe_list;
import com.example.home.post_recipe_list;
import com.example.home.recipe.GetRecipe;
import com.example.home.recipePostAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_profile extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ProgressDialog progressDialog;

    private List<GetRecipe> arrayList;
    private RecyclerView recyclerView;
    private GetRecipeAdapter myAdapter;

    SearchLikeRecipe searchLikeRecipe;
    likeRecipeAdapter adapter1;
    public static java.util.ArrayList<SearchLikeRecipe> ArrayList1 = new ArrayList<>();


    SearchRecipePost searchRecipePost;
    recipePostAdapter adapter;
    public static java.util.ArrayList<SearchRecipePost> ArrayList = new ArrayList<>();
    String url = "https://louis32132118.000webhostapp.com/getUser_recipe.php";
    String urlProfile = "https://louis32132118.000webhostapp.com/getName.php";
    String urlLike = "https://louis32132118.000webhostapp.com/getRecipe_like.php";

    /*user_id =3*/
    Button btn_up;
    Button btn_noti;
    TextView post_view,like_view,full_name,second_name;
    ImageView user_view;
    int recipe_total=0,user_id=90,recipe_like =0;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_profile.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_profile newInstance(String param1, String param2) {
        fragment_profile fragment = new fragment_profile();
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
        getName();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.dismiss();

        //字型設定
        Button button1 = (Button) view.findViewById(R.id.btnNoti);
        Button button2 = (Button) view.findViewById(R.id.btnUp);
        Button button3 = (Button) view.findViewById(R.id.btnAot);
        Button button4 = (Button) view.findViewById(R.id.btnUse);
        Button button5 = (Button) view.findViewById(R.id.btnOut);

        full_name = (TextView) view.findViewById(R.id.full_name);
        second_name = (TextView) view.findViewById(R.id.second_name);
        like_view = (TextView) view.findViewById(R.id.booking_label);
        TextView textView2 = (TextView) view.findViewById(R.id.payment_desc);

//        Typeface type = Typeface.createFromAsset(view.getContext().getAssets(), "setofont.ttf");
//        full_name.setTypeface(type);textView2.setTypeface(type);//like_view.setTypeface(type);
//        button1.setTypeface(type);button2.setTypeface(type);button3.setTypeface(type);
//        button4.setTypeface(type);button5.setTypeface(type);

        ImageView user_view = (ImageView) view.findViewById(R.id.profile_image);
        //getName();

        adapter1 = new likeRecipeAdapter(getActivity(),ArrayList1);
        adapter = new recipePostAdapter(getActivity(), ArrayList); //add a new adapter
        /*進入此fragment介面後要建立查詢該user_id的個人食譜發布量及愛心收藏數*/
        retrievePost();
        //getLike();

        arrayList=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        getLikeData();
        btn_up = view.findViewById(R.id.btnUp);
        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), EditProfile.class); startActivity(intent);
            }
        });

        btn_noti = view.findViewById(R.id.btnNoti);
        btn_noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), NotiActivity.class); startActivity(intent);
            }
        });

        post_view = view.findViewById(R.id.post_view);
        post_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), post_recipe_list.class)
                        .putExtra("position",recipe_total));
            }
        });

        like_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), like_recipe_list.class)
                        .putExtra("position",recipe_like));
            }
        });
        return view;
    }

    private void getLikeData() {
        String url="https://louis32132118.000webhostapp.com/test2.php";
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    ArrayList1.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String sucess = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("user");

                        if (sucess.equals("1")) {
                            for (int i = 0; i < jsonArray.length(); i++) { //取得資料庫的筆數
                                JSONObject object = jsonArray.getJSONObject(i);

                                String id = object.getString("id");
                                String recipe_name = object.getString("recipename");
                                String user_name = object.getString("user_name");
                                String time = object.getString("time");
                                String category = object.getString("category");
                                String cooking = object.getString("cooking");
                                String cookingid = object.getString("cookingid");
                                String people = object.getString("people");
                                String inter = object.getString("inter");
                                String intro = object.getString("intro");
                                String cookingtime = object.getString("cookingtime");
                                String picture = object.getString("picture");
                                String likes = object.getString("likes");
                                String privacy = object.getString("privacy");

                                recipe_like=i+1;
                                searchLikeRecipe = new SearchLikeRecipe(id, recipe_name, user_name,time,category,cooking,cookingid,people,
                                        inter,intro,cookingtime,picture,likes,privacy);

                                //將資料庫抓下來的資料存進searchRecipePost
                                ArrayList1.add(searchLikeRecipe); //將searchDiary存進ArrayList
                                adapter1.notifyDataSetChanged();
                            }
                            like_view.setText(String.valueOf(recipe_like));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("userID", String.valueOf(user_id));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

//    private void getLike() {
//        StringRequest request = new StringRequest(Request.Method.POST, urlLike,
//                response -> {
//                    try {
//                        JSONObject jsonObject = new JSONObject(response);
//                        String sucess = jsonObject.getString("success");
//                        JSONArray jsonArray = jsonObject.getJSONArray("user");
//
//                        if (sucess.equals("1")) {
//                            for (int i = 0; i < jsonArray.length(); i++) { //取得資料庫的筆數
//                                JSONObject object = jsonArray.getJSONObject(i);
//
//                                String id = object.getString("id");
//                                String recipe_id = object.getString("recipe_id");
//
//                                recipe_like=i+1;
//                                //Glide.with(getContext()).load(user_pic).into(user_view);
//                            }
//                            like_view.setText(String.valueOf(recipe_like));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("user_id", String.valueOf(user_id));
//                return params;
//            }
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(request);
//
//    }
    public void retrievePost() {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    ArrayList.clear();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String sucess = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("user");

                        if (sucess.equals("1")) {
                            for (int i = 0; i < jsonArray.length(); i++) { //取得資料庫的筆數
                                JSONObject object = jsonArray.getJSONObject(i);

                                String id = object.getString("id");
                                String user_id = object.getString("userid");
                                String category_id = object.getString("category_id");
                                String cooking_id = object.getString("cooking_id");
                                String recipe_name = object.getString("recipe_name");
                                String quantity = object.getString("quantity");
                                String release_time = object.getString("release_time");
                                String international = object.getString("international");
                                String vegetarian = object.getString("vegetarian");
                                String degree = object.getString("degree");
                                String introduction = object.getString("introduction");
                                String cooking_time = object.getString("cooking_time");
                                String likes = object.getString("likes");
                                String privacy = object.getString("privacy");

                                recipe_total=i+1;
                                searchRecipePost = new SearchRecipePost(id, user_id, category_id, cooking_id, recipe_name, quantity,
                                        release_time, international, vegetarian, degree, introduction, cooking_time, likes, privacy);
                                //將資料庫抓下來的資料存進searchRecipePost
                                ArrayList.add(searchRecipePost); //將searchDiary存進ArrayList
                                adapter.notifyDataSetChanged();
                            }
                            post_view.setText(String.valueOf(recipe_total));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("user_id", String.valueOf(user_id));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }
    private void getName() {
        StringRequest request = new StringRequest(Request.Method.POST, urlProfile,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String sucess = jsonObject.getString("success");
                        JSONArray jsonArray = jsonObject.getJSONArray("user");

                        if (sucess.equals("1")) {
                            for (int i = 0; i < jsonArray.length(); i++) { //取得資料庫的筆數
                                JSONObject object = jsonArray.getJSONObject(i);

                                String name = object.getString("name");
                                String email = object.getString("email");
                                String user_pic = object.getString("user_pic");

                                full_name.setText(name);
                                second_name.setText(email);
                                //Glide.with(getContext()).load(user_pic).into(user_view);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(user_id));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

}
