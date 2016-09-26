package com.example.engab.edumeapp.Lesson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.engab.edumeapp.AppController;
import com.example.engab.edumeapp.Lesson1;
import com.example.engab.edumeapp.LessonAdapter;
import com.example.engab.edumeapp.Post;
import com.example.engab.edumeapp.PostAdapter;
import com.example.engab.edumeapp.R;


/**
 * Created by engab on 09-May-16.
 */
public class Lecture extends Fragment {
    private static final String TAG = Lecture.class.getSimpleName();
    private static final String url = "http://mentorz.info/Edume/public/api/v1/lecture";
    private List<Lesson1> lessons = new ArrayList<Lesson1>();
    private ListView listView1;
    private ProgressDialog pDialog;
    private LessonAdapter adapter;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String id = "id";


    public Lecture (){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.lesson, container, false);
        listView1 = (ListView)view.findViewById(R.id.lessson_list);
        adapter = new LessonAdapter(getActivity(),lessons);
        listView1.setAdapter(adapter);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        SharedPreferences shared =this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String id2 = (shared.getString("cid", ""));



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

                                Lesson1 lesson1 = new Lesson1();
                                lesson1.setId(jsonObject.getInt("id"));
                                lesson1.setCourse_name(jsonObject.getString("title"));
                                lesson1.setUrl(jsonObject.getString("url"));

                                lessons.add(lesson1);
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
                map.put("course_id",id2);
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