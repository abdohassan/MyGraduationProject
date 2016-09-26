package com.example.engab.edumeapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    private String UPLOAD_URL ="http://mentorz.info/Edume/public//api/v1/updateUserDetails";
    NetworkImageView pict;
    Button c_image,edit;
    EditText editText,editText2,editText3,editText1,editText4;
    private ImageView imageView;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String id = "id";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
         editText = (EditText) findViewById(R.id.editText);
         editText1 = (EditText) findViewById(R.id.editText2);
         editText2 = (EditText) findViewById(R.id.editText3);
         editText3 = (EditText) findViewById(R.id.editText4);
        editText4 =(EditText)findViewById(R.id.editText5);
        imageView  = (ImageView) findViewById(R.id.ppic);
        c_image = (Button) findViewById(R.id.buttonChoose);
         edit = (Button) findViewById(R.id.button7);
        Intent intent = getIntent();
        String t1 = intent.getExtras().getString("firstname");
        String t2 = intent.getExtras().getString("lastname");
        String t3 = intent.getExtras().getString("username");
        String t4 = intent.getExtras().getString("mail");

        editText.setText(t1, TextView.BufferType.EDITABLE);
        editText1.setText(t2, TextView.BufferType.EDITABLE);
        editText2.setText(t3, TextView.BufferType.EDITABLE);
        editText3.setText(t4, TextView.BufferType.EDITABLE);
        c_image.setOnClickListener(this);
        edit.setOnClickListener(this);
    }

        public String getStringImage(Bitmap bmp)
          {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            return encodedImage;
          }
        private void showFileChooser()
        {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //Setting the Bitmap to ImageView
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void upload(){
        SharedPreferences shared =getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String id1 = (shared.getString("uid", ""));
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(EditProfile.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                  //      Toast.makeText(EditProfile.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String image = getStringImage(bitmap);
                String firstname = editText.getText().toString().trim();
                String lastname = editText1.getText().toString().trim();
                String username = editText2.getText().toString().trim();
                String email = editText3.getText().toString().trim();
                String pass = editText4.getText().toString().trim();

                HashMap map = new HashMap();
                map.put("user_id", id1);
                map.put("firstname", firstname);
                map.put("lastname", lastname);
                map.put("email", email);
                map.put("username", username);
                map.put("password", pass);
                map.put("profile_pic", image);
                return map;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
//        Intent intent = new Intent(EditProfile.this,Login_Screen.class);
//        startActivity(intent);

    }




    @Override
    public void onClick(View v)
    {
        if(v == c_image){
            showFileChooser();
        }

        if(v == edit){
            upload();

        }

    }
}
