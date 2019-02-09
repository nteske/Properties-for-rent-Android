package com.example.tesic.projekat.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.model.UserConnected;
import com.example.tesic.projekat.volley.VollaySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String username=intent.getStringExtra("username");

        JsonObjectRequest req= new JsonObjectRequest(Request.Method.GET, VollaySingleton.url+"user/profile/"+username,null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            NetworkImageView avatar=findViewById(R.id.accImg);
                            avatar.setDefaultImageResId(R.drawable.prikaz);
                            TextView usernaem=findViewById(R.id.txtUsernamePod);
                            TextView ime=findViewById(R.id.txtImePrzPod);
                            TextView email=findViewById(R.id.txtEmailPod);
                            TextView phone=findViewById(R.id.txtPhonePodaci);
                            TextView street=findViewById(R.id.txtStreetPod);
                            TextView street2=findViewById(R.id.txtStreet2Pod);

                            JSONObject korisnik = response.getJSONObject("user");
                            ime.setText(korisnik.getString("lastName")+" "+korisnik.getString("firstName"));
                            usernaem.setText(korisnik.getString("userName"));
                            email.setText(korisnik.getString("email"));
                            phone.setText(korisnik.getString("phone"));
                            street.setText(korisnik.getString("street"));
                            street2.setText(korisnik.getString("street2"));
                            avatar.setImageUrl(VollaySingleton.url+"uploads/avatars/"+ korisnik.getString("avatar"),VollaySingleton.getInstance(getApplicationContext()).getImageLoader());

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers=new HashMap<String, String>();
                headers.put("Authorization",UserConnected.getUser().getToken());
                return headers;
            }
        };
        VollaySingleton.getInstance(this).getRequestQueue().add(req);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
