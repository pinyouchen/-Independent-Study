package com.example.home.homeFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.home.CreateRecipeActivity;
import com.example.home.recipe.GetRecipe;
import com.example.home.GetRecipeAdapter;
import com.example.home.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_recipe#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class fragment_recipe extends Fragment {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private GetRecipeAdapter myAdapter;
    private FloatingActionButton floatingActionButton;
    private MaterialSearchView searchView;
    private List<GetRecipe> arrayList;
    private ProgressDialog progressDialog;
    FragmentTransaction transaction;
    Fragment fragment;
    Boolean like_true=false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_recipe.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_recipe newInstance(String param1, String param2) {
        fragment_recipe fragment = new fragment_recipe();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_recipe() {
        // Required empty public constructor
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


        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        toolbar=view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        searchView=view.findViewById(R.id.serach_view);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Recipe");

        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        arrayList=new ArrayList<>();
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        floatingActionButton=view.findViewById(R.id.FAB_creat);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateRecipeActivity.class));
            }
        });
        getData();

        return view;
    }


    private void getData() {
        String url="https://louis32132118.000webhostapp.com/getRecipe.php";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        GetRecipe recipe = new GetRecipe(
                                jsonObject.getString("id"),
                                jsonObject.getString("recipename"),
                                jsonObject.getString("username"),
                                jsonObject.getString("time"),
                                jsonObject.getString("category"),
                                jsonObject.getString("cooking"),
                                jsonObject.getString("cookingid"),
                                jsonObject.getString("inter"),
                                jsonObject.getString("intro"),
                                jsonObject.getString("people"),
                                jsonObject.getString("cookingtime"),
                                jsonObject.getString("picture"),
                                jsonObject.getString("likes")
                        );
                        arrayList.add(recipe);
                    }
                    myAdapter=new GetRecipeAdapter(getActivity(),arrayList);
                    myAdapter.setHasStableIds(true);
                    recyclerView.setAdapter(myAdapter);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        int socketTime=7000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(retryPolicy);
        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }

}
