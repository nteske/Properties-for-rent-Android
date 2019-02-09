package com.example.tesic.projekat.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tesic.projekat.model.Property;
import com.example.tesic.projekat.adapter.PropertyViewAdapter;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.volley.VollaySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HomeFragment extends Fragment {

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mIds = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private ArrayList<String> mStreet = new ArrayList<>();
    private ArrayList<String> mDeposit = new ArrayList<>();

    private View view;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        initImageBitmaps();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,null);
    }


    private void initImageBitmaps(){

            JsonObjectRequest json=new JsonObjectRequest(Request.Method.GET, Property.url + "all", null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray jsonArray = null;
                            try {
                                jsonArray = response.getJSONArray("obj");
                                for(int i=0;i<jsonArray.length();i++) {

                                    JSONObject ap = jsonArray.getJSONObject(i);
                                    String id=ap.getString("_id");
                                    String title=ap.getString("title");
                                    String image=ap.getString("image1");
                                    String street=ap.getString("street");
                                    int deposit=Integer.parseInt(ap.getString("deposit"));
                                    Property p=new Property(id,title,image,street,deposit);
                                    mNames.add(p.getTitle());
                                    mIds.add(id);
                                    mImageUrls.add(VollaySingleton.url+"uploads/properties/"+p.getImage1());
                                    mStreet.add(p.getStreet());
                                    mDeposit.add(String.valueOf(p.getDeposit())+'€');
                                }
                                initRecyclerView();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            VollaySingleton.getInstance(getActivity()).addToRequestQueue(json);
    }

    private void initRecyclerView(){

            Collections.reverse(mNames);
            Collections.reverse(mImageUrls);
            Collections.reverse(mStreet);
            Collections.reverse(mDeposit);
             Collections.reverse(mIds);
            RecyclerView recyclerView = view.findViewById(R.id.property_list);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            PropertyViewAdapter adapter = new PropertyViewAdapter(getActivity(),mIds, mNames, mImageUrls, mStreet, mDeposit);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
