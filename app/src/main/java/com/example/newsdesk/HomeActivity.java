package com.example.newsdesk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.newsdesk.Fragments.AddArticle;
import com.example.newsdesk.Fragments.Home_Fragment;
import com.example.newsdesk.Fragments.Login_Fragment;
import com.example.newsdesk.Fragments.Profile_Fragment;
import com.example.newsdesk.Fragments.ViewArticles_Fragment;
import com.example.newsdesk.Fragments.topHeadlines;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    SharedPreferences preferences;


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav);

        if(savedInstanceState == null){
            Home_Fragment Home = new Home_Fragment();

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.Main, Home).commit();

        }

        preferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        boolean isUserLoggedIn = preferences.getBoolean("isUserLogin", false);

        if (isUserLoggedIn) {
            navigationView.getMenu().findItem(R.id.AddArticle).setVisible(true);
            navigationView.getMenu().findItem(R.id.ViewArticles).setVisible(true);
            navigationView.getMenu().findItem(R.id.Profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.Login).setVisible(false);
        } else {
            navigationView.getMenu().findItem(R.id.AddArticle).setVisible(false);
            navigationView.getMenu().findItem(R.id.ViewArticles).setVisible(false);
            navigationView.getMenu().findItem(R.id.Profile).setVisible(false);
            navigationView.getMenu().findItem(R.id.Login).setVisible(true);
        }

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.Home:{
                        ChangeFragment(new Home_Fragment());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }

                    case R.id.topHeadlines:{
                        ChangeFragment(new topHeadlines());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }

                    case R.id.ViewArticles:{
                        ChangeFragment(new ViewArticles_Fragment());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }

                    case R.id.Profile:{
                        ChangeFragment(new Profile_Fragment());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case R.id.AddArticle:{
                        ChangeFragment(new AddArticle());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case R.id.Login:{
                        ChangeFragment(new Login_Fragment());
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    }
                    case R.id.Logout:{
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("name");
                        editor.remove("password");
                        editor.remove("email");
                        editor.remove("address");
                        editor.remove("image");
                        editor.remove("title");
                        editor.remove("isUserLogin");
                        editor.apply();
                        Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                        startActivity(intent);
                        break;
                    }

                    case R.id.Exit:{
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                        break;
                    }
                }

                return true;
            }
        });
    }

    public void ChangeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.Main, fragment);
        transaction.commit();
    }
}