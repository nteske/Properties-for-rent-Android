package com.example.tesic.projekat.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.adapter.PropertySettingsAdapter;
import com.example.tesic.projekat.adapter.PropertyViewAdapter;
import com.example.tesic.projekat.model.Property;
import com.example.tesic.projekat.model.UserConnected;
import com.example.tesic.projekat.volley.VollaySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PropertyListActivity extends AppCompatActivity {
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mIds = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initImageBitmaps();

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

    public void create(View v){
        Intent intent = new Intent(getApplication(), CreatePropertyActivity.class);
        getApplication().startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initImageBitmaps();
    }

    public void initImageBitmaps(){

        JsonObjectRequest json=new JsonObjectRequest(Request.Method.GET, VollaySingleton.url+"user/property", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("obj");
                            mNames.clear();
                            mIds.clear();
                            mImageUrls.clear();
                            for(int i=0;i<jsonArray.length();i++) {

                                JSONObject ap = jsonArray.getJSONObject(i);
                                String id=ap.getString("_id");
                                String title=ap.getString("title");
                                String image=ap.getString("image1");
                                Property p=new Property(id,title,image,"",0);
                                mNames.add(p.getTitle());
                                mIds.add(id);
                                mImageUrls.add(VollaySingleton.url+"uploads/properties/"+p.getImage1());
                            }
                            initRecyclerView();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }

        }){@Override
        public Map<String, String> getHeaders() {
            HashMap<String,String> headers=new HashMap<String, String>();
            headers.put("Authorization", UserConnected.getUser().getToken());
            return headers;
        }};
        VollaySingleton.getInstance(this).addToRequestQueue(json);
    }

    private void initRecyclerView(){

        Collections.reverse(mNames);
        Collections.reverse(mImageUrls);
        Collections.reverse(mIds);
        RecyclerView recyclerView = findViewById(R.id.rvMojaLista);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        PropertySettingsAdapter adapter = new PropertySettingsAdapter(this,mIds, mNames, mImageUrls);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
