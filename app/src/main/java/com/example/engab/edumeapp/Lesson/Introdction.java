package com.example.engab.edumeapp.Lesson;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.engab.edumeapp.AppController;
import com.example.engab.edumeapp.Lesson1;
import com.example.engab.edumeapp.LessonAdapter;
import com.example.engab.edumeapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by engab on 09-May-16.
 */
public class Introdction  extends Fragment {
    private static final String TAG = Introdction.class.getSimpleName();
    private static final String url = "http://mentorz.info/Edume/public/api/v1/course";
    private static final String url1 = "http://mentorz.info/Edume/public/api/v1/joinCourse";
    private ProgressDialog pDialog;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String id = "id";
    public TextView c_name,c_discription,n_inst;
    public Button join;

    public NetworkImageView p_course,p_inst;

    public Introdction (){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.cintro, container, false);
        c_name=(TextView)view.findViewById(R.id.textView8);
        c_discription=(TextView)view.findViewById(R.id.textView);
        n_inst=(TextView)view.findViewById(R.id.textView6);
        p_course=(NetworkImageView)view.findViewById(R.id.c_iamge);
        p_inst=(NetworkImageView)view.findViewById(R.id.imageView4);
        join=(Button)view.findViewById(R.id.button6);
        SharedPreferences shared =this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String id2 = (shared.getString("cid", ""));
        final String id1 = (shared.getString("uid", ""));





        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response.toString());
                        hidePDialog();
                        try
                        {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            String course_name=jsonObject.getString("title");
                            String course_pic= jsonObject.getString("thumbnail");
                            String course_dis=jsonObject.getString("description");
                            String inst_name= jsonObject.getString("inestructor_name");
                            String inst_pic= jsonObject.getString("inestructor_profile_pic");
                            c_name.setText( course_name);
                            c_discription.setText(course_dis);
                            n_inst.setText(inst_name);
                            p_course.setImageUrl(course_pic,AppController.getInstance().getImageLoader());
                            p_inst.setImageUrl(inst_pic,AppController.getInstance().getImageLoader());

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest postRequest = new StringRequest(Request.Method.POST,url1,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                VolleyLog.d(TAG, "Error: " + error.getMessage());
                                hidePDialog();                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams()throws AuthFailureError {
                        HashMap map = new HashMap();
                        map.put("course_id", id2);
                        map.put("user_id", id1);


                        return map;
                    }

                };
                AppController.getInstance().addToRequestQueue(postRequest);

            }
        });


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