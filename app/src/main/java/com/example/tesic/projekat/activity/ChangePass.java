package com.example.tesic.projekat.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.model.UserConnected;
import com.example.tesic.projekat.volley.VollaySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ChangePass extends AppCompatActivity {
   private int num=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change password ");
        final Button changepass = findViewById(R.id.btnChangePass);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.playSoundEffect(SoundEffectConstants.CLICK);
                final EditText oldpassword = findViewById(R.id.edOldPassword);
                final EditText newpassword = findViewById(R.id.edNewPassword);
                final EditText confpassword = findViewById(R.id.edConfPassword);
                if(num==1){

                }
                else if (newpassword.getText().length() < 8) {
                    newpassword.setError(getString(R.string.error_invalid_password));
                    newpassword.requestFocus();
                } else if (!newpassword.getText().toString().equals(confpassword.getText().toString())) {
                    confpassword.setError(getString(R.string.error_confirm_password_dont_match));
                    confpassword.requestFocus();
                } else {

                    JSONObject data = new JSONObject();
                    try {
                        data.put("old", oldpassword.getText().toString());
                        data.put("new", newpassword.getText().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    JsonObjectRequest jso = new JsonObjectRequest(Request.Method.POST, VollaySingleton.url + "user/password", data,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NetworkResponse response = error.networkResponse;
                            EditText old = findViewById(R.id.edOldPassword);
                            if (response != null && response.data != null)
                                switch (response.statusCode) {

                                    case 400:
                                        old.setError(getString(R.string.error_incorrect_password));
                                        old.requestFocus();
                                        break;
                                }
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Authorization", UserConnected.getUser().getToken());
                            return headers;
                        }
                    };
                    VollaySingleton.getInstance(getApplication()).getRequestQueue().add(jso);

                }
            }
        });

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
