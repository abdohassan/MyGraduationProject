package com.example.engab.edumeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

public class TimeLine extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = TimeLine.class.getSimpleName();
    private static final String url = "http://mentorz.info/Edume/public/api/v1/posts";
    private ProgressDialog pDialog;
    private List<Post> post = new ArrayList<Post>();
    private ListView listView1;
    private PostAdapter adapter;
    private EditText add_post;
    private TextView name1;
    public NetworkImageView imageView;
    private Button addpost,b1;
    String x,y;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String id = "id";
    public static final String Name = "name";
    public static final String Pic = "pic";
    private TextView name;
    private NetworkImageView pic;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title);
        listView1 = (ListView) findViewById(R.id.post_list);
        adapter = new PostAdapter(this, post);
        listView1.setAdapter(adapter);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        add_post = (EditText) findViewById(R.id.post);
        addpost = (Button) findViewById(R.id.addpost);
        final SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
      final String id1 = (shared.getString("uid", ""));
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Post posts = new Post();
                                posts.setId(jsonObject.getInt("id"));
                                posts.setName(jsonObject.getString("name"));
                                posts.setContent(jsonObject.getString("content"));
                                posts.setProfilepicture(jsonObject.getString("profile_pic"));
                                post.add(posts);
                                String x =jsonObject.getString("name");
                                String y = jsonObject.getString("profile_pic");
                                SharedPreferences.Editor editor = shared.edit();
                                editor.putString(Name, x);
                                editor.putString(Pic, y);
                                editor.commit();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map = new HashMap();
                map.put("user_id",id1);
                return map;
            }

        };


        AppController.getInstance().addToRequestQueue(postRequest);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = LayoutInflater.from(this).inflate(R.layout.nav_header_time_line, navigationView, false);
        name1=(TextView)headerView.findViewById(R.id.textView3);
         pic=(NetworkImageView)headerView.findViewById(R.id.ppic);
         final String x = (shared.getString("name", ""));
         final String y = (shared.getString("pic", ""));
         name1.setText(x);
         pic.setImageUrl(y,AppController.getInstance().getImageLoader());
        navigationView.addHeaderView(headerView);
         navigationView.setNavigationItemSelectedListener(this);


        addpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = "http://mentorz.info/Edume/public/api/v1/add/post";
                final String add = add_post.getText().toString();
                RequestQueue requestQueue = AppController.getInstance().getRequestQueue();
                StringRequest postRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(TimeLine.this, "done", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(getIntent());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(TimeLine.this, error.toString(), Toast.LENGTH_LONG).show();

                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap map = new HashMap();

                        map.put("user_id", id1);
                        map.put("post", add);
                        return map;
                    }

                };
                requestQueue.add(postRequest);
                postRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


            }
        });




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
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.time_line, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            Intent intent = new Intent(TimeLine.this, Profile.class);
            startActivity(intent);

        } else if (id == R.id.massage) {


        } else if (id == R.id.Course) {
            Intent intent = new Intent(TimeLine.this, Course_Screen.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}