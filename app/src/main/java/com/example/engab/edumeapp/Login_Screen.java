package com.example.engab.edumeapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login_Screen extends AppCompatActivity implements View.OnClickListener {
    private EditText email, pass;
    private Button login,reg;
    private static final String LOGIN_URL = "http://mentorz.info/Edume/public/api/v1/login";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    private String mail;
    private String password;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Id = "id";
    String s;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__screen);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        email = (EditText)findViewById(R.id.l_mail);
        pass = (EditText)findViewById(R.id.l_pass);
        login = (Button)findViewById(R.id.login_button);
        login.setOnClickListener(this);
    }
    private void userLogin() {
        mail = email.getText().toString().trim();
        password = pass.getText().toString().trim();
        RequestQueue requestQueue = AppController.getInstance().getRequestQueue();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonArray = null;
                        try {
                            jsonArray = new JSONObject(response);
                            s = jsonArray.getString("success");
                            int id1 = jsonArray.getInt("id");
                            String id = String.valueOf(id1);
                            opentimeline(s);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("uid", id);
                            editor.commit();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                         Toast.makeText(Login_Screen.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap map = new HashMap();
                map.put(KEY_EMAIL,mail);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };
        requestQueue.add(stringRequest);


    }
    private void opentimeline(String s){

        if( s.equals("true")) {
            Intent intent = new Intent(Login_Screen.this, TimeLine.class);
            startActivity(intent);
        }
        else

        {

            Toast.makeText(Login_Screen.this,"mail or password is not correct",Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v== login)
            userLogin();
    }
}
