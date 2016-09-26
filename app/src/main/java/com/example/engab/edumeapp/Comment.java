package com.example.engab.edumeapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Comment extends AppCompatActivity {
    private static final String TAG = Course_Screen.class.getSimpleName();
    private static final String url = "http://mentorz.info/Edume/public/api/v1/getPost";
    private ProgressDialog pDialog;
    private List<coment> coments = new ArrayList<coment>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Comment_Adapter adapter;
    private TextView t1, t2;
    private NetworkImageView pic;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        t1 = (TextView) findViewById(R.id.name);
        t2 = (TextView) findViewById(R.id.content);
        pic = (NetworkImageView) findViewById(R.id.profilePic1);

        recyclerView = (RecyclerView) findViewById(R.id.c_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        coments = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Comment_Adapter(coments, this);
        recyclerView.setAdapter(adapter);

        id = getIntent().getIntExtra("id", 0);
        String name = getIntent().getStringExtra("name");
        String content = getIntent().getStringExtra("content");
        String pic1 = getIntent().getStringExtra("pic");
        t1.setText(name);
        t2.setText(content);
        pic.setImageUrl(pic1, AppController.getInstance().getImageLoader());

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        try {
                            coment coment = new coment();
                            JSONObject obj = new JSONObject(response);
                            JSONArray obj1 = obj.getJSONArray("comments");
                            for (int i = 0; i < obj1.length(); i++) {
                                JSONObject jsonObject = obj1.getJSONObject(i);
                                coment.setContent(jsonObject.getString("content"));
                                JSONObject obj2 = jsonObject.getJSONObject("user");
                                coment.setName(obj2.getString("username"));
                                coment.setThumbnailUrl( obj2.getString("profile_pic"));
                                coments.add(coment);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map = new HashMap();
                map.put("post_id", id + "");
                return map;
            }

        };


        AppController.getInstance().addToRequestQueue(postRequest);


    }
}
