package com.example.engab.edumeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Course_Screen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = Course_Screen.class.getSimpleName();
    private static final String url = "http://mentorz.info/Edume/public/api/v1/courses";
    private ProgressDialog pDialog;
    private List<Course> courses =new ArrayList<Course>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String CId = "id";
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course__screen);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        courses = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CourseAdapter(courses,new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(Course_Screen.this,Lesson_Screen.class);
                startActivity(intent);
                int x = position + 1 ;
                String id = String.valueOf(x);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("cid", id);
                editor.commit();
            }
        });
        recyclerView.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        JsonArrayRequest cou = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();

                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Course course = new Course();
                                course.setId(obj.getInt("id"));
                                course.setTitle(obj.getString("title"));
                                course.setThumbnailUrl(obj.getString("thumbnail"));
                                course.setDescription(obj.getString("description"));
                                course.setnInstractor(obj.getString("inestructor_name"));
                                course.setpInstractor(obj.getString("inestructor_profile_pic"));
                                courses.add(course);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(cou);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Course_Screen.this,TimeLine.class);
        startActivity(intent);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      //  Toast.makeText(getApplicationContext(), "You clicked on position : " + position + " and id : " + id, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Course_Screen.this,Lesson_Screen.class);
        intent.putExtra("position",position);
        startActivity(intent);


    }
}
