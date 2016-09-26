package com.example.engab.edumeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register_Screen extends AppCompatActivity implements View.OnClickListener{
    private EditText fname, lname, mail, pass;
    private Button register,login;
    private static final String REGISTER_URL = "http://mentorz.info/Edume/public/api/v1/register";
    public static final String KEY_FNAME = "firstname";
    public static final String KEY_LNAME = "lastname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    String s ,massege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__screen);
        fname = (EditText) findViewById(R.id.firstname);
        lname = (EditText) findViewById(R.id.lastname);
        mail = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

    }
    public void registeruser()
    {
        final String FirstName = fname.getText().toString();
        final String LastName = lname.getText().toString();
        final String Email = mail.getText().toString();
        final String Password = pass.getText().toString();
        RequestQueue requestQueue = AppController.getInstance().getRequestQueue();
        StringRequest postRequest = new StringRequest(Request.Method.POST,REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonArray = null;
                        try {
                            jsonArray = new JSONObject(response);
                            s = jsonArray.getString("success");
                            massege =jsonArray.getString("success");
                            openlogin(s);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register_Screen.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams()throws AuthFailureError {
                HashMap map = new HashMap();
                map.put(KEY_FNAME, FirstName);
                map.put(KEY_LNAME, LastName);
                map.put(KEY_EMAIL, Email);
                map.put(KEY_PASSWORD, Password);
                return map;
            }

        };
        requestQueue.add(postRequest);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

    }
    private void openlogin(String s){

        if( s.equals("true")) {
            Toast.makeText(Register_Screen.this, "Done", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Register_Screen.this,Login_Screen.class);
            startActivity(intent);
        }
        else

        {

            Toast.makeText(Register_Screen.this," not registered",Toast.LENGTH_LONG ).show();
        }
    }
    @Override
    public void onClick(View v) {
        if (v == register)
        {
            registeruser();
        }

    }
}

