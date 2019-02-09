package com.example.tesic.projekat.activity;

import android.content.Context;
import android.content.Intent;
import android.media.midi.MidiInputPort;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.tesic.projekat.R;
import com.example.tesic.projekat.model.UserConnected;
import com.example.tesic.projekat.fragment.HomeFragment;
import com.example.tesic.projekat.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                InputMethodManager inputMethodManager=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
            }
        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //<!-- app:headerLayout="@layout/nav_header_main"!-->
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_home));

        UserConnected.getUser();
        UserConnected.act=this;
        UserConnected.ctx=this;
        UserConnected.setUser();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment=null;
        int id = item.getItemId();

        if(id==R.id.nav_home)
        {
            fragment=new HomeFragment();
        }
        else if (id == R.id.nav_login) {
           /* fragment=new LoginFragment();*/
            Intent intent = new Intent(this, LoginActivity.class);
            this.startActivity(intent);

        } else if (id == R.id.nav_register) {
           // fragment = new RegisterFragment();
            Intent intent = new Intent(this, RegisterActivity.class);
            this.startActivity(intent);
        } else if (id == R.id.nav_share) {
            try {
                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Property rent");
                String shareMessage="\nLet me recommend you this application\n\n";
                shareMessage=shareMessage+"https://www.propertyrent.herokuapp.com";
                shareIntent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                startActivity(Intent.createChooser(shareIntent,"choose one"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }else if (id == R.id.nav_account) {
            Intent intent = new Intent(this, UserProfile.class);
            intent.putExtra("username",UserConnected.getUser().getUserName());
            this.startActivity(intent);
        }
        else if (id == R.id.nav_logout) {
            UserConnected.logOut();
        }else if(id==R.id.nav_settings)
        {
            fragment=new SettingsFragment();
        }else if(id==R.id.nav_property){
            Intent intent = new Intent(this, PropertyListActivity.class);
            this.startActivity(intent);
        }
        if(fragment!=null){
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();

            ft.replace(R.id.screen_area, fragment);

            ft.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
