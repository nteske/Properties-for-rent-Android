package com.example.tesic.projekat.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.volley.CircularNetworkImageView;
import com.example.tesic.projekat.volley.VollaySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class UserConnected {
    private static User user;
    public static Context ctx;
    public static Activity act;
    public static boolean connected=false;
    public static void setUser()
    {
        SharedPreferences preferences =ctx.getSharedPreferences("PropertyPref", MODE_PRIVATE);
        String token=preferences.getString("token","");
        getUser().setToken(token);
        JsonObjectRequest req= new JsonObjectRequest(Request.Method.GET, VollaySingleton.url+"user/settings",null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject korisnik = response.getJSONObject("user");
                            getUser().setId(korisnik.getString("id"));
                            getUser().setFirstName(korisnik.getString("name"));
                            getUser().setLastName(korisnik.getString("last"));
                            getUser().setUserName(korisnik.getString("userName"));
                            getUser().setEmail(korisnik.getString("email"));
                            getUser().setPhone(korisnik.getString("phone"));
                            getUser().setStreet(korisnik.getString("street"));
                            getUser().setStreet2(korisnik.getString("street2"));
                            getUser().setAvatar(korisnik.getString("avatar"));
                            getUser().setAdmin(Integer.parseInt(korisnik.getString("admin")));
                            connected=true;
                            changeNav();
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        connected=false;
                        changeNav();
                        error.printStackTrace();
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> headers=new HashMap<String, String>();
                headers.put("Authorization",getUser().getToken());
                return headers;
            }
        };
        VollaySingleton.getInstance(ctx).getRequestQueue().add(req);

    }
    public static User getUser()
    {
        if(user==null)user=new User();
        return user;
    }
    public static void logOut(){
        connected=false;
        user=new User();
        SharedPreferences preferences = ctx.getSharedPreferences("PropertyPref", MODE_PRIVATE);
        preferences.edit().putString("token", null).commit();
        changeNav();
    }
    public static void changeNav() {
        TextView name = act.findViewById(R.id.headName);
        TextView email = act.findViewById(R.id.headEmail);
        CircularNetworkImageView avatar=act.findViewById(R.id.headIm);
        NavigationView navigationView = (NavigationView) act.findViewById(R.id.nav_view);
        if (UserConnected.connected) {
            name.setText(UserConnected.getUser().getUserName());
            email.setText(UserConnected.getUser().getEmail());
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_register).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_account).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_settings).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_property).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(true);
            avatar.setDefaultImageResId(R.drawable.prikaz);
            avatar.setImageUrl(VollaySingleton.url+"uploads/avatars/"+getUser().getAvatar(),VollaySingleton.getInstance(ctx).getImageLoader());

        } else {
            name.setText("");
            email.setText("");
            navigationView.getMenu().findItem(R.id.nav_login).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_register).setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_account).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_settings).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_property).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout).setVisible(false);
            avatar.setDefaultImageResId(0);
            avatar.setImageUrl(null,null);
        }
    }
}
