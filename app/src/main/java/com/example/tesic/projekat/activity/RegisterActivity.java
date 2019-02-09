package com.example.tesic.projekat.activity;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.tesic.projekat.R;
import com.example.tesic.projekat.volley.VollaySingleton;

import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {



    private EditText mFistNameView;
    private EditText mLastNameView;
    private EditText mUserNameView;
    private EditText mPasswordView;
    private EditText mConfPasswordView;
    private EditText mEmailView;
    private EditText mPhoneView;
    private EditText mAdressView;
    private EditText mAdress2View;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mFistNameView = (EditText) findViewById(R.id.register_first_name);
        mLastNameView = (EditText) findViewById(R.id.register_last_name);
        mUserNameView = (EditText) findViewById(R.id.register_username);
        mConfPasswordView = (EditText) findViewById(R.id.register_conf_password);
        mPhoneView = (EditText) findViewById(R.id.register_phone);
        mAdressView = (EditText) findViewById(R.id.register_address);
        mAdress2View = (EditText) findViewById(R.id.register_address2);


        mEmailView = (EditText) findViewById(R.id.register_email);

        mPasswordView = (EditText) findViewById(R.id.register_password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.register_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                view.playSoundEffect(SoundEffectConstants.CLICK);
                attemptRegister();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLoginFormView = findViewById(R.id.register_form);
    }



    private void attemptRegister() {

        mEmailView.setError(null);
        mPasswordView.setError(null);

        String firstName = mFistNameView.getText().toString();
        String lastName = mLastNameView.getText().toString();
        String username = mUserNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String cpassword = mConfPasswordView.getText().toString();
        String email = mEmailView.getText().toString();
        String phone = mPhoneView.getText().toString();
        String address = mAdressView.getText().toString();
        String address2 = mAdress2View.getText().toString();

        boolean cancel = false;
        View focusView = null;


  if (TextUtils.isEmpty(firstName)) {
            mFistNameView.setError(getString(R.string.error_field_required));
            focusView = mFistNameView;
            cancel = true;
        } else if (!isFirstNameTooShort(firstName)) {
            mFistNameView.setError(getString(R.string.error_first_name_too_short));
            focusView = mFistNameView;
            cancel = true;
        } else if (TextUtils.isEmpty(lastName)) {
            mLastNameView.setError(getString(R.string.error_valid_last_name));
            focusView = mLastNameView;
            cancel = true;
        } else if (!isLastNameTooShort(lastName)) {
            mLastNameView.setError(getString(R.string.error_last_name_too_short));
            focusView = mLastNameView;
            cancel = true;
        }else if (TextUtils.isEmpty(username)) {
            mUserNameView.setError(getString(R.string.error_valid_username));
            focusView = mUserNameView;
            cancel = true;
        } else if (!isUserNameTooShort(username))
        {
            mUserNameView.setError(getString(R.string.error_username_too_short));
            focusView = mUserNameView;
            cancel = true;
        }else if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordTooShort(password)) {
            mPasswordView.setError(getString(R.string.error_password_too_short));
            focusView = mPasswordView;
            cancel = true;
        }else if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailTooShort(email)) {
            mEmailView.setError(getString(R.string.error_email_too_short));
            focusView = mEmailView;
            cancel = true;
        }else if (TextUtils.isEmpty(phone)) {
            mPhoneView.setError(getString(R.string.error_valid_phone_number));
            focusView = mPhoneView;
            cancel = true;
        } else if (!isPhoneTooShort(phone)) {
            mPhoneView.setError(getString(R.string.error_phone_number_too_short));
            focusView = mPhoneView;
            cancel = true;
        }else if (TextUtils.isEmpty(address)) {
            mAdressView.setError(getString(R.string.error_address_number));
            focusView = mAdressView;
            cancel = true;
        } else if (!isAddressTooShort(address)) {
            mAdressView.setError(getString(R.string.error_address_too_short));
            focusView = mAdressView;
            cancel = true;
        }else if (!isPasswordConfirmed(password,cpassword)) {
            mConfPasswordView.setError(getString(R.string.error_confirm_password_dont_match));
            focusView = mConfPasswordView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            JSONObject slanje=new JSONObject();
            try {
                slanje.put("firstName",firstName);
                slanje.put("lastName",lastName);
                slanje.put("userName",username);
                slanje.put("password",password);
                slanje.put("email",email);
                slanje.put("phone",phone);
                slanje.put("street",address);
                slanje.put("street2",address2);

            }catch (Exception e){
                e.printStackTrace();
            }
            Register(slanje);
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

    private void Register(JSONObject slanje){
        JsonObjectRequest jso=new JsonObjectRequest(Request.Method.POST, VollaySingleton.url + "user/register", slanje,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            finish();
                            Intent intent = new Intent(getApplication(), LoginActivity.class);
                            intent.putExtra("email",mEmailView.getText().toString());
                            getApplication().startActivity(intent);
                        }catch (Exception e){
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response=error.networkResponse;
                mEmailView = (AutoCompleteTextView) findViewById(R.id.register_email);
                if(response!=null&&response.data!=null)
                    switch (response.statusCode) {

                       case 409:
                            mEmailView.setError(getString(R.string.error_user_exists));
                            mEmailView.requestFocus();
                            break;

                    }
            }
        });
        VollaySingleton.getInstance(this).getRequestQueue().add(jso);
    }


    private boolean isFirstNameTooShort(String text) {
        return text.length() > 3;
    }

    private boolean isLastNameTooShort(String text) {
        return text.length() > 3;
    }

    private boolean isUserNameTooShort(String text) {
        return text.length() > 5;
    }

    private boolean isPasswordTooShort(String text) {
        return text.length() > 7;
    }

    private boolean isEmailTooShort(String text) {
        return text.length() > 5;
    }
    private boolean isPhoneTooShort(String text) {
        return text.length() > 5;
    }
    private boolean isAddressTooShort(String text) {
        return text.length() > 5;
    }

    private boolean isPasswordConfirmed(String text,String text2) {
        return text.equals(text2);
    }

}

