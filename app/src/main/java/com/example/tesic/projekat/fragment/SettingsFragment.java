package com.example.tesic.projekat.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.example.tesic.projekat.activity.ChangePass;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.model.UserConnected;
import com.example.tesic.projekat.volley.VollaySingleton;
import com.example.tesic.projekat.volley.VolleyMultipartRequest;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.example.tesic.projekat.volley.VollaySingleton.url;

public class SettingsFragment extends Fragment {
    private static int RESULT_LOAD_IMAGE = 1;
    private NetworkImageView avatar;
    private Bitmap slika;
    private EditText firstName,lastName,userName,email,phone,password;

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity()!=null)getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getActivity()!=null)getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avatar=view.findViewById(R.id.imgPrikaz);
        avatar.setDefaultImageResId(R.drawable.prikaz);
        avatar.setImageUrl(url+"uploads/avatars/"+ UserConnected.getUser().getAvatar(), VollaySingleton.getInstance(getActivity()).getImageLoader());

        firstName=view.findViewById(R.id.etFirstName);
        lastName=view.findViewById(R.id.etLastName);
        userName=view.findViewById(R.id.etUsername);
        email=view.findViewById(R.id.etEmail);
        phone=view.findViewById(R.id.etPhone);
        password=view.findViewById(R.id.etPassword);

        firstName.setText(UserConnected.getUser().getFirstName());
        lastName.setText(UserConnected.getUser().getLastName());
        userName.setText(UserConnected.getUser().getUserName());
        email.setText(UserConnected.getUser().getEmail());
        phone.setText(UserConnected.getUser().getPhone());

        ImageButton camera = view.findViewById(R.id.btnCamera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });
        Button changePass=view.findViewById(R.id.btnChangePass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getContext(), ChangePass.class);
                getActivity().startActivity(in);
            }
        });
        Button save=view.findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolleyMultipartRequest multipartRequest= new VolleyMultipartRequest(Request.Method.POST, VollaySingleton.url+"user/settings", new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        UserConnected.setUser();
                        // parse success output
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
                        JSONObject ob=new JSONObject();
                        try {
                            ob.put("password",password.getText());
                            ob.put("firstName",firstName.getText());
                            ob.put("lastName",lastName.getText());
                            ob.put("userName",userName.getText());
                            ob.put("email",email.getText());
                            ob.put("phone",phone.getText());
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        params.put("thisUser",ob.toString());
                        return params;
                    }

                    @Override
                    protected Map<String, DataPart> getByteData() {
                        Map<String, DataPart> params = new HashMap<>();
                                try {

                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    slika.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);

                                    params.put("avatar", new DataPart("ime.png", byteArrayOutputStream.toByteArray()));
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

                VollaySingleton.getInstance(getActivity()).addToRequestQueue(multipartRequest);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==RESULT_LOAD_IMAGE){
                Uri image=data.getData();
                try {
                    slika = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), image);
                }catch (Exception e){
                    e.printStackTrace();
                }
                avatar.setImageURI(image);

            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings,null);
    }
}
