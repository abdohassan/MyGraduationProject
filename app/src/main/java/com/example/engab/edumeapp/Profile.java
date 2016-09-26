package com.example.engab.edumeapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.engab.edumeapp.profile.CV;
import com.example.engab.edumeapp.profile.Courses;
import com.example.engab.edumeapp.profile.Timeline;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profile extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView name;
    private NetworkImageView pic;

    Button edit;
    private static final String url = "http://mentorz.info/Edume/public/api/v1/userDetails";

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String id = "id";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.title);
        edit = (Button)findViewById(R.id.button5);
        name=(TextView)findViewById(R.id.textView4);
        pic=(NetworkImageView)findViewById(R.id.pic);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        final String x = (shared.getString("name", ""));
        final String y = (shared.getString("pic", ""));
        name.setText(x);
        pic.setImageUrl(y,AppController.getInstance().getImageLoader());
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        final SharedPreferences shared1 = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        final String id1 = (shared.getString("uid", ""));


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try
                                {
                                    JSONObject obj = new JSONObject(response);

                                    String f_name=obj.getString("firstname");
                                    String l_name=obj.getString("lastname");
                                    String u_name= obj.getString("username");
                                    String mail=obj.getString("email");
                                    Intent intent = new Intent(Profile.this,EditProfile.class);
                                    intent.putExtra("firstname", f_name);
                                    intent.putExtra("lastname", l_name);
                                    intent.putExtra("username", u_name);
                                    intent.putExtra("mail", mail);
                                    startActivity(intent);




                                }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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

            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Timeline(), "TIMELINE");
        adapter.addFragment(new CV(), "CV");
        adapter.addFragment(new Courses(), "COURESES");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(Profile.this,TimeLine.class);
        startActivity(intent);

    }
}