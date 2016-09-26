package com.example.engab.edumeapp.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.engab.edumeapp.AppController;
import com.example.engab.edumeapp.LessonAdapter;
import com.example.engab.edumeapp.MyCourseAdapter;
import com.example.engab.edumeapp.Post;
import com.example.engab.edumeapp.PostAdapter;
import com.example.engab.edumeapp.R;
import com.example.engab.edumeapp.Tpost;
import com.example.engab.edumeapp.TpostAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by engab on 05-May-16.
 */
public class Timeline extends Fragment {
    private static final String TAG = Timeline.class.getSimpleName();
    private static final String url = "http://mentorz.info/Edume/public/api/v1/myProfile";
    private ProgressDialog pDialog;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter1;
    private List<Tpost> post = new ArrayList<Tpost>();
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String id = "id";

    public Timeline (){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final  View view = inflater.inflate(R.layout.ftimeline, container, false);
        final FragmentActivity c = getActivity();
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView2);
        layoutManager = new LinearLayoutManager(c);
        post = new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        adapter1 = new TpostAdapter(getActivity(), post);
        recyclerView.setAdapter(adapter1);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        SharedPreferences shared = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String id1 = (shared.getString("uid", ""));
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        adapter1.notifyDataSetChanged();
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                Tpost posts = new Tpost();
                                posts.setId(jsonObject.getInt("id"));
                                posts.setName(jsonObject.getString("name"));
                                posts.setContent(jsonObject.getString("content"));
                                posts.setProfilepicture(jsonObject.getString("profile_pic"));
                                post.add(posts);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter1.notifyDataSetChanged();
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
                map.put("user_id", id1);
                return map;
            }

        };

        AppController.getInstance().addToRequestQueue(postRequest);
        return view;
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
}
