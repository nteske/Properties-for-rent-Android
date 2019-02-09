package com.example.tesic.projekat.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.model.Property;
import com.example.tesic.projekat.model.UserConnected;
import com.example.tesic.projekat.volley.CircularNetworkImageView;
import com.example.tesic.projekat.volley.VollaySingleton;
import com.example.tesic.projekat.volley.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.example.tesic.projekat.volley.VollaySingleton.url;

public class CreatePropertyActivity extends AppCompatActivity {

    NetworkImageView img1,img2,img3,img4;
    private Bitmap slika1,slika2,slika3,slika4;
    private boolean bwifi,bhdtv,bbBed,bsBed,bair,bfridge,bstove,bwash,bbath,bmicroWave,blandline,bfire;
    private ImageView wifi,hdtv,bBed,sBed,air,fridge,stove,wash,bath,microWave,landline,fire;
    private String id;
    int called=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_property);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        id= intent.getStringExtra("id");
        if(id!=null)setData();
        img1=findViewById(R.id.crtImg1);
        img2=findViewById(R.id.crtImg2);
        img3=findViewById(R.id.crtImg3);
        img4=findViewById(R.id.crtImg4);
        img1.setDefaultImageResId(R.drawable.ic_camera);
        img2.setDefaultImageResId(R.drawable.ic_camera);
        img3.setDefaultImageResId(R.drawable.ic_camera);
        img4.setDefaultImageResId(R.drawable.ic_camera);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CreatePropertyActivity.this.requestPermissions(new String[]{Manifest.permission_group.STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},300);
        }
        final int flags=Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.playSoundEffect(SoundEffectConstants.CLICK);
                called=1;

                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivityForResult(i,1);

            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.playSoundEffect(SoundEffectConstants.CLICK);
                called=2;
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,2);
            }
        });
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.playSoundEffect(SoundEffectConstants.CLICK);
                called=3;
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,3);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.playSoundEffect(SoundEffectConstants.CLICK);
                called=4;
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,4);
            }
        });
         wifi=findViewById(R.id.ivWifi);
         hdtv=findViewById(R.id.ivHDTV);
         bBed=findViewById(R.id.ivBBed);
         sBed=findViewById(R.id.ivSBed);

         air=findViewById(R.id.ivAir);
         fridge=findViewById(R.id.ivFridge);
         stove=findViewById(R.id.ivStove);
         wash=findViewById(R.id.ivWash);

         bath=findViewById(R.id.ivBath);
         microWave=findViewById(R.id.ivMicro);
         landline=findViewById(R.id.ivLandLine);
         fire=findViewById(R.id.ivFirePlace);

        final Button save=findViewById(R.id.create_property);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.playSoundEffect(SoundEffectConstants.CLICK);
                EditText crtTitle=findViewById(R.id.create_title);
                EditText crtDeposit=findViewById(R.id.create_deposit);
                EditText crtRooms=findViewById(R.id.create_rooms);
                EditText crtArea=findViewById(R.id.create_area);
                EditText crtStreet=findViewById(R.id.create_street);
                int deposit=0,rooms=0,area=0;
                try {
                     deposit = Integer.parseInt(crtDeposit.getText().toString());
                     rooms = Integer.parseInt(crtRooms.getText().toString());
                     area = Integer.parseInt(crtArea.getText().toString());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
                if(crtTitle.getText().toString().length()<3||crtTitle.getText().toString().isEmpty()){
                    crtTitle.setError("Title is too short!");
                    crtTitle.requestFocus();
                } else if (deposit < 50||deposit>1000) {
                    crtDeposit.setError("Deposit must be between 50 and 1000!");
                    crtDeposit.requestFocus();
                }else if (rooms < 1||rooms>10) {
                    crtRooms.setError("Rooms must be between 1 and 10!");
                    crtRooms.requestFocus();

                }else if (area < 10||area>1000) {
                    crtArea.setError("Area must be between 10 and 1000!");
                    crtArea.requestFocus();

                }else if(crtStreet.getText().toString().length()<3||crtStreet.getText().toString().isEmpty()){
                    crtStreet.setError("Street name is too short!");
                    crtStreet.requestFocus();
                }
                else {save.setEnabled(false);saveProperty();}
            }
        });

        wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bwifi){
                    wifi.setAlpha((float)0.3);
                    bwifi=false;
                }else{
                    wifi.setAlpha((float)0.9);
                    bwifi=true;
                }
            }
        });
        hdtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bhdtv){
                    hdtv.setAlpha((float)0.3);
                    bhdtv=false;
                }else{
                    hdtv.setAlpha((float)0.9);
                    bhdtv=true;
                }
            }
        });
        bBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bbBed){
                    bBed.setAlpha((float)0.3);
                    bbBed=false;
                }else{
                    bBed.setAlpha((float)0.9);
                    bbBed=true;
                }
            }
        });
        sBed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bsBed){
                    sBed.setAlpha((float)0.3);
                    bsBed=false;
                }else{
                    sBed.setAlpha((float)0.9);
                    bsBed=true;
                }
            }
        });
        air.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bair){
                    air.setAlpha((float)0.3);
                    bair=false;
                }else{
                    air.setAlpha((float)0.9);
                    bair=true;
                }
            }
        });
        stove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bstove){
                    stove.setAlpha((float)0.3);
                    bstove=false;
                }else{
                    stove.setAlpha((float)0.9);
                    bstove=true;
                }
            }
        });
        fridge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bfridge){
                    fridge.setAlpha((float)0.3);
                    bfridge=false;
                }else{
                    fridge.setAlpha((float)0.9);
                    bfridge=true;
                }
            }
        });
        wash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bwash){
                    wash.setAlpha((float)0.3);
                    bwash=false;
                }else{
                    wash.setAlpha((float)0.9);
                    bwash=true;
                }
            }
        });
        bath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bbath){
                    bath.setAlpha((float)0.3);
                    bbath=false;
                }else{
                    bath.setAlpha((float)0.9);
                    bbath=true;
                }
            }
        });
        microWave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bmicroWave){
                    microWave.setAlpha((float)0.3);
                    bmicroWave=false;
                }else{
                    microWave.setAlpha((float)0.9);
                    bmicroWave=true;
                }
            }
        });
        landline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(blandline){
                    landline.setAlpha((float)0.3);
                    blandline=false;
                }else{
                    landline.setAlpha((float)0.9);
                    blandline=true;
                }
            }
        });
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bfire){
                    fire.setAlpha((float)0.3);
                    bfire=false;
                }else{
                    fire.setAlpha((float)0.9);
                    bfire=true;
                }
            }
        });
    }
    public void setIcons(Property property){
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

        if(property.getInternet()){wifi.setAlpha(0.9f);bwifi=true;}
        if(property.getCableTV()){hdtv.setAlpha(0.9f);bhdtv=true;}
        if(property.getbBed()){bBed.setAlpha(0.9f);bbBed=true;}
        if(property.getsBed()){sBed.setAlpha(0.9f);bsBed=true;}

        if(property.getConditioner()){air.setAlpha(0.9f);bair=true;}
        if(property.getFridge()){fridge.setAlpha(0.9f);bfridge=true;}
        if(property.getStove()){stove.setAlpha(0.9f);bstove=true;}
        if(property.getWasher()){wash.setAlpha(0.9f);bwash=true;}

        if(property.getBathub()){bath.setAlpha(0.9f);bbath=true;}
        if(property.getMicrowave()){microWave.setAlpha(0.9f);bmicroWave=true;}
        if(property.getLandline()){landline.setAlpha(0.9f);blandline=true;}
        if(property.getFireplace()){fire.setAlpha(0.9f);bfire=true;}
    }
    public void loadProperty(Property property){
        EditText txtStreet=findViewById(R.id.create_street);
        txtStreet.setText(property.getStreet());
        EditText txtArea=findViewById(R.id.create_area);
        txtArea.setText(String.valueOf(property.getArea()));
        EditText txtRooms=findViewById(R.id.create_rooms);
        txtRooms.setText(String.valueOf(property.getRooms()));
        EditText title=findViewById(R.id.create_title);
        title.setText(String.valueOf(property.getTitle()));
        setIcons(property);

        EditText txtDeposit=findViewById(R.id.create_deposit);
        txtDeposit.setText(String.valueOf(property.getDeposit()));
        Log.i("urlovi",property.getImage1()+" "+property.getImage2()+" "+property.getImage3()+" "+property.getImage4());
        if(property.getImage1()!="no") {
            img1.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage1(), VollaySingleton.getInstance(this).getImageLoader());
        }
        if(property.getImage2()!="no") {
            img2.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage2(), VollaySingleton.getInstance(this).getImageLoader());

        }
        if(property.getImage3()!="no") {
            img3.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage3(), VollaySingleton.getInstance(this).getImageLoader());

        }
        if(property.getImage4()!="no") {
            img4.setImageUrl(VollaySingleton.url + "uploads/properties/" + property.getImage4(), VollaySingleton.getInstance(this).getImageLoader());

        }
    }
    private void setData(){
        JsonObjectRequest req=new JsonObjectRequest(Request.Method.GET, Property.url + "view/"+id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject prop=response.getJSONObject("prop");
                            Property property=new Property(prop);
                            loadProperty(property);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        VollaySingleton.getInstance(this).addToRequestQueue(req);
    }
    private void saveProperty() {
        String adresa="add";
        int rm= Request.Method.POST;
        if(id==null){adresa="add";rm= Request.Method.POST;}
        else {adresa="edit/"+id;rm= Request.Method.PATCH;}
        VolleyMultipartRequest multipartRequest= new VolleyMultipartRequest(rm, Property.url + adresa,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                                finish();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                JSONObject obj=new JSONObject();
                EditText crtTitle=findViewById(R.id.create_title);
                EditText crtDeposit=findViewById(R.id.create_deposit);
                EditText crtRooms=findViewById(R.id.create_rooms);
                EditText crtArea=findViewById(R.id.create_area);
                EditText crtStreet=findViewById(R.id.create_street);
                try {
                    obj.put("title",crtTitle.getText());
                    obj.put("deposit",crtDeposit.getText());
                    obj.put("rooms",crtRooms.getText());
                    obj.put("area",crtArea.getText());
                    obj.put("street",crtStreet.getText());

                    obj.put("internet",bwifi);
                    obj.put("cableTV",bhdtv);
                    obj.put("bBed",bbBed);
                    obj.put("sBed",bsBed);
                    obj.put("conditioner",bair);
                    obj.put("fridge",bfridge);
                    obj.put("stove",bstove);
                    obj.put("washer",bwash);
                    obj.put("bathub",bbath);
                    obj.put("microwave",bmicroWave);
                    obj.put("landline",blandline);
                    obj.put("fireplace",bfire);
                }catch (Exception e){
                    e.printStackTrace();

                }
                params.put("thisProp",obj.toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                try {
                    ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
                    ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream();
                        if(slika1!=null){
                            slika1.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream1);
                        }
                    if(slika2!=null){
                        slika2.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream2);
                    }
                    if(slika3!=null){
                        slika3.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream3);
                    }
                    if(slika4!=null){
                        slika4.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream4);
                    }
                    byte[] lol= addAll(byteArrayOutputStream1.toByteArray(),byteArrayOutputStream2.toByteArray());
                    byte[] lol2= addAll(lol,byteArrayOutputStream3.toByteArray());
                    byte[] lol3= addAll(lol2,byteArrayOutputStream4.toByteArray());
                    params.put("photo", new DataPart("ime3.png", lol3));
                    Log.i("podaci",params.toString());


                }catch (Exception e){
                    e.printStackTrace();
                }

                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String,String> headers=new HashMap<String, String>();
                headers.put("Authorization", UserConnected.getUser().getToken());
                return headers;
            }
        };
        VollaySingleton.getInstance(this).addToRequestQueue(multipartRequest);
    }
    public static byte[] addAll(final byte[] array1,byte[] array2){
        byte[] joinedArray= Arrays.copyOf(array1,array1.length+array2.length);
        System.arraycopy(array2,0,joinedArray,array1.length,array2.length);
        return  joinedArray;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode>=1){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    CreatePropertyActivity.this.requestPermissions(new String[]{Manifest.permission_group.STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},400);
                }
                Uri image=data.getData();
                this.grantUriPermission(getPackageName(),image,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                NetworkImageView avatar=null;
                if(called!=0) {
                    if(called==1){avatar=img1;}
                    else if(called==2){avatar=img2;}
                    else if(called==3){avatar=img3;}
                    else if(called==4){avatar=img4;}
                    try {
                        if(called==1)slika1 =MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), image);
                        else if(called==2)slika2 =MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), image);
                        else if(called==3)slika3 =MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), image);
                        else if(called==4)slika4 =MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), image);
                        avatar.setImageUrl(UserConnected.getUser().getAvatar(),VollaySingleton.getInstance(this).getImageLoader());
                        avatar.setDefaultImageResId(0);


                        avatar.setImageURI(image);
                        //avatar.setImageUrl(url+"uploads/avatars/"+UserConnected.getUser().getAvatar(), VollaySingleton.getInstance(this).getImageLoader());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    called=0;
                }
            }
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

}
