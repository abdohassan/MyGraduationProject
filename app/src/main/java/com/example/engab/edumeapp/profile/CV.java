package com.example.engab.edumeapp.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.engab.edumeapp.AppController;
import com.example.engab.edumeapp.Course;
import com.example.engab.edumeapp.Education;
import com.example.engab.edumeapp.EducationAdapter;
import com.example.engab.edumeapp.Experiences;
import com.example.engab.edumeapp.ExperiencesAdapter;
import com.example.engab.edumeapp.InterestAdapter;
import com.example.engab.edumeapp.Interests;
import com.example.engab.edumeapp.MyCourse;
import com.example.engab.edumeapp.ProjectAdapter;
import com.example.engab.edumeapp.Projects;
import com.example.engab.edumeapp.R;
import com.example.engab.edumeapp.Skills;
import com.example.engab.edumeapp.SkillsAdapter;
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
public class CV  extends Fragment {
    private static final String TAG = CV.class.getSimpleName();
    private static final String url = "http://mentorz.info/Edume/public/api/v1/cv";
    private ProgressDialog pDialog;
    private List<Interests> interest =new ArrayList<Interests>();
    private List<Skills> skills =new ArrayList<Skills>();
    private List<Projects> project =new ArrayList<Projects>();
    private List<Education> educations =new ArrayList<Education>();
    private List<Experiences> exp =new ArrayList<Experiences>();
    private RecyclerView recyclerView,recyclerView1,recyclerView2,recyclerView3,recyclerView4;
    private RecyclerView.LayoutManager layoutManager,layoutManager2,layoutManager3,layoutManager4,layoutManager1;
    private RecyclerView.Adapter adapter,adapter1, adapter2, adapter3, adapter4;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String id = "id";
    public CV (){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fcv, container, false);
        final FragmentActivity c = getActivity();
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView3);
        recyclerView1 = (RecyclerView)view.findViewById(R.id.recyclerView4);
        recyclerView2 = (RecyclerView)view.findViewById(R.id.recyclerView5);
        recyclerView3 = (RecyclerView)view.findViewById(R.id.recyclerView6);
        recyclerView4 = (RecyclerView)view.findViewById(R.id.recyclerView7);
        layoutManager = new LinearLayoutManager(c);
        layoutManager1 = new LinearLayoutManager(c);
        layoutManager2 = new LinearLayoutManager(c);
        layoutManager3 = new LinearLayoutManager(c);
        layoutManager4 = new LinearLayoutManager(c);
        skills = new ArrayList<>();
        interest = new ArrayList<>();
        project=new ArrayList<>();
        educations=new ArrayList<>();
        exp=new ArrayList<>();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView3.setLayoutManager(layoutManager3);
        recyclerView4.setLayoutManager(layoutManager4);
        adapter = new SkillsAdapter(getActivity(), skills);
        adapter1 = new InterestAdapter(getActivity(), interest);
        adapter2=new ProjectAdapter(getActivity(),project);
        adapter3=new EducationAdapter(getActivity(),educations);
        adapter4=new ExperiencesAdapter(getActivity(),exp);
        recyclerView.setAdapter(adapter3);
        recyclerView1.setAdapter(adapter4);
        recyclerView2.setAdapter(adapter);
        recyclerView3.setAdapter(adapter1);
        recyclerView4.setAdapter(adapter2);
        pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        pDialog.show();
        SharedPreferences shared = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String id1 = (shared.getString("uid", ""));
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.d(TAG, response.toString());
                        hidePDialog();
                        try {

                            JSONObject obj = new JSONObject(response);
                            JSONArray obj5 = obj.getJSONArray("projects");
                            for(int i=0;i<obj5.length();i++)
                            {
                                JSONObject jsonObject = obj5.getJSONObject(i);
                                Projects projects=new Projects();
                                projects.setTitle(jsonObject.getString("title"));
                                projects.setDescription(jsonObject.getString("description"));
                                project.add(projects);
                            }
                         JSONArray obj1 = obj.getJSONArray("skills");
                            for(int i=0;i<obj1.length();i++)
                            {
                                JSONObject jsonObject = obj1.getJSONObject(i);
                                Skills skill=new Skills();
                                skill.setSkill(jsonObject.getString("skill"));
                                skills.add(skill);
                            }
                            JSONArray obj2 = obj.getJSONArray("interests");
                            for(int i=0;i<obj2.length();i++)
                            {
                                JSONObject jsonObject = obj2.getJSONObject(i);
                                Interests in=new Interests();
                                in.setInterest(jsonObject.getString("interest"));
                                interest.add(in);
                            }

                            JSONArray obj3 = obj.getJSONArray("educations");
                            for(int i=0;i<obj3.length();i++)
                            {
                                JSONObject jsonObject = obj3.getJSONObject(i);
                                Education ed=new Education();
                                ed.setS_name(jsonObject.getString("school_name"));
                                ed.setDescription(jsonObject.getString("description"));
                                educations.add(ed);


                            }
                            JSONArray obj4 = obj.getJSONArray("experiences");
                            for(int i=0;i<obj4.length();i++)
                            {
                                JSONObject jsonObject = obj4.getJSONObject(i);
                                Experiences ex =new Experiences();
                                ex.setCompany_name(jsonObject.getString("company_name"));
                                ex.setDiscription(jsonObject.getString("description"));
                                ex.setTitle(jsonObject.getString("title"));
                                exp.add(ex);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }





                        adapter.notifyDataSetChanged();
                        adapter1.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();
                        adapter3.notifyDataSetChanged();
                        adapter4.notifyDataSetChanged();
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
