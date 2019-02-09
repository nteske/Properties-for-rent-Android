package com.example.tesic.projekat.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tesic.projekat.model.Property;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.model.UserConnected;
import com.example.tesic.projekat.volley.AnimatedNetworkImageView;
import com.example.tesic.projekat.volley.CircularNetworkImageView;
import com.example.tesic.projekat.volley.VollaySingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class PreviewActivity extends AppCompatActivity {
    private String id;
    private AnimatedNetworkImageView iv;
    private Property property;
    private int onPosition=1;
    private String userIme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        iv=findViewById(R.id.ivImageShow);
        Intent intent = getIntent();
        id= intent.getStringExtra("id");
        String name = intent.getStringExtra("name");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        CircularNetworkImageView userImages=findViewById(R.id.ivImageUser);

        userImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.playSoundEffect(SoundEffectConstants.CLICK);
                Intent intent = new Intent(getApplication(), UserProfile.class);
                intent.putExtra("username",userIme );
                getApplication().startActivity(intent);
            }
        });
        if(!UserConnected.connected)userImages.setVisibility(View.GONE);
        JsonObjectRequest req=new JsonObjectRequest(Request.Method.GET, Property.url + "view/"+id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject prop=response.getJSONObject("prop");
                            property=new Property(prop);
                            loadProperty();
                            JSONObject user=response.getJSONObject("user");
                            CircularNetworkImageView userImage=findViewById(R.id.ivImageUser);
                            userImage.setImageUrl(VollaySingleton.url+"uploads/avatars/"+ user.getString("avatar"),VollaySingleton.getInstance(getApplicationContext()).getImageLoader());
                            userIme=user.getString("userName");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
        });
        VollaySingleton.getInstance(this).addToRequestQueue(req);

    }
    public void setIcons(){
        ImageView wifi=findViewById(R.id.ivWifi);
        ImageView hdtv=findViewById(R.id.ivHDTV);
        ImageView bBed=findViewById(R.id.ivBBed);
        ImageView sBed=findViewById(R.id.ivSBed);

        ImageView air=findViewById(R.id.ivAir);
        ImageView fridge=findViewById(R.id.ivFridge);
        ImageView stove=findViewById(R.id.ivStove);
        ImageView wash=findViewById(R.id.ivWash);

        ImageView bath=findViewById(R.id.ivBath);
        ImageView microWave=findViewById(R.id.ivMicro);
        ImageView landline=findViewById(R.id.ivLandLine);
        ImageView fire=findViewById(R.id.ivFirePlace);
        Float hide=0.3f;
        if(!property.getInternet())wifi.setAlpha(hide);
        if(!property.getCableTV())hdtv.setAlpha(hide);
        if(!property.getbBed())bBed.setAlpha(hide);
        if(!property.getsBed())sBed.setAlpha(hide);

        if(!property.getConditioner())air.setAlpha(hide);
        if(!property.getFridge())fridge.setAlpha(hide);
        if(!property.getStove())stove.setAlpha(hide);
        if(!property.getWasher())wash.setAlpha(hide);

        if(!property.getBathub())bath.setAlpha(hide);
        if(!property.getMicrowave())microWave.setAlpha(hide);
        if(!property.getLandline())landline.setAlpha(hide);
        if(!property.getFireplace())fire.setAlpha(hide);
    }
    public void loadProperty(){
        TextView txtStreet=findViewById(R.id.txtStreetName);
        txtStreet.setText(property.getStreet());
        TextView txtArea=findViewById(R.id.txtAreaNum);
        txtArea.setText(String.valueOf(property.getArea()));
        TextView txtRooms=findViewById(R.id.txtRoomsNum);
        txtRooms.setText(String.valueOf(property.getRooms()));
        setIcons();

        TextView txtDeposit=findViewById(R.id.txtDepositNum);
        txtDeposit.setText(String.valueOf(property.getDeposit())+"$");
        if(property.getImage1()!="no") {
            iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage1(), VollaySingleton.getInstance(this).getImageLoader());
            onPosition=1;
        }
        else  if(property.getImage2()!="no") {
            iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage2(), VollaySingleton.getInstance(this).getImageLoader());
            onPosition=2;
        }
        else  if(property.getImage3()!="no") {
            iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage3(), VollaySingleton.getInstance(this).getImageLoader());
            onPosition=3;
        }
        else  if(property.getImage4()!="no") {
            iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage4(), VollaySingleton.getInstance(this).getImageLoader());
            onPosition=4;
        }else {
            iv.setImageUrl(VollaySingleton.url + "uploads/properties/no", VollaySingleton.getInstance(this).getImageLoader());
            onPosition=0;
            ImageButton btnLeft = findViewById(R.id.btnLeft);
            btnLeft.setVisibility(View.INVISIBLE);
            ImageButton btnRight = findViewById(R.id.btnRight);
            btnRight.setVisibility(View.INVISIBLE);
        }
    }
    public void moveImageTo(int br){
        if(br==1)
            if(property.getImage1()!="no") {
                iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage1(), VollaySingleton.getInstance(this).getImageLoader());
                onPosition=1;
                return;
            }else moveImageTo(4);

        else if(br==2)
            if(property.getImage2()!="no") {
                iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage2(), VollaySingleton.getInstance(this).getImageLoader());
                onPosition=2;
                return;
            }else moveImageTo(1);
        else if(br==3)
            if(property.getImage3()!="no") {
                iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage3(), VollaySingleton.getInstance(this).getImageLoader());
                onPosition=3;
                return;
            }else moveImageTo(2);
        else if(br==4)
            if(property.getImage4()!="no") {
                iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage4(), VollaySingleton.getInstance(this).getImageLoader());
                onPosition=4;
                return;
            }else moveImageTo(3);
    }

    public void moveImageToRight(int br){
        if(br==1)
            if(property.getImage1()!="no") {
                iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage1(), VollaySingleton.getInstance(this).getImageLoader());
                onPosition=1;
                return;
            }else moveImageTo(2);

        else if(br==2)
            if(property.getImage2()!="no") {
                iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage2(), VollaySingleton.getInstance(this).getImageLoader());
                onPosition=2;
                return;
            }else moveImageTo(3);
        else if(br==3)
            if(property.getImage3()!="no") {
                iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage3(), VollaySingleton.getInstance(this).getImageLoader());
                onPosition=3;
                return;
            }else moveImageTo(4);
        else if(br==4)
            if(property.getImage4()!="no") {
                iv.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage4(), VollaySingleton.getInstance(this).getImageLoader());
                onPosition=4;
                return;
            }else moveImageTo(1);
    }
    public void goLeft(View v){
        v.playSoundEffect(SoundEffectConstants.NAVIGATION_LEFT);
        if(onPosition!=0){
            if(onPosition!=1)
            moveImageTo(onPosition-1);
            else moveImageTo(4);
        }
    }
    public void goRight(View v){
        v.playSoundEffect(SoundEffectConstants.NAVIGATION_RIGHT);
        if(onPosition!=0){
            if(onPosition!=4)
                moveImageToRight(onPosition+1);
            else moveImageToRight(1);
        }
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
