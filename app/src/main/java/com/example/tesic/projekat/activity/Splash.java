package com.example.tesic.projekat.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.volley.VollaySingleton;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.top));
        }
        Thread welcome=new Thread(){
            @Override
            public void run() {
                try {
                    super.run();
                    JsonObjectRequest jso=new JsonObjectRequest(Request.Method.GET, VollaySingleton.url, null,
                            null,
                            null);
                    VollaySingleton.getInstance(getApplication()).getRequestQueue().add(jso);
                    sleep(10000);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    Intent i=new Intent(Splash.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcome.start();

    }
}
